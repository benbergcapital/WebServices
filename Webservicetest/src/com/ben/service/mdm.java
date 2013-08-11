package com.ben.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class mdm {

	List<TickerQuotes> _quotes = new ArrayList<TickerQuotes>();
	GoogleScrape GS = new GoogleScrape();
	 String url = "jdbc:mysql://192.168.0.6:3306/Stocks";
	    String user = "root";
	    String password = "root";
	    ResultSet rs = null;
		 Connection con = null;
		 Statement st = null;
		 
		 
	public mdm() {
		// TODO Auto-generated method stub

		GoogleScrape GS = new GoogleScrape();
    	GS.getLast("AAPL",_quotes);
    	GS.getLast("NOK",_quotes);
    	GS.getLast("BAC",_quotes);
    	GS.getLast("NYSE:AVG",_quotes);
    	GS.getLast("UKX",_quotes);
    	GS.getLast("DAX",_quotes);
    	GS.getLast("LULU",_quotes);
    	GS.getLast("LLOY",_quotes);
    	GS.getLast("FSLR",_quotes);
    	GS.getLast("IXIC",_quotes);//Nasdaq
    	GS.getLast("INX",_quotes);//s&p
		
       
	}
	
	public void start(){
		try{
    		System.out.println("Running Quote Thread");
    		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    		Date date = new Date();
   
    		DateFormat idateFormat = new SimpleDateFormat("HH:mm:ss");
    	
    	System.out.println("STILL ALIVE");
    	 for(TickerQuotes quote : _quotes)
		 {
    		 GS.getLast(quote.symbol,_quotes);
			 System.out.println(quote.symbol);
		 System.out.println(quote.Price);
		 System.out.println(quote.Change);
		 System.out.println(quote.Status);
		try
		{
			String _vol =	GS.getVolume(quote.symbol);
			String[] __vol = _vol.split("/");
			
			if (__vol[0].contains("M"))
			{
				__vol[0] = String.valueOf(Double.valueOf(__vol[0].substring(0,__vol[0].length()-1))*1000000);
				
			}
	
			ExecuteQuery("insert into volume values ('"+quote.symbol+"','"+__vol[0]+"','"+__vol[1]+"','"+dateFormat.format(date)+"','"+idateFormat.format(date)+"')");
		}
		catch (Exception e)
		{
			
		}
	
		}
    	
    }
    	catch (Exception e)
    	{
    		System.out.println("ERROR : "+e.toString());
    		 Server S = new Server();
  	       S.WriteLog("ERROR : "+e.toString());
    		
    		
    	}
		
		
		
	}
	public void ExecuteQuery(String Message) throws SQLException
	{
		 Server S = new Server();
	       S.WriteLog(Message);
	       Connection con = DriverManager.getConnection(url, user, password);
			
		try
		{
		
		PreparedStatement pst = null;
		 pst = con.prepareStatement(Message);
            pst.executeUpdate();
		}
		catch (Exception e)
		{
			   S.WriteLog(e.toString());
			
			
		}
    
	}	

}