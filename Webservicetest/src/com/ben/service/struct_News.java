package com.ben.service;

public class struct_News {
	
	public String symbol = null;
	public String[] articles = null;
       public String Title = null;
       public String Link = null;
      

       public struct_News(String Ticker)
       {
           symbol=Ticker;
       }
       public struct_News()
       {
       
       }

}
