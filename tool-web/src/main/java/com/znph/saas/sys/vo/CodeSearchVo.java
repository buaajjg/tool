package com.znph.saas.sys.vo;

import com.zbph.core.common.vo.PageVo;

/**
 * @author Minco
 * @date 2017年11月25日 下午3:27:37
 * 
 */
public class CodeSearchVo extends PageVo {

	private String codeName;

	private String status;

	private Long tableId;

	public CodeSearchVo() {
		super();
	}

	public CodeSearchVo(Long tableId) {
		super();
		this.tableId = tableId;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getTableId() {
		return tableId;
	}

	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}

}
