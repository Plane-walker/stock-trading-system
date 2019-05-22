package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import account.account;

/**
 * Servlet implementation class logserv
 */
@WebServlet("/logserv")
public class logserv extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public logserv() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userID=request.getParameter("userID");
        String password=request.getParameter("password");
        account acc=new account();
        acc.login(userID, password);
        if(acc.getID()==null) {
        request.setAttribute("error", "用户名或者密码错误");
        request.setAttribute("userID", userID);
        request.setAttribute("password", password);
        request.getRequestDispatcher("login").forward(request, response);
        }
        else {
        	HttpServletRequest req = (HttpServletRequest)request;
        	HttpSession session = req.getSession();
        	session.setAttribute("ID", acc.getID());
        	session.setAttribute("name", acc.getname());
        	request.getRequestDispatcher("main").forward(request, response);
        }
	}

}
