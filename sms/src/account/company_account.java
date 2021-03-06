package account;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;

import database.dbconnect;
import stock.stock;

public class company_account extends account{

	@Override
	public String register(HttpServletRequest request) {
		String error=null;
		String userID=request.getParameter("userID");
		String username=request.getParameter("username");
        String country=request.getParameter("country");
		String pwHash=getmd5(request.getParameter("password"));
		if(userID == null || userID.trim().equals(""))
			error="用户ID不能为空";
		else if (username == null || username.trim().equals("")) 
			error="用户名不能为空！";
		else if (!request.getParameter("password").matches("[^\\u3400-\\u9FFF]{5,18}"))
				error="密码必须为5-18位！";
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
				psta.setString(1, userID);
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
		stock st=new stock();
		st.inti(request, ID);
		error=st.publish();
		st.newday();
		return error;
		
	}

	@Override
	public void getinfo(HttpServletRequest request) {
		String userID=request.getParameter("ID");
		dbconnect dc=null;
		PreparedStatement psta=null;
		ResultSet rs = null;
		try{
			dc=new dbconnect();
			psta=dc.getconn().prepareStatement(
					"select ID,company_name,country from company_account"
					+ " where ID=?");
			psta.setString(1, userID);
			rs=dc.query(psta);
			if(rs.next()) {
				this.ID=rs.getString("ID");
				this.name=rs.getString("company_name");
				this.country=rs.getString("country");
			}
			request.setAttribute("name", name);
			request.setAttribute("country", country);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void register() {
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
				if(ID!=null&&!rs.next()) {
				psta=dc.getconn().prepareStatement(
						"insert into company_account(ID,company_name,pwHash,country)"
						+ " value(?,?,?,?) ");
				psta.setString(1, ID);
				psta.setString(2, name);
				psta.setString(3, getmd5(ID));
				psta.setString(4, country);
				dc.add(psta);
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
	}
}
