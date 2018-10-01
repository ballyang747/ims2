/**
 * 
 */
package org.kingson.Ims.hrm.dao.impl;

import java.util.List;
import java.util.Map;

import org.kingson.Ims.hrm.dao.JobDao;
import org.kingson.commrs.dao.impl.BaseDaoImpl;

/**alt+shift+j
 * @author kingson
 * 2018年7月31日
   org.kingson.Ims.hrm.dao.impl
   Imsn
   @version 1.0

  @email 1606656167@qq.com
  @tel 15768607477
   
 */
public class JobDaoImpl extends BaseDaoImpl implements JobDao {

	/* (non-Javadoc)"select new Map(d.id as id,d.name as name) from Dept d order by d.id asc"
	 * @see org.kingson.Ims.hrm.dao.JobDao#findAllJob()
	 */
	@Override
	public List<Map<Long, String>> findAllJob() {
		     String hql = "select new Map(j.code as code, j.name as name ) from Job j order by j.id asc";
		return this.find(hql);
	}

}
