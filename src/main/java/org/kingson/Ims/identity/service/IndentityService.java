/**
 * 
 */
package org.kingson.Ims.identity.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.kingson.Ims.identity.domain.Module;
import org.kingson.Ims.identity.domain.Role;
import org.kingson.Ims.identity.domain.User;
import org.kingson.Ims.logMenager.domain.Diary;
import org.kingson.Ims.vacation.domain.LeaveType;
import org.kingson.commrs.pager.PageModel;
import org.springframework.web.context.request.WebRequest;

/**alt+shift+j
 * @author kingson
 * 2018年7月30日
   org.kingson.Ims.identity.service
   Imsn
   @version 1.0
 */
public interface IndentityService {

	/**
	 * @param user
	 * @param vcode
	 * @param remMe
	 * @param request
	 * @return
	 */
	String ajaxLogin(User user, String vcode, String remMe,HttpServletRequest request,HttpServletResponse response);

	/**
	 * @param string
	 * @return
	 */
	User getUserById(String string);

	/**
	 * @return
	 */
	String ajaxLoadDeptAndJob();

	/**
	 * @param user
	 * @param pageModel
	 * @return
	 */
	
	/**
	 * @param user
	 * @return
	 */
	List<User> selectUserByPage(User user, PageModel pageModel);

	/**
	 * @param 
	 */
	

	/**
	 * @param userId
	 * @return
	 */
	

	/**
	 * @param userId
	 * @return
	 */
	String checkUsername(String userId);

	/**
	 * @param user 
	 * @param request 
	 * 
	 */
	void saveUser(User user, WebRequest request);

	/**
	 * @param userIds
	 */
	void deleteUser(String userIds);

	/**
	 * @param user
	 * @param request
	 * @param createDate 
	 * @param creater 
	 */
	void saveUserorUpdate(User user, WebRequest request);

	/**
	 * @param userIdsn
	 * @return
	 */
	User updateCheckUser(String userIdsn);

	/**
	 * @param userId
	 * @param status
	 * @param request
	 */
	

	/**
	 * @param userId
	 * @param status
	 * @param request
	 */
	User activate(String userId, short status, WebRequest request);

	/**
	 * @param roles
	 * @param pageModel
	 * @return
	 */
	List<Role> selectRole(Role roles, PageModel pageModel);

	/**
	 * @param roleId
	 */
	String checkRolename(String roleId);

	/**
	 * @param role
	 * @param request
	 */
	void saveRole(Role role, WebRequest request);

	/**
	 * @param roleId
	 * @param request
	 */
	
	/**
	 * @param roleId
	 * @param request
	 */
	void updateRole(Role role, WebRequest request);

	/**
	 * @param roleId
	 * @return
	 */
	Role getRoleById(Long role);

	/**
	 * @param roleIds
	 */
	void deleteRole(String roleIds);

	/**
	 * @param id
	 * @param pageModel
	 * @return
	 */
	List<User> selectRoleUser(Long id, PageModel pageModel);

	/**
	 * @param id
	 * @param userId
	 */
	void saveBoundUser(Long id, String userId);

	/**
	 * @param roleId
	 * @param pageModel
	 * @return
	 */
	List<User> showbindUser(Long roleId, PageModel pageModel);

	/**
	 * @param roleId
	 * @param userIds
	 */
	
	/**
	 * @param roleId
	 * @param userIds
	 */
	void deleteBoundUser(Long roleId, String userIds);

	/**
	 * @return
	 */
	String ajaxLoadModule();

	/**
	 * @param user
	 * @param request
	 */
	void updateSelfForm(User user, WebRequest request);

	/**
	 * @param pCode
	 * @param pageModel
	 * @param request
	 * @return
	 */
	List<Module> getModulesByParent(String pCode, PageModel pageModel, WebRequest request);

	/**
	 * @param module
	 * @param pCode
	 * @param request
	 */
	void addModule(Module module, String pCode, WebRequest request);

	/**
	 * @param pCode
	 * @param codes
	 */
	void deleteModule(String pCode, String codes);

	/**
	 * @param code
	 */
	Module getModuleByCode(String code);

	/**
	 * @param module
	 * @param request 
	 */
	void updateModule(Module module, WebRequest request);

	/**
	 * @return
	 */
	
	
	

	/**
	 * @param code
	 * @return
	 */
	List<Module> getPopedom(String code);

	/**
	 * @param code
	 * @param id
	 * @return
	 */
	List<String> ajaxLoadPopedomInfo(String code, Long id);

	/**
	 * @param roleId
	 * @param pCode
	 * @param codes
	 * @param request 
	 */
	void bindopertion(Long roleId, String pCode, String codes, WebRequest request);

	/**
	 * @param request
	 * @return
	 */
	Map<Module, List<Module>> findLeftMenuOptions(WebRequest request);

	/**
	 * @param request
	 * @return
	 */
	List<String> findLeftOptions(WebRequest request);

	/**
	 * @param diary
	 * @param pageModel
	 * @return
	 */
	List<Diary> getLogType(Diary diary, PageModel pageModel);

	/**
	 * @param diary
	 * @param request
	 */
	void saveLogDiary(Diary diary, WebRequest request);

	/**
	 * @param ids
	 */
	void deleteDiary(String ids);

	/**
	 * @param id
	 * @return
	 */
	Diary getDiary(Long id);

	/**
	 * @param leaveType
	 * @param pageModel
	 * @return
	 */
	List<LeaveType> getAllLeave(LeaveType leaveType, PageModel pageModel);

	/**
	 * @return
	 */
	



}
