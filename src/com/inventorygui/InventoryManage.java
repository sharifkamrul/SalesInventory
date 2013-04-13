package com.inventorygui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.commonfiles.Product;
import com.wearhouse.WearHouse;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.StringTokenizer;

public class InventoryManage  extends JFrame implements ActionListener {

	/**
	 * @param args
	 */
	
	// STATIC INITIALIZATION TO GET SCREEN DIMENSION
	static Dimension screeDimension = Toolkit.getDefaultToolkit().getScreenSize();
	static int screenHight = screeDimension.height;
	static int screenWidth = screeDimension.width;
	
	
	
	// BUTTON TO PROMPT FOR STOCKING OUT PRODUCTS FROM WEAR HOUSE
	JButton stckOut = new JButton("stock out product");	
	// BUTTON TO PROMPT FOR PRODUCT PURCHASE FOR WEAR HOUSE
	JButton prdprchs = new JButton("Product Purchase");
	
	//LABELS,TEXTFIELDS,BUTTONS FOR WEAR HOUSE PRODUCT PURCHASE FORM
	JLabel lProductName = new JLabel("Product Name");
	JTextField tProductName = new JTextField(4);
  
	JLabel lProductCode = new JLabel("Product Code");
	JTextField tProductCode =new JTextField(20);
  
	JLabel lBrandName = new JLabel("Brand Name");
	JTextField tBrandName =new JTextField(20);
  
	JLabel lBarCode = new JLabel("Bar Code");
	JTextField tBarCode =new JTextField(20);
  
	JLabel lPurchaseRate = new JLabel("Purchase Rate");
	JTextField tPurchaseRate =new JTextField(20);
  
	JLabel lSellingRate = new JLabel("Selling Rate");
	JTextField tSellingRate =new JTextField(20);
  
	JLabel lquantity = new JLabel("Quantity");
	JTextField tquantity =new JTextField(20);
	
	JButton wprdprchsfrmsbmt;
	
	// JTable CLASS
	JTable table;
	DefaultTableModel dModel;
	DefaultTableModel dm;
	
	// FRAME CLASS
	JFrame frame;
	
	//PANEL CLASS
	
	
	WearHouse wearHouse = new WearHouse();
	
//	public InventoryManage() {
//		super("Inventory Management");
//		addWindowListener(new WindowAdapter() {
//			public void windowClosing(WindowEvent e) {
//				//dispose();
//				System.exit(0);
//		}
//		});
//		
//	}
	 
	
	public void actionPerformed(ActionEvent e) {
		
		InventoryManage service = new InventoryManage();
				
		
				
		if(e.getSource() == stckOut) { // product stock out
			service.prdStockOut();
						
		}
		
		if(e.getSource() == prdprchs) { // product purchase form
			service.prodPurchaseForm();
			
		}
		
		if(e.getSource() == wprdprchsfrmsbmt) { // purchase form submission
			
			//GET INPUT VALUES
			String prodName = tProductName.getText();
			String prodCode = tProductCode.getText();
			String brandName = tBrandName.getText();
			String barCode = tBarCode.getText();
			double purchaseRate = Integer.parseInt(tPurchaseRate.getText());
			double sellingRate = Integer.parseInt(tSellingRate.getText());
			int quantity = Integer.parseInt(tquantity.getText());
			
			wearHouse.prodPurchase(prodName, prodCode, barCode, brandName, purchaseRate, sellingRate, quantity);
			
		}
	};
		
	
	// GENERATE TABLE TO SHOW CURRENT STOCK IN WEAR HOUSE
	public void createStockTable(String ac) {
		String title = null;
		
		Object [] column = {"Product Name", "Product Code", "Brand Name", 
				 "Purchase Rate", "Selling Rate", "Quantity", "Date"};//create column for JTable
		Object [][] data = null;
		
		if(ac.equals(Product.AC_CURRENTSTOCK)) {
			title = "Wear House Stock";
			data = wearHouse.showCurrentStock(Product.AC_CURRENTSTOCK);// get dynamic data for JTable
		}
		else if (ac.equals(Product.AC_STOCKOUTINFO)) {
			title = "Wear House Stock Out Info";
			data = wearHouse.showCurrentStock(Product.AC_STOCKOUTINFO);// get dynamic data for JTable

		}	
		
		frame = new JFrame(title);
		
		dModel = new DefaultTableModel(data,column);
		table = new JTable(dModel);
		table.setPreferredScrollableViewportSize(new Dimension(700,600));//set scrollable view
		table.setPreferredSize(new Dimension(700,700));//set table size
		
		JScrollPane scrollPane = new JScrollPane(table);
		
		JPanel wStockPanel = new JPanel();//hold the table
		JPanel totalStockPanel = new JPanel();//hold total stock
		JPanel servicePanel = new JPanel();//hold stock out,purchase of product 
											//interactive interface
		
		wStockPanel.add(scrollPane);
		
		if(ac.equals(Product.AC_CURRENTSTOCK)) {
			totalStockPanel.setLayout(new GridLayout(1, 2));
			totalStockPanel.add(new JLabel("Total Stocked Product"));
			String totalNumOfProd = data[0][7]+"";
			totalStockPanel.add(new JLabel(totalNumOfProd));
			
			servicePanel.setLayout(new FlowLayout());
			servicePanel.add(stckOut);	stckOut.addActionListener(this);
			servicePanel.add(prdprchs);	prdprchs.addActionListener(this);
		}
		
		 			 
		frame.setLayout(new GridLayout(3,1));
		frame.add(wStockPanel);
		frame.add(totalStockPanel);
		frame.add(servicePanel);
 			
		frame.setSize(850,600);
		Dimension frameSize = frame.getSize();
		int frameHeight = (int)frameSize.getHeight();
		int frameWidth = (int)frameSize.getWidth();
		frame.setLocation((screenHight-frameHeight)/2, (screenWidth-frameWidth)/2);
		frame.setVisible(true);
		//frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		//this.setBounds(100,100,200, 200);
		//pack();
		
	}
	
	// GENERATE FORM TO PURCHASE PRODUCT FOR WEAR HOUSE
	public void prodPurchaseForm() {
		frame = new JFrame("Product Purchase Form");
		
		frame.setSize(400,300);
		Dimension frameSize = frame.getSize();
		int frameHeight = (int)frameSize.getHeight();
		int frameWidth = (int)frameSize.getWidth();
		frame.setLocation((screenHight-frameHeight)/2, (screenWidth-frameWidth)/2);
		frame.setVisible(true);
		/*frame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);
				}
			});*/
	  
		Panel frmPanel = new Panel();
		frmPanel.setLayout(new GridLayout(0,2));
		
		frmPanel.add(lProductName);
		frmPanel.add(tProductName);
		
		frmPanel.add(lProductCode);
		frmPanel.add(tProductCode);
		
		frmPanel.add(lBrandName);
		frmPanel.add(tBrandName);
		
		frmPanel.add(lBarCode);
		frmPanel.add(tBarCode);
		
		frmPanel.add(lPurchaseRate);
		frmPanel.add(tPurchaseRate);
		
		frmPanel.add(lSellingRate);
		frmPanel.add(tSellingRate);
		
		frmPanel.add(lquantity);
		frmPanel.add(tquantity);
		
		wprdprchsfrmsbmt=new JButton("Submit");
		frmPanel.add(wprdprchsfrmsbmt);	wprdprchsfrmsbmt.addActionListener(this);
	  
		frame.add(frmPanel,BorderLayout.NORTH);
	}
	
	// FUNCTION FOR STOCKING OUT PRODUCT FROM WEAR HOUSE TO A SHOW ROOM
	public void prdStockOut() {
		
		String []prodInfo = null;
		
		String inputString = JOptionPane.showInputDialog(null,"Enter product Code, number of product to stock out and showroom name" +
				" e.g (45677,2,showroom1)");
		
		if( (inputString.isEmpty() == false) ) {
			StringTokenizer tokens = new StringTokenizer(inputString, ",");
			if(tokens.countTokens() != 0) {
				prodInfo = new String[3];
				int i = 0;
				
				//GET TOKENIZED VALUES
				while(tokens.hasMoreTokens()) {
					if( i == 3) {// won't accept input if user inputs more
						break;
					}
					prodInfo[i] = tokens.nextToken();// PRODUCT INFO
					i++;
				}
			}
		}
					
		if(prodInfo != null) {
			wearHouse.stockOut(prodInfo[0], Integer.parseInt(prodInfo[1]), prodInfo[2]);
		}	
	}
}
