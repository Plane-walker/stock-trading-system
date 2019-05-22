package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class dbconnect {
	static String DBDRIVER="com.mysql.cj.jdbc.Driver";
	static String DBURL=""
	static String DBUSER="";
	static String DBPASS="";
	Connection conn=null;
	
	public dbconnect() {
		try{
			Class.forName(DBDRIVER);
			conn=DriverManager.getConnection(DBURL,DBUSER,DBPASS);

		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	public Connection getconn(){
		return conn;
	}
	public ResultSet query(PreparedStatement psta)throws Exception{
		return psta.executeQuery();
	}
	public void update(PreparedStatement psta)throws Exception{
		 psta.executeUpdate();
	}
	public void add(PreparedStatement psta)throws Exception{
		 psta.executeUpdate();
	}
	public void delete(PreparedStatement psta)throws Exception{
		 psta.executeUpdate();
	}
}
