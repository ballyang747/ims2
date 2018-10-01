package org.kingson.Ims.test.dao.impl;

import org.kingson.Ims.test.dao.TestProcessDao;
import org.kingson.Ims.test.domain.TestProcess;
import org.kingson.Ims.workflow.dao.impl.BusinessDataRepositoryImpl;
import org.springframework.stereotype.Repository;

@Repository
public class TestProcessDaoImpl extends BusinessDataRepositoryImpl<TestProcess> implements TestProcessDao {

}
