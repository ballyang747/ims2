/**
 * 
 */
package org.kingson.Ims.identity.contrller;

import java.util.List;

import org.kingson.Ims.identity.domain.Module;
import org.kingson.Ims.identity.domain.Role;
import org.kingson.Ims.identity.service.IndentityService;
import org.kingson.commrs.pager.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

/**alt+shift+j
 * @author kingson
 * 2018年8月7日
   org.kingson.Ims.identity.contrller
   Imsn2
   @version 1.0

  @email 1606656167@qq.com
  @tel 15768607477
   
 */
@Controller
@Transactional
@RequestMapping("/identity/popedom")
public class PopedomContrller {
	
	
	@Autowired
	private IndentityService indentityService;
	
	//通过ajax请求获得操作相关信息
	@RequestMapping("/mgrPopedom.jspx")
	public String ajaxLoadpopedom(@RequestParam("id")Long id ,PageModel pageModel ,Model model) {
		  model.addAttribute("id",id);
		return "/identity/bindPopedom/mgrPopedom";
	} 

	//根据上一级获取下一级信息
	@RequestMapping("/getLoadPopedom.jspx")
	public String getLoadpopedom(Module module,@RequestParam("id") Long id,Model model) {
		
		
		try {
			//获得模块信息
		List<Module> modules= indentityService.getPopedom(module.getCode());
		model.addAttribute("modules",modules);
		//获取角色信息
		Role role = indentityService.getRoleById(id);
		model.addAttribute("role",role);
		
		//根据角色id和第二级模块查权限表,获得角色已经绑定的操作
	   List<String> operas=	indentityService.ajaxLoadPopedomInfo(module.getCode(),id);
		model.addAttribute("operas",operas);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "identity/bindPopedom/operas";
	}
	@RequestMapping("/Bindopertion.jspx")
	public String Bindopertion(@RequestParam("code")String pCode,@RequestParam("id")Long roleId,String codes,Model model,WebRequest request) {
		
		    try {  
		    	indentityService.bindopertion(roleId,pCode,codes,request);
				model.addAttribute("message","绑定成功");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				model.addAttribute("message",e.getMessage());
			}
		return "forward:/identity/popedom/getLoadPopedom.jspx";
				
	
	}

}
