package com.znph.saas.shiro;

import com.znph.saas.constant.SubjectConstant;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class AnonFilter extends org.apache.shiro.web.filter.authc.AnonymousFilter {
	
	static Logger logger = LoggerFactory.getLogger(AnonFilter.class);
	
	@Override
	protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) {
		System.out.println("我进来了---------------------------------------------------------------");
		Subject subject = SecurityUtils.getSubject();
		if (subject != null) {
			Session session = subject.getSession();
			session.setAttribute(SubjectConstant.KEY_ACCESS_IP, request.getRemoteAddr());
			logger.debug("remoteAddr: "+request.getRemoteAddr());
			String userAgent = HttpServletRequest.class.cast(request).getHeader("user-agent");
			session.setAttribute(SubjectConstant.KEY_ACCESS_USER_AGENT, userAgent);
			String xRealIp = HttpServletRequest.class.cast(request).getHeader("X-real-ip");
			if(StringUtils.isNotBlank(xRealIp)) {
				if (xRealIp.contains(",")) {
					xRealIp = xRealIp.substring(0, xRealIp.indexOf(",")).trim();
				}
				session.setAttribute(SubjectConstant.KEY_ACCESS_IP, xRealIp);
				logger.debug("xRealIp: "+request.getRemoteAddr());
			}
		}
		return super.onPreHandle(request, response, mappedValue);
	}
	
}