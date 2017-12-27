package com.znph.saas.sys.dto;
/**
* @author Minco
* @date 2017年11月30日 下午3:32:25
* 
*/

import java.util.Map;

public class CriterionDto {
	
	/**
	 * 标识
	 */
	private Long identity;
	
	private Map<Object, Object> content;

	private Map<Object, Object> criterion;
	
	private Map<Object, Object> orderby;

	private CriterionPageDto page;

	public Map<Object, Object> getContent() {
		return content;
	}

	public void setContent(Map<Object, Object> content) {
		this.content = content;
	}

	public Map<Object, Object> getCriterion() {
		return criterion;
	}

	public void setCriterion(Map<Object, Object> criterion) {
		this.criterion = criterion;
	}

	public CriterionPageDto getPage() {
		return page;
	}

	public void setPage(CriterionPageDto page) {
		this.page = page;
	}

	public Long getIdentity() {
		return identity;
	}

	public void setIdentity(Long identity) {
		this.identity = identity;
	}

	public Map<Object, Object> getOrderby() {
		return orderby;
	}

	public void setOrderby(Map<Object, Object> orderby) {
		this.orderby = orderby;
	}
	
	
	
	

}
