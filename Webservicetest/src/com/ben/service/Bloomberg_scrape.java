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
	
	
	private String scrape_value() throws IOException {
		Document doc_curr = Jsoup.connect("Http://www.bloomberg.com/markets/").get();

		Element content_curr = doc_curr.getElementById("currencies_data_table");
		Elements links_curr = content_curr.getElementsByClass("value");

		int m = 0;
		for (Element link : links_curr) {
	//		System.out.println(link.text());

			if (m == 3)
				return link.text();

			m++;
		}
		return "0";
	}

	
}
