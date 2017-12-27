package com.znph.saas.sys.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * <p>
 * 字典表
 * </p>
 *
 * @author minco
 * @since 2017-09-20
 */
@TableName("tool_dict")
public class Dict extends Model<Dict> {
    public Dict(){

    }

    public Dict(String code, String name, String remark, String tableName) {
        this.code = code;
        this.name = name;
        this.remark = remark;
        this.tableName = tableName;
    }

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @JSONField(serialize=false)
    private Long id;
    /**
     * 相当于key
     */
    private String code = "";
    /**
     * 相当于value
     */
    private String name = "";
    /**
     * 备注
     */

    private String remark = "";
    /**
     * 字典项分类
     */
    @TableField("table_name")
    @JSONField(serialize=false)
    private String tableName = "";


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Dict{" +
                "id=" + id +
                ", code=" + code +
                ", name=" + name +
                ", remark=" + remark +
                ", tableName=" + tableName +
                "}";
    }
}
