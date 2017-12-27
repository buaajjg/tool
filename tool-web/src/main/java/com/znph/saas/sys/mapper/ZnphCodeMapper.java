package com.znph.saas.sys.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.znph.saas.sys.entity.Code;
import com.znph.saas.sys.vo.ZnphCodeVo;

/**
 * <p>
 * 字典表 Mapper 接口
 * </p>
 *
 * @author minco
 * @since 2017-09-20
 */
public interface ZnphCodeMapper {

	List<Map<String, Object>> excuteSql(@Param("sql") String  sql);
	
	int excuteSqlCountTotal(@Param("sql") String  sql);

	int createNewTable(ZnphCodeVo znphCodeVo);
	
	List<Code> getCodeVoInfo(Long id);
	
	

}