package com.znph.saas.sys.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.znph.core.common.bean.Result;
import com.znph.core.common.util.Collections;
import com.znph.core.common.util.Strings;
import com.znph.core.common.utlis.JsonUtils;
import com.znph.saas.sys.entity.Code;
import com.znph.saas.sys.entity.Table;
import com.znph.saas.sys.service.CodeService;
import com.znph.saas.sys.service.TableService;
import com.znph.saas.sys.vo.CodeSearchVo;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author minco
 * @since 2017-11-24
 */
@Controller
@RequestMapping("/sys/code")
public class CodeController {

	@Resource
	private CodeService service;
	
	@Resource
	private TableService tableService;
	
	
	/**
	 * 跳转到接口列表
	 * @param tableId
	 * @return
	 * @author Minco
	 * @date 2017年11月28日
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public ModelAndView jumpList(@RequestParam(value = "tableId") Long tableId) {
		
		Table table = tableService.selectById(tableId);
		
		ModelAndView modelAndView = new ModelAndView("snd/code/codeList");
		
		Map<String, Object>  map = service.searchList(new CodeSearchVo(tableId));
		String jsonString = JSONObject.toJSONStringWithDateFormat(map,"yyyy-MM-dd HH:mm:ss", JsonUtils.features);
		
		Map<String, Object>  tableMap = Collections.hashMap();
		tableMap.put("tableId", table.getId());
		tableMap.put("tableName", table.getTableName());
		String tableJosn = JSONObject.toJSONString(tableMap,JsonUtils.features);

		modelAndView.addObject("object",jsonString);
		modelAndView.addObject("table",tableJosn);
		return modelAndView;
	} 
	
	@RequestMapping(value = "add", method = RequestMethod.GET)
	public ModelAndView jumpAdd(@RequestParam(value = "tableId") String tableId) {
		
		ModelAndView modelAndView = new ModelAndView("snd/code/codeAdd");
		
		Map<String, Object> map = service.getPreAddInfo(Long.parseLong(tableId));
		String tableJosn = JSONObject.toJSONString(map,JsonUtils.features);
		modelAndView.addObject("object", tableJosn);
		
		return modelAndView;
	}
	
	@RequestMapping(value = "test", method = RequestMethod.GET)
	public ModelAndView jumpTest(@RequestParam(value = "id", required = false) Long id) {
		
		
		ModelAndView modelAndView = new ModelAndView("snd/code/codeedit");
		String jsonString = JSONObject.toJSONString(service.getCodeInfo(id), JsonUtils.features);
		modelAndView.addObject("obejct",jsonString);
		return modelAndView;
	}

	@RequestMapping(value = "search", method = RequestMethod.POST)
	@ResponseBody
	public Result search(@RequestBody CodeSearchVo codeSearchVo) {

		return Result.ok(service.searchList(codeSearchVo));
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public Result saveOrUpdate(@RequestBody Code code) {
		
		
		if(code.getId() == null) {
			code.setStatus("1");
			if(Strings.isBlank(code.getCodeName()) || code.getCodeType() == null ) {
				return Result.failure("请完善必填信息！");
			}
		}


		return Result.judge(service.insertOrUpdate(code));
	}
	
	@RequestMapping(value="toggle/{id}/{status}",method = RequestMethod.GET)
	@ResponseBody
	public Result toggleStatus(@PathVariable("id") Long id,@PathVariable("status") String status) {
		
		Code code = new Code();
		code.setId(id);
		code.setStatus(status);
		return Result.judge(service.insertOrUpdate(code));
	}
	
	

}
