package servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import simulation.simulation;

/**
 * Servlet implementation class simuserv
 */
@WebServlet("/simuserv")
public class simuserv extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private simulation sml=null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	public simuserv() {
		inti();
	}
	public void inti() {
		sml=new simulation();
		sml.start();
	}
}
