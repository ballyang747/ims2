package org.kingson.Ims.vacation.dao.Impl;

import org.kingson.Ims.vacation.dao.LeaveRequestDao;
import org.kingson.Ims.vacation.domain.LeaveRequest;
import org.kingson.Ims.workflow.dao.impl.BusinessDataRepositoryImpl;
import org.springframework.stereotype.Repository;

// 所有的DAO实现，都要继承BusinessDataRepositoryImpl类
@Repository
public class LeaveRequestDaoImpl extends BusinessDataRepositoryImpl<LeaveRequest> implements LeaveRequestDao {

}
