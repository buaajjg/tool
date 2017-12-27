package com.znph.saas.sys.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author minco
 * @since 2017-11-28
 */
@TableName("tool_code")
public class Code extends Model<Code> {

	private static final long serialVersionUID = 1L;
	
	public static final String Code_Type_Query = "1";
	public static final String Code_Type_Add = "3";
	public static final String Code_Type_Update = "2";
	public static final String Code_Type_Delete = "4";
	

	private Long id;
	/**
	 * 接口名称
	 */
	@TableField("code_name")
	private String codeName;
	/**
	 * sql语句
	 */
	@TableField("sql_sen")
	private String sqlSen;
	/**
	 * dict-d_have
	 */
	private String status;
	@TableField(value = "create_time", fill = FieldFill.INSERT)
	private Date createTime;
	@TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
	private Date updateTime;
	@TableField(value = "table_id")
	private Long tableId;
	@TableField(value = "code_type")
	private String codeType;

	@TableField(exist = false)
	private String host;
	@TableField(exist = false)
	private String tableName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public String getSqlSen() {
		return sqlSen;
	}

	public void setSqlSen(String sqlSen) {
		this.sqlSen = sqlSen;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Long getTableId() {
		return tableId;
	}

	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getCodeType() {
		return codeType;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
