package stock;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import database.dbconnect;

public class stock {
	String name;
	String ID;
	String issue_time;
	int issue_circulation;
	double issue_price;
	String type;
	double open_price;
	double closing_price;
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
	public void inti(HttpServletRequest request,String ID) {
		this.ID=ID;
		name=request.getParameter("name");
		type=request.getParameter("type");
		issue_circulation=Integer.valueOf(request.getParameter("issue_circulation"));
		issue_price=Double.valueOf(request.getParameter("issue_price"));
		open_price=Double.valueOf(request.getParameter("issue_price"));
		open_price=Double.valueOf(request.getParameter("issue_price"));
		max_price=Double.valueOf(request.getParameter("issue_price"));
		min_price=Double.valueOf(request.getParameter("issue_price"));
		turnover=0;
		now_price=Double.valueOf(request.getParameter("issue_price"));
		upsanddowns=0;
	}
	public String publish() {
		String error=null;
		dbconnect dc=null;
		PreparedStatement psta=null;
		ResultSet rs = null;
		try{
			dc=new dbconnect();
			psta=dc.getconn().prepareStatement(
					"select ID from stock"
					+ " where ID=?");
			psta.setString(1, ID);
			rs=dc.query(psta);
			if(!rs.next()) {
			psta=dc.getconn().prepareStatement(
					"insert into stock(ID,name,issue_time,issue_circulation,issue_price,type,now_price,turnover,upsanddowns)"
					+ " value(?,?,now(),?,?,?,?,?,?) ");
			psta.setString(1, ID);
			psta.setString(2, name);
			psta.setInt(3, issue_circulation);
			psta.setDouble(4, issue_price);
			psta.setString(5, type);
			psta.setDouble(6, now_price);
			psta.setDouble(7, turnover);
			psta.setDouble(8, upsanddowns);
			dc.add(psta);
			}
			psta=dc.getconn().prepareStatement(
					"select sto_ID from for_trading"
					+ " where sto_ID=? and acc_ID='admin'");
			psta.setString(1, ID);
			rs=dc.query(psta);
			if(!rs.next()) {
			psta=dc.getconn().prepareStatement(
					"insert into for_trading(sto_ID,acc_ID,datetime,transcation,price,number)"
					+ " value(?,'admin',now(),'sell',?,?) ");
			psta.setString(1, ID);
			psta.setDouble(2, issue_price);
			psta.setInt(3, issue_circulation);
			dc.add(psta);
			}
			else {
				error="请勿重复发布股票！";
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	return error;		
	}
	public void newday() {
		dbconnect dc=null;
		PreparedStatement psta=null;
		ResultSet rs = null;
		try{
			dc=new dbconnect();
			psta=dc.getconn().prepareStatement(
					"select ID from daily_info"
					+ " where ID=? and date=curdate()");
			psta.setString(1, ID);
			rs=dc.query(psta);
			if(!rs.next()) {
			psta=dc.getconn().prepareStatement(
					"insert into daily_info(ID,date,open_price,closing_price,max_price,min_price)"
					+ " value(?,curdate(),?,?,?,?) ");
			psta.setString(1, ID);
			psta.setDouble(2, now_price);
			psta.setDouble(3, now_price);
			psta.setDouble(4, now_price);
			psta.setDouble(5, now_price);
			dc.add(psta);
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
	public double getnow_price() {
		return now_price;
	}
	public double getupsanddowns() {
		return upsanddowns;
	}
	public String getissue_time() {
		return issue_time;
	}
	public int getissue_circulation() {
		return issue_circulation;
	}
	public double getissue_price() {
		return issue_price;
	}
	public String type() {
		return type;
	}
	public double getopen_price() {
		return open_price;
	}
	public double getclosing_price() {
		return closing_price;
	}
	public double getmax_price() {
		return max_price;
	}
	public double getmin_price() {
		return min_price;
	}
	public int getturnover() {
		return turnover;
	}
	public void setID(String ID) {
		this.ID=ID;
	}
	public void setname(String name) {
		if(this.name==null)
			if(name.length()<50)
			this.name=name;
			else
				this.name=name.substring(0, 49);
	}
	public void setnow_price(double now_price) {
		this.now_price=now_price;
	}
	public void setupsanddowns(double upsanddowns) {
		this.upsanddowns=upsanddowns;
	}
	public void setissue_time(String issue_time) {
		this.issue_time=issue_time;
	}
	public void setissue_circulation(int issue_circulation) {
		this.issue_circulation=issue_circulation;
	}
	public void setissue_price(double issue_price) {
		this.issue_price=issue_price;
	}
	public void settype(String type) {
		this.type=type;
	}
	public void setopen_price(double open_price) {
		this.open_price=open_price;
	}
	public void setclosing_price(double closing_price) {
		this.closing_price=closing_price;
	}
	public void setmax_price(double max_price) {
		this.max_price=max_price;
	}
	public void setmin_price(double min_price) {
		this.min_price=min_price;
	}
	public void setturnover(int turnover) {
		this.turnover=turnover;
	}
}
