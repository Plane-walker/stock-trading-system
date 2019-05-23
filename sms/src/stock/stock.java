package stock;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.dbconnect;

public class stock {
	String name;
	String ID;
	String issue_time;
	int issue_circluation;
	double issue_price;
	int remain;
	String type;
	public void gettop() {
		dbconnect dc=null;
		PreparedStatement psta=null;
		ResultSet rs = null;
		try {
			dc=new dbconnect();
			psta=dc.getconn().prepareStatement(
					"select ID,name from stock natural join daily_info "
					+ " where turnover=(select max(turnover) from stock natural join daily_info)");
			this.ID=rs.getString(ID);
			this.name=rs.getString(name);
			rs=dc.query(psta);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public String getID() {
		return ID;
	}
	public String getname() {
		return name;
	}
}
