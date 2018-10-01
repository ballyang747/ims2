/**
 * 
 */
package org.kingson.Ims.vacation.dao.Impl;

import java.util.List;

import org.kingson.Ims.vacation.dao.LeaveDao;
import org.kingson.Ims.vacation.domain.LeaveType;
import org.kingson.commrs.dao.impl.BaseDaoImpl;
import org.kingson.commrs.pager.PageModel;

/**alt+shift+j
 * @author kingson
 * 2018年8月10日
   org.kingson.Ims.vacation.dao.Impl
   Imsn2
   @version 1.0

  @email 1606656167@qq.com
  @tel 15768607477
   
 */
public class LeaveDaoImpl extends BaseDaoImpl implements LeaveDao {

	/* (non-Javadoc)
	 * @see org.kingson.Ims.vacation.dao.LeaveDao#findBypages(org.kingson.Ims.vacation.domain.LeaveType, org.kingson.commrs.pager.PageModel)
	 */
	@Override
	public List<LeaveType> findBypages(LeaveType leaveType, PageModel pageModel) {
		String hql =" select l from LeaveType l where 1=1 order by l.code asc";
		return this.findByPage(hql, pageModel, null);
	}

}
