package com.znph.saas.sys.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author minco
 * @since 2017-09-15
 */
@TableName("tool_auth_user")
public class AuthUser extends Model<AuthUser> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Long id;
	@TableField("user_name")
	private String userName;
    /**
     * 加密后的值
     */
	private String pwd;
    /**
     * 锁定用户
     */
	private Integer locked;
    /**
     * 最后登录时间
     */
	@TableField("last_login_time")
	private Date lastLoginTime;
    /**
     * 最后一次登录ip
     */
	@TableField("last_login_ip")
	private String lastLoginIp;
    /**
     * 登录失败次数
     */
	@TableField("psd_error_count")
	private Integer psdErrorCount;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public Integer getLocked() {
		return locked;
	}

	public void setLocked(Integer locked) {
		this.locked = locked;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public Integer getPsdErrorCount() {
		return psdErrorCount;
	}

	public void setPsdErrorCount(Integer psdErrorCount) {
		this.psdErrorCount = psdErrorCount;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "AuthUser{" +
			"id=" + id +
			", userName=" + userName +
			", pwd=" + pwd +
			", locked=" + locked +
			", lastLoginTime=" + lastLoginTime +
			", lastLoginIp=" + lastLoginIp +
			", psdErrorCount=" + psdErrorCount +
			"}";
	}
}
