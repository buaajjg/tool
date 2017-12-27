package com.znph.core.common.bean;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.FieldFill;


public class BaseEntity implements Serializable {

	private static final long serialVersionUID = -3143710532040143076L;

	private Long id;
    
    @TableField(value="create_time",fill = FieldFill.INSERT)
    private Date createTime;
    
    @TableField(value="update_time",fill = FieldFill.INSERT)
    private Date updateTime;
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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





    
}
