package com.ben.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ben.service.TickerQuotes;

public class FlyNews {
	public ArrayList<String> geFly() throws ParseException {
		try {
			return FlyNews();
		} catch (IOException e) {
			return null;
		}
	}
	public ArrayList<String> FlyNews() throws ParseException, IOException
	{
		Document doc_curr = Jsoup.connect("http://www.theflyonthewall.com/beta/news.php").get();

		Element content_curr = doc_curr.getElementById("ul_allNews");
		Elements links_title = content_curr.getElementsByClass("title");
		Elements links_body = content_curr.getElementsByClass("Headline");
		
		ArrayList<String> lst_time = new ArrayList<String>();
		ArrayList<String> lst_body = new ArrayList<String>();
		ArrayList<String> lst_feed = new ArrayList<String>();
				for (Element link : links_title) 
				{
					lst_time.add(link.text().substring(link.text().indexOf(" ")+1,link.text().indexOf(" ")+9));
					System.out.println(link.text().substring(link.text().indexOf(" ")+1,link.text().indexOf(" ")+9));
				}
				for (Element link : links_body) 
				{
					lst_body.add(link.text());
					System.out.println(link.text());
				}
				
				for(int i=0;i<lst_body.size();i++)
				{
					lst_feed.add(lst_time.get(i)+";"+lst_body.get(i));
				}
				
				
				return lst_feed;
		
	}
}
