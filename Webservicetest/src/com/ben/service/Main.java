package com.ben.service;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;



import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.ben.service.GoogleScrape;

public class Main {

	public  void main(String[] args) throws URISyntaxException 
	{
		Main M = new Main();
		GoogleScrape GS = new GoogleScrape();
		TickerQuotes TQ = new TickerQuotes();
		List<TickerQuotes> quotes = new ArrayList<TickerQuotes>();
		
		
		
		
		 
		 
		 
		 
	//	GS.getLast("GOOG");
		GS.getLast("AAPL",quotes);
		GS.getLast("BAC",quotes);
		GS.getLast("NOK",quotes);
	//	GS.getLast("NOK");
	//	GS.getLast("BAC");
	//	GS.getLast("CCL.L");
		//GS.getLastChange();
		
		
		
		
	//	M.GetValue();
	}
	
	private void GetValue() throws URISyntaxException
	{
	
         String symbolList= "%22"+"AAPL"+"%22"+"%2C";
	
	String symbolurl = symbolList.substring(0,symbolList.length()-3);
		 URI uri = new URI("http://query.yahooapis.com/v1/public/yql?q=select%20Bid%20from%20yahoo.finance.quotes%20where%20symbol%20in%20("+symbolurl+")&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys");
     Boolean Downloaded = false;
   
			 while (!Downloaded)  
			 {
       try{
    	  
       
         //   String uri = "c:\\samplexml.xml";
           DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
       	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
       	Document doc = dBuilder.parse(uri.toString());
       	Element docEle = doc.getDocumentElement();
		NodeList nList = docEle.getElementsByTagName("quote");
		for (int temp = 0; temp < nList.getLength(); temp++) {

			Node nNode = nList.item(temp);
			// rs.next();
			Element el = (Element) nList.item(temp);
			
			System.out.println(getTextValue(el,"Bid"));
		}
			
       }
       catch (Exception e)
       {
    	   System.out.println(e.toString());
       }
}
	}
	
	private String getTextValue(Element ele, String tagName) {

		NodeList nl = ele.getElementsByTagName(tagName);
		if (nl != null && nl.getLength() > 0) {
			Element el = (Element) nl.item(0);
			try {
				return el.getFirstChild().getNodeValue();
			} catch (Exception e) {
				return "0";
			}
		} else
			return "0";
	}
}