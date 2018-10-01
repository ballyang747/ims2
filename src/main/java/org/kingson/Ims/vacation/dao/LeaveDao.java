/**
 * 
 */
package org.kingson.Ims.vacation.dao;

import java.util.List;

import org.kingson.Ims.vacation.domain.LeaveType;
import org.kingson.commrs.dao.BaseDao;
import org.kingson.commrs.pager.PageModel;

/**alt+shift+j
 * @author kingson
 * 2018年8月10日
   org.kingson.Ims.vacation.dao
   Imsn2
   @version 1.0

  @email 1606656167@qq.com
  @tel 15768607477
   
 */
public interface LeaveDao  extends BaseDao{

	/**
	 * @param leaveType
	 * @param pageModel
	 * @return
	 */
	List<LeaveType> findBypages(LeaveType leaveType, PageModel pageModel);

}
