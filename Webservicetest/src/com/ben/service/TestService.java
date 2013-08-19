package com.ben.service;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.jws.WebMethod;

import javax.jws.WebService;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.ben.service.GoogleScrape;
import com.ben.service.TickerQuotes;
import com.ben.service.NewsScrape;
import com.google.gson.JsonArray;

@WebService
public class TestService {

	 List<struct_News> _News = new ArrayList<struct_News>();
	 List<TickerQuotes> _quotes = new ArrayList<TickerQuotes>();
	 
	 mainPnL _m = new mainPnL();
	 String _env ="PROD";
	 @WebMethod
	  public String sayGreeting(String Ticker,int test) {
		
		 
		  Ticker = Ticker.toUpperCase();
		 
		 int i=0;
		 String result  = "";
		 for(TickerQuotes quote : _quotes)
		 {
			
		 if (quote.symbol.equals(Ticker))
		 {
		 result = quote.symbol+"#"+quote.Price+"#"+quote.Change+"#"+quote.Status;
		 return result;
	
		 }
		 }
	///	 if (result ==null || result =="")
		 //{
			 GoogleScrape GS = new GoogleScrape();
		    	GS.getLast(Ticker,_quotes); 
		    	for(TickerQuotes quote : _quotes)
				 {
					
				 if (quote.symbol.equals(Ticker))
				 {
				 result = quote.symbol+"#"+quote.Price+"#"+quote.Change+"#"+quote.Status;
				//	 array[i]=quote.symbol+"#"+quote.Price+"#"+quote.Change+"#"+quote.Status;
			//	 i++;
				 }
				 }
			 
	//	 }
		    Server S = new Server();
		       S.WriteLog("New Request : "+result);
		 
		 System.out.println("REQ : "+result);
			// return array;
		 return result;
	    }
	
	 public TestService(String _env) throws InterruptedException, SQLException
	 {
		 this._env = _env;
		  Server S = new Server();
	       S.WriteLog("Starting market data service...");
		GetLatest();
		 S.WriteLog("MDM up!");
	 }
	 
	 public void GetLatest() throws InterruptedException, SQLException
	 {
		
		 GoogleScrape GS = new GoogleScrape();
		 List<String> Tickers = new ArrayList<String>();
		 ResultSet rs = null;
	//	mainPnL _pnl = new mainPnL();
			try {
				rs = _m.LoadData("Select Ticker from interestlist");
				while (rs.next()) {
			
					//Tickers.add(rs.getString(1));
					
				//	for (String name : Tickers)
					//{
						 GS.getLast(rs.getString(1),_quotes);
						
				//	}
				}
			
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
		/*
		 
		 GS.getLast("AAPL",_quotes);
	    	GS.getLast("NOK",_quotes);
	    	GS.getLast("BAC",_quotes);
	    	GS.getLast("NYSE:AVG",_quotes);
	    	GS.getLast("UKX",_quotes);
	    	GS.getLast("DAX",_quotes);
	    	GS.getLast("LULU",_quotes);
	    	GS.getLast("LLOY",_quotes);
	    	GS.getLast("FSLR",_quotes);
	    	GS.getLast("IXIC",_quotes);//Nasdaq
	    	GS.getLast("INX",_quotes);//s&p
		 */
			// TODO Auto-generated method stub
			Timer _timerquotes = new Timer ();
			TimerTask _hourlyTaskquotes = new TimerTask () {
			    @Override
			    public void run () {
			    	 mdm _mdm = new mdm(_env);
			    	_mdm.start(_quotes);
			    			    	
			    	
			    }
			};
			_timerquotes.schedule(_hourlyTaskquotes, 01,300000);
		/*	
			Timer _timernews = new Timer ();
			TimerTask _hourlyTasknews = new TimerTask () {
			    @Override
			    public void run () {
			    	try{
			    		System.out.println("Running News Thread");
			    	NewsScrape NS = new NewsScrape();
			    	NS.getNews("AAPL",_News);
			    	NS.getNews("NOK",_News);
			    	NS.getNews("BAC",_News);
			    	NS.getNews("FSLR",_News);
			   	 for(struct_News __News : _News)
				 {
				System.out.println(__News.symbol);
				System.out.println(__News.articles[0]);
				 }
			    	}
			 	catch (Exception e)
		    	{
		    		 Server S = new Server();
		  	       S.WriteLog("ERROR : "+e.toString());
		    		
		    		
		    	}
			   	 
			   	 
			    }
			    };
		//	_timernews.schedule(_hourlyTasknews, 01,900000);
					
		*/
		}
		
	
	 public String[] TrendingTwits() throws ParseException
	 {
		
		 StockTwits ST = new StockTwits();
		 ArrayList<String> al_StockTwits = new ArrayList<String>();
		 al_StockTwits =  ST.getTrending();
		 String[] str_st = new String[ al_StockTwits.size()];
		 str_st  = al_StockTwits.toArray(str_st);
		 		 return str_st;
		 
		 
		 
		 
	 }	 
	 
	
	 
	 
	 public String[] getTwits(String Ticker) throws ParseException
	 {
		
		 StockTwits ST = new StockTwits();
		 ArrayList<String> al_StockTwits = new ArrayList<String>();
		 al_StockTwits =  ST.getStockTwit(Ticker);
		 String[] str_st = new String[ al_StockTwits.size()];
		 str_st  = al_StockTwits.toArray(str_st);
		 
		 return str_st;
		 
		 
		 
		 
	 }
	 public String[] getFly() throws ParseException
	 {
		 FlyNews FN = new FlyNews();
		 ArrayList<String> FlyNews = new ArrayList<String>();
		 FlyNews =  FN.geFly();
		 String[] FlyNews_str = new String[ FlyNews.size()];
		 FlyNews_str = FlyNews.toArray(FlyNews_str);
		 
		 return FlyNews_str;
	 }
	 public String[] getTopNews() 
	 
	 {
		 String[] array = new String[_News.size()];
	 
		 
		 
		 
		 return array;
	 }
	 public String[] getNews(String Ticker) 
	 
	 {
	    		 Server S = new Server();
	  	       S.WriteLog("News request for "+Ticker);
	    		
	    		
	    	
	/*	 ArrayList<String> lst_news = new ArrayList<String>();
		 NewsScrape NS = new NewsScrape();
		lst_news= NS.getNews(Ticker);
		 
		   String[] NewsArr = new String[lst_news.size()];
		 NewsArr = lst_news.toArray(NewsArr);
		*/ 
		// System.out.println("REQ = "+Ticker);
		
	//	 String[] array = new String[_News.size()];
		 int i=0;
		 
		 for(struct_News __News : _News)
		 {
		 if (__News.symbol.equals(Ticker))
		 {
			 
		 return __News.articles;
		
		 }
		 }
			 return null;
		 
	 }
	 
	public String call_line_main() throws SQLException
		 {
		mainPnL p = new mainPnL();
		
		
	//	String result = p.Value_pie_json(true);
		String result = p.Value_Line_json();
		System.out.println("HERE----"+result);
		return result;
	//	return "test";
		
		 }
	public String call_pie_live() throws SQLException
	 {
	mainPnL p = new mainPnL();
	
	String result = p.Value_pie_json(true);
//	String result = p.Value_Line_json();
	System.out.println("PieChartNowCall----"+result);
	return result;
	
	 }
	public String call_pie_initial() throws SQLException
	 {
	mainPnL p = new mainPnL();
	
	String result = p.Value_pie_json(false);
//	String result = p.Value_Line_json();
	System.out.println("PieChartNowInitial----"+result);
	return result;
	
	 }
	
	
	
	public String Table_holdings() throws SQLException
	{
		mainPnL m = new mainPnL();
		String result= m.Table_holdings();
		
		
		return result;
		
		
		
	}
	 public String call_pltotal() throws SQLException 
	 {
		 mainPnL m = new mainPnL();
			String result= m.Table_PnL();
			
			
			return result;
		//return "IT WORKS!!"; 
	 }
	 
	public String call_fx_table() throws SQLException 
	{
		mainPnL m = new mainPnL();
		String result= m.FXTable();
		
		
		return result;
		
	}
	 
	public String call_pl_table() throws SQLException 
	{
		mainPnL m = new mainPnL();
		String result= m.Table_PL_Realised();
		
		
		return result;
		
	}
	public String call_insert_trade(String Ticker, double Quantity, double Price, String Side,String Ccy) throws SQLException 
	{
		mainPnL p = new mainPnL();
		String result =p.Insert_Trader(Ticker, Quantity, Price, Side,Ccy);
		
			return result;
	}
	 
	
	public String call_alertlevels_table() throws SQLException
	{
		
		mainPnL m = new mainPnL();
		String result= m.WatchListTable();
		
		return result;
		
	}
	public int send_delete_alert(String ticker,String price) throws SQLException
	{
		
		System.out.println(ticker+price);
		mainPnL m = new mainPnL();
		try
		{
		m.ExecuteQuery("Delete from AlertKeyLevels where Symbol='"+ticker+"' and Value='"+price+"'");
		return -1;
		}
		catch (Exception e)
		{
		return 0;
			
		}
			
		
		
	}
	public int send_new_alert(String ticker,String cate,String price) throws SQLException
	{
		
		System.out.println(ticker+price+cate);
		mainPnL m = new mainPnL();
		try
		{
		m.ExecuteQuery("Insert into AlertKeyLevels values ('"+ticker+"','"+cate+"','"+price+"','N')");
		return -1;
		}
		catch (Exception e)
		{
		return 0;
			
		}
	}
		public String call_vol_chart(String Ticker) throws SQLException
		{
			
			//mainPnL m = new mainPnL();
			String result= _m.Vol_Chart(Ticker);
			
			return result;	
			
			
		}
		
		
	public String call_Future(String Index)
	{
		Bloomberg_scrape BS = new Bloomberg_scrape();
		
		String result = BS.getIndex(Index);
		return result;
		
	}
	
	
	}
	

