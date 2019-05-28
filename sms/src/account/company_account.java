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
			error="用户ID不能为空";
		else if (name == null || name.trim().equals("")) 
			error="用户名不能为空！";
		else if (!request.getParameter("password").matches("[^\\u3400-\\u9FFF]{5,18}"))
				error="密码必须为5-18位，且必须同时包含大小写字母和数字！";
		else if (!request.getParameter("repassword").equals(request.getParameter("password"))) 
				error="两次密码不一致！";
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
					error="用户名已存在！";
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
				this.name=rs.getString("company_name");
			}
			else
				error="用户名或密码错误";
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
		int issue_circulation=Integer.valueOf(request.getParameter("circulation"));
		double issue_price=Double.valueOf(request.getParameter("stockprice"));
		int remain=Integer.valueOf(request.getParameter("stocknum"));
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
						"insert into company_account(ID,company_name,issue_time,issue_circulation,issue_price,remain)"
						+ " value(?,?,now(),?,?,?) ");
				psta.setString(1, ID);
				psta.setString(2, name);
				psta.setInt(3, issue_circulation);
				psta.setDouble(4, issue_price);
				psta.setInt(5, remain);
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
