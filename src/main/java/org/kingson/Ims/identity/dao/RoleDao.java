/**
 * 
 */
package org.kingson.Ims.identity.dao;

import java.util.List;

import org.kingson.Ims.identity.domain.User;
import org.kingson.commrs.dao.BaseDao;
import org.kingson.commrs.pager.PageModel;
import org.springframework.stereotype.Repository;

/**alt+shift+j
 * @author kingson
 * 2018年8月4日
   org.kingson.Ims.identity.dao
   Imsn2
   @version 1.0

  @email 1606656167@qq.com
  @tel 15768607477
   
 */
@Repository("roleDao")
public interface RoleDao extends BaseDao{

	/**
	 * @param ids
	 */
	void deleteRole(String[] ids);

	/**
	 * @param id
	 * @param pageModel
	 * @return
	 */
	List<User> selectRoleUser(Long id, PageModel pageModel);

	/**
	 * @param roleId
	 * @param pageModel
	 * @return
	 */
	List<User> showbindUser(Long roleId, PageModel pageModel);

	/**
	 * @param id
	 * @param userId
	 */
	

}
