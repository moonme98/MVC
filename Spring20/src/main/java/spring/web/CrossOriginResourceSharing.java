package spring.web;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class Test
 */
//@WebFilter("/Test")
public class CrossOriginResourceSharing extends HttpFilter implements Filter {

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		System.out.println("CrossOriginResourceSharing.doFilter()");
		// TODO Auto-generated method stub
		HttpServletResponse res = (HttpServletResponse) response; 
		res.setHeader("Access-Control-Allow-Origin", "*"); //허용대상 도메인
		res.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, PUT"); 
		res.setHeader("Access-Control-Max-Age", "3600"); 
		res.setHeader("Access-Control-Allow-Headers", "x-requested-with, origin, content-type, accept"); 

		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
		System.out.println("CrossOriginResourceSharing.init()");
	}

}
