/**
 * 
 */
package org.kingson.Ims.identity.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.kingson.Ims.identity.dao.ModuleDao;
import org.kingson.Ims.identity.domain.Module;
import org.kingson.commrs.dao.impl.BaseDaoImpl;
import org.kingson.commrs.pager.PageModel;




/**alt+shift+j
 * @author kingson
 * 2018年8月6日
   org.kingson.Ims.identity.dao.impl
   Imsn2
   @version 1.0

  @email 1606656167@qq.com
  @tel 15768607477
   
 */
public class ModuleDaoImpl  extends BaseDaoImpl implements ModuleDao{

	/* (non-Javadoc)
	 * @see org.kingson.Ims.identity.dao.ModuleDao#findAllModule()
	 */
	@Override
	public List<Module> findAllModule() {

         String hql = " from Module where delFlag = '1'";
         
		return this.find(hql);
	}

	/* (non-Javadoc)
	 * @see org.kingson.Ims.identity.dao.ModuleDao#getModulesByParent(java.lang.String, org.kingson.commrs.pager.PageModel)
	 */
	@Override
	public List<Module> getModulesByParent(String pCode, PageModel pageModel) {
		List<Object> params=new ArrayList<>();
		params.add(StringUtils.isEmpty(pCode)? "%%" : pCode+"%" );
		params.add(StringUtils.isEmpty(pCode) ? 4 : pCode.length() +4);
		String hql = " from Module where delFlag = '1' and code like ? and length(code) =  ? " ;
		
		return this.findByPage(hql, pageModel, params);
	}

	/* (non-Javadoc)
	 * @see org.kingson.Ims.identity.dao.ModuleDao#findMaxCodeByPcode(java.lang.String)
	 */
	@Override
	public String findMaxCodeByPcode(String pCode) {
		String hql ="SELECT MAX(code) FROM Module WHERE  code LIKE '"+pCode+"%' and length(code) = "+(pCode.length()+4);
		
		return this.findUniqueEntity(hql, null);
	}

	/* (non-Javadoc)
	 * @see org.kingson.Ims.identity.dao.ModuleDao#deleteModule(java.lang.String, java.lang.String[])
	 */
	@Override
	public void deleteModule(String pCode, String[] code) {
		
		String[] params = new String[code.length];
		StringBuffer hql= new StringBuffer();
		hql.append(" update Module m set delFlag ='0' where ");
		for (int i = 0; i < code.length; i++) {
			hql.append(i==0? "code like ? " : " or code like ? ");
			params[i]=code[i]+"%";
		}
		
		this.bulkUpdate(hql.toString(), params);
		
	}

	/* (non-Javadoc)
	 * @see org.kingson.Ims.identity.dao.ModuleDao#getPopedom(java.lang.String, java.lang.String)
	 */
	@Override
	public List<Module> getPopedom(String code) {
	String hql = " select m from Module m where code like ? and length(code) = ? and delFlag = '1'";
		return (List<Module>) this.find(hql, new Object[] {code+"%",code.length()+4});
	}

}
