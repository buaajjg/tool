package com.znph.saas.sys.service.impl;

import java.util.Date;

import org.apache.shiro.authc.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.znph.core.common.util.Encrypts;
import com.znph.saas.sys.entity.AuthUser;
import com.znph.saas.sys.mapper.AuthUserMapper;
import com.znph.saas.sys.service.AuthUserService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author minco
 * @since 2017-09-15
 */
@Service
public class AuthUserServiceImpl extends ServiceImpl<AuthUserMapper, AuthUser> implements AuthUserService {

	
	@Override
	@Transactional
	public AuthUser signIn(String username, String password) {
		
		EntityWrapper<AuthUser> entityWrapper = new EntityWrapper<>();
		entityWrapper.eq("user_name", username);
		AuthUser authUser = selectOne(entityWrapper);
		if(authUser == null){
			throw new  AuthenticationException("用户名不存在");
		}
		
		String encryptPassword = encrypt(password);
		if(!encryptPassword.equals(authUser.getPwd())){
			
			throw new  AuthenticationException("密码不正确");
		}
		
		if(authUser.getLocked() == 1){
			throw new  AuthenticationException("该账号已被禁用！");
		}
		
		authUser.setLastLoginTime(new Date());
		updateById(authUser);		
		return authUser;
	}
	
	private String encrypt(String password) {
		return Encrypts.md5(password);
	}

	
	

	
}
