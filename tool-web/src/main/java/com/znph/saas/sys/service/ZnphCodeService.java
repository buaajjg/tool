package com.znph.saas.sys.service;

import com.znph.core.common.bean.Result;
import com.znph.saas.sys.dto.CriterionDto;
import com.znph.saas.sys.entity.Code;
import com.znph.saas.sys.entity.Table;

public interface ZnphCodeService {
	
	Code getCode(Long id);

	Result excuteSql(String tableName,CriterionDto criterionDto,String codeType);

	Result createTable(Table table);
	
	String select(String tableName,CriterionDto criterionDto);
	
	String update(String tableName,CriterionDto criterionDto);
	
	String insert(String tableName,CriterionDto criterionDto);
	
	String delete(String tableName,CriterionDto criterionDto);

}
