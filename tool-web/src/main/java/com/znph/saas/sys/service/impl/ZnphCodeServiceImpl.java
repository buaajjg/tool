package com.znph.saas.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.znph.core.common.bean.Result;
import com.znph.core.common.util.Collections;
import com.znph.core.common.util.Strings;
import com.znph.saas.sys.dto.CriterionDto;
import com.znph.saas.sys.entity.Code;
import com.znph.saas.sys.entity.Table;
import com.znph.saas.sys.mapper.ZnphCodeMapper;
import com.znph.saas.sys.service.TableService;
import com.znph.saas.sys.service.ZnphCodeService;
import com.znph.saas.sys.utils.ConditionalAnalysis;
import com.znph.saas.sys.vo.ZnphCodeVo;

@Service
public class ZnphCodeServiceImpl implements ZnphCodeService {
	
	static final Logger logger = LoggerFactory.getLogger(ZnphCodeServiceImpl.class);
	
	@Resource
	private ZnphCodeMapper mapper;
	
	@Resource
	private TableService tableService;
	
	
	@Override
	public Code getCode(Long id) {
		Code code = new Code();
		if (id != null) {
			List<Code> list = mapper.getCodeVoInfo(id);
			if (Collections.isNotEmpty(list)) {
				code = list.get(0);
			}
		}
		return code;
	}

	@Override
	public Result excuteSql(String tableName,CriterionDto criterionDto,String codeType) {
		
		
		String sqlStr = "";
		if(Code.Code_Type_Query.equals(codeType)) {
			sqlStr = select(tableName, criterionDto);
		}else if(Code.Code_Type_Add.equals(codeType)) {
			sqlStr = insert(tableName, criterionDto);
		}else if(Code.Code_Type_Update.equals(codeType)) {
			sqlStr = update(tableName, criterionDto);
		}else if(Code.Code_Type_Delete.equals(codeType)) {
			sqlStr = delete(tableName, criterionDto);
		}
		if(Strings.isBlank(sqlStr)) {
			return Result.failure("参数不合法");
		}
		
		Map<String, Object> map = Collections.hashMap();
		
		List<Map<String, Object>> list = mapper.excuteSql(sqlStr);
		
		if(Code.Code_Type_Query.equals(codeType) && criterionDto.getPage() != null) {
			String totalSql = ConditionalAnalysis.selectCount(tableName, criterionDto.getCriterion());
			int total = mapper.excuteSqlCountTotal(totalSql); 
			map.put("total", total);
		}
		
		map.put("list", list);
		
		
		return Result.ok(map);
	}

	@Override
	public Result createTable(Table table) {
		if(Strings.isBlank(table.getHost()) ||  Strings.isBlank(table.getTableName()) ||  Strings.isBlank(table.getTableSen())) {
			return Result.failure("请填写所有信息");
		}
		String tableName = "auto_"+table.getTableName();
		int count = tableService.existTableName(tableName);
		if(count == 0) {
			
			ZnphCodeVo zcVo = new ZnphCodeVo();
			zcVo.setTableSen(table.getTableSen());
			zcVo.setTableName(tableName);
			mapper.createNewTable(zcVo);
		    tableService.insertOrUpdate(table);
		}
		
		return Result.ok();
	}

	@Override
	public String select(String tableName, CriterionDto criterionDto) {
		
		return ConditionalAnalysis.select(tableName, criterionDto.getCriterion(), criterionDto.getOrderby(), criterionDto.getPage());
	}

	@Override
	public String update(String tableName, CriterionDto criterionDto) {
		
		return ConditionalAnalysis.update(tableName, criterionDto.getContent(), criterionDto.getCriterion());
	}

	@Override
	public String insert(String tableName, CriterionDto criterionDto) {
		
		return ConditionalAnalysis.insert(tableName, criterionDto.getContent());
	}

	@Override
	public String delete(String tableName, CriterionDto criterionDto) {
		
		return ConditionalAnalysis.delete(tableName, criterionDto.getCriterion());
	}


}
