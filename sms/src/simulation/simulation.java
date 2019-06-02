package simulation;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import account.company_account;
import database.dbconnect;
import net.sf.json.JSONObject;
import stock.stock;
import net.sf.json.JSONArray;

public class simulation extends Thread{
	@Override
	public void run() {
		gainsotck();
		while(true) {
			try {
				sleep(24*60*60*1000);
				updatestock();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	void updatestock() {
		try {
			data(9,false,"USA");
			data(9,false,"HongKong");
			data(9,false,"ShenZhen");
			data(9,false,"ShangHai");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	void gainsotck() {
		dbconnect dc=null;
		PreparedStatement psta=null;
		ResultSet rs = null;
		
		try {
			dc=new dbconnect();
			psta=dc.getconn().prepareStatement(
					"select ID from individual_account"
					+ " where ID=?");
			psta.setString(1, "admin");
			rs=dc.query(psta);
			if(!rs.next()) {
			psta=dc.getconn().prepareStatement(
					"insert into  individual_account(ID,name,pwHash,gender,asset,country)"
					+ " value('admin','admin','21232f297a57a5a743894a0e4a801fc3','male',3,'China')");
			dc.add(psta);
			}
			data(9,true,"USA");
			data(9,true,"HongKong");
			data(9,true,"ShenZhen");
			data(9,true,"ShangHai");
		} catch (SQLException e) {

			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	

    public static void data(int page,boolean upd,String country) throws SQLException {
    	Random rd=new Random();
		String url_str =null;
		switch(country) {
		case "USA":url_str = "http://web.juhe.cn:8080/finance/stock/usaall?key=ac22996b586444fc9abe3adf89c2f80c&page=";break;
		case "HongKong":url_str = "http://web.juhe.cn:8080/finance/stock/hkall?key=ac22996b586444fc9abe3adf89c2f80c&page=";break;
		case "ShenZhen":url_str = "http://web.juhe.cn:8080/finance/stock/szall?key=ac22996b586444fc9abe3adf89c2f80c&page=";break;
		case "ShangHai":url_str = "http://web.juhe.cn:8080/finance/stock/shall?key=ac22996b586444fc9abe3adf89c2f80c&page=";break;
		}
		for(int i=1;i<=page;i++) {
    		URL url=null;
			try {
				url = new URL(url_str+i);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
            StringBuffer sb = new StringBuffer();   
            try(BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(),"utf-8") )){
             String str = null;    
             while((str = in.readLine()) != null) {
            	 sb.append(str);
             }     
            } catch (Exception ex) {               
            }   
               String  data =sb.toString();
               JSONObject json = JSONObject.fromObject(data);
    		json = json.getJSONObject("result");
    		JSONArray jsa = json.getJSONArray("data");
    		for(int j=0;j<jsa.length();j++) {
    			JSONObject tmp = jsa.getJSONObject(j);
    			if(upd) {
    			company_account ca=new company_account();
    			ca.setID(tmp.optString("symbol"));
    			ca.setname(tmp.optString("cname"));
    			ca.setcountry(country);
    			ca.register();
    			}
    			stock st =new stock();
    			st.setID(tmp.optString("symbol"));
    			switch(country) {
    			case "USA":st.setname(tmp.optString("cname"));
    			st.setclosing_price(tmp.optDouble("preclose"));
    			st.setnow_price(tmp.optDouble("price"));
    			st.setupsanddowns(tmp.optDouble("chg"));break;
    			case "HongKong":st.setname(tmp.optString("name"));
    			st.setclosing_price(tmp.optDouble("prevclose"));
    			st.setnow_price(tmp.optDouble("lasttrade"));
    			st.setupsanddowns(tmp.optDouble("changepercent"));break;
    			case "ShenZhen":st.setname(tmp.optString("name"));
    			st.setclosing_price(tmp.optDouble("settlement"));
    			st.setnow_price(tmp.optDouble("trade"));
    			st.setupsanddowns(tmp.optDouble("changepercent"));break;
    			case "ShangHai":st.setname(tmp.optString("name"));
    			st.setclosing_price(tmp.optDouble("settlement"));
    			st.setnow_price(tmp.optDouble("trade"));
    			st.setupsanddowns(tmp.optDouble("changepercent"));break;
    			}
    			st.setopen_price(tmp.optDouble("open"));
    			st.setmax_price(tmp.optDouble("high"));
    			st.setmin_price(tmp.optDouble("low"));
    			st.setturnover(tmp.optInt("volume"));
    			
    			
    			st.settype(country);
    			st.setissue_circulation(rd.nextInt(400000)+20000);
    			st.setissue_price(rd.nextInt(80)+40);
    			if(upd)
    			st.publish();
    			st.newday();
    		}
    	}		   
    }
}
 
