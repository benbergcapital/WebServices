package com.ben.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;

public class Calendar_news {
	
	public  ArrayList<String> getTodaysCal()
	{
		try {
			return getCal();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	

private  ArrayList<String> getCal() throws IOException
{
	Calendar cal = Calendar.getInstance();
	int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH)+1;
	int monthnumber = cal.get(Calendar.MONTH)+1;
	int year = cal.get(Calendar.YEAR);
	String dayOfMonthStr = String.valueOf(dayOfMonth);
	List<String> _news = new ArrayList<String>();
	List<String> _time = new ArrayList<String>();
	List<String> _link = new ArrayList<String>();
	 ArrayList<String> _result= new ArrayList<String>();
		Document doc3 = Jsoup.connect("http://global.econoday.com/byday.asp?cust=global-premium&day="+String.valueOf(dayOfMonth)+"&month="+String.valueOf(monthnumber)+"&year="+String.valueOf(year)+"&lid=0").get();
//		Document doc3 = Jsoup.connect("http://global.econoday.com/byday.asp?cust=global-premium&day=23&month=10&year=2012&lid=0").get();
		//String title2 = doc2.title();
	 Elements content3 = doc3.getElementsByClass("dailyeventtext");
	System.out.println(content3.text());
	 int last =0; 
	 int start =0; 
	 			 
	 
	 
	 for (Element value : content3)
	 {
		 
		 try{
		
	 _news.add(value.select("a").first().text());	 
	
	// String _time = Convert.toLocal(value.select("td").first().text());
	 
	 _time.add(value.select("td").first().text().replace("&nbsp;"," "));
	_link.add("http://global.econoday.com/"+value.select("a").attr("href"));
	
		 }
		catch (Exception e){
			 
		 }
	 }
	 
	 
	 
	 
	 
	 
	 
	 Gson gson = new Gson();
	 _result.add(gson.toJson(_time));
	 _result.add(gson.toJson(_news));
	 _result.add(gson.toJson(_link));
	 return _result;
}
}