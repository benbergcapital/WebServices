package com.ben.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONObject;



public class mainPnL {

	 ResultSet rs = null;
	 Connection con = null;
	    Statement st = null;
	   
	   
	    String url = "jdbc:mysql://localhost:3306/Stocks";
	    String user = "root";
	    String password = "typhoon";
	
	
	public String Value_pie_json() throws SQLException
	{
		JSONObject obj=new JSONObject();
		  JSONObject obj_cols_1=new JSONObject();
		  JSONObject obj_cols_2=new JSONObject();
		  obj_cols_1.put("id","");
		  obj_cols_1.put("label","Stock");
		  obj_cols_1.put("type","string");
		  
		  obj_cols_2.put("id","");
		  obj_cols_2.put("label","$Value");
		  obj_cols_2.put("type","number");
		  
		  LinkedList l_cols = new LinkedList();
		  
		  l_cols.add(obj_cols_1);
		  l_cols.add(obj_cols_2);
		  obj.put("cols", l_cols);
		  try
		  {
			  
		rs = LoadData("Select distinct Ticker from CurrentHoldings");
		  }
		  catch (Exception e)
		  {
			  System.out.println(e.toString());
			  
			  
		  }
		String Ticker;
		String LastPx;
		String Qty;
		String AvgPx;
		Double Value;
		GoogleScrape gs = new GoogleScrape();
		List<String> Tickers = new ArrayList<String>();
		 LinkedList l_final = new LinkedList();
		while (rs.next())
		{
			//Ticker = (rs.getString(1));
			Tickers.add(rs.getString(1));
		}
		for (String name : Tickers)
		{	
		Ticker = (name);
	//	LastPx = gs.getLast(Ticker);
  		LastPx = "10";
		Qty = LoadData_str("Select Quantity from CurrentHoldings where Ticker = '"+Ticker+"'");
		AvgPx = LoadData_str("Select AvgPx from CurrentHoldings where Ticker = '"+Ticker+"'");
		Value = Double.valueOf(LastPx)*Double.valueOf(Qty);
				  
		  JSONObject obj2=new JSONObject();
		  JSONObject obj3=new JSONObject();
		  JSONObject obj4=new JSONObject();
		  JSONObject obj_col=new JSONObject();
		  
		  obj3.put("v", Ticker);
		  obj3.put("f", null);
		  obj4.put("v",Value);
		  obj4.put("f", null);
		 
		  LinkedList l1 = new LinkedList();
		  LinkedHashMap m1 = new LinkedHashMap();
		  l1.add(obj3);
		  l1.add(obj4);
		  m1.put("c",l1);
		  
		  l_final.add(m1);
		}
		obj.put("rows",l_final);
		 
		  return obj.toJSONString();
		
	}
	public String Value_Line_json() throws SQLException
	{
		List<String> Tickers = new ArrayList<String>();
		rs = LoadData("Select distinct Ticker from CurrentHoldings");
		LinkedList l_cols = new LinkedList();
		JSONObject obj=new JSONObject();
		while (rs.next())
		{
			//Ticker = (rs.getString(1));
			Tickers.add(rs.getString(1));
		}
		for (String name : Tickers)
		{
		
		  JSONObject obj_cols_1=new JSONObject();
		  
		  obj_cols_1.put("id","");
		  obj_cols_1.put("label",name);
		  obj_cols_1.put("type","number");
		  
		  l_cols.add(obj_cols_1);
		}
		
		JSONObject obj_cols_2=new JSONObject();
		  
		  
		  obj_cols_2.put("id","");
		  obj_cols_2.put("label","dates");
		  obj_cols_2.put("type","string");
		  
		 
		  l_cols.add(0,obj_cols_2);
		  
		  obj.put("cols", l_cols);
		 		  
		  
		  try
		  {
			  
		rs = LoadData("Select distinct date  from PNL");
		  }
		  catch (Exception e)
		 {
			 System.out.println(e.toString());
		 
			 
		 }
		String Ticker;
		String LastPx;
		String Qty;
		String PnL;
		Double Value;
		GoogleScrape gs = new GoogleScrape();
		List<String> Date_list = new ArrayList<String>();
		 LinkedList l_final = new LinkedList();
		 
		 
		while (rs.next())
		
		{
			//Ticker = (rs.getString(1));
			Date_list.add(rs.getString(1));
		}
		
		
		
		  
		
		  
		for (String date : Date_list)
		{
			 LinkedList l1 = new LinkedList();
			for (String ticker : Tickers)
			{
		
		Ticker = (ticker);
	//	LastPx = gs.getLast(Ticker);
	//	Qty = LoadData_str("Select Quantity from CurrentHoldings where Ticker = '"+Ticker+"'");
		try
		{
					PnL = LoadData_str("Select PL from PnL where date = '"+date+"' and Ticker = '"+ticker+"'");
		}
		catch (Exception e)
		{
			PnL = "0";
		}
					//	Value = Double.valueOf(LastPx)*Double.valueOf(Qty);
				  
		
		 
		  
		  JSONObject obj_col=new JSONObject();
		  JSONObject obj_val=new JSONObject(); 
		//  obj3.put("v", date);
		//  obj3.put("f", null);
		  obj_val.put("v",Double.valueOf(PnL));
		  obj_val.put("f", null);
		 
		  l1.add(obj_val);
		 
		 
			}
			
			  JSONObject obj_date=new JSONObject();
			  LinkedHashMap m1 = new LinkedHashMap();
			obj_date.put("v", date);
			obj_date.put("f",null);
			
			 l1.add(0,obj_date);
			
			
			 m1.put("c",l1);
			 l_final.add(m1);
			 
			//  l1.clear();
			 // m1.clear();
		}
		 
		obj.put("rows",l_final);
		 System.out.println(obj);
		 
		  return obj.toJSONString();
		
	}
	
	
	public ArrayList<String> tableholdings() throws SQLException
	{
		
		ArrayList<String> lst_json = new ArrayList<String>();
		rs = LoadData("Select distinct Ticker from CurrentHoldings");
		ArrayList<String> l_Tickers = new ArrayList<String>();
		ArrayList<String> l_Qty = new ArrayList<String>();
		ArrayList<String> l_Px = new ArrayList<String>();
		ArrayList<String> l_Last = new ArrayList<String>();
		ArrayList<String> l_PL = new ArrayList<String>();
		ArrayList<String> l_PL_Percent = new ArrayList<String>();
		ArrayList<String> l_date = new ArrayList<String>();
		List<String> Tickers = new ArrayList<String>();
		rs = LoadData("Select distinct Ticker from CurrentHoldings");

		while (rs.next())
		{
			//Ticker = (rs.getString(1));
			Tickers.add(rs.getString(1));
		}
		
		
		for (String name : Tickers)
		{
			rs = LoadData("Select Quantity, AvgPx from CurrentHoldings where Ticker ='"+name+"'");
			rs.next();
			l_Tickers.add(name);
			l_Qty.add(rs.getString(1));
			l_Px.add(rs.getString(2));
			rs = LoadData("Select LastPx, PL,PL_Percent,date from pnl where Ticker ='"+name+"' order by date desc limit 1");
			rs.next();
			l_Last.add(rs.getString(1));
			l_PL.add(rs.getString(2));
			l_PL_Percent.add(rs.getString(3));
			l_date.add(rs.getString(4));
		}
		
		for (int i=0;i<l_Tickers.size();i++)
		{
		lst_json.add(l_Tickers.get(i)+";"+l_Qty.get(i)+";"+l_Px.get(i)+";"+l_Last.get(i)+";"+l_PL.get(i)+";"+l_PL_Percent.get(i)+";"+l_date.get(i));
		
		}
		
		return lst_json;
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public String LoadData_str(String Message) throws SQLException
	{
		LogOutput(Message);
		 PreparedStatement pst = null;
		
	//	 con = DriverManager.getConnection(url, user, password);
	//	 st = con.createStatement();
     //    rs = st.executeQuery("SELECT VERSION()");	
    //     rs.next();
	//	 System.out.println(rs.getString(1));
         pst = con.prepareStatement(Message);
         rs = pst.executeQuery();
         rs.next();
      //   while (rs.next()) {
      //       System.out.print(rs.getString(1));
      //       System.out.print(": ");
         //    System.out.println(rs.getString(2));
      //   }
		return rs.getString(1);
	}
	public ResultSet LoadData(String Message) throws SQLException
	{
		LogOutput(Message);
		 PreparedStatement pst = null;
		
		 con = DriverManager.getConnection(url, user, password);
	//	 st = con.createStatement();
    //     rs = st.executeQuery("SELECT VERSION()");	
    //     rs.next();
//		 System.out.println(rs.getString(1));
         pst = con.prepareStatement(Message);
         rs = pst.executeQuery();
		
      //   while (rs.next()) {
      //       System.out.print(rs.getString(1));
      //       System.out.print(": ");
         //    System.out.println(rs.getString(2));
      //   }
         
		return rs;
	}
	
	public void LogOutput(String Message)
	{
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		DateFormat dateFormat_log = new SimpleDateFormat("yyyy.MM.dd");
		Date date = new Date();
		System.out.println(dateFormat.format(date)+" : "+Message);
 		//System.out.printf("%D %R : ",date + Message);
	/*
		try {
			 
		
 
			File file = new File("/home/pi/logs/"+dateFormat_log.format(date)+".PiFinance.log.txt");
			//File file = new File("c:\\"+dateFormat_log.format(date)+".PiFinance.log.txt");
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
 
			FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(dateFormat.format(date)+" : "+Message+"\n");
			bw.close();
 
		
 
		} catch (IOException e) {
			System.out.println(e.toString());
		}
		*/
		
	}
	
}
