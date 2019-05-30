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
					"select ID,name,turnover,now_price,upsanddowns from stock "
					+ " order by turnover desc limit ?");
			psta.setInt(1, size);
			rs=dc.query(psta);
			genrs(rs,size);
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
					"select ID,name,open_price,max_price,min_price,turnover,now_price,upsanddowns from stock natural join daily_info "
					+ " where ID=?");
			psta.setString(1, ID);
			rs=dc.query(psta);
			detailrs(rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public stock[] getbyname(String name) {
		dbconnect dc=null;
		PreparedStatement psta=null;
		ResultSet rs = null;
		try {
			dc=new dbconnect();
			psta=dc.getconn().prepareStatement(
					"select ID,name,open_price,max_price,min_price,turnover,now_price,upsanddowns from stock natural join daily_info "
					+ " where LOCATE(?, name)>0",ResultSet.TYPE_SCROLL_INSENSITIVE);
			psta.setString(1, name);
			rs=dc.query(psta);
			return partrs(rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public void detailrs(ResultSet rs) throws SQLException{
		try {
			if(rs.next()){
				this.ID=rs.getString("ID");
				this.name=rs.getString("name");
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
	public void genrs(ResultSet rs,int size) throws SQLException{
		try {
			for(int i=0;i<size&&rs.next();i++) 
			if(i==size-1){
				this.ID=rs.getString("ID");
				this.name=rs.getString("name");
				this.turnover=rs.getInt("turnover");
				this.now_price=rs.getDouble("now_price");
				this.upsanddowns=rs.getDouble("upsanddowns");
			}
		} catch (SQLException e) {
			throw e;
		}
	}
	public stock[] partrs(ResultSet rs) throws SQLException{
		try {
			rs.last(); 
			stock[] out=new stock[rs.getRow()];
			rs.beforeFirst(); 
			for(int i=0;rs.next();i++) {
				out[i]=new stock();
				out[i].setID(rs.getString("ID"));
				out[i].setname(rs.getString("name"));
				out[i].setnow_price(rs.getDouble("now_price"));
				out[i].setupsanddowns(rs.getDouble("upsanddowns"));
			}
			return out;
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
	public void setID(String ID) {
		this.ID=ID;
	}
	public void setname(String name) {
		this.name=name;
	}
	public void setnow_price(double now_price) {
		this.now_price=now_price;
	}
	public void setupsanddowns(double upsanddowns) {
		this.upsanddowns=upsanddowns;
	}
}
