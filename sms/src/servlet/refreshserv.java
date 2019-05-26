package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import stock.stock;

/**
 * Servlet implementation class refreshserv
 */
@WebServlet("/refreshserv")
public class refreshserv extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public refreshserv() {
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
		int size=Integer.valueOf(request.getParameter("size"));
		stock[] st=new stock[size];
		JSONArray jsona = new JSONArray();
		if(request.getParameter("s_ID")==null||request.getParameter("s_ID").length()==0)
		for(int i=0;i<size;i++) {
			st[i]=new stock();
			st[i].gettop(i+1);
			JSONObject json=new JSONObject();
			json.put("ID", st[i].getID());
			json.put("name", st[i].getname());
			json.put("now_price", st[i].getnow_price());
			json.put("upsanddowns", st[i].getupsanddowns());
			jsona.put(json);
		}
		else {
			st[0]=new stock();
			st[0].getbyID(request.getParameter("s_ID"));
			JSONObject json=new JSONObject();
			json.put("ID", st[0].getID());
			json.put("name", st[0].getname());
			json.put("now_price", st[0].getnow_price());
			json.put("upsanddowns", st[0].getupsanddowns());
			jsona.put(json);
		}
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json; charset=utf-8");
        response.getWriter().print(jsona.toString());
		
	}

}