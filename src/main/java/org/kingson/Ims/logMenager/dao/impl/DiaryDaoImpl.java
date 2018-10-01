/**
 * 
 */
package org.kingson.Ims.logMenager.dao.impl;

import java.util.List;

import org.kingson.Ims.logMenager.dao.DiaryDao;
import org.kingson.Ims.logMenager.domain.Diary;
import org.kingson.commrs.dao.impl.BaseDaoImpl;
import org.kingson.commrs.pager.PageModel;

/**alt+shift+j
 * @author kingson
 * 2018年8月10日
   org.kingson.Ims.logMenager.dao.impl
   Imsn2
   @version 1.0

  @email 1606656167@qq.com
  @tel 15768607477
   
 */
public class DiaryDaoImpl  extends BaseDaoImpl implements DiaryDao{

	/* (non-Javadoc)
	 * @see org.kingson.Ims.logMenager.dao.DiaryDao#findbyPageLog(org.kingson.Ims.logMenager.domain.Diary, org.kingson.commrs.pager.PageModel)
	 */
	@Override
	public List<Diary> findbyPageLog(Diary diary, PageModel pageModel) {
		String hql ="select d  from Diary d  order by d.id asc";
		return this.findByPage(hql, pageModel, null);
	}

	/* (non-Javadoc)
	 * @see org.kingson.Ims.logMenager.dao.DiaryDao#deleteDiary(java.lang.String[])
	 */
	@Override
	public void deleteDiary(String[] id) {
		StringBuilder hql = new StringBuilder();
		hql.append(" update Diary set delFlag =0 where ");
		for (int i = 0; i < id.length; i++) {
			hql.append(i==0? "id = '"+id[i]+"'" : " or id  = '"+id[i]+"'");
		}
		this.bulkUpdate(hql.toString(), null);
		
	}

}
