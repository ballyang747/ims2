package org.kingson.Ims.identity.contrller;



import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.kingson.Ims.identity.domain.User;
import org.kingson.Ims.identity.service.IndentityService;
import org.kingson.commrs.CookieUtils;
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
   org.kingson.Ims.identity.contrller
   /identity/user/ajaxLogin.jspx 
    * /indentity/user/selectUser.jspx
   @version 1.0
 */
@Controller
@RequestMapping("/identity/user")
public class UserContrlller {
	
	
	@Autowired
	private IndentityService indentityService;
	@ResponseBody
	@RequestMapping(value="/ajaxLogin.jspx",produces="application/text;charset=utf-8")
	public String ajaxLogin(User user ,String vcode,String  remMe, HttpServletRequest request ,HttpServletResponse response) {
		String msg =indentityService.ajaxLogin(user,vcode,remMe,request,response);
		
		return msg;
	}
	@RequestMapping(value="/logout.jspx")
	public String logoutjspx(HttpServletRequest request ,HttpServletResponse response) {
		 request.getSession().removeAttribute("session_user");
		 CookieUtils.removeCookies("LOGIN_COOKIE_NAME", request, response);
		
		return "login";
	}
	//展示用户信息分页查询
	@RequestMapping(value="/selectUser.jspx",produces= {"application/json;charset=utf-8"})
	public  String selectUser(User user ,PageModel pageModel,Model modle) {
	
	List<User> users=	indentityService.selectUserByPage(user,pageModel);
	System.out.println("users------------>"+users);
	modle.addAttribute("users",users);
		return "/identity/user/user";
		
		
	}
	
	@RequestMapping(value="/ajaxLoadDeptAndJob.jspx",produces= {"application/json;charset=utf-8"})
	@ResponseBody
	public String ajaxLoadDeptAndJob () {
		
		
		String jsonStr = indentityService.ajaxLoadDeptAndJob();
		return jsonStr;
		//,produces= {"application/json;charset=utf-8"}
	}
	//异步校验
	@RequestMapping(value="/checkUser.jspx",method=RequestMethod.POST)
	@ResponseBody
	public String addUser(@RequestParam("userId")String  userId) {
		    String user = "";
		
		     System.out.println("json数据"+userId);
	         user=   indentityService.checkUsername(userId);
		
	     return  user;
		
	}
	
	//添加用户跳转
	@RequestMapping("/showAduUser.jspx")
	public String addUserRequest() {
		
		
		
		return "/identity/user/addUser";
	}
	
	  //删除用户
	    @RequestMapping("/deleteUser.jspx")
	    public String deleteUser(@RequestParam("userIds") String userIds,Model model) {
	    	 try {
	    		   indentityService.deleteUser(userIds);
		    	    model.addAttribute("message","删除成功");
		    	    
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				
				model.addAttribute("message","删除失败");
			}
	    	    return "forward:/identity/user/selectUser.jspx";
	    }
	    
	    //添加用户信息
	    @RequestMapping(value="/addUser.jspx")
	    public String addUser(User user ,WebRequest request,Model model) {
	    	
	    	System.err.println(user.toString());
	    	try {
	    		indentityService.saveUser(user,request);
				model.addAttribute("message","添加成功");
			} catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("message","添加失败,请查看是否重名");
			}
	    	return "/identity/user/addUser";
	    }
	    
	    @RequestMapping("/updateUser.jspx")
	    public String updateUser(@RequestParam("userId")String userId,Model model) {
	    	System.out.println("userIdsn"+userId);
	    	
	    	try {
	    		
	    		
	    		 User user =    indentityService.updateCheckUser(userId);
	    		 System.out.println("用户---------->"+user.getStatus());
		    	  model.addAttribute("user",user);
		    	
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
	    	
	    	
	    	//updateUser
	    	return "/identity/user/updateUser";
	    }
	    //修改用户
	    //updateUserorsave.jspx"
	    @RequestMapping("/updateUserorsave.jspx")
	    public String uptateUserorsave(User user,Model model,WebRequest request) {
	    	try {
	    		//System.out.println("创建人"+user.getCreater());
	    		
	    		 indentityService.saveUserorUpdate(user,request);
				model.addAttribute("message","修改成功");
			} catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("message","修改失败");
			}
	    	
	    	return "/identity/user/updateUser";
	    }
	    //预览用户
	    @RequestMapping("/lookUser.jspx")
	    public String lookuser(String userId,Model model) {
	   User user = 	indentityService.getUserById(userId);
	    	model.addAttribute("user",user);
	    	return"/identity/user/preUser";
	    }
	    //激活用户
	    @RequestMapping("/activeUser.jspx")
	    public String activate( String userId, @RequestParam("status")short status,WebRequest request,Model model) {
	    	User users=  indentityService.activate(userId,status,request);
	    	try {
	              model.addAttribute("users",users);
			 model.addAttribute("message",users.getStatus()==1?"激活成功": "冻结成功");
				} catch (Exception e) {
					// TODO: handle exception
				e.printStackTrace();
				model.addAttribute("message",users.getStatus()==1?"激活成功": "冻结成功");
				}
	    	return "forward:/identity/user/selectUser.jspx";
	    }
	    @RequestMapping("/updateSelf.jspx")
	    public String updateSelfForm(User user ,Model model ,WebRequest request ) {
	       try {
	    	   indentityService.updateSelfForm(user,request);
	    	   model.addAttribute("message","修改成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException("修改失败"+e.getMessage());
		}
	       return "home";
	    
	    }
}
