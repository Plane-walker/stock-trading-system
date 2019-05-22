package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import account.account;

public class loginfilter implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse resp = (HttpServletResponse)response;
        HttpServletRequest req = (HttpServletRequest)request;
        HttpSession session = req.getSession();
        String ID = (String)session.getAttribute("ID");
        String uri = req.getRequestURI();
        if(ID==null){
            if(uri.endsWith("login.jsp")){
                chain.doFilter(request, response);
            }else{
                resp.sendRedirect(req.getContextPath()+"/login");
            }    
        }else{
            chain.doFilter(request, response);
        }
	}

}
