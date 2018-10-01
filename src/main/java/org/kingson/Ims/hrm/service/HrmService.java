/**
 * 
 */
package org.kingson.Ims.hrm.service;

import java.util.List;

import org.kingson.Ims.hrm.domain.Dept;
import org.kingson.commrs.pager.PageModel;
import org.springframework.web.context.request.WebRequest;


/**alt+shift+j
 * @author kingson
 * 2018年7月30日
   org.kingson.Ims.hrm.service
   Imsn
   @version 1.0
 */
public interface HrmService {

	/**
	 * @param pageModel 
	 * @param dept 
	 * @return
	 */
	List<Dept> getAllDept(Dept dept, PageModel pageModel);

	/**
	 * @return
	 */
	

	/**
	 * @param deptname
	 * @return
	 */
	String checkDeptname(String deptname);

	/**
	 * @param dept
	 * @param request
	 */
	void addDept(Dept dept, WebRequest request);

	/**
	 * @param ids
	 */
	void delectDept(String ids);

	/**
	 * @param dept
	 * @param request
	 */
	void updateDept(Dept dept, WebRequest request);


	/**
	 * @param id
	 * @return
	 */
	Dept getDept(Long id);

	/**
	 * 
	 */
	

	/**
	 * 
	 */
 
}
