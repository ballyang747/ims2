package org.kingson.Ims.vacation.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kingson.Ims.workflow.domain.BusinessData;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// 这个名字通常跟流程定义的KEY相同
@Entity
@Table(name = "OA_LEAVE_REQUEST")
@JsonIgnoreProperties({ "handler", "hibernateLazyInitializer" })
public class LeaveRequest extends BusinessData {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 请假总共的小时数
	private int leaveHours;
	// 请假的明细
	// 级联操作：LeaveDetail类没有DAO，只通过LeaveRequest来操作整个数据
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "leave_id")
	private List<LeaveDetail> details;

	public int getLeaveHours() {
		return leaveHours;
	}

	public void setLeaveHours(int leaveHours) {
		this.leaveHours = leaveHours;
	}

	public List<LeaveDetail> getDetails() {
		return details;
	}

	public void setDetails(List<LeaveDetail> details) {
		this.details = details;
	}
}
