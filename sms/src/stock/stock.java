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
	double open_price;
	double max_price;
	double min_price;
	int turnover;
	double now_price;
	double upsanddowns;
	public void gettop(int size) {
		dbconnect dc=null;
		PreparedStatement psta=null;
		ResultSet rs = null;
		try {
			dc=new dbconnect();
			psta=dc.getconn().prepareStatement(
					"select ID,name,remain,open_price,max_price,min_price,turnover,now_price,upsanddowns from stock natural join daily_info "
					+ " order by turnover desc limit ?");
			psta.setInt(1, size);
			rs=dc.query(psta);
			dealrs(rs,size);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void getbyID(String ID) {
		dbconnect dc=null;
		PreparedStatement psta=null;
		ResultSet rs = null;
		try {
			dc=new dbconnect();
			psta=dc.getconn().prepareStatement(
					"select ID,name,remain,open_price,max_price,min_price,turnover,now_price,upsanddowns from stock natural join daily_info "
					+ " where ID=?");
			psta.setString(1, ID);
			rs=dc.query(psta);
			dealrs(rs,1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void dealrs(ResultSet rs,int size) throws SQLException{
		try {
			for(int i=0;i<size&&rs.next();i++) 
			if(i==size-1){
				this.ID=rs.getString("ID");
				this.name=rs.getString("name");
				this.remain=rs.getInt("remain");
				this.open_price=rs.getDouble("open_price");
				this.max_price=rs.getDouble("max_price");
				this.min_price=rs.getDouble("min_price");
				this.turnover=rs.getInt("turnover");
				this.now_price=rs.getDouble("now_price");
				this.upsanddowns=rs.getDouble("upsanddowns");
			}
		} catch (SQLException e) {
			throw e;
		}
	}
	public String getID() {
		return ID;
	}
	public String getname() {
		return name;
	}
	public double getnow_price() {
		return now_price;
	}
	public double getupsanddowns() {
		return upsanddowns;
	}
}
