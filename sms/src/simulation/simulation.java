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
				updatestock();
				sleep(24*60*60*1000);
				updatestock();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	void updatestock() {
		
	}
	void gainsotck() {
		dbconnect dc=null;
		PreparedStatement psta=null;
		ResultSet rs = null;
		
		try {
			dc=new dbconnect();
			psta=dc.getconn().prepareStatement(
					"select ID from stock"
					+ " where ID=?");
			psta.setString(1, "admin");
			rs=dc.query(psta);
			if(!rs.next()) {
			psta=dc.getconn().prepareStatement(
					"insert into  individual_account(ID,name,pwHash,gender,asset,country)"
					+ " value('admin','admin','21232f297a57a5a743894a0e4a801fc3','male',3,'China')");
			dc.add(psta);
			}
			data(5);
		} catch (SQLException e) {

			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	

    public static void data(int page) throws SQLException {
    	Random rd=new Random();
		String url_str = "http://web.juhe.cn:8080/finance/stock/usaall?key=ac22996b586444fc9abe3adf89c2f80c&page=";
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
    			company_account ca=new company_account();
    			ca.setID(tmp.optString("symbol"));
    			ca.setname(tmp.optString("cname"));
    			ca.setcountry("USA");
    			ca.register();
    			stock st =new stock();
    			st.setID(tmp.optString("symbol"));
    			st.setname(tmp.optString("cname"));
    			st.setopen_price(tmp.optDouble("open"));
    			st.setclosing_price(tmp.optDouble("preclose"));
    			st.setmax_price(tmp.optDouble("high"));
    			st.setmin_price(tmp.optDouble("low"));
    			st.setturnover(tmp.optInt("volume"));
    			st.setnow_price(tmp.optDouble("price"));
    			st.setupsanddowns(tmp.optDouble("chg"));
    			st.settype("USA");
    			st.setissue_circulation(rd.nextInt(400000)+20000);
    			st.setissue_price(rd.nextInt(80)+40);
    			st.publish();
    			st.newday();
    		}
    	}		   
    }
}
 
