/**
 * 
 */
package org.kingson.Ims.identity.contrller;

import java.util.List;
import java.util.Map;

import org.kingson.Ims.identity.domain.Module;
import org.kingson.Ims.identity.service.IndentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;



@Controller

public class MainContrller {
	@Autowired
	private  IndentityService indentityService;
	 
	@RequestMapping(value="/main.jspx")
	public String main(Model model ,WebRequest request) {
		
		try {
			//根据用户账号获得main.jsp左侧菜单栏相关信息
		Map<Module, List<Module>> mapMenu=	indentityService.findLeftMenuOptions(request);
			model.addAttribute("mapMenu",mapMenu);
			
			
			List<String>  options=indentityService.findLeftOptions(request);
			request.setAttribute("session_option", options, WebRequest.SCOPE_SESSION);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "main";
	}

}
