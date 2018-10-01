/**
 * 
 */
package org.kingson.Ims.hrm.dao;

import java.util.List;
import java.util.Map;

import org.kingson.Ims.hrm.domain.Dept;
import org.kingson.commrs.dao.BaseDao;
import org.kingson.commrs.pager.PageModel;

/**alt+shift+j
 * @author kingson
 * 2018年7月30日
   org.kingson.Ims.hrm.dao
   Imsn
   @version 1.0
 */
public interface DeptDao extends BaseDao  {

	/**
	 * @return 
	 * 
	 */
	
	/**
	 * @param dept
	 * @param pageModel
	 * @return
	 */
	List<Dept> findbyPages(Dept dept, PageModel pageModel);

	/**
	 * @return
	 */
	List<Map<Long, String>> findAllDept();

	/**
	 * @param id
	 */
	void deleteDept(String[] id);

}
