package com.quxin.freshfun.spring.config;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SimpleCORSFilter implements Filter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) res;
		HttpServletRequest request = (HttpServletRequest) req;
		//response.setHeader("Access-Control-Allow-Origin", "*");
		if(request.getHeader("origin")!=null&&request.getHeader("origin").contains(".freshfun365.com")) {
			response.setHeader("Access-Control-Allow-Origin", request.getHeader("origin"));
			response.setHeader("Access-Control-Allow-Credentials", "true");
		}

	    response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
	    response.setHeader("Access-Control-Max-Age", "3600");
	    response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
	    chain.doFilter(req, res);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}
	@Override
	public void destroy() {
		
	}

}
