package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import account.account;

/**
 * Servlet implementation class registerserv
 */
@WebServlet("/registerserv")
public class registerserv extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public registerserv() {
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
		String username=request.getParameter("username");
        String password=request.getParameter("password");
        String gender=request.getParameter("gender");
        String country=request.getParameter("country");
        account acc=new account();
        acc.register(userID, username,password,gender,country);
        if(acc.getID()==null) {
        request.setAttribute("error", "ID已存在");
        request.setAttribute("userID", userID);
        request.setAttribute("username", username);
        request.setAttribute("password", password);
        request.setAttribute("gender", gender);
        request.setAttribute("country", country);
        request.getRequestDispatcher("register").forward(request, response);
        }
        else {
        	request.setAttribute("username", acc.getname());
        	request.getRequestDispatcher("main").forward(request, response);
        }
	}

}
