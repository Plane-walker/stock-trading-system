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
import account.individual_account;
import database.dbconnect;
import net.sf.json.JSONObject;
import stock.stock;
import net.sf.json.JSONArray;

public class simulation extends Thread{
	@Override
	public void run() {
		gainsotck();
		Random rdt=new Random();
		while(true) {
			try {
				simutrade();
				sleep(rdt.nextInt(30*1000)+3*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	void simutrade() {
		Random rd=new Random();
			individual_account iacc=new individual_account();
			iacc.setID(""+(rd.nextInt(100)+1));
			dbconnect dc=null;
			PreparedStatement psta=null;
			ResultSet rs = null;
			try {
				dc=new dbconnect();
				if(rd.nextBoolean()) {
				psta=dc.getconn().prepareStatement(
						"select * from own "
						+ " where acc_ID=? limit 1 ");
				psta.setString(1, iacc.getID());
				rs=dc.query(psta);
				if(rs.next()) {
					String sto_ID=rs.getString("sto_ID");
					int own_number=rs.getInt("number");
					psta=dc.getconn().prepareStatement(
							"select * from stock "
							+ " where ID=?");
					psta.setString(1, sto_ID);
					rs=dc.query(psta);
					if(rs.next()) {
					psta=dc.getconn().prepareStatement(
							"delete from pre_trading");
					dc.delete(psta);
					psta=dc.getconn().prepareStatement(
							"call sell(?,?,?,?)");
					psta.setString(1, iacc.getID());
					psta.setString(2, sto_ID);
					psta.setInt(3, own_number);
					psta.setDouble(4, Double.valueOf(String.format("%.2f",rs.getDouble("now_price")+(rd.nextDouble()*rs.getDouble("now_price")*0.2-rs.getDouble("now_price")*0.1))));
					dc.query(psta);
					}
				}
				}
				else {
				psta=dc.getconn().prepareStatement(
						"select asset from individual_account"
						+ " where ID=?");
				psta.setString(1, iacc.getID());
				rs=dc.query(psta);
				if(rs.next()) {
					iacc.setasset(rs.getDouble("asset"));
					int aimst=rd.nextInt(720);
					int aimnum=(rd.nextInt(36)+5)*100;
					psta=dc.getconn().prepareStatement(
							"select * from stock "
							+ " order by turnover desc limit ?, 1 ");
					psta.setInt(1, aimst);
					rs=dc.query(psta);
					if(rs.next()&&rs.getDouble("now_price")*aimnum<=iacc.getasset()){
						psta=dc.getconn().prepareStatement(
								"delete from pre_trading");
						dc.delete(psta);
						psta=dc.getconn().prepareStatement(
								"call purchase(?,?,?,?)");
						psta.setString(1, iacc.getID());
						psta.setString(2, rs.getString("ID"));
						psta.setInt(3, aimnum);
						psta.setDouble(4, Double.valueOf(String.format("%.2f",rs.getDouble("now_price")+(rd.nextDouble()*rs.getDouble("now_price")*0.2-rs.getDouble("now_price")*0.1))));
						dc.query(psta);
					}
				}
				}
			} catch (Exception e) {
				e.printStackTrace();
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
		case "USA":url_str = "http://web.juhe.cn:8080/finance/stock/usaall?key=06f6390127aa3abcbbba25e21c9740e4&page=";break;
		case "HongKong":url_str = "http://web.juhe.cn:8080/finance/stock/hkall?key=06f6390127aa3abcbbba25e21c9740e4&page=";break;
		case "ShenZhen":url_str = "http://web.juhe.cn:8080/finance/stock/szall?key=06f6390127aa3abcbbba25e21c9740e4&page=";break;
		case "ShangHai":url_str = "http://web.juhe.cn:8080/finance/stock/shall?key=06f6390127aa3abcbbba25e21c9740e4&page=";break;
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
    			if(st.getnow_price()==0)
    				st.setnow_price(10);
    			st.settype(country);
    			st.setissue_circulation((rd.nextInt(400)+20)*1000);
    			st.setissue_price(st.getnow_price());
    			if(upd)
    			st.publish();
    			st.newday();
    		}
    	}		   
    }
}
 
