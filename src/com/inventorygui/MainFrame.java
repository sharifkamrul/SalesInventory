package com.inventorygui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.commonfiles.Product;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class MainFrame extends JFrame implements ActionListener {

	// STATIC INITIALIZATION TO GET SCREEN DIMENSION
	static Dimension screeDimension = Toolkit.getDefaultToolkit().getScreenSize();
	static int screenHight = screeDimension.height;
	static int screenWidth = screeDimension.width;
	
	// MENU BUTTONS
	JButton ws = new JButton("WearHouse Stock");
	JButton wsout = new JButton("WearHouse Stock Out Info");
	JButton prdprchs = new JButton("Product Purchase for WearHouse");
	JButton ss = new JButton("Showroom Stock");
	JButton ssout = new JButton("Showroom Stock Out Info");
	JButton prdclct = new JButton("Product Collect in ShowRoom");
	JButton report = new JButton("Show Report");
	
	public void menu() {
		Container con = getContentPane();
		con.setLayout(new GridLayout(5,1));
		
		con.add(ws);	ws.addActionListener(this);
		con.add(wsout);	wsout.addActionListener(this);
		con.add(prdprchs); prdprchs.addActionListener(this);
		con.add(ss);	ss.addActionListener(this);
		con.add(ssout);	ssout.addActionListener(this);
		con.add(prdclct); prdclct.addActionListener(this);
		//con.add(report);report.addActionListener(this);
		
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == ws) {
			InventoryManage in = new InventoryManage();
			in.createStockTable(Product.AC_CURRENTSTOCK);
		}
		
		if(e.getSource() == wsout) {
			InventoryManage in = new InventoryManage();
			in.createStockTable(Product.AC_STOCKOUTINFO);
		}
		
		if(e.getSource() == ss) {
			ShowRoomStockManage sh = new ShowRoomStockManage();
			sh.createStockTable(Product.AC_CURRENTSTOCK);
		}
		
		if(e.getSource() == ssout) {
			ShowRoomStockManage sh = new ShowRoomStockManage();
			sh.createStockTable(Product.AC_STOCKOUTINFO);
		}
		
		if(e.getSource() == prdprchs) {
			InventoryManage in = new InventoryManage();
			in.prodPurchaseForm();
		}
		
		if(e.getSource() == prdclct) {
			ShowRoomStockManage sh = new ShowRoomStockManage();
			sh.prodCollectForm();
			
		}
	}

	
	public static void main(String[] args) {
		MainFrame mainFrame = new MainFrame();

		mainFrame.menu();
		mainFrame.setSize(500,500);
		mainFrame.setLayout(new GridLayout(6, 1));
		MainFrame.setDefaultLookAndFeelDecorated(true);
		
		/*Dimension frameSize = mainFrame.getSize();
		int frameHeight = (int)frameSize.getHeight();
		int frameWidth = (int)frameSize.getWidth();*/
		//mainFrame.setLocation((screenHight-frameHeight)/2, (screenWidth-frameWidth)/2);
		
		mainFrame.setLocation(200, 100);
		mainFrame.setVisible(true);
		
		mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}

}
