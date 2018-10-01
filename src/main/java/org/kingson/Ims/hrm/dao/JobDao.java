/**
 * 
 */
package org.kingson.Ims.hrm.dao;

import java.util.List;
import java.util.Map;

import org.kingson.commrs.dao.BaseDao;

/**alt+shift+j
 * @author kingson
 * 2018年7月31日
   org.kingson.Ims.hrm.dao
   Imsn
   @version 1.0

  @email 1606656167@qq.com
  @tel 15768607477
   
 */
public interface JobDao  extends BaseDao{

	/**
	 * 
	 */
	List <Map<Long, String>>  findAllJob();
}
