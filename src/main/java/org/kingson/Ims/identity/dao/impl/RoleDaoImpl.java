/**
 * 
 */
package org.kingson.Ims.identity.dao.impl;

import java.util.List;

import org.kingson.Ims.identity.dao.RoleDao;
import org.kingson.Ims.identity.domain.User;
import org.kingson.commrs.dao.impl.BaseDaoImpl;
import org.kingson.commrs.pager.PageModel;

/**alt+shift+j
 * @author kingson
 * 2018年8月4日
   org.kingson.Ims.identity.dao.impl
   Imsn2
   @version 1.0

  @email 1606656167@qq.com
  @tel 15768607477
   
 */
public class RoleDaoImpl extends BaseDaoImpl implements RoleDao {

	/* (non-Javadoc)
	 * @see org.kingson.Ims.identity.dao.RoleDao#deleteRole(java.lang.String[])
	 */
	@Override
	public void deleteRole(String[] ids) {
		StringBuffer hql = new StringBuffer();
		hql.append("update Role set delFlag = 0 where ");
		
		for (int i = 0; i < ids.length; i++) {
			hql.append(i==0?" id = '"+ids[i]+"'" : " or id = '"+ids[i]+"'" );
		}
		this.bulkUpdate(hql.toString(), null);
	}

	/* (non-Javadoc)
	 * @see org.kingson.Ims.identity.dao.RoleDao#selectRoleUser(java.lang.Long, org.kingson.commrs.pager.PageModel)
	 */
	@Override
	public List<User> selectRoleUser(Long id, PageModel pageModel) {
		StringBuffer hql= new StringBuffer();
		hql.append(" select u  from User u where u.userId in ( ");
		hql.append(" select u.userId from User u inner join u.roles r where r.id = "+id+")");
		
		return this.findByPage(hql.toString(),pageModel, null);
	}

	/* (non-Javadoc)
	 * @see org.kingson.Ims.identity.dao.RoleDao#showbindUser(java.lang.Long, org.kingson.commrs.pager.PageModel)
	 */
	@Override
	public List<User> showbindUser(Long roleId, PageModel pageModel) {
		StringBuffer hql= new StringBuffer();
		hql.append(" select u  from User u where u.userId not in ( ");
		hql.append(" select u.userId from User u  inner join u.roles r where r.id = "+roleId+")");
		
		return this.findByPage(hql.toString(),pageModel, null);
	}

	/* (non-Javadoc)
	 * @see org.kingson.Ims.identity.dao.RoleDao#saveBoundUser(java.lang.Long, java.lang.String)
	 */
	

}
