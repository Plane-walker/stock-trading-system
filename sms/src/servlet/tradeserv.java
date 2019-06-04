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
		request.setAttribute("now_price", String.format("%.2f",st.getnow_price()));
		request.setAttribute("upsanddowns", String.format("%.2f",st.getupsanddowns()));
		st.getbs();
		String[] sname=st.getsellname();
		String[] bname=st.getbuyname();
		double[] sp=st.getsellerp();
		double[] bp=st.getbuyerp();
		int[] sn=st.getsellern();
		int[] bn=st.getbuyern();
		for(int i=0;i<5;i++) {
			if(sp[i]!=-1) {
				request.setAttribute("sname"+i, sname[i]);
			request.setAttribute("sp"+i, String.format("%.2f",sp[i]));
			request.setAttribute("sn"+i, sn[i]);
			}
			else {
				request.setAttribute("sname"+i, "--");
				request.setAttribute("sp"+i, "--");
				request.setAttribute("sn"+i, "--");
			}
			if(bp[i]!=-1) {
				request.setAttribute("bname"+i, bname[i]);
			request.setAttribute("bp"+i, String.format("%.2f",bp[i]));
			request.setAttribute("bn"+i, bn[i]);
			}
			else {
				request.setAttribute("bname"+i, "--");
				request.setAttribute("bp"+i, "--");
				request.setAttribute("bn"+i, "--");
			}
		}
		
		request.getRequestDispatcher("/trading").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}
}
