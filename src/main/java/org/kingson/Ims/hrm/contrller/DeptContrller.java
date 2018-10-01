/**
 * 
 */
package org.kingson.Ims.hrm.contrller;

import java.util.List;

import org.kingson.Ims.hrm.domain.Dept;
import org.kingson.Ims.hrm.service.HrmService;
import org.kingson.commrs.pager.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;



/**alt+shift+j
 * @author kingson
 * 2018年7月30日
   org.kingson.Ims.hrm.contrller
   Imsn
   @version 1.0
 */
@Controller
@RequestMapping("/hrm/dept")
public class DeptContrller {
	
	@Autowired
	private  HrmService hrmService;
	
	
	@RequestMapping(value="/dept.jspx")
	
	public String deptAll(Dept dept, Model model,PageModel pageModel) {
		
	List<Dept> depts=	hrmService.getAllDept(dept,pageModel);
	model.addAttribute("depts",depts);
	depts.forEach(d->System.out.println(d));
		return "/hrm/dept/dept";
	}
	//添加部门
	@RequestMapping("/showAddDept.jspx")
	public String showAddDept() {
		
		return "/hrm/dept/add_dept";
		
	}
	
	@RequestMapping(value="/checkDeptName.jspx",method=RequestMethod.POST)
	@ResponseBody
	public String checkDeptName(String deptname) {
		
		String deptnames="";
		deptnames =hrmService.checkDeptname(deptname);
		
		return deptnames;
	}
	@RequestMapping("/addDept.jspx")
	public String addDept(Dept dept ,Model model,WebRequest request) {
		
		try {
			hrmService.addDept(dept,request);
			model.addAttribute("message","添加成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			model.addAttribute("message","添加失败");
		}
		return "/hrm/dept/add_dept";
	}
	//删除部门
     @RequestMapping("/deleteDept.jspx")
	public String delectDept(@RequestParam("ids")String ids,PageModel pageModel,Model model  ) {
    	 
    	 try {
    		 hrmService.delectDept(ids);
			model.addAttribute("message","删除失成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		model.addAttribute("message","删除失败");
		}
		return "forward:/hrm/dept/dept.jspx";
     }
     @RequestMapping(value="/showUpdateDept.jspx")
     public String showUpdateDept(@RequestParam("id")Long id,Model model) {
    try {
   	 Dept dept = hrmService.getDept(id);
   	 model.addAttribute("dept",dept);
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
    	 
    	 return "/hrm/dept/update_dept";
     }
     //修改用户
     @RequestMapping("/updateDept.jspx")
     public String updateDept(Dept dept ,Model model ,WebRequest request) {
    	  
    	 
    	 try {
			  hrmService.updateDept(dept,request);
    		 model.addAttribute("message","修改成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			model.addAttribute("message","修改失败");
		}
    	 return "/hrm/dept/update_dept";
    	 
     }

}
