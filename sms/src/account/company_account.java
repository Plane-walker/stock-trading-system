package account;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;

import database.dbconnect;

public class company_account extends account{

	@Override
	public String register(HttpServletRequest request) {
		String error=null;
		String userID=request.getParameter("userID");
		String username=request.getParameter("username");
        String country=request.getParameter("country");
		String pwHash=getmd5(request.getParameter("password"));
		if(ID == null || ID.trim().equals(""))
			error="鐢ㄦ埛ID涓嶈兘涓虹┖锛�";
		else if (name == null || name.trim().equals("")) 
			error="鐢ㄦ埛鍚嶄笉鑳戒负绌猴紒";
		else if (!request.getParameter("password").matches("[^\\u3400-\\u9FFF]{5,18}"))
				error="瀵嗙爜蹇呴』涓�5-18浣嶏紝涓斿繀椤诲悓鏃跺寘鍚ぇ灏忓啓瀛楁瘝鍜屾暟瀛楋紒";
		else if (!request.getParameter("repassword").equals(request.getParameter("password"))) 
				error="涓ゆ瀵嗙爜涓嶄竴鑷达紒";
		else {
			dbconnect dc=null;
			PreparedStatement psta=null;
			ResultSet rs = null;
			try{
				dc=new dbconnect();
				psta=dc.getconn().prepareStatement(
						"select ID from company_account"
						+ " where ID=?");
				psta.setString(1, ID);
				rs=dc.query(psta);
				if(!rs.next()) {
				psta=dc.getconn().prepareStatement(
						"insert into company_account(ID,company_name,pwHash,country)"
						+ " value(?,?,?,?) ");
				psta.setString(1, userID);
				psta.setString(2, username);
				psta.setString(3, pwHash);
				psta.setString(4, country);
				dc.add(psta);
				ID=userID;
				name=username;
				}
				else {
					error="鐢ㄦ埛鍚嶅凡瀛樺湪锛�";
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
			}
		return error;
	}

	@Override
	public String login(HttpServletRequest request) {
		String error=null;
		String userID=request.getParameter("userID");
		String pwHash=getmd5(request.getParameter("password"));
		dbconnect dc=null;
		PreparedStatement psta=null;
		ResultSet rs = null;
		try{
			dc=new dbconnect();
			psta=dc.getconn().prepareStatement(
					"select ID,company_name from company_account"
					+ " where ID=? and pwHash=?");
			psta.setString(1, userID);
			psta.setString(2, pwHash);
			rs=dc.query(psta);
			if(rs.next()) {
				this.ID=rs.getString("ID");
				this.name=rs.getString("name");
			}
			else
				error="鐢ㄦ埛鍚嶆垨瀵嗙爜閿欒";
		}catch(Exception e) {
			e.printStackTrace();
		}
		return error;
	}
	public String publish(HttpServletRequest request)
	{
		String error=null;
		String ID=request.getParameter("userID");
		String name=request.getParameter("stockname");
		Int issue_circulation=getParameter("circulation");
		Double issue_price=getParameter("stockprice");
		Int remain=getParameter("stocknum");
			dbconnect dc=null;
			PreparedStatement psta=null;
			ResultSet rs = null;
			try{
				dc=new dbconnect();
				psta=dc.getconn().prepareStatement(
						"select ID from company_account"
						+ " where ID=? and name=?");
				psta.setString(1, ID);
				psta.setString(2, name);
				rs=dc.query(psta);
				if(!rs.next()) {
				psta=dc.getconn().prepareStatement(
						"insert into company_account(ID,name,issue_time,issue_circulation,issue_price,remain)"
						+ " value(?,?,now(),?,?,?) ");
				psta.setString(1, ID);
				psta.setString(2, name);
				psta.setString(3, issue_time);
				psta.setString(4, issue_circulation);
				psta.setString(5, issue_price);
				psta.setString(6, remain);
				dc.add(psta);
				this.ID=ID;
				this.name=name;
				}
				else {
					error="请勿重复发布股票！";
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		return error;
		
	}
}
