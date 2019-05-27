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
		System.out.println(request.getParameter("trade_type"));
        if(request.getParameter("trade_type").equals("purchase")) {
        acc.purchase(request);
        }
        else {
        	acc.sell();
        }
	}
}