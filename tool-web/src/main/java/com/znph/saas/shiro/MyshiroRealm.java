package com.znph.saas.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.znph.saas.constant.SubjectConstant;
import com.znph.saas.sys.entity.AuthUser;
import com.znph.saas.sys.service.AuthUserService;



/**
* @author Minco
* @date 2017年8月9日 上午10:32:07
* 
*/
public class MyshiroRealm extends AuthorizingRealm{
	
	 	static Logger logger = LoggerFactory.getLogger(MyshiroRealm.class);
	
		public static final String LOGIN_SUCCESS = "LOGIN_SUCCESS";

	    @Autowired
	    private AuthUserService authUserService;
	    

	    @Override
	    public boolean supports(AuthenticationToken token) {

	        return token instanceof UsernamePasswordToken;
	    }


	    @Override
	    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
	    	
	    	
			UsernamePasswordToken token = UsernamePasswordToken.class.cast(authenticationToken);
			String username = token.getUsername();
			String password = new String(token.getPassword());
			
			logger.debug("用户名密码登录： " + username + "|" + password);
			AuthUser authUser = authUserService.signIn(username, password);
			
			Session session = SecurityUtils.getSubject().getSession();
			session.setAttribute(SubjectConstant.KEY_ACCESS_USER_ID, authUser.getId());
			session.setAttribute(SubjectConstant.KEY_ACCESS_USER_IDENTITY, AuthUser.class);
			session.setAttribute(LOGIN_SUCCESS, "用户登录成功");
			session.setAttribute(SubjectConstant.Login_Type, SubjectConstant.Login_Type_Web);
			System.out.println("用户名密码登录成功： " + token.getPrincipal().toString() + "|" + token.getCredentials().toString()+"|"+token.getPassword().toString());
			return new SimpleAuthenticationInfo(username, password, getName());

	    }

	    @Override
	    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {

	    	Session session = SecurityUtils.getSubject().getSession();
			session.setAttribute(SubjectConstant.KEY_ACCESS_USER_IDENTITY, principal.getClass().getName());
			return new SimpleAuthorizationInfo();

	    }

}
