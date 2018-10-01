package org.kingson.Ims.workflow.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.activiti.engine.repository.ProcessDefinition;
import org.kingson.Ims.workflow.vo.ProcessForm;
import org.kingson.Ims.workflow.vo.ProcessImage;
import org.kingson.Ims.workflow.vo.TaskItem;
import org.kingson.commrs.pager.PageModel;

public interface WorkflowService {

	void deploy(String name, InputStream in);

	List<ProcessDefinition> findProcessDefinitions(PageModel pageModel);

	void activeProcessDefinition(String id);

	void suspendProcessDefinition(String id);

	ProcessDefinition getProcessDefinitionById(String id);

	ProcessImage getProcessDefinitionImageById(String id);

	ProcessForm getStartForm(String key);

	void startProcessInstance(String id, Map<String, String[]> params);

	List<TaskItem> findTasks(PageModel pageModel);

	TaskItem findTaskById(String taskId);

	void complete(String taskId, Map<String, String[]> params);

}
