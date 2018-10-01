package org.kingson.Ims.workflow.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.kingson.Ims.vacation.domain.LeaveRequest;
import org.kingson.Ims.workflow.dao.BusinessDataRepository;
import org.kingson.Ims.workflow.domain.BusinessData;
import org.kingson.commrs.dao.impl.BaseDaoImpl;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public abstract class BusinessDataRepositoryImpl<T extends BusinessData> //
		extends BaseDaoImpl implements BusinessDataRepository<T>, InitializingBean {

	/**
	 * 当只有一个SessionFactory的时候，就可以使用自动注入
	 */
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void afterPropertiesSet() throws Exception {
		// 把注入得到的sessionFactory设置给BaseDaoImpl
		super.setSessionFactory(sessionFactory);
	}

	// 如果业务数据特别复杂，可以在子类重新save和findById方法
	@Override
	public T save(T entity) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(entity);
		return entity;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public T findById(String id) {
		Session session = sessionFactory.getCurrentSession();
		T entity = session.get(getEntityClass(), id);
		return entity;
	}

	@SuppressWarnings("unchecked")
	private Class<T> getEntityClass() {
		// 利用反射得到泛型的实际类型

		// 得到当前实例的类，此时是运行时类型
		// 得到的是BusinessDataRepositoryImpl的子类
		// 子类就要求传入实际类型
		Class<?> cla = this.getClass();

		// 得到泛型化的父类，此时相当于是得到了有传入实际类型的BusinessDataRepositoryImpl类。
		Type superType = cla.getGenericSuperclass();
		ParameterizedType type = (ParameterizedType) superType;

		// 得到尖括号传入的实际类型
		Type[] types = type.getActualTypeArguments();

		// 引入传入的实际类型只有一个
		return (Class<T>) types[0];
	}

	// 运行该main方法可以测试：是否能够正常获取到通过泛型传入的实际类型
	public static void main(String[] args) {
		// 写一个DAO类，继承抽象的父类DAO
		class TestDao extends BusinessDataRepositoryImpl<LeaveRequest> {
		}

		BusinessDataRepositoryImpl<LeaveRequest> dao = new TestDao();
		Class<?> cla = dao.getEntityClass();
		System.out.println(cla);
	}
}
