package com.ben.service;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Bloomberg_scrape {

	public String getFX() {
		try {
			return scrape_value();
		} catch (IOException e) {
			return "--error--";
		}

	}
	public String getFuture(String value) {
		try {
			return scrape_Future(Index.valueOf(value));
		} catch (IOException e) {
			return "--error--";
		}

	}
	
	private String scrape_value() throws IOException {
		Document doc_curr = Jsoup.connect("Http://www.bloomberg.com/markets/").get();

		Element content_curr = doc_curr.getElementById("currencies_data_table");
		Elements links_curr = content_curr.getElementsByClass("value");
		Elements links_curr1 = content_curr.getElementsByClass("percent_change");
		int m = 0;
		String result="";
		for (Element link : links_curr) {
			if (m == 3)
				result= link.text();
			m++;
		}
		m=0;
		for (Element link : links_curr1) {
			if (m == 3)
				result+=";"+ link.text();
			m++;
		}
		
		
		return "GBP-USD;"+result;
	}
	private enum Index {
	    sap, nasdaq;
	}
private String scrape_Future(Index _Index) throws IOException
{

	  Document doc_fut = Jsoup.connect("Http://www.bloomberg.com/markets/").get();
		
		 Element content_fut = doc_fut.getElementById("futures_data_table");
		 Elements links_fut = content_fut.getElementsByClass("value");
		 Elements links_fut_change = content_fut.getElementsByClass("percent_change");
		 int l=0;
		 String result = "";
		 String result_chg = "";
		 
		 int _k=0;
		 switch(_Index) 
			 {
			    case sap:
			        _k=1;
			        break;
			    case nasdaq:
			    	_k=2;
			        break;	
			}
		 for (Element link : links_fut) 
		 {
			if (l==_k) 	result= link.text();
			l++;
		 }
		 l=0;
		 for (Element link : links_fut_change) 
		 {
			if (l==_k) 	result_chg= link.text();
			l++;
		 }
		 
		 
		 
		 return _Index+";"+result+";"+result_chg;
}




}
