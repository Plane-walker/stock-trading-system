package account;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.http.HttpServletRequest;

import database.dbconnect;

public class individual_account extends account{
	double asset=0;
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
		else if (!request.getParameter("password").matches("[^\\u3400-\\u9FFF]{5,18}"))
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
					+ " where ID=?");
			psta.setString(1, userID);
			rs=dc.query(psta);
			if(!rs.next()) {
			psta=dc.getconn().prepareStatement(
					"insert into individual_account(ID,name,pwHash,gender,asset,country)"
					+ " value(?,?,?,?,20000,?) ");
			psta.setString(1, userID);
			psta.setString(2, username);
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
	public String purchase(HttpServletRequest request) {
		String info=null;
		double cost=Integer.valueOf(request.getParameter("number"))*Double.valueOf(request.getParameter("price"));
		dbconnect dc=null;
		PreparedStatement psta=null;
		ResultSet rs = null;
		try {
			dc=new dbconnect();
			psta=dc.getconn().prepareStatement(
					"delete from pre_trading");
			dc.delete(psta);
			psta=dc.getconn().prepareStatement(
					"select asset from individual_account"
					+ " where ID=?");
			psta.setString(1, ID);
			rs=dc.query(psta);
			if(rs.next()) {
				asset=rs.getDouble("asset");
			}
			System.out.println(asset);
			if(asset<cost) {
				info="资金不足，请充值";
			}
			else {
				asset-=cost;
				psta=dc.getconn().prepareStatement(
						"call purchase(?,?,?,?)");
				psta.setString(1, ID);
				psta.setString(2, request.getParameter("stockID"));
				psta.setInt(3, Integer.valueOf(request.getParameter("number")));
				psta.setDouble(4, Double.valueOf(request.getParameter("price")));
				dc.query(psta);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return info;
	}
	public String sell(HttpServletRequest request) {
		String info=null;
		int num=Integer.valueOf(request.getParameter("number"));
		dbconnect dc=null;
		PreparedStatement psta=null;
		ResultSet rs = null;
		try {
			dc=new dbconnect();
			psta=dc.getconn().prepareStatement(
					"delete from pre_trading");
			dc.delete(psta);
			psta=dc.getconn().prepareStatement(
					"select number from own"
					+ " where acc_ID=? and sto_ID=?");
			psta.setString(1, ID);
			psta.setString(2,request.getParameter("stockID"));
			rs=dc.query(psta);
			int own_num=0;
			if(rs.next()) {
				own_num=rs.getInt("number");
			}
			if(own_num<num) {
				info="数量不足";
			}
			else {
				psta=dc.getconn().prepareStatement(
						"call sell(?,?,?,?)");
				psta.setString(1, ID);
				psta.setString(2, request.getParameter("stockID"));
				psta.setInt(3, Integer.valueOf(request.getParameter("number")));
				psta.setDouble(4, Double.valueOf(request.getParameter("price")));
				dc.query(psta);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return info;
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
	public double getasset() {
		return asset;
	}
}