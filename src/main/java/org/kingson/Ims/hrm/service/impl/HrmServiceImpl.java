/**
 * 
 */
package org.kingson.Ims.hrm.service.impl;

import java.util.Date;
import java.util.List;

import org.kingson.Ims.hrm.dao.DeptDao;
import org.kingson.Ims.hrm.domain.Dept;
import org.kingson.Ims.hrm.service.HrmService;
import org.kingson.Ims.identity.domain.User;
import org.kingson.commrs.ConstantUtil;
import org.kingson.commrs.pager.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.WebRequest;

/**alt+shift+j
 * @author kingson
 * 2018年7月30日
   org.kingson.Ims.hrm.service.impl
   Imsn
   @version 1.0
 */
@Service
@Transactional
public class HrmServiceImpl implements HrmService {

	/* (non-Javadoc)
	 * @see org.kingson.Ims.hrm.service.HrmService#getAllDept()
	 */
	@Autowired
	private DeptDao deptDao;
	

	/* (non-Javadoc)
	 * @see org.kingson.Ims.hrm.service.HrmService#getAllDept(org.kingson.Ims.hrm.domain.Dept, org.kingson.commrs.pager.PageModel)
	 */
	@Override
	public List<Dept> getAllDept(Dept dept, PageModel pageModel) {
		    try {
				 
		    	
		  return  deptDao.findbyPages(dept,pageModel);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				throw new RuntimeException("查询异常"+e.getMessage(),e);
			}
		
	}


	/* (non-Javadoc)
	 * @see org.kingson.Ims.hrm.service.HrmService#checkDeptname(java.lang.String)
	 */
	@Override
	public String checkDeptname(String deptname) {
		try {
		List<Dept> depts =	deptDao.find(Dept.class);
		for (Dept dept : depts) {
			if(deptname !=null && !deptname.equals("")) {
				if(deptname.equals(dept.getName())) {
					return "error";
				}
			}
		}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
		}
		return "success";
	}


	/* (non-Javadoc)
	 * @see org.kingson.Ims.hrm.service.HrmService#addDept(org.kingson.Ims.hrm.domain.Dept, org.springframework.web.context.request.WebRequest)
	 部门保存
	 */
	@Override
	public void addDept(Dept dept, WebRequest request) {
	try {
		//设置创建时间
		dept.setCreateDate(new Date());
		dept.setDelFlag("1");
	User user =	(User) request.getAttribute(ConstantUtil.SESSION_USER, WebRequest.SCOPE_SESSION);
    dept.setCreater(user);
    deptDao.save(dept);
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
		throw new RuntimeException("保存异常"+e.getMessage(),e);
	}
		
	}


	/* (non-Javadoc)
	 * @see org.kingson.Ims.hrm.service.HrmService#delectDept(java.lang.String)
	 */
	@Override
	public void delectDept(String ids) {

          String [] id = ids.split(",");
          deptDao.deleteDept(id);
          
		
	}


	/* (non-Javadoc)
	 * @see org.kingson.Ims.hrm.service.HrmService#updateDept(org.kingson.Ims.hrm.domain.Dept, org.springframework.web.context.request.WebRequest)
	 */
	@Override
	public void updateDept(Dept dept, WebRequest request) {
	  try {
		  Dept dept2 =	deptDao.get(Dept.class, dept.getId());
			dept2.setModifyDate(new Date());
			dept2.setName(dept.getName());
			dept2.setRemark(dept.getRemark());
			dept2.setModifier((User) request.getAttribute(ConstantUtil.SESSION_USER, WebRequest.SCOPE_SESSION));
			deptDao.save(dept2);
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
		throw new RuntimeException("修改失败"+e.getMessage());
	}
	}


	/* (non-Javadoc)
	 * @see org.kingson.Ims.hrm.service.HrmService#getDept(java.lang.String)
	 */
	@Override
	public Dept getDept(Long id) {
		// TODO Auto-generated method stub
		
	try {
		return	deptDao.get(Dept.class, id);
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
		throw new RuntimeException("查询异常"+e.getMessage());
	}
		
	}



}
