package org.kingson.Ims.hrm.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kingson.Ims.identity.domain.User;



@Entity
@Table(name="oa_hrm_employee")
public class Employee implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(name="NAME", length=50)
	private String name;
	@Column(name="card_id", length=18)
	private String cardId;
	private String tel;
	private String email;
	private int sex;
	private java.util.Date birthday;
	private String picture;	
	
	@Column(name="create_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	
	/** MODIFIER VARCHAR2(50) 修改人	FK(OA_ID_USER) N-1*/
	@ManyToOne(fetch=FetchType.EAGER, targetEntity=User.class)
	@JoinColumn(name="creater", referencedColumnName="USER_ID", 
				foreignKey=@ForeignKey(name="FK_emp_creater")) // 更改外键约束名
	private User creater;
	
	/** MODIFIER VARCHAR2(50) 修改人	FK(OA_ID_USER) N-1*/
	@ManyToOne(fetch=FetchType.EAGER, targetEntity=User.class)
	@JoinColumn(name="MODIFIER", referencedColumnName="USER_ID", 
				foreignKey=@ForeignKey(name="FK_EMP_MODIFIER")) // 更改外键约束名
	private User modifier;
	
	/** MODIFY_DATE	DATE	修改时间*/
	@Column(name="MODIFY_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifyDate;
	
	/** MODIFIER VARCHAR2(50) 修改人	FK(OA_ID_USER) N-1*/
	@ManyToOne(fetch=FetchType.EAGER, targetEntity=Job.class)
	@JoinColumn(name="job_id", referencedColumnName="code", 
				foreignKey=@ForeignKey(name="FK_EMP_job")) // 更改外键约束名
	private Job job;
	/** MODIFIER VARCHAR2(50) 修改人	FK(OA_ID_USER) N-1*/
	@ManyToOne(fetch=FetchType.EAGER, targetEntity=Dept.class)
	@JoinColumn(name="dept_id", referencedColumnName="id", 
				foreignKey=@ForeignKey(name="FK_EMP_dept")) // 更改外键约束名
	private Dept dept;
	
	
	/** setter and getter method */
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public java.util.Date getBirthday() {
		return birthday;
	}
	public void setBirthday(java.util.Date birthday) {
		this.birthday = birthday;
	}
	
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}

	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public User getCreater() {
		return creater;
	}
	public void setCreater(User creater) {
		this.creater = creater;
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
	public Job getJob() {
		return job;
	}
	public void setJob(Job job) {
		this.job = job;
	}
	public Dept getDept() {
		return dept;
	}
	public void setDept(Dept dept) {
		this.dept = dept;
	}
	
	
     

	
}