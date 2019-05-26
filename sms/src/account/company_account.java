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
			error="用户ID不能为空！";
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
	
}
