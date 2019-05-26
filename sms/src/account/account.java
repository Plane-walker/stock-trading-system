package account;

import database.dbconnect;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;

abstract public class account {
	String ID=null;
	String name=null;
	abstract public String register(HttpServletRequest request);
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
					"select ID,name from individual_account"
					+ " where ID=? and pwHash=?");
			psta.setString(1, userID);
			psta.setString(2, pwHash);
			rs=dc.query(psta);
			if(rs.next()) {
				this.ID=rs.getString("ID");
				this.name=rs.getString("name");
			}
			else
				error="用户名或密码错误";
		}catch(Exception e) {
			e.printStackTrace();
		}
		return error;
	}
	public String getID() {
		return ID;
	}
	public String getname() {
		return name;
	}
	String getmd5(String input) {
		String output="";
		try {
			MessageDigest md5=MessageDigest.getInstance("MD5");
			md5.update(input.getBytes());
			byte [] encryContext = md5.digest();
			int i;
            for (int offset = 0; offset < encryContext.length; offset++) {
                i = encryContext[offset];  
                if (i < 0) i += 256;  
                if (i < 16) output+="0";  
                output+=Integer.toHexString(i);  
           } 
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		}
		return output;
	}
}