package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import account.account;
import account.company_account;
import account.individual_account;

/**
 * Servlet implementation class dealserv
 */
@WebServlet("/dealserv")
public class dealserv extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		individual_account acc=new individual_account();
		acc.setID((String)session.getAttribute("ID"));
		acc.setname((String)session.getAttribute("name"));
		String info;
        if(request.getParameter("trade_type").equals("purchase")) {
        info=acc.purchase(request);
        }
        else {
        	info=acc.sell(request);
        }
        if(info!=null)
        request.setAttribute("result", info);
        else
        	request.setAttribute("result", "委托成功");
        request.getRequestDispatcher("WEB-INF/dresult.jsp").forward(request, response);
	}
}