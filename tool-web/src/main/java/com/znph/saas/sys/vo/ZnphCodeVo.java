package com.znph.saas.sys.vo;

/**
 * 基础接口
 * 
 * @author Minco
 * @date 2017年11月24日 下午7:08:29
 * 
 */
public class ZnphCodeVo {

	private Long codeId;
	private String tableName;
	private String tableSen;
	private String sqlSen;

	/**
	 * 分页所需条件
	 */
	private Integer page;
	private Integer pageSize;
	private Integer index;

	public String getTableSen() {
		return tableSen;
	}

	public void setTableSen(String tableSen) {
		this.tableSen = tableSen;
	}

	public Long getCodeId() {
		return codeId;
	}

	public void setCodeId(Long codeId) {
		this.codeId = codeId;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public String getSqlSen() {
		return sqlSen;
	}

	public void setSqlSen(String sqlSen) {
		this.sqlSen = sqlSen;
	}

}
