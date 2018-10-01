package org.kingson.Ims.workflow.dao;

import org.kingson.Ims.workflow.domain.BusinessData;
import org.kingson.commrs.dao.BaseDao;

/**
 * 限定该接口只能操作{@link BusinessData}的子类的数据
 * 
 * @author lwq
 *
 * @param <T> 具体的业务数据的子类
 */
public interface BusinessDataRepository<T extends BusinessData> //
		extends BaseDao {

	/**
	 * 修改和新增的方法，有ID就修改、否则新增。
	 * 
	 * @param entity
	 * @return
	 */
	T save(T entity);

	/**
	 * 根据主键查询具体的业务数据对象
	 * 
	 * @param id
	 * @return
	 */
	T findById(String id);
}
