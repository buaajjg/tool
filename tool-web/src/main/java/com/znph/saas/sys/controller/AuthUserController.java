package com.znph.saas.sys.controller;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.znph.core.common.bean.Result;
import com.znph.core.common.util.Strings;
import com.znph.saas.constant.SubjectConstant;
import com.znph.saas.shiro.MyLoginTypeToken;
import com.znph.saas.shiro.MyshiroRealm;
import com.znph.saas.sys.service.AuthUserService;
import com.znph.saas.sys.vo.UserLoginVo;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author minco
 * @since 2017-09-15
 */
@Controller
@RequestMapping("/sys/authUser")
public class AuthUserController {

	static Logger logger = LoggerFactory.getLogger(AuthUserController.class);

	@Resource
	private AuthUserService authUserService;

	@RequestMapping(value = "auth", method = RequestMethod.POST)
	@ResponseBody
	public Result webLogin(@RequestBody UserLoginVo userLoginVo) {

		if (Strings.isBlank(userLoginVo.getUsername()) || Strings.isBlank(userLoginVo.getPwd().trim())) {
			return Result.failure("参数不合法");
		}

		MyLoginTypeToken myLoginTypeToken = new MyLoginTypeToken(userLoginVo.getUsername(), userLoginVo.getPwd().trim(),
				SubjectConstant.Login_Type_Web);

		Subject subject = SecurityUtils.getSubject();
		if (!subject.isAuthenticated()) {
			subject.login(myLoginTypeToken);
			System.out.println(subject.getSession().getAttribute(SubjectConstant.KEY_ACCESS_IP));
			String loginstatus = (String) subject.getSession().getAttribute(MyshiroRealm.LOGIN_SUCCESS);
			if (Strings.isNotBlank(loginstatus)) {
				return Result.ok();
			}
		}

		return Result.failure("登录失败！");

	}

	@RequestMapping(value = "go/login", method = RequestMethod.GET)
	public ModelAndView jumpView() {
		return new ModelAndView("login2");
	}

}
