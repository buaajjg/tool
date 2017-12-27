package com.znph.saas.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
* @author Minco
* @date 2017年10月9日 上午9:51:07
* 
*/
public class MyLoginTypeToken extends UsernamePasswordToken {
	


	private static final long serialVersionUID = 4832580731179101407L;
	/**
    *登陆类型
    */
    private String loginType;

    public MyLoginTypeToken(String username, String password,String loginType) {
        super(username, password);
        this.loginType = loginType;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

}
