/**
 * 
 */
package org.kingson.Ims.identity.dao;

import java.util.List;

import org.kingson.commrs.dao.BaseDao;

/**alt+shift+j
 * @author kingson
 * 2018年8月7日
   org.kingson.Ims.identity.dao
   Imsn2
   @version 1.0

  @email 1606656167@qq.com
  @tel 15768607477
   
 */
public interface PopedomDao extends BaseDao  {

	
	

	/**
	 * @param code
	 * @param id
	 * @return
	 */
	List<String> getLoadPopedoms(String code, Long id);

	/**
	 * @param roleId
	 * @param pCode
	 */
	void deletePopedom(Long roleId, String pCode);

	/**
	 * @param userId
	 * @return
	 */
	List<String> findLeftMenuOperas(String userId);

	/**
	 * @param userId
	 * @return
	 */
	List<String> findPageOpers(String userId);

}
