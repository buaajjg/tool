package com.znph.saas.shiro;

import java.util.Collection;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.authc.pam.UnsupportedTokenException;
import org.apache.shiro.realm.Realm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.znph.saas.constant.SubjectConstant;

/**
* @author Minco
* @date 2017年10月9日 上午9:59:20
* 
*/
public class MyModularRealmAuthenticator extends ModularRealmAuthenticator {
	
    private final Logger log = LoggerFactory.getLogger(MyModularRealmAuthenticator.class);


    @Override
    protected AuthenticationInfo doMultiRealmAuthentication(Collection<Realm> realms, AuthenticationToken token) throws AuthenticationException {
        Realm uniqueRealm = getUniqueRealm(realms, token);
        if (uniqueRealm == null) {
            throw new UnsupportedTokenException("没有匹配类型的realm");
        }
        return uniqueRealm.getAuthenticationInfo(token);
    }

    /**
     * 判断realms是否匹配,并返回唯一的可用的realm,否则返回空
     *
     * @param realms realm集合
     * @param token  登陆信息
     * @return 返回唯一的可用的realm
     */
    private Realm getUniqueRealm(Collection<Realm> realms, AuthenticationToken token) {
    	MyLoginTypeToken myLoginTypeToken = (MyLoginTypeToken)token;
        for (Realm realm : realms) {
         if(realm.getName().indexOf(MyshiroRealm.class.getName()) >= 0   && SubjectConstant.Login_Type_Web.equals(myLoginTypeToken.getLoginType())) {
        		return realm;
        	}
        }
        log.error("一个可用的realm都没有找到......");
        return null;
    }

}
