package com.znph.saas.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.znph.saas.sys.entity.AuthUser;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author minco
 * @since 2017-09-15
 */
public interface AuthUserService extends IService<AuthUser> {

	/**
	 * 登录
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	AuthUser signIn(String username, String password);

}
