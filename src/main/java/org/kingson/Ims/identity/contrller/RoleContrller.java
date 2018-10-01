/**
 * 
 */
package org.kingson.Ims.identity.contrller;

import java.util.List;

import org.kingson.Ims.identity.domain.Role;
import org.kingson.Ims.identity.domain.User;
import org.kingson.Ims.identity.service.IndentityService;
import org.kingson.commrs.pager.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

/**alt+shift+j
 * @author kingson
 * 2018年8月4日
   org.kingson.Ims.identity.contrller
   Imsn2
   @version 1.0

  @email 1606656167@qq.com
  @tel 15768607477
   /identity/role/selectRole.jspx
 */

@Controller
@Transactional
@RequestMapping("identity/role")
public class RoleContrller {
	@Autowired
	private IndentityService indentityService;
	//展示角色
	@RequestMapping(value="/selectRole.jspx")
	public String selectRole(Role role ,PageModel pageModel,Model model) {
		List<Role>  roles=indentityService.selectRole(role,pageModel);
		
		model.addAttribute("roles",roles);
		///identity/role/role
		return "/identity/role/role";
		//添加角色
		
		
	}
	
	 //跳转至添加角色页面
	  @RequestMapping(value="/showAddRole.jspx")
	  public String showAddRole() {
		  
		  return "/identity/role/addRole";
	  }
	  
	//添加角色的操作
	@RequestMapping(value="/addRole.jspx")
	public String addRole(Role role,WebRequest request,Model model) {
		try {
			indentityService.saveRole(role,request);
			model.addAttribute("message","添加成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			model.addAttribute("message","添加失败");
		}
		
		
		return "/identity/role/addRole";
	}
	
	
	
	//检查角色中是否有该角色了
	@RequestMapping("/checkRole.jspx")
	@ResponseBody
	public String checkRole(@RequestParam("roleId")String roleId) {
		String role="";
		
	role=	indentityService.checkRolename(roleId);
	return role;
	}
	
	//根据角色id查角色
	@RequestMapping("/showupdateRole.jspx")
	public String showypdateRle(@RequestParam("roleId")Long roleId,Model model) {
		try {
		Role role=	indentityService.getRoleById(roleId);
		
		model.addAttribute("role",role);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return "identity/role/updateRole";
		
	}
	//修改角色
	@RequestMapping("/updateRole.jspx")
	public String updateRole(Role role,Model model,WebRequest request) {
		
		try {
			indentityService.updateRole(role,request);
			model.addAttribute("message","修改成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			model.addAttribute("message","修改失败");
		}
		return "/identity/role/updateRole";
	}
	//删除角色
	@RequestMapping("/deleteRole.jspx")
	public String deleteRole(@RequestParam("roleIds")String roleIds,Model model) {
		
	 try {
		     indentityService.deleteRole(roleIds);
		 model.addAttribute("message","删除成功");
		
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
		model.addAttribute("message","删除失败");
	}
		return"forward:/identity/role/selectRole.jspx";
	}
	
	//查绑定用户：根据角色id查看已经绑定的用户
	@RequestMapping("/selectRoleUser.jspx")
	public String selectRoleUser(@RequestParam("id")Long id ,PageModel pageModel ,Model model) {
		//查看绑定用户具体代码
		List<User>  roleUser=   indentityService.selectRoleUser(id,pageModel);
		 
		model.addAttribute("roleUser",roleUser);
		Role role =indentityService.getRoleById(id);
		model.addAttribute("role",role);
		return "/identity/role/bindUser/roleUser";
	}
	
	
	//展示未绑定用户
	@RequestMapping("/showbindUser.jspx")
	public String showbindUser(@RequestParam("id")Long roleId,Model model,PageModel pageModel) {
		//根据角色查未绑定用户
	List<User> users=	indentityService.showbindUser(roleId,pageModel);
	           //获取分页的id值    
	         model.addAttribute("roleId",roleId);
	         model.addAttribute("users",users);
		return "identity/role/bindUser/bindUser";
	}
	//绑定用户操作
	@RequestMapping("/bound.jspx")
	public String bound (@RequestParam("id")Long id,Model model , String userId) {
		
		 try {
			 indentityService.saveBoundUser(id,userId);
			 model.addAttribute("message","添加成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			 model.addAttribute("message","添加失败");
		}
		return"forward:/identity/role/showbindUser.jspx";
	}
	//解绑用户信息
	@RequestMapping("/unboundUser.jspx")
	public String unboundUser(@RequestParam("userIds")String userIds,@RequestParam("id")Long id,Model model) {

		 try {
			 indentityService.deleteBoundUser(id,userIds);
			 model.addAttribute("message","解绑成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			 model.addAttribute("message","解绑失败");
		}
		
		return"forward:/identity/role/selectRoleUser.jspx";
	}
	
	
}
