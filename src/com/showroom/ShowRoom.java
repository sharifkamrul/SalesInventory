package com.showroom;

import com.commonfiles.MySQLAccess;
import com.commonfiles.Product;

import java.sql.*;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ShowRoom extends Product {
	
	private MySQLAccess dbAccess = new MySQLAccess();
	private Connection conn;
	private Statement stmt;
	private String sql ;
	private ResultSet rs = null;
	
	private String[]column = new String[9];
	
	private String sRoomName;
	
	private String show_room_products = "show_room_products";
	private String showroom_stockout_info = "showroom_stockout_info";
 
	//	Add product into show_room_products table
	public void prodCollect(String prodName, String prodCode, String barCode, 
			String brandName, double purchaseRate, double sellingRate, int quantity) {
	
		try {
			conn = dbAccess.connectToDataBase();
			stmt = conn.createStatement();
			
			//	insert product detail in show_room_products table 
			sql = dbAccess.insertToCurrentStock(show_room_products, prodName, prodCode, barCode, brandName, purchaseRate, sellingRate, quantity);
			stmt.executeUpdate(sql);
			
			JOptionPane.showMessageDialog(null,"Product detail has been added to ShowRoom");
			
		}catch(Exception e) {
		      //Handle errors for Class.forName
		      e.printStackTrace();
		   }finally {
		      //finally block used to close resources
			  try {
				   if(stmt != null) { conn.close(); }
		      }catch(SQLException se) { }
		      try {
		         if(conn != null) { conn.close(); }
		      }catch(SQLException se) { se.printStackTrace(); }
		   }//end finally try
		
	}
	
	//SHOW CURRENT STOCK/STOCK OUT INFO IN A SHOWROOM
	public Object[][] showCurrentStock(String ac) {
		
		Object [][] data = null;
		
		try {
			conn = dbAccess.connectToDataBase();
			stmt = conn.createStatement();
			
			/** Getting total number of rows to initialize an object data array 
			 * to be sent to the view
			 */
			column[0] = "count(*) as totalRow";// Get total number of rows in the table
			
			if(ac.equals(Product.AC_CURRENTSTOCK)) {
				column[1] = "sum(quantity) as totalNumOfProd";// Get total number of products
				sql = dbAccess.selectAll(show_room_products, null, column, null);
			}
			else if(ac.equals(Product.AC_STOCKOUTINFO)) {
				sql = dbAccess.selectAll(show_room_products, showroom_stockout_info, column, "prodCode");
			}
			else {}
			
			rs = stmt.executeQuery(sql);
			
			int totalRows = 0;
			int totalNumOfProd = 0;
			
			while(rs.next()) {
			       //Retrieve by column name
				totalRows  = rs.getInt("totalRow");
				if(ac.equals(Product.AC_CURRENTSTOCK)) {
					totalNumOfProd = rs.getInt("totalNumOfProd");
				}
			}
			rs.close();
			/** ***************** **/
			
			
			if(ac.equals(Product.AC_CURRENTSTOCK)) {
				// Generate an array and get all product info from show_room_products
				sql = dbAccess.selectAll(show_room_products, null, null, null);
			}
			else if(ac.equals(Product.AC_STOCKOUTINFO)) {
				column[0] = show_room_products+".prodName";
				column[1] = show_room_products+".prodCode";
				column[2] = show_room_products+".brandName";
				column[3] = showroom_stockout_info+".purchaseRate";
				column[4] = showroom_stockout_info+".sellingRate";
				column[5] = showroom_stockout_info+".quantity";
				column[6] = showroom_stockout_info+".Date";
				
				// Generate an array and get all product info from show_room_products joining showroom_stockout_info
				sql = dbAccess.selectAll(show_room_products, showroom_stockout_info, column, "prodCode");
			}
			//System.out.println("Sql "+sql);
			rs = stmt.executeQuery(sql);
			
			int j = 0;

//			using totalRows to instantiate and declare size of Object data array 
			data = new Object[totalRows][8];
			
			while(rs.next()){
				data[j][0] = rs.getString("prodName");
				data[j][1] = rs.getString("prodCode");
				data[j][2] = rs.getString("brandName");
				data[j][3] = rs.getDouble("purchaseRate");
				data[j][4] = rs.getDouble("sellingRate");
				data[j][5] = rs.getInt("quantity");
				data[j][6] = rs.getString("Date");
				
				j++;
				 
			}
			rs.close();
			
			// sending total number of products through this index
			if(ac.equals(Product.AC_CURRENTSTOCK)) {
				data[0 ][7] = totalNumOfProd;
			}
			
		}catch(Exception e) {
		      //Handle errors for Class.forName
		      e.printStackTrace();
		   }finally {
		      //finally block used to close resources
			   try {
				   if (rs != null) { rs.close(); }
			   }catch (Exception e) { }
			   try {
				   if(stmt != null) { conn.close(); }
		      }catch(SQLException se) { }
		      try {
		         if(conn != null) { conn.close(); }
		      }catch(SQLException se) { se.printStackTrace(); }
		   }//end finally try
		   
		   return data;
	}
	
	public void sellProduct(String prodCode, int stockOutQuantity, String sRoomName) {
		try {
			conn = dbAccess.connectToDataBase();
			stmt = conn.createStatement();
			
			
			// Get current state of this product from show_room_products 
			int curQuantity = 0;	// variable to hold the current stocked quantity
			double purchaseRate = 0.0;
			double sellingRate = 0.0;
			column[0] = "quantity";
			column[1] = "purchaseRate";
			column[2] = "sellingRate";
			sql = dbAccess.selectAll(show_room_products, null, column, null);
			sql += " where prodCode='"+prodCode+"'";//System.out.println(sql);
			rs = stmt.executeQuery(sql);
			while(rs.next()){
			       //Retrieve by column name
			       curQuantity  = rs.getInt("quantity");
			       purchaseRate = rs.getDouble("purchaseRate");
			       sellingRate = rs.getDouble("sellingRate");
			}
			rs.close();
			
			//	update product detail in show_room_products table 
			int new_quantity = curQuantity-stockOutQuantity;
			sql = dbAccess.updateCurrentStock(show_room_products, "quantity", new_quantity);
			sql+= " where prodCode='"+prodCode+"'";
			//System.out.println(sql);
			stmt.executeUpdate(sql);
			
			//	insert relevant info in showroom_stockout_info table to keep stock out info*/
			sql = dbAccess.insertToStockOUTInfo(showroom_stockout_info, prodCode, purchaseRate, sellingRate, stockOutQuantity, sRoomName);
			//System.out.println(sql);
			stmt.executeUpdate(sql);
			//System.out.println("Product sto");
			
		} catch(Exception e) {
		      //Handle errors for Class.forName
		      e.printStackTrace();
		   }finally {
		      //finally block used to close resources
			   try {
				   if (rs != null) { rs.close(); }
			   }catch (Exception e) { }
			   try {
				   if(stmt != null) { conn.close(); }
		      }catch(SQLException se) { }
		      try {
		         if(conn != null) { conn.close(); }
		      }catch(SQLException se) { se.printStackTrace(); }
		   }//end finally try
		
	}
	
	
}

