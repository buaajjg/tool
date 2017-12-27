package com.znph.saas.sys.controller;


import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.znph.core.common.bean.Result;
import com.znph.core.common.utlis.JsonUtils;
import com.znph.saas.sys.service.TableService;
import com.znph.saas.sys.vo.TableSearchVo;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author minco
 * @since 2017-11-28
 */
@Controller
@RequestMapping("/sys/table")
public class TableController {
	
	@Resource
	private TableService service;
	
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView jumpList() {
		
		ModelAndView modelAndView = new ModelAndView("snd/table/tableList");
		// 跳转到配肥站列表
			String jsonString = JSONObject.toJSONStringWithDateFormat(service.searchTable(new TableSearchVo()),"yyyy-MM-dd HH:mm:ss", JsonUtils.features);
			modelAndView.addObject("object",jsonString);

		return modelAndView;
	}
	
	
	@RequestMapping(value = "search", method = RequestMethod.POST)
	@ResponseBody
	public Result search(@RequestBody TableSearchVo tableSearchVo) {

		return Result.ok(service.searchTable(tableSearchVo));
	}
	
	
}
