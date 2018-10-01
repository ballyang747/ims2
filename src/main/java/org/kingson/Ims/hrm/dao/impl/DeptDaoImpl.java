/**
 * 
 */
package org.kingson.Ims.hrm.dao.impl;

import java.util.List;
import java.util.Map;

import org.kingson.Ims.hrm.dao.DeptDao;
import org.kingson.Ims.hrm.domain.Dept;
import org.kingson.commrs.dao.impl.BaseDaoImpl;
import org.kingson.commrs.pager.PageModel;

/**alt+shift+j
 * @author kingson
 * 2018年7月30日
   org.kingson.Ims.hrm.dao.impl
   Imsn
   @version 1.0
 */
public class DeptDaoImpl extends BaseDaoImpl implements DeptDao {

	
	
	/* (non-Javadoc)
	 * @see org.kingson.Ims.hrm.dao.DeptDao#findbyPages(org.kingson.Ims.hrm.domain.Dept, org.kingson.commrs.pager.PageModel)
	 
	 *String hql = "select  new Map(d.id as id ,d.name as name) from Dept d order by d.id asc";
	 */
	@Override
	public List<Dept> findbyPages(Dept dept, PageModel pageModel) {
		String hql = "select  d from Dept d where delFlag='1' order by d.id asc";
		
		
		return this.findByPage(hql, pageModel, null);
	}

	/* (non-Javadoc)
	 * @see org.kingson.Ims.hrm.dao.DeptDao#findAllDept()
	 */
	@Override
	public List<Map<Long, String>> findAllDept() {
		String hql ="select  new Map(d.id as id ,d.name as name) from  Dept d where delFlag='1' order by d.id asc";
		return this.find(hql);
	}

	/* (non-Javadoc)
	 * @see org.kingson.Ims.hrm.dao.DeptDao#deleteDept(java.lang.String[])
	 */
	@Override
	public void deleteDept(String[] id) {
		StringBuilder hql = new StringBuilder();
		hql.append("update Dept set delFlag =0 where ");
		for (int i = 0; i < id.length; i++) {
			hql.append(i==0? "id = '"+id[i]+"'" : " or id = '"+id[i]+"'");
		}
		this.bulkUpdate(hql.toString(), null);
	}

}
