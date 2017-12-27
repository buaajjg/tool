package com.znph.saas.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.znph.saas.sys.entity.Dict;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 字典表 服务类
 * </p>
 *
 * @author minco
 * @since 2017-09-20
 */
public interface DictService extends IService<Dict> {

	Dict getDict(String tableName, String code);

	String getDictName(String tableName, String code);

	List<Dict> getDictList(String tableName);
	
	Map<String, List<Dict>> getDictList(String[] tableNames);

	List<Dict> getDictListQuery(String tableName);

	List<Dict> getDictListSel(String tableName);
	
	String getMoreTitel(String tableName, String codes);

}
