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
@TableName("tool_table")
public class Table extends Model<Table> {

	private static final long serialVersionUID = 1L;

	private Long id;
	/**
	 * 创建表语句
	 */
	@TableField("table_sen")
	private String tableSen;
	/**
	 * 表名
	 */
	@TableField("table_name")
	private String tableName;
	@TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
	private Date updateTime;
	@TableField(value = "create_time", fill = FieldFill.INSERT)
	private Date createTime;
	/**
	 * 表说明
	 */
	@TableField("table_remark")
	private String tableRemark;

	private String host;
	
	@TableField(exist=false)
	private Integer number;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTableSen() {
		return tableSen;
	}

	public void setTableSen(String tableSen) {
		this.tableSen = tableSen;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getTableRemark() {
		return tableRemark;
	}

	public void setTableRemark(String tableRemark) {
		this.tableRemark = tableRemark;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}
	
	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}
	

}
