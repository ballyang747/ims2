package org.kingson.Ims.vacation.dao;

import org.kingson.Ims.vacation.domain.LeaveRequest;
import org.kingson.Ims.workflow.dao.BusinessDataRepository;

// 所有的业务数据的DAO都要继承BusinessDataRepository接口
public interface LeaveRequestDao extends BusinessDataRepository<LeaveRequest> {

}
