package com.znph.saas.sys.vo;

import com.zbph.core.common.vo.PageVo;

/**
 * @author Minco
 * @date 2017年11月24日 下午3:27:37
 * 
 */
public class TableSearchVo extends PageVo {

	private String tableName;

	private String host;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

}
