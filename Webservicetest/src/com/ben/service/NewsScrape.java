package com.ben.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



public class NewsScrape {


	public String getNews(String Ticker,List<struct_News> _News) {
		try {
			return scrapeNews(Ticker,_News);
		} catch (IOException e) {
			return null;
		}
	}

		
		
		private String scrapeNews(String Ticker,List<struct_News> _News) throws IOException {
			// TODO Auto-generated method stub
			Document doc_curr = Jsoup.connect("http://finviz.com/quote.ashx?t="+Ticker).get();

			Elements content_curr = doc_curr.getElementsByClass("fullview-news-outer");
		String Time = "";
		String Title="";
		String url = "";
		ArrayList<String> lst_news = new ArrayList<String>();
		int i=0;
		String story="";
			
		boolean _exist=false;
		
		 for(struct_News __News : _News)
		 {
			 if (__News.symbol.equals(Ticker))
			 {
						_exist=true; 
		
				for (Element link : content_curr) 
					{
					
						Elements el_link = link.getElementsByTag("td");
						for (Element links : el_link) 
						{
							
						
							 story = story+links.text()+";#";
						
				//			 System.out.println(story);
					//	System.out.println(links.text());
						
						Elements el_href = links.getElementsByTag("a");
						for (Element linkshref : el_href) 
							{
							 story = story+linkshref.attr("href");
					//		 System.out.println(story);
							 //System.out.println(linkshref.attr("href"));
							lst_news.add(story);
							i++;
							story="";
							}
						}
						__News.articles = lst_news.toArray(__News.articles);
					}
			 }
		 }

		if (!_exist)
		{
						
			for (Element link : content_curr) 
			{
			
				Elements el_link = link.getElementsByTag("td");
				for (Element links : el_link) 
				{
					
				
					 story = story+links.text()+";#";
				
		//			 System.out.println(story);
			//	System.out.println(links.text());
				
				Elements el_href = links.getElementsByTag("a");
				for (Element linkshref : el_href) 
					{
					 story = story+linkshref.attr("href");
			//		 System.out.println(story);
					 //System.out.println(linkshref.attr("href"));
					lst_news.add(story);
					i++;
					story="";
					}
				}
			}
		struct_News s_News = new struct_News();
		s_News.symbol=Ticker;
		String[] articles = new String[lst_news.size()];
		s_News.articles = new String[lst_news.size()];
		articles = lst_news.toArray(s_News.articles);
			s_News.articles = articles;
			_News.add(s_News);
		}
		
			
			
		
		
	//		for (int j=0;j<11;j++)
		//	{
			//	System.out.println(lst_news.get(j));
				
				
		//	}
		
		
		return "0";
		
			
		}
		
		
}
