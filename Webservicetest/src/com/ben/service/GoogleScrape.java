package com.ben.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ben.service.TickerQuotes;


public class GoogleScrape {
	public String getLast(String Ticker,List<TickerQuotes> quotes) {
		try {
			
			return scrape_value(Ticker,quotes);
		} catch (IOException e) {
			return null;
		}

	}
	public String getLastChange() {
		try {
			return scrape_change();
		} catch (IOException e) {
			return null;
		}

	}
	public String getLast(String Ticker) {
		try {
				return scrape_value_simple(Ticker);
			} catch (IOException e) {
				return null;
			}
	}
	public String getVolume(String Ticker) {
		try {
				return scrape_volume(Ticker);
			} catch (IOException e) {
				return null;
			}
	}
	private String scrape_value_simple(String Ticker) throws IOException {
		Document doc_curr = Jsoup.connect("https://www.google.co.uk/finance?q="+Ticker).get();

		Element content_curr = doc_curr.getElementById("price-panel");
		Elements links_curr = content_curr.getElementsByClass("pr");
		
		
		
	
		for (Element link : links_curr) {
			System.out.println(link.text());
			return link.text();
			
		}
		
		
		return "0";
	}
		
		
		
	private String scrape_value(String Ticker,List<TickerQuotes> quotes) throws IOException {
		Document doc_curr = Jsoup.connect("https://www.google.co.uk/finance?q="+Ticker).get();
	//	System.out.println("Enetered class"+Ticker);
	//	File in = new File("C:\\Users\\Ben\\Desktop\\premkt.htm");
	//	Document doc_curr = Jsoup.parse(in,null);
		Element content_curr = doc_curr.getElementById("price-panel");
		Elements links_curr = content_curr.getElementsByClass("pr");
		String _price = null;
		boolean _exist=false;
		
		 
		 for(TickerQuotes quote : quotes)
		 {
			 if (quote.symbol.equals(Ticker))
			 {
						_exist=true; 		
						System.out.println("exist set true "+Ticker);	
				for (Element link : links_curr) 
				{
					//	System.out.println(link.text());
						quote.Price = link.text();
						
					}
					links_curr = content_curr.getElementsByClass("chr");
					for (Element link : links_curr) {
					//	System.out.println(link.text());
					}
					links_curr = content_curr.getElementsByClass("nwp");
					int m = 0;
					for (Element link : links_curr) {
					//	System.out.println(link.text());
						if (m==0)
							quote.Change = link.text();
						if (m==1)
							quote.Status = link.text();
						m++;
					}
					if (content_curr.toString().contains("Pre-market:") || content_curr.toString().contains("After-Hours"))
					{
					String state = null;
					if (content_curr.toString().contains("Pre-market:"))
						{
							state = "Pre-Market:";
							
						}
						else
						{
							
							state = "After-Hours:";
						}
						_price = "OOH"+";"+state;
					Elements test = content_curr.getElementsByClass("bld");
					int i=0;
					for (Element link : test) 
					{
					if (i==1) 
						{
						_price = _price +";"+link.text()+";";
						System.out.println(link.text());
						}
			 		i++;
					
					}
					Elements test1 = content_curr.getElementsByClass("chg");
					int j=0;
					for (Element link : test1) 
					{
					 System.out.println(link.text());
					 _price = _price + link.text()+";";
					
					}
					 System.out.println(_price);
					 quote.Status = _price;
					}
					
			 }
		 }


		 if (!_exist)
		 {
			
		TickerQuotes TQ = new TickerQuotes();
		TQ.symbol=Ticker;
		for (Element link : links_curr) {
			System.out.println(link.text());
			TQ.Price = link.text();
			
		}
		links_curr = content_curr.getElementsByClass("chr");
		for (Element link : links_curr) {
		//	System.out.println(link.text());
		}
		links_curr = content_curr.getElementsByClass("nwp");
		int m = 0;
		for (Element link : links_curr) {
		//	System.out.println(link.text());
			if (m==0)
				TQ.Change = link.text();
			if (m==1)
				TQ.Status = link.text();
			m++;
		}
		
		if (content_curr.toString().contains("Pre-market:") || content_curr.toString().contains("After Hours:"))
		{
		String state = null;
		if (content_curr.toString().contains("Pre-market:"))
			{
				state = "Pre-Market:";
				
			}
			else
			{
				
				state = "After-Hours:";
			}
		_price = "OOH"+";"+state;
		Elements test = content_curr.getElementsByClass("bld");
		int i=0;
		for (Element link : test) 
		{
		if (i==1) 
			{
			_price = _price +";"+link.text()+";";
			System.out.println(link.text());
			}
 		i++;
		
		}
		Elements test1 = content_curr.getElementsByClass("chg");
		int j=0;
		for (Element link : test1) 
		{
		 System.out.println(link.text());
		 _price = _price + link.text()+";";
		
		}
		System.out.println(_price);
		 TQ.Status = _price;
		
		}
		 
		
		
		
		quotes.add(TQ);
		 }
		return "0";
	}

	private String scrape_change() throws IOException {
		int m = 0;
		Document doc_curr = Jsoup.connect("https://www.google.co.uk/finance?q=GOOG").get();
		Element content_curr = doc_curr.getElementById("price-panel");
		Elements links_curr_change = content_curr.getElementsByClass("chr");
		
		for (Element link : links_curr_change) {
			System.out.println(link.text());

		//	if (m == 3)
			//	 if (link.text().startsWith("-"))
				//	 return "<font color=\"red\">"+link.text()+"</font>";						 
			//	 else   return "<font color=\"green\">"+link.text()+"</font>";	
				

			m++;
		}
		
		
		
		
		return "0";
	}

	private String scrape_futures() throws IOException {
		

	  Document doc_fut = Jsoup.connect("Http://www.bloomberg.com/markets/").get();
		
		 Element content_fut = doc_fut.getElementById("futures_data_table");
		 Elements links_fut = content_fut.getElementsByClass("value");
		 Elements links_fut_change = content_fut.getElementsByClass("percent_change");
		 int l=0;
		 for (Element link : links_fut) {
	//		System.out.println(link.text());
			
			
			 if (l==3) 	return link.text();

			l++;
			}
		 return "0";
	}
	private String scrape_futuresch() throws IOException {
		  Document doc_fut = Jsoup.connect("Http://www.bloomberg.com/markets/").get();
			
			 Element content_fut = doc_fut.getElementById("futures_data_table");
			 Elements links_fut = content_fut.getElementsByClass("value");
			 Elements links_fut_change = content_fut.getElementsByClass("percent_change"); 
		 
			 int l=0;
		 for (Element link : links_fut_change) {
	//			System.out.println(link.text());
				
				
				 if (l==3)
					 {
					
					 if (link.text().startsWith("-"))
						 return "<font color=\"red\">"+link.text()+"</font>";						 
					 else   return "<font color=\"green\">"+link.text()+"</font>";	
					
					 }
		 
					
				 
				 
				l++;
				}
		 return "0";
	}
	
	private String scrape_volume(String ticker) throws IOException
	{
		Document doc_curr = Jsoup.connect("https://www.google.co.uk/finance?q="+ticker).get();
		//	System.out.println("Enetered class"+Ticker);
		//	File in = new File("C:\\Users\\Ben\\Desktop\\premkt.htm");
		//	Document doc_curr = Jsoup.parse(in,null);
			Element content_curr = doc_curr.getElementById("market-data-div");
			Elements links_curr = content_curr.getElementsByClass("val");
			int i =0;
			for (Element link : links_curr) 
			{
				
				if (i==3)
				{
					System.out.println(link.text());
					return link.text();
				}
				i++;
					
			}
		
		return null;
		
		
	}
	
}