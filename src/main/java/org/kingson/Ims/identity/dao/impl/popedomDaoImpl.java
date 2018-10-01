/**
 * 
 */
package org.kingson.Ims.identity.dao.impl;

import java.util.List;

import org.kingson.Ims.identity.dao.PopedomDao;
import org.kingson.commrs.dao.impl.BaseDaoImpl;

/**alt+shift+j
 * @author kingson
 * 2018年8月7日
   org.kingson.Ims.identity.dao.impl
   Imsn2
   @version 1.0

  @email 1606656167@qq.com
  @tel 15768607477
   
 */
public class popedomDaoImpl extends BaseDaoImpl implements PopedomDao {




	@Override
	public List<String> getLoadPopedoms(String code, Long id) {
		String hql =" select p.opera.code from Popedom  p where p.module.code = ? and p.role.id= ? ";
				
		return  (List<String>) this.find(hql,new Object[] {code,id});
	}

	/* (non-Javadoc)
	 * @see org.kingson.Ims.identity.dao.PopedomDao#deletePopedom(java.lang.Long, java.lang.String)
	 */
	@Override
	public void deletePopedom(Long roleId, String pCode) {
		// TODO Auto-generated method stub
		String hql =" delete from Popedom where role.id="+roleId+" and module.code  = '"+pCode+"'";
		 this.bulkUpdate(hql, null);
	}

	/* (non-Javadoc)
	 * @see org.kingson.Ims.identity.dao.PopedomDao#findLeftMenuOperas(java.lang.String)
	 */
	@Override
	public List<String> findLeftMenuOperas(String userId) {
		StringBuilder hql=new StringBuilder();
		 hql.append(" select distinct p.module.code from Popedom p where p.role.id   in (");
		 hql.append(" select r.id from Role r inner join r.users u where u.userId = ? ) order by module.code asc ");
		 
		
		return (List<String>) this.find(hql.toString(),new Object[] {userId});
	}

	/* (non-Javadoc)
	 * @see org.kingson.Ims.identity.dao.PopedomDao#findPageOpers(java.lang.String)
	 */
	@Override
	public List<String> findPageOpers(String userId) {
		StringBuilder hql=new StringBuilder();
		 hql.append(" select distinct p.opera.code from Popedom p where p.role.id   in (");
		 hql.append(" select r.id from Role r inner join r.users u where u.userId = ? ) order by module.code asc ");
		 
		
		return (List<String>) this.find(hql.toString(),new Object[] {userId});
	}

	

}
