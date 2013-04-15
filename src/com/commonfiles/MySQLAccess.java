package com.commonfiles;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MySQLAccess {
	
	//  Database credentials
	private final String DATABASENAME = "gdlmediasport_fraser2";
	private final String USER = "fraser";
	private final String PASS = "B$z32$fr";
	
	// JDBC driver name and database URL
	private final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	private final String DB_URL = "jdbc:mysql://localhost/"+DATABASENAME;


	private String sql;
	
	//	Get current date 
	Date d = new Date();
	String strDateFormat = "yyyy-MM-dd";//Date format is Specified
	SimpleDateFormat dSDF = new SimpleDateFormat (strDateFormat);//Date format string is passed as an argument to the Date format object
	String curDate = dSDF.format(d);//Date formatting is applied to the current date
	
  
	public Connection connectToDataBase() throws Exception {
    
		try {
			// This will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// Setup the connection with the DB
      
			Connection conn;
			return conn = DriverManager.getConnection(DB_URL, USER, PASS);
			/* return connect = DriverManager
          			.getConnection("jdbc:mysql://localhost/"+DatabaseName+"?"
              		+ "user="+user+"&password="+password);*/
		}catch (Exception e) {throw e;} 
	}
	
	/*sql for insert into  tables
	 * show_room_products
	 * ware_house_products
	 */
	public String insertToCurrentStock(String tableName, String prodName, String prodCode, String barCode,
			String brandName, double purchaseRate, double sellingRate, int quantity) {
		
		sql = "insert into "+tableName+" values(NULL,'"+prodName+"','"+prodCode+"','"+
		barCode+"','"+brandName+"',"+purchaseRate+","+sellingRate+","+quantity+",'"+this.curDate+"')";
		System.out.println(sql);
		return sql;
	}
	
	/*sql for insert into  
	 * showroom_stockout_info
	 * warehouse_stockout_info
	 */
	public String insertToStockOUTInfo(String tableName, String  prodCode, double purchaseRate, double sellingRate, int stockOutQuantity, 
			String sRoomName) {
		
		sql = "insert into "+tableName+" values(NULL,'"+prodCode+"',"+
		purchaseRate+","+sellingRate+","+stockOutQuantity+",null,'"+this.curDate+"')";
		//System.out.println(sql);
		return sql;
	}
	
	public String updateSCurrentStock(String tableName, String field, String value) {
		sql = "update "+tableName+" set "+field+"='"+value+"'";
		return sql;
	}
	
	public String updateCurrentStock(String tableName, String field, int value) {
		sql = "update "+tableName+" set "+field+"="+value;
		return sql;
	}
	
	public String selectAll(String tableName1, String tableName2, String[]column, String conditionField) {
		sql = "select ";
		
		// Get column names if sent
		if(column != null) {
			for(int i = 0; i<column.length; i++) {
				if(column[i] == null) {	break;}
				sql += column[i];
				if(column[i+1] != null) {
					sql += ", ";
				}
			
			}
		}else {// if no column name specified, select all columns
			sql+= "* ";
		}
		sql += " from "+tableName1;
		
		if(tableName2 != null) {
			sql+= " inner join "+tableName2+" where "
				+tableName1+"."+conditionField
				+" = "
				+tableName2+"."+conditionField;
		}
		//System.out.println("Sql "+sql);		
		
		return sql;
	}
   
   
  /*// You need to close the resultSet
  private void close() {
    try {
      if (resultSet != null) {
        resultSet.close();
      }

      if (statement != null) {
        statement.close();
      }

      if (connect != null) {
        connect.close();
      }
    } catch (Exception e) {

    }
  }*/

} 
