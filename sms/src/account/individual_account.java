package account;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;

import database.dbconnect;

public class individual_account extends account{
	@Override
	public String register(HttpServletRequest request) {
		String error=null;
		String userID=request.getParameter("userID");
		String username=request.getParameter("username");
        String gender=request.getParameter("gender");
        String country=request.getParameter("country");
		String pwHash=getmd5(request.getParameter("password"));
		if(userID == null || userID.trim().equals(""))
			error="用户ID不能为空！";
		else if (username == null || username.trim().equals("")) 
			error="用户名不能为空！";
		else if (!request.getParameter("password").matches("^(?!.*[\\u4e00-\\u9fa5])(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{5,18}$"))
				error="密码必须为5-18位，且必须同时包含大小写字母和数字！";
		else if (!request.getParameter("repassword").equals(request.getParameter("password"))) 
				error="两次密码不一致！";
		/*else if (email != null && !email.trim().equals("")&&!email.matches("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$")) 
				error="邮箱格式不正确！";

		else if(phone != null && !phone.trim().equals("")&&!phone.matches("^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\\\d{8}$")) 
				errors.put("phone","不存在此号码！");*/
		else {
		dbconnect dc=null;
		PreparedStatement psta=null;
		ResultSet rs = null;
		try{
			dc=new dbconnect();
			psta=dc.getconn().prepareStatement(
					"select ID from individual_account"
					+ " where ID=? and pwHash=?");
			psta.setString(1, ID);
			psta.setString(2, pwHash);
			rs=dc.query(psta);
			if(!rs.next()) {
			psta=dc.getconn().prepareStatement(
					"insert into individual_account(ID,name,pwHash,gender,asset,country)"
					+ " value(?,?,?,?,20000,?) ");
			psta.setString(1, ID);
			psta.setString(2, name);
			psta.setString(3, pwHash);
			psta.setString(4, gender);
			psta.setString(5, country);
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
	public void purchase() {
		
	}
	public void sell() {
		
	}
}
