package com.znph.saas.sys.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.znph.core.common.bean.Result;
import com.znph.core.common.constant.CommonConstant;
import com.znph.core.common.util.Strings;
import com.znph.saas.sys.dto.CriterionDto;
import com.znph.saas.sys.entity.Code;
import com.znph.saas.sys.entity.Table;
import com.znph.saas.sys.service.ZnphCodeService;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author minco
 * @since 2017-09-15
 */
@Controller
@RequestMapping("/znph/code")
public class ZnphCodeController {

	static Logger logger = LoggerFactory.getLogger(ZnphCodeController.class);

	@Resource
	private ZnphCodeService service;

	@ResponseBody
	@RequestMapping(value = "excute", method = RequestMethod.POST)
	public Result excuteSql(@RequestBody CriterionDto criterionDto) {

		Code code = service.getCode(criterionDto.getIdentity());
		if (code == null || Strings.isBlank(code.getTableName())) {
			return Result.failure("接口不存在！");
		}
		
		if(CommonConstant.status_false.equals(code.getStatus())) {
			return Result.failure("该接口被禁用！");
		}
		

		return service.excuteSql(code.getTableName(), criterionDto, code.getCodeType());
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public Result saveTable(@RequestBody Table table) {

		return service.createTable(table);
	}

}
