package org.kingson.Ims.identity.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.kingson.Ims.hrm.domain.Dept;
import org.kingson.Ims.hrm.domain.Job;



@Entity @Table(name="OA_ID_USER", 
		indexes={@Index(columnList="NAME", name="IDX_USER_NAME")})
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class User implements Serializable {
	
	private static final long serialVersionUID = -3417930882448168081L;
	/** 用户ID	PK，大小写英文和数字 */
	@Id @Column(name="USER_ID", length=50)
	private String userId;
	/** 密码	MD5加密 */	
	@Column(name="PASS_WORD", length=50)
	private String passWord;
	/** 姓名 */
	@Column(name="NAME", length=50)
	private String name;
	/** 性别	1:男 2:女 */
	@Column(name="SEX")
	private Short sex = 1;
	/** 用户与部门存在多对一关联    部门	FK(OA_ID_DEPT) */
	@ManyToOne(fetch=FetchType.LAZY, targetEntity=Dept.class)
	@JoinColumn(name="DEPT_ID", referencedColumnName="ID",
			foreignKey=@ForeignKey(name="FK_USER_DEPT"))
	private Dept dept;
	
	/** 用户与职位存在多对一关联    职位	FK(OA_ID_JOB) */
	@ManyToOne(fetch=FetchType.LAZY, targetEntity=Job.class)
	@JoinColumn(name="JOB_CODE", referencedColumnName="CODE",
			foreignKey=@ForeignKey(name="FK_USER_JOB"))
	private Job job;
	/** 邮箱 */
	@Column(name="EMAIL", length=50)
	private String email;
	/** 电话号码 */
	@Column(name="TEL", length=50)
	private String tel;
	/** 手机号码 */
	@Column(name="PHONE", length=50)
	private String phone;
	/** QQ号码 */
	@Column(name="QQ_NUM", length=50)
	private String qqNum;
	/** 问题编号 */
	@Column(name="QUESTION")
	private Short question;
	/** 回答结果 */
	@Column(name="ANSWER", length=200)
	private String answer;
	/** 状态	0新建,1审核,2不通过审核,3冻结  */
	@Column(name="STATUS")
	private Short status = 0;
	/** 用户创建人与用户存在多对一关联(FK(OA_ID_USER)) */
	@ManyToOne(fetch=FetchType.LAZY, targetEntity=User.class)
	@JoinColumn(name="CREATER", referencedColumnName="USER_ID", 
				foreignKey=@ForeignKey(name="FK_USER_CREATER")) // 更改外键约束名
	private User creater;
	/** 创建时间 */
	@Column(name="CREATE_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	/** 用户修改人与用户存在多对一关联(FK(OA_ID_USER)) */
	@ManyToOne(fetch=FetchType.LAZY, targetEntity=User.class)
	@JoinColumn(name="MODIFIER", referencedColumnName="USER_ID", 
				foreignKey=@ForeignKey(name="FK_USER_MODIFIER")) // 更改外键约束名
	private User modifier;
	/** 修改时间 */
	@Column(name="MODIFY_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifyDate;
	/** 部门审核人与用户存在多对一关联(FK(OA_ID_USER)) */
	@ManyToOne(fetch=FetchType.LAZY, targetEntity=User.class)
	@JoinColumn(name="CHECKER", referencedColumnName="USER_ID", 
				foreignKey=@ForeignKey(name="FK_USER_CHECKER")) // 更改外键约束名
	private User checker;
	/** 审核时间 */
	@Column(name="CHECK_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date checkDate;
	/** 用户与角色存在N-N关联 */
	@ManyToMany(fetch=FetchType.LAZY, targetEntity=Role.class, mappedBy="users")
	private Set<Role> roles = new HashSet<>();
	private String delFlag="1";
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	/** setter and getter method */
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Short getSex() {
		return sex;
	}
	public void setSex(Short sex) {
		this.sex = sex;
	}
	public Dept getDept() {
		return dept;
	}
	public void setDept(Dept dept) {
		this.dept = dept;
	}
	public Job getJob() {
		return job;
	}
	public void setJob(Job job) {
		this.job = job;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getQqNum() {
		return qqNum;
	}
	public void setQqNum(String qqNum) {
		this.qqNum = qqNum;
	}
	public Short getQuestion() {
		return question;
	}
	public void setQuestion(Short question) {
		this.question = question;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public Short getStatus() {
		return status;
	}
	public void setStatus(Short status) {
		this.status = status;
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
	public User getChecker() {
		return checker;
	}
	public void setChecker(User checker) {
		this.checker = checker;
	}
	public Date getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	@Override
	public String toString() {
		return "User [userId=" + userId + ", passWord=" + passWord + ", name=" + name + ",status="+status +"]";
	}
	
		@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (answer == null) {
			if (other.answer != null)
				return false;
		} else if (!answer.equals(other.answer))
			return false;
		if (checkDate == null) {
			if (other.checkDate != null)
				return false;
		} else if (!checkDate.equals(other.checkDate))
			return false;
		if (checker == null) {
			if (other.checker != null)
				return false;
		} else if (!checker.equals(other.checker))
			return false;
		if (createDate == null) {
			if (other.createDate != null)
				return false;
		} else if (!createDate.equals(other.createDate))
			return false;
		if (creater == null) {
			if (other.creater != null)
				return false;
		} else if (!creater.equals(other.creater))
			return false;
		if (dept == null) {
			if (other.dept != null)
				return false;
		} else if (!dept.equals(other.dept))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (job == null) {
			if (other.job != null)
				return false;
		} else if (!job.equals(other.job))
			return false;
		if (modifier == null) {
			if (other.modifier != null)
				return false;
		} else if (!modifier.equals(other.modifier))
			return false;
		if (modifyDate == null) {
			if (other.modifyDate != null)
				return false;
		} else if (!modifyDate.equals(other.modifyDate))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (passWord == null) {
			if (other.passWord != null)
				return false;
		} else if (!passWord.equals(other.passWord))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		if (qqNum == null) {
			if (other.qqNum != null)
				return false;
		} else if (!qqNum.equals(other.qqNum))
			return false;
		if (question == null) {
			if (other.question != null)
				return false;
		} else if (!question.equals(other.question))
			return false;
		if (roles == null) {
			if (other.roles != null)
				return false;
		} else if (!roles.equals(other.roles))
			return false;
		if (sex == null) {
			if (other.sex != null)
				return false;
		} else if (!sex.equals(other.sex))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (tel == null) {
			if (other.tel != null)
				return false;
		} else if (!tel.equals(other.tel))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}
	
	
}