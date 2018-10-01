/**
 * 
 */
package org.kingson.Ims.identity.dao.impl;

import org.kingson.Ims.identity.dao.UserDao;
import org.kingson.commrs.dao.impl.BaseDaoImpl;

/**alt+shift+j
 * @author kingson
 * 2018年7月30日
   org.kingson.Ims.identity.dao.impl
   Imsn
   @version 1.0
 */
public class UserDaoImpl extends BaseDaoImpl implements UserDao {

	/* (non-Javadoc)
	 * @see org.kingson.Ims.identity.dao.UserDao#deleteUser(java.lang.String[])
	 */
	@Override
	public void deleteUser(String[] ids) {
		// TODO Auto-generated method stub
		StringBuffer hql = new StringBuffer();
		hql.append(" update User set delFlag = 0 where ");
		for (int i = 0; i < ids.length; i++) {
			hql.append(i==0?" userId = '"+ids[i]+"'" : " or userId = '"+ids[i]+"'");
			
		}
		this.bulkUpdate(hql.toString(), null);
		
	}


	

}
