package account;

import database.dbconnect;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class account {
	String ID=null;
	String name=null;
	public void register(String ID,String name,String pw,String gender,String country) {
		String pwHash=getmd5(pw);
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
			this.ID=ID;
			this.name=name;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void login(String ID,String pw) {
		String pwHash=getmd5(pw);
		dbconnect dc=null;
		PreparedStatement psta=null;
		ResultSet rs = null;
		try{
			dc=new dbconnect();
			psta=dc.getconn().prepareStatement(
					"select ID,name from individual_account"
					+ " where ID=? and pwHash=?");
			psta.setString(1, ID);
			psta.setString(2, pwHash);
			rs=dc.query(psta);
			if(rs.next()) {
				this.ID=rs.getString("ID");
				this.name=rs.getString("name");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
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