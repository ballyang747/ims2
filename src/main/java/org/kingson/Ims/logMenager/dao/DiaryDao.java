/**
 * 
 */
package org.kingson.Ims.logMenager.dao;

import java.util.List;

import org.kingson.Ims.logMenager.domain.Diary;
import org.kingson.commrs.dao.BaseDao;
import org.kingson.commrs.pager.PageModel;

/**alt+shift+j
 * @author kingson
 * 2018年8月10日
   org.kingson.Ims.logMenager.dao
   Imsn2
   @version 1.0

  @email 1606656167@qq.com
  @tel 15768607477
   
 */
public interface DiaryDao extends BaseDao  {

	/**
	 * @param diary
	 * @param pageModel
	 * @return
	 */
	List<Diary> findbyPageLog(Diary diary, PageModel pageModel);

	/**
	 * @param id
	 */
	void deleteDiary(String[] id);

}
