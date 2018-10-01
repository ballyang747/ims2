package org.kingson.Ims.workflow.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kingson.Ims.identity.domain.User;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 所有需要被流程引擎统一管理的业务数据，全部继承该类。
 * 
 * @author lwq
 *
 */
@MappedSuperclass // 映射的父类
public abstract class BusinessData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 主键，不要使用主键生成器，而是在处理数据的过程中，通过UUID类生成一个随机的UUID
	@Id
	@Column(length = 36)
	private String id;
	// 哪个用户提交
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	@JsonIgnore // 不要生成JSON
	private User user;
	// 什么时候提交的
	@Temporal(TemporalType.TIMESTAMP)
	private Date submitTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}
}
