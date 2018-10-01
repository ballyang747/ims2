/**
 * 
 */
package org.kingson.Ims.identity.dao;

import org.kingson.commrs.dao.BaseDao;

/**alt+shift+j
 * @author kingson
 * 2018年7月30日
   org.kingson.Ims.identity.dao
   Imsn
   @version 1.0
 */
public interface UserDao  extends BaseDao{

	/**
	 * @param ids
	 */
	void deleteUser(String[] ids);

	/**
	 * @param string
	 * @param pageModel
	 * @param params
	 * @return
	 */
	

}
