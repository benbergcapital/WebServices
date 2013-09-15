package com.ben.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class StockTwits {
	
	public ArrayList<String> getStockTwit(String Ticker) throws ParseException {
		try {
			return StockTwits(Ticker);
		} catch (IOException e) {
			return null;
		}
	}
	public ArrayList<String> getTrending() throws ParseException {
		try {
			return StockTwitsTrending();
		} catch (IOException e) {
			return null;
		}
	}
	
	
	public ArrayList<String> StockTwits(String Ticker) throws ParseException, IOException
	{
 		  InputStream is = new URL("https://api.stocktwits.com/api/2/streams/symbol/"+Ticker+".json").openStream();
		  BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
	      String jsonText = readAll(rd);
	     
	      ArrayList<String> lst_body = new ArrayList<String>();
	      ArrayList<String> lst_time = new ArrayList<String>();
	      ArrayList<String> lst_feed = new ArrayList<String>();
	      ArrayList<String> lst_user = new ArrayList<String>();
	      ArrayList<String> lst_avatar = new ArrayList<String>();
	      JsonElement jelement = new JsonParser().parse(jsonText);
		    JsonObject  jobject = jelement.getAsJsonObject();
	//	    jobject = jobject.getAsJsonObject("messages");
		    JsonArray jarray = jobject.getAsJsonArray("messages");
		  
		   // jarray.getAsJsonArray()
	//	    jobject = jarray.get(0).getAsJsonObject();
	//	    JsonArray jarray_u = jobject.getAsJsonArray("user");
		 
		    JSONParser parser = new JSONParser();
		    String result ="";
		    String time  ="";
		    String user ="";
		    
		    for(int i=0;i<30;i++)
		    {
		    	  result= jarray.get(i).getAsJsonObject().get("body").toString();
		    	  
		    	  time = jarray.get(i).getAsJsonObject().get("created_at").toString();
		    	  
		    	 user =  jarray.get(i).getAsJsonObject().get("user").toString();
		    	 
		    	
		    	 Object obj = parser.parse((user));
		    	 JSONObject jsonObject = (JSONObject) obj;
		    	 
		 		String name = (String) jsonObject.get("username");
		 		String avatar = (String) jsonObject.get("avatar_url");
		    	 System.out.println(name);
		    	 
		    	  lst_body.add(result.substring(1,result.length()-1));
		    	  lst_time.add(time.substring(time.indexOf("T")+1,time.indexOf("Z")));
		    	  lst_user.add(name);
		    	  lst_avatar.add(avatar);
		    	 
		    }
		    System.out.println(result);
		    System.out.println(time);
		    
		    for(int i=0;i<lst_body.size();i++)
			{
				lst_feed.add(lst_time.get(i)+";#"+lst_user.get(i)+";#"+lst_avatar.get(i)+";#"+lst_body.get(i));
			}
			
		    return lst_feed;
	
	}
	 public ArrayList<String> StockTwitsTrending() throws ParseException, IOException
		{
	 		  InputStream is = new URL("https://api.stocktwits.com/api/2/trending/symbols.json").openStream();
			  BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
		      String jsonText = readAll(rd);
		     
		      ArrayList<String> lst_symbol = new ArrayList<String>();
		      ArrayList<String> lst_title = new ArrayList<String>();
		      ArrayList<String> lst_feed = new ArrayList<String>();
		      JsonElement jelement = new JsonParser().parse(jsonText);
			    JsonObject  jobject = jelement.getAsJsonObject();
	
			    JsonArray jarray = jobject.getAsJsonArray("symbols");
	
			    String symbol ="";
			    String title  ="";
			    
	 	    for(int i=0;i<jarray.size();i++)
			    {
			    	  symbol= jarray.get(i).getAsJsonObject().get("symbol").toString().substring(1, jarray.get(i).getAsJsonObject().get("symbol").toString().length()-1);
			    	  title = jarray.get(i).getAsJsonObject().get("title").toString().substring(1, jarray.get(i).getAsJsonObject().get("title").toString().length()-1);
			    	 
			  
			    	 lst_title.add(title) ;
			    	  lst_symbol.add(symbol);
			//    	 System.out.println(jobject.getAsJsonArray("User").get(0).getAsJsonObject().get("username")
			    	
			    }
	 	    
			//    lst_symbol.add(symbol);
		    	//  lst_title.add(title);
		    	//  lst_feed.add(symbol);
		    	//  lst_feed.add(title);
	 	    Gson gson = new Gson();
			   lst_feed.add(gson.toJson(lst_symbol));
			   lst_feed.add(gson.toJson(lst_title));
				
			    return lst_feed;
		
		}
	
	 private static String readAll(Reader rd) throws IOException {
		    StringBuilder sb = new StringBuilder();
		    int cp;
		    while ((cp = rd.read()) != -1) {
		      sb.append((char) cp);
		    }
		    return sb.toString();
		  }
	
}
