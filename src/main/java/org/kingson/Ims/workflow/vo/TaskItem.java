package org.kingson.Ims.workflow.vo;

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.task.Task;
import org.kingson.Ims.identity.domain.User;
import org.kingson.Ims.workflow.domain.BusinessData;

public class TaskItem extends ProcessForm {

	// 流程实例，历史流程实例，比流程实例包含的信息更多
	// 查询的时候，由于是主键查询，所以没有太大的效率问题
	private HistoricProcessInstance instance;
	// 流程定义
//	private ProcessDefinition definition;
	// 流程的创始人，要根据HistoricProcessInstance的startUserId再查询
	private User initiator;
	// 待办任务
	private Task task;

	// 业务数据对象
	private BusinessData businessData;

	public HistoricProcessInstance getInstance() {
		return instance;
	}

	public void setInstance(HistoricProcessInstance instance) {
		this.instance = instance;
	}

//	public ProcessDefinition getDefinition() {
//		return definition;
//	}
//
//	public void setDefinition(ProcessDefinition definition) {
//		this.definition = definition;
//	}

	public User getInitiator() {
		return initiator;
	}

	public void setInitiator(User initiator) {
		this.initiator = initiator;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public BusinessData getBusinessData() {
		return businessData;
	}

	public void setBusinessData(BusinessData businessData) {
		this.businessData = businessData;
	}
}
