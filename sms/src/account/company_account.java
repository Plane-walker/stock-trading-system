package account;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;

import database.dbconnect;

public class company_account extends account{

	@Override
	public String register(HttpServletRequest request) {
		String error=null;
		String ID=request.getParameter("userID");
		String name=request.getParameter("username");
        String country=request.getParameter("country");
		String pwHash=getmd5(request.getParameter("password"));
		if(ID == null || ID.trim().equals(""))
			error="用户ID不能为空！";
		else if (name == null || name.trim().equals("")) 
			error="用户名不能为空！";
		else if (!request.getParameter("password").matches("^(?!.*[\\u4e00-\\u9fa5])(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{5,18}$"))
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
						"select ID from individual_account"
						+ " where ID=? and pwHash=?");
				psta.setString(1, ID);
				psta.setString(2, pwHash);
				rs=dc.query(psta);
				if(!rs.next()) {
				psta=dc.getconn().prepareStatement(
						"insert into company_account(ID,name,pwHash,country)"
						+ " value(?,?,?,?) ");
				psta.setString(1, ID);
				psta.setString(2, name);
				psta.setString(3, pwHash);
				psta.setString(4, country);
				dc.add(psta);
				this.ID=ID;
				this.name=name;
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
