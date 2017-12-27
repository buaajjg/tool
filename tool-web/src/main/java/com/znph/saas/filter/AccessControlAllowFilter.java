package com.znph.saas.filter;

import com.znph.core.common.property.PropertyHolder;
import com.znph.core.common.util.Collections;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


public class AccessControlAllowFilter implements Filter {

	private List<String> allowOriginList;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		List<String> allowOrigin = getAllowOrigin();
		String origin = httpRequest.getHeader("Origin");
		if (allowOrigin.contains(origin) || "true".equals(PropertyHolder.get("znph.allowOrigin.debug"))) {
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			// 允许跨域访问
			httpResponse.setHeader("Access-Control-Allow-Origin", origin);
			httpResponse.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Cookie");
			httpResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
			httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
		}
		chain.doFilter(request, response);
	}

	private List<String> getAllowOrigin() {
		if (allowOriginList == null) {
			String string = PropertyHolder.get("znph.allowOrigin");
			allowOriginList = Collections.arrayList(string.split(","));
		}
		return allowOriginList;
	}

	@Override
	public void destroy() {
	}

}
