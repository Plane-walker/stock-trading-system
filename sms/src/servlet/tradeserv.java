package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import stock.stock;
/**
 * Servlet implementation class searchserv
 */
@WebServlet("/tradeserv")
public class tradeserv extends HttpServlet {

	private static final long serialVersionUID = 1L;
	 /**
     * @see HttpServlet#HttpServlet()
     */
    public tradeserv() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		stock st=new stock();
		st.getbyID(request.getParameter("stockID"));
		request.setAttribute("stockID", st.getID());
		request.setAttribute("stockname", st.getname());
		request.setAttribute("stockprice", st.getnow_price());
		request.getRequestDispatcher("WEB-INF/sresult.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}
}
