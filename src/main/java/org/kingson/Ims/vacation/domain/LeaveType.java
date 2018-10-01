package org.kingson.Ims.vacation.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kingson.Ims.identity.domain.User;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "OA_LEAVE_TYPE")
@JsonIgnoreProperties({ "handler", "hibernateLazyInitializer" })
public class LeaveType implements Serializable {

	private static final long serialVersionUID = -227626827894886737L;
	@Id
	@Column(name = "CODE", length = 255)
	private String code;
	/** 名称 */
	@Column(name = "NAME", length = 100)
	private String name;
	/** 备注 */
	@Column(name = "REMARK", length = 500)
	@JsonIgnore
	private String remark;
	/** 修改人 */
	@ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "MODIFIER", referencedColumnName = "USER_ID", foreignKey = @ForeignKey(name = "FK_LEAVETYPE_MODIFIER"))
	@JsonIgnore
	private User modifier;
	/** 修改日期 */
	@Column(name = "MODIFY_DATE")
	@JsonIgnore
	private Date modifyDate;
	/** 创建人 */
	@ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "CREATER", referencedColumnName = "USER_ID", foreignKey = @ForeignKey(name = "FK_LEAVETYPE_CREATER"))
	@JsonIgnore
	private User creater;
	/** 创建日期 */
	@Column(name = "CREATE_DATE")
	@JsonIgnore
	private Date createDate;

	/** setter and getter method */
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

	public User getModifier() {
		return modifier;
	}

	public void setModifier(User modifier) {
		this.modifier = modifier;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public User getCreater() {
		return creater;
	}

	public void setCreater(User creater) {
		this.creater = creater;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}