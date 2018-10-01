/**
 * 
 */
package org.kingson.Ims.identity.dao;

import java.util.List;

import org.kingson.Ims.identity.domain.Module;
import org.kingson.commrs.dao.BaseDao;
import org.kingson.commrs.pager.PageModel;

/**alt+shift+j
 * @author kingson
 * 2018年8月6日
   org.kingson.Ims.identity.dao
   Imsn2
   @version 1.0

  @email 1606656167@qq.com
  @tel 15768607477
   
 */
public interface ModuleDao extends BaseDao{

	/**
	 * @return
	 */
	List<Module> findAllModule();

	/**
	 * @param pCode
	 * @param pageModel
	 * @return
	 */
	List<Module> getModulesByParent(String pCode, PageModel pageModel);

	/**
	 * @param pCode
	 * @return
	 */
	String findMaxCodeByPcode(String pCode);

	/**
	 * @param pCode
	 * @param code
	 */
	void deleteModule(String pCode, String[] code);

	

	/**
	 * @param code
	 * @return
	 */
	List<Module> getPopedom(String code);

}
