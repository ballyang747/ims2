package org.kingson.Ims.workflow.controller;

import java.util.HashMap;
import java.util.Map;

import org.kingson.Ims.workflow.service.WorkflowService;
import org.kingson.Ims.workflow.vo.ProcessForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/workflow/process/instance")
public class ProcessInstanceController {

	@Autowired
	private WorkflowService workflowService;

	// 通过GET请求显示启动的表单
	@GetMapping("{key}.jspx")
	public ModelAndView startForm(@PathVariable("key") String key) {
		ModelAndView mav = new ModelAndView("workflow/process/instance/startForm");

		// 根据KEY找到最后一个版本的流程定义
		ProcessForm pf = workflowService.getStartForm(key);
		mav.addObject("pf", pf);

		return mav;
	}

	// WebRequest是Spring MVC提供的一个Request封装/门面，能够获取所有的请求参数。
	// 对WebRequest的操作，最终会对应到HttpServletRequest对象。
	@PostMapping("start.jspx")
	public String startForm(@RequestParam("processDefinitionId") String processDefinitionId, WebRequest request) {

		// 获取所有请求参数
		Map<String, String[]> params = new HashMap<>(request.getParameterMap());
		params.remove("processDefinitionId");
		// 启动流程实例
		this.workflowService.startProcessInstance(processDefinitionId, params);

		// 启动流程实例以后，重定向到【我的申请】
		return "redirect:/workflow/history/instance";
	}
}
