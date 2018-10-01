package org.kingson.Ims.workflow.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kingson.Ims.workflow.service.WorkflowService;
import org.kingson.Ims.workflow.vo.TaskItem;
import org.kingson.commrs.pager.PageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping({ "/workflow/task", "/workflow/task.jspx" })
public class TaskController {

	private static final Logger LOG = LoggerFactory.getLogger(TaskController.class);
	@Autowired
	private WorkflowService workflowService;
	// 使用Jackson实现Java和JSON的相互转换
	private ObjectMapper objectMapper = new ObjectMapper();

	@GetMapping
	public ModelAndView list(PageModel pageModel) {

		ModelAndView mav = new ModelAndView("workflow/task/list");

		List<TaskItem> list = this.workflowService.findTasks(pageModel);
		mav.addObject("list", list);

		return mav;
	}

	@GetMapping("view.jspx")
	public ModelAndView view(@RequestParam("taskId") String taskId) {

		ModelAndView mav = new ModelAndView("workflow/task/view");

		TaskItem item = this.workflowService.findTaskById(taskId);
		mav.addObject("item", item);
		mav.addObject("td", item);

		// 把业务数据对象转换为JSON
		String json = "{}";

		try {
			json = objectMapper.writeValueAsString(item.getBusinessData());
		} catch (JsonProcessingException e) {
			// 如果出现异常，json就是空对象，为了避免JS的语法报错
			LOG.warn("无法把业务数据对象转换为JSON字符串: " + e.getLocalizedMessage(), e);
		}

		mav.addObject("json", json);

		return mav;
	}

	@PostMapping
	public String complete(@RequestParam("taskId") String taskId, WebRequest request) {
		// 获取所有请求参数
		Map<String, String[]> params = new HashMap<>(request.getParameterMap());
		params.remove("taskId");

		this.workflowService.complete(taskId, params);

		return "redirect:/workflow/task.jspx";
	}
}
