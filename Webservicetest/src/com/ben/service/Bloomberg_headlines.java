package com.ben.service;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Bloomberg_headlines {

	/**
	 * @param args
	 * @return 
	 */
	void Bloomerg_headlines()
	{
		
		
		
	}
	
	public ArrayList<String> getHeadlines() throws IOException
	{
		Document doc_curr = Jsoup.connect("http://www.bloomberg.com/").get();

		Element content_curr = doc_curr.getElementById("top_headlines_module");
		Elements links_curr = content_curr.getElementsByClass("top_headlines_news_module");
		Elements links_curr1 = content_curr.getElementsByTag("li");
		 ArrayList<String> result_arr = new ArrayList<String>();
		int m = 0;
		String result="";
		for (Element link : links_curr1) {
			
			//System.out.println(link.text());
			result+= link.text()+";";
		result_arr.add(link.text());
		}
		
		
		
		
		
		
		
		return result_arr;
		
		
		
		
	}
	

}
