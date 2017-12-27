package com.znph.saas.sys.service;

import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.znph.saas.sys.entity.Table;
import com.znph.saas.sys.vo.TableSearchVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author minco
 * @since 2017-11-28
 */
public interface TableService extends IService<Table> {
	
	Map<String, Object> searchTable(TableSearchVo tableSearchVo);
	
	Integer existTableName(String tableName);
	
	
	
}
