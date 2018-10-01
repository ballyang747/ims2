package org.kingson.Ims.workflow.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.activiti.engine.repository.ProcessDefinition;
import org.kingson.Ims.workflow.service.WorkflowService;
import org.kingson.Ims.workflow.vo.ProcessImage;
import org.kingson.commrs.pager.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = { "/workflow/process/definition.jspx", "/workflow/process/definition" })
public class ProcessDefinitionController {

	@Autowired
	private WorkflowService workflowService;

	// 显示流程定义的列表
	@GetMapping
	public ModelAndView list(PageModel pageModel) {
		// Model : 数据、模型
		// View : 视图
		ModelAndView mav = new ModelAndView("workflow/process/definition/list");

		List<ProcessDefinition> list = this.workflowService.findProcessDefinitions(pageModel);
		mav.addObject("list", list);

		return mav;
	}

	// 映射的路径，只是upload.jspx，但是它会继承类上面的注解的值
	// /workflow/process/definition/upload.jspx
	// /workflow/process/definition.jspx/upload.jspx
	@PostMapping("upload.jspx")
	public String upload(@RequestParam("process") MultipartFile file) throws IOException {
		// 原始的文件名
		String name = file.getOriginalFilename();
		// 获取输入流
		try (InputStream in = file.getInputStream();) {
			// 调用业务逻辑层的方法，完成流程定义的部署
			this.workflowService.deploy(name, in);
		}

		return "redirect:/workflow/process/definition.jspx";
	}

	@GetMapping("active.jspx")
	public String active(@RequestParam("id") String id) throws IOException {
		this.workflowService.activeProcessDefinition(id);

		return "redirect:/workflow/process/definition.jspx";
	}

	@GetMapping("suspend.jspx")
	public String suspend(@RequestParam("id") String id) throws IOException {
		this.workflowService.suspendProcessDefinition(id);

		return "redirect:/workflow/process/definition.jspx";
	}

	// 根据ID获取流程定义
	@GetMapping("view.jspx")
	public ModelAndView view(@RequestParam("id") String id) {
		ModelAndView mav = new ModelAndView("workflow/process/definition/view");
		ProcessDefinition def = this.workflowService.getProcessDefinitionById(id);
		mav.addObject("def", def);
		return mav;
	}

	// ResponseEntity是完全自定义的响应，包括响应头、响应体
	@GetMapping("image.jspx")
	public ResponseEntity<byte[]> image(@RequestParam("id") String id) throws UnsupportedEncodingException {

		ProcessImage image = this.workflowService.getProcessDefinitionImageById(id);
		byte[] data = image.getData();
		String name = image.getName();
		name = URLEncoder.encode(name, "UTF-8");

		// ok() : 其实就是设置响应状态码为200
		BodyBuilder builder = ResponseEntity.ok();
		builder.contentType(MediaType.IMAGE_PNG);
		builder.contentLength(data.length);
		// 完全自定义响应头
		// Content-Disposition告诉浏览器如何处理响应，attachment表示一个附件，直接访问该链接会打开文件保存窗口
		// filename*=UTF-8''，指定字符串的编码方式，name需要使用URLEncoder进行编码，有两个单引号隔开
		builder.header("Content-Disposition", "attachment; filename*=UTF-8''" + name);

		ResponseEntity<byte[]> entity = builder.body(data);
		return entity;
	}
}
