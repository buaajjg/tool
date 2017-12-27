package com.znph.saas.sys.service.impl;

import java.util.Map;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.znph.core.common.util.Collections;
import com.znph.core.common.utlis.JsonUtils;
import com.znph.saas.sys.dto.CriterionDto;
import com.znph.saas.sys.dto.CriterionPageDto;
import com.znph.saas.sys.utils.ConditionalAnalysis;

/**
* @author Minco
* @date 2017年11月30日 下午11:26:36
* 
*/
public class Testaa {
	
	private static final String Table_Name = "aaa";
	
	@Test
	public void selectTable() {
		
		
		String sql = ConditionalAnalysis.select(Table_Name);
		System.out.println(sql);
		
	}
	
	@Test
	public void selectTableWhere() {
		
		CriterionDto cDto = new CriterionDto();
		Map<Object,Object> criterion = Collections.hashMap();
		criterion.put("user_name", "aaa");
		cDto.setCriterion(criterion);
		
		
		String sql = ConditionalAnalysis.select(Table_Name,cDto.getCriterion());
		System.out.println(sql);
		
	}
	
	@Test
	public void selectTableWhereOrder() {
		
		CriterionDto cDto = new CriterionDto();
		Map<Object,Object> criterion = Collections.hashMap();
		criterion.put("user_name", "aaa");
		Map<Object,Object> orderby = Collections.hashMap();
		orderby.put("last_login_time", "desc");
		
		cDto.setCriterion(criterion);
		cDto.setOrderby(orderby);
		
		System.out.println(JSON.toJSONString(cDto,JsonUtils.features));
		
		String sql = ConditionalAnalysis.select(Table_Name,cDto.getCriterion(),cDto.getOrderby());
		System.out.println(sql);
		
	}
	
	@Test
	public void selectWhereListOrderPage() {
		
		CriterionDto cDto = new CriterionDto();
		Map<Object,Object> criterion = Collections.hashMap();
		criterion.put("user_name", "aaa");
		Map<Object,Object> orderby = Collections.hashMap();
		orderby.put("last_login_time", "desc");
		CriterionPageDto criterionPageDto = new CriterionPageDto();
		criterionPageDto.setPage(1);
		criterionPageDto.setSize(2);
		
		cDto.setCriterion(criterion);
		cDto.setOrderby(orderby);
		cDto.setPage(criterionPageDto);
		
		String sql = ConditionalAnalysis.select(Table_Name, cDto.getCriterion(), cDto.getOrderby(), cDto.getPage());
		System.out.println(sql);
		
	}
	
	@Test
	public void update() {
		
		CriterionDto cDto = new CriterionDto();
		Map<Object,Object> content = Collections.hashMap();
		content.put("user_name", "aaa1");
		content.put("pwd", "1235324111");
		content.put("locked", 0);
		content.put("last_login_time", "2017-12-1 1:11:11");
		
		Map<Object,Object> criterion = Collections.hashMap();
		criterion.put("id", 11);
		
		cDto.setContent(content);
		cDto.setCriterion(criterion);
		
		String sql = ConditionalAnalysis.update(Table_Name, cDto.getContent(), cDto.getCriterion());
		System.out.println(sql);
		
	}
	
	
	@Test
	public void delete() {
		
		CriterionDto cDto = new CriterionDto();
		
		Map<Object,Object> criterion = Collections.hashMap();
		criterion.put("id", 1);
		cDto.setCriterion(criterion);
		
		String sql = ConditionalAnalysis.delete(Table_Name, cDto.getCriterion());
		System.out.println(sql);
	}
	
	@Test
	public void insert() {
		
		CriterionDto cDto = new CriterionDto();
		Map<Object,Object> content = Collections.hashMap();
		content.put("user_name", "aaa1");
		content.put("pwd", "1235324111");
		content.put("locked", 0);
		content.put("last_login_time", "2017-12-1 12:11:11");
		cDto.setContent(content);
		
		String sql = ConditionalAnalysis.insert(Table_Name, cDto.getContent());
		System.out.println(sql);
		
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	


}
