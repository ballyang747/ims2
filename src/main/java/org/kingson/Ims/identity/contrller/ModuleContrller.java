/**
 * 
 */
package org.kingson.Ims.identity.contrller;

import java.util.List;

import org.kingson.Ims.identity.domain.Module;
import org.kingson.Ims.identity.service.IndentityService;
import org.kingson.commrs.pager.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

/**alt+shift+j
 * @author kingson
 * 2018年8月5日
   org.kingson.Ims.identity.contrller
   Imsn2
   @version 1.0

  @email 1606656167@qq.com
  @tel 15768607477
   
 */
@Controller
@RequestMapping("/identity/module")
public class ModuleContrller {
	
	@Autowired
	private IndentityService  indentityService;
	
	@RequestMapping("/mgrModule.jspx")
	public String Moudle() {
		
		return "/identity/module/mgrModule";
		
	}
	@ResponseBody
	@RequestMapping(value="/ajaxAndjsonLoadModule.jspx",produces= {"application/json;charset=utf-8"})
	public String ajaxLoadModule() {
	            try {
	            	String jsonobj=indentityService.ajaxLoadModule();
	        		return jsonobj;
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					
				}
	            return"";
	          
	}
	//根据上一级模块获取下一级模块信息
	@RequestMapping("/getModulesByParent.jspx")
	public String getModulesByParent(String pCode,PageModel pageModel,Model model,WebRequest request) {
		
		            try {
		            	List<Module> modules=  indentityService.getModulesByParent(pCode,pageModel,request);
		            	model.addAttribute("modules",modules);
		            	model.addAttribute("pCode",pCode);
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
		return "identity/module/module";
		
	}
	
	//展示添加模块版
	@RequestMapping("/showAddModule.jspx")
	public String showAddModule(@RequestParam("pCode")String pCode,Model model) {
		
		model.addAttribute("pCode",pCode);
		return"/identity/module/addModule";
		
	}
	
	@RequestMapping("/addModule.jspx")
	public String addModule(Module module,@RequestParam("pCode")String pCode ,Model model,WebRequest request) {
		
		try {
			indentityService.addModule(module,pCode,request);
			model.addAttribute("pCode",pCode);
			model.addAttribute("message","添加成功");
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			model.addAttribute("message",e.getMessage());
			
		}
		
		return "identity/module/addModule";
	}
	//删除模块
	@RequestMapping("/deleteModule.jspx")
	  public String deleteModule( @RequestParam("codes")String codes,Model  model,String pCode) {
	   
		
		try {
			indentityService.deleteModule(pCode,codes);
			
			model.addAttribute("message","删除成功");
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		     model.addAttribute("message","删除失败");
		}
		return "forward:/identity/module/getModulesByParent.jspx";
   }
	
	//跳转到修改页面->从数据库中查数据到前台
	@RequestMapping("/showUpdateModuleByCode.jspx")
	public String showUpdateModuleByCode(@RequestParam("code")String code,Model model) {
		
		try {
		Module module =	 indentityService.getModuleByCode(code);
			 model.addAttribute("module",module);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return"identity/module/updateModule";
		
	
	}
	@RequestMapping("/updateModule.jspx")
	public String updateModule(Module module ,Model model,WebRequest request) {
		
		try {
			indentityService.updateModule(module,request);
			model.addAttribute("message", "修改成功");
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			model.addAttribute("message", "修改失败");
		}
		return"/identity/module/updateModule";
	}
}