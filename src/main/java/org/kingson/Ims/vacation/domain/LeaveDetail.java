package org.kingson.Ims.vacation.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.kingson.commrs.DateTimeJsonSerializer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name = "OA_LEAVE_DETAIL")
@JsonIgnoreProperties({ "handler", "hibernateLazyInitializer" })
public class LeaveDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 主键
	@Id
	// 声明要使用Hibernate的主键生成器
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	// 具体使用前面定义的、名为uuid2的主键生成器，长度为36
	@GeneratedValue(generator = "uuid2")
	@Column(length = 36)
	private String id;
	// 请假类型
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "type_code")
	private LeaveType type;
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = DateTimeJsonSerializer.class)
	private Date startTime;
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = DateTimeJsonSerializer.class)
	private Date endTime;
	private int leaveHours;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public LeaveType getType() {
		return type;
	}

	public void setType(LeaveType type) {
		this.type = type;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public int getLeaveHours() {
		return leaveHours;
	}

	public void setLeaveHours(int leaveHours) {
		this.leaveHours = leaveHours;
	}
}
