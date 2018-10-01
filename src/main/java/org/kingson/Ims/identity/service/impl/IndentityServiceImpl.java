/**
 * 
 */
package org.kingson.Ims.identity.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.kingson.Ims.hrm.dao.DeptDao;
import org.kingson.Ims.hrm.dao.JobDao;
import org.kingson.Ims.identity.dao.ModuleDao;
import org.kingson.Ims.identity.dao.PopedomDao;
import org.kingson.Ims.identity.dao.RoleDao;
import org.kingson.Ims.identity.dao.UserDao;
import org.kingson.Ims.identity.domain.Module;
import org.kingson.Ims.identity.domain.Popedom;
import org.kingson.Ims.identity.domain.Role;
import org.kingson.Ims.identity.domain.User;
import org.kingson.Ims.identity.service.IndentityService;
import org.kingson.Ims.logMenager.dao.DiaryDao;
import org.kingson.Ims.logMenager.domain.Diary;
import org.kingson.Ims.vacation.dao.LeaveDao;
import org.kingson.Ims.vacation.domain.LeaveType;
import org.kingson.commrs.ConstantUtil;
import org.kingson.commrs.CookieUtils;
import org.kingson.commrs.pager.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.WebRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * alt+shift+j
 * 
 * @author kingson 2018年7月30日 org.kingson.Ims.identity.service.impl Imsn
 * @version 1.0
 */
@Service("indentityService")
@Transactional(rollbackFor = java.lang.Exception.class, readOnly = false)
public class IndentityServiceImpl implements IndentityService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private DeptDao deptDao;
	@Autowired
	private JobDao jobDao;

	// 角色管理
	@Autowired
	private RoleDao roleDao;

	// 模块管理
	@Autowired
	private ModuleDao moduleDao;

	// 绑定操作
	@Autowired
	private PopedomDao popedomDao;
	//工作日记
	@Autowired
	private DiaryDao diaryDao;
	
	//假期管理
	@Autowired
	private LeaveDao leaveDao;

	@Override
	public String ajaxLogin(User user, String vcode, String remMe, HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		try {
			String session_code = (String) request.getSession().getAttribute(ConstantUtil.VCODE);

			if (!session_code.equals(vcode)) {
				return "验证码不 正确";

			} else {
				User u = userDao.get(User.class, user.getUserId());
				System.out.println("进来到indentityService");
				if (u == null) {
					return "账号不正确";
				} else if (!u.getPassWord().equals(user.getPassWord())) {
					return "密码不正确";
				} else {
					request.getSession().setAttribute(ConstantUtil.SESSION_USER, u);

					if ("checked".equals(remMe)) {
						// 判断从浏览器传回来的参数是否需要记住一周
						/**
						 * 1.LOGIN_COOKIE_NAME Cookie的名字 2.7*24*60*60 ->有效时间 3.用户名 4.密码 5.请求对象 6.响应对象
						 */
						CookieUtils.addCookie(ConstantUtil.LOGIN_COOKIE_NAME, 7 * 24 * 60 * 60, u.getUserId(),
								u.getPassWord(), request, response);

					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public User getUserById(String userId) {
		// TODO Auto-generated method stub

		try {

			return userDao.get(User.class, userId);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String ajaxLoadDeptAndJob() {
		try {
			List<Map<Long, String>> depts = deptDao.findAllDept();

			List<Map<Long, String>> jobs = jobDao.findAllJob();
			System.out.println("部门" + depts + "职位" + jobs);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("depts", depts);
			jsonObject.put("jobs", jobs);
			return jsonObject.toString();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<User> selectUserByPage(User user, PageModel pageModel) {
		try {
			List<Object> params = new ArrayList<>();
			StringBuffer hql = new StringBuffer();
			hql.append("select u from User u where delFlag=1 ");
			if (StringUtils.isNotEmpty(user.getName())) {

				hql.append(" and u.name like ?");

				params.add("%" + user.getName() + "%");
			}

			if (StringUtils.isNotEmpty(user.getPhone())) {

				hql.append(" and u.phone like ?");
				params.add("%" + user.getPhone() + "%");

			}

			if (user.getDept() != null && user.getDept().getId() != null && user.getDept().getId() != 0) {

				hql.append(" and u.job.cod =?");

				params.add(user.getJob().getCode());
			}

			return userDao.findByPage(hql.toString(), pageModel, params);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	@Override // 检查用户名是否存在
	public String checkUsername(String userId) {
		// TODO Auto-generated method stub
		try {

			System.out.println(userId);
			List<User> user1 = userDao.find(User.class);
			for (User user : user1) {
				System.out.println("user数量" + user1.size());
				System.out.println(user.getUserId());

				if (userId != null && !userId.equals("")) {

					if (userId.equals(user.getUserId())) {

						return "error";
					}

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";

	}

	@Override // 删除用户
	public void deleteUser(String userIds) {
		// TODO Auto-generated method stub
		String[] ids = userIds.split(",");

		// 使用逻辑删除
		userDao.deleteUser(ids);

	}

	@SuppressWarnings("deprecation")
	@Override // 修改用户
	public void saveUserorUpdate(User user, WebRequest request) {
		try {

			user.setCreateDate(new Date());
			user.setCreater(user.getCreater());
			user.setModifyDate(new Date());
			User modifierUser = (User) request.getAttribute(ConstantUtil.SESSION_USER, WebRequest.SCOPE_SESSION);
			user.setModifier(modifierUser);

			userDao.update(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public User updateCheckUser(String userIdsn) {
		// TODO Auto-generated method stub

		try {
			User user = userDao.get(User.class, userIdsn);

			System.out.println(user);
			return user;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kingson.Ims.identity.service.IndentityService#activate(java.lang.String,
	 * java.lang.String, org.springframework.web.context.request.WebRequest)
	 */
	@Override
	public User activate(String userId, short status, WebRequest request) {
		// TODO Auto-generated method stub
		try {

			User u = userDao.get(User.class, userId);

			if (status == 0) {
				// 冻结
				u.setStatus((short) 0);

			} else {
				// 解冻
				u.setStatus((short) 1);
			}

			u.setModifyDate(new Date());

			User userModify = (User) request.getAttribute(ConstantUtil.SESSION_USER, WebRequest.SCOPE_SESSION);
			u.setChecker(userModify);
			u.setCheckDate(new Date());
			u.setModifier(userModify);

			return u;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kingson.Ims.identity.service.IndentityService#saveUser(org.kingson.Ims.
	 * identity.domain.User, org.springframework.web.context.request.WebRequest)
	 */
	@Override
	public void saveUser(User user, WebRequest request) {
		// TODO Auto-generated method stub
		try {

			user.setCreateDate(new Date());

			User createUser = (User) request.getAttribute(ConstantUtil.SESSION_USER, WebRequest.SCOPE_SESSION);

			user.setCreater(createUser);
			userDao.save(user);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	/*******************************
	 * Role角色管理区
	 **************************************/
	@Override
	public List<Role> selectRole(Role roles, PageModel pageModel) {

		try {

			String hql = " from Role  where delFlag= '1' ";

			List<Role> roles2 = roleDao.findByPage(hql, pageModel, null);
			if (roles2 != null) {

				for (int i = 0; i < roles2.size(); i++) {
					if (roles2.get(i).getCreater() != null) {
						roles2.get(i).getCreater().getName();
					}
					if (roles2.get(i).getModifier() != null) {
						roles2.get(i).getModifier().getName();
					}
				}
			}
			return roles2;

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("查询数据失败" + e.getMessage());
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kingson.Ims.identity.service.IndentityService#checkRolename(java.lang.
	 * String)
	 */
	@Override
	public String checkRolename(String roleId) {
		try {

			System.out.println(roleId);
			List<Role> roles = roleDao.find(Role.class);
			for (Role role : roles) {

				if (roleId != null && !roleId.equals("")) {

					if (roleId.equals(role.getName())) {

						return "error";
					}

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kingson.Ims.identity.service.IndentityService#saveRole(org.kingson.Ims.
	 * identity.domain.Role, org.springframework.web.context.request.WebRequest)
	 */
	@Override
	public void saveRole(Role role, WebRequest request) {
		// TODO Auto-generated method stub
		try {

			role.setCreateDate(new Date());
			User createrUser = (User) request.getAttribute(ConstantUtil.SESSION_USER, WebRequest.SCOPE_SESSION);
			role.setCreater(createrUser);
			roleDao.save(role);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException("保存数据失败" + e.getMessage());
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.kingson.Ims.identity.service.IndentityService#updateRole(java.lang.
	 * String, org.springframework.web.context.request.WebRequest)
	 */
	@Override
	public void updateRole(Role roleId, WebRequest request) {

		try {
			Role role = roleDao.get(Role.class, roleId.getId());
			role.setName(roleId.getName());
			role.setModifyDate(new Date());
			role.setRemark(roleId.getRemark());
			User usermodifiet = (User) request.getAttribute(ConstantUtil.SESSION_USER, WebRequest.SCOPE_SESSION);
			role.setModifier(usermodifiet);
		} catch (Exception e) {

			e.printStackTrace();
			throw new RuntimeException("修改失败" + e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kingson.Ims.identity.service.IndentityService#getRoleById(java.lang.Long)
	 */
	@Override
	public Role getRoleById(Long roleId) {

		try {
			return roleDao.get(Role.class, roleId);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException("查询异常" + e.getMessage());
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.kingson.Ims.identity.service.IndentityService#deleteRole(java.lang.
	 * String)
	 */
	@Override
	public void deleteRole(String roleIds) {
		try {
			String[] ids = roleIds.split(",");
			roleDao.deleteRole(ids);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException("删除异常" + e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kingson.Ims.identity.service.IndentityService#selectRoleUser(java.lang.
	 * Long, org.kingson.commrs.pager.PageModel)
	 */
	@Override
	public List<User> selectRoleUser(Long id, PageModel pageModel) {
		try {
			return roleDao.selectRoleUser(id, pageModel);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException("查询数据失败" + e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kingson.Ims.identity.service.IndentityService#showbindUser(java.lang.
	 * Long, org.kingson.commrs.pager.PageModel)
	 */
	@Override
	public List<User> showbindUser(Long roleId, PageModel pageModel) {

		try {
			return roleDao.showbindUser(roleId, pageModel);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException("展示为绑定用户失败" + e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kingson.Ims.identity.service.IndentityService#saveBoundUser(java.lang.
	 * Long, java.lang.String)
	 */
	@Override
	public void saveBoundUser(Long id, String userId) {
		// TODO Auto-generated method stub
		// 根据角色id获取角色信息
		try {
			Role role = roleDao.get(Role.class, id);
			// 获取角色已经绑定了哪些用户信息
			Set<User> roleusers = role.getUsers();

			String[] ids = userId.split(",");
			for (int i = 0; i < ids.length; i++) {
				User user = userDao.get(User.class, ids[i]);
				roleusers.add(user);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException("绑定异常 " + e.getMessage());
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kingson.Ims.identity.service.IndentityService#deleteBoundUser(java.lang.
	 * String, java.lang.String)
	 */
	@Override
	public void deleteBoundUser(Long roleId, String userIds) {

		try {
			String[] userId = userIds.split(",");
			// 拿到已经绑定的用户
			Role role = roleDao.get(Role.class, roleId);
			Set<User> roleUser = role.getUsers();
			for (int i = 0; i < userId.length; i++) {
				User user = userDao.get(User.class, userId[i]);
				roleUser.remove(user);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException("解绑失败" + e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.kingson.Ims.identity.service.IndentityService#ajaxLoadModule()
	 */
	// 模块系统树
	@Override
	public String ajaxLoadModule() {
		// TODO Auto-generated method stub
		try {
			List<Module> modules = moduleDao.findAllModule();
			JSONArray jsonArray = new JSONArray();
			for (int i = 0; i < modules.size(); i++) {
				JSONObject jsonObject = new JSONObject();
				Module module = modules.get(i);
				String code = module.getCode();
				jsonObject.put("id", code);
				jsonObject.put("pid", code.length() == 4 ? "1" : code.substring(0, code.length() - 4));
				jsonObject.put("name", module.getName());
				jsonArray.add(jsonObject);
			}
			return jsonArray.toString();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException("数据加载异常" + e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kingson.Ims.identity.service.IndentityService#updateSelfForm(org.kingson.
	 * Ims.identity.domain.User, org.springframework.web.context.request.WebRequest)
	 */
	@Override
	public void updateSelfForm(User user, WebRequest request) {
		// TODO Auto-generated method stub

		try {
			User user2 = userDao.get(User.class, user.getUserId());
			user2.setName(user.getName());
			user2.setPhone(user.getPhone());
			user2.setTel(user.getTel());
			user2.setQqNum(user.getQqNum());
			user2.setQuestion(user.getQuestion());
			request.setAttribute(ConstantUtil.SESSION_USER, user2, WebRequest.SCOPE_SESSION);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException("修改失败" + e.getMessage());
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kingson.Ims.identity.service.IndentityService#getModulesByParent(java.
	 * lang.String, org.kingson.commrs.pager.PageModel,
	 * org.springframework.web.context.request.WebRequest)
	 */
	@Override
	public List<Module> getModulesByParent(String pCode, PageModel pageModel, WebRequest request) {
		try {
			List<Module> modules = moduleDao.getModulesByParent(pCode, pageModel);

			return modules;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException("查询异常" + e.getMessage());
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kingson.Ims.identity.service.IndentityService#addModule(org.kingson.Ims.
	 * identity.domain.Module, java.lang.String,
	 * org.springframework.web.context.request.WebRequest)
	 */
	@Override
	public void addModule(Module module, String pCode, WebRequest request) {

		System.out.println(module);
		System.out.println("p1--------->" + pCode);

		try {
			// 定义 新添加的模块的code'
			String newCode = "";
			// 从前台传过来的PCode为null时，我们就将""赋给pCode
			pCode = pCode == null ? "" : pCode;
			System.out.println("pCode" + pCode);
			// 获取最大的子模块
			String maxSonCode = moduleDao.findMaxCodeByPcode(pCode);
			// maxSonCode没问题
			System.out.println("maxSonCode------->" + maxSonCode);
			if (StringUtils.isEmpty(maxSonCode)) {
				// 当maxSonCode为空时,说明没有子模块
				newCode = pCode + "0001";
			} else {
				String code = "";
				// 获取从页面传过来的pCode,截取最后的四位
				System.out.println("pCode-------->" + pCode);
				
					//我们要切的是从数据
					code = maxSonCode.substring(maxSonCode.length() - 4, maxSonCode.length());
			

				int intCode = Integer.valueOf(code) + 1;
				System.out.println("code:" + code + "intCode" + intCode);
				String finalCode = String.valueOf(intCode);

				for (int i = 0; i < 4 - String.valueOf(intCode).length(); i++) {
					finalCode = "0" + finalCode;
					System.out.println("finalCode" + finalCode);
				}
				newCode = maxSonCode.substring(0, maxSonCode.length() - 4) + finalCode;

				

			}
			
			module.setCode(newCode);
			module.setCreateDate(new Date());
			module.setCreater((User) request.getAttribute(ConstantUtil.SESSION_USER, WebRequest.SCOPE_SESSION));
			moduleDao.save(module);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException("添加异常" + e.getMessage(), e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kingson.Ims.identity.service.IndentityService#deleteModule(java.lang.
	 * String, java.lang.String)
	 */
	@Override
	public void deleteModule(String pCode, String codes) {
		try {
			String[] code = codes.split(",");
			
			
			moduleDao.deleteModule(pCode, code);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException("删除异常" + e.getMessage());
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kingson.Ims.identity.service.IndentityService#getModuleByCode(java.lang.
	 * String)
	 */
	@Override
	public Module getModuleByCode(String code) {

		try {
			Module module = moduleDao.get(Module.class, code);
			return module;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException("查询异常" + e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kingson.Ims.identity.service.IndentityService#updateModule(org.kingson.
	 * Ims.identity.domain.Module)
	 */
	@Override
	public void updateModule(Module module, WebRequest request) {
		// TODO Auto-generated method stub
		try {
			Module module2 = moduleDao.get(Module.class, module.getCode());
			module2.setName(module.getName());
			module2.setUrl(module.getUrl());
			module2.setRemark(module.getRemark());
			module2.setModifyDate(new Date());
			module2.setModifier((User) request.getAttribute(ConstantUtil.SESSION_USER, WebRequest.SCOPE_SESSION));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();

			throw new RuntimeException("修改异常" + e.getMessage());
		}

	}

	

	/* (non-Javadoc)
	 * 根据二级模块获得三级模块信息
	 */
	@Override
	public List<Module> getPopedom(String code) {
		try {
			return moduleDao.getPopedom(code);
		} catch (Exception e) {
			// TODO: handle exception
		
		 throw	new RuntimeException("查询异常"+e.getMessage(),e);
		}
		
	}

	/* (non-Javadoc)
	 * @see org.kingson.Ims.identity.service.IndentityService#ajaxLoadPopedomInfo(java.lang.String, java.lang.Long)
	 */
	@Override
	public List<String> ajaxLoadPopedomInfo(String code, Long id) {
		
		try {
			return popedomDao.getLoadPopedoms(code,id);
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException("查询异常"+e.getMessage(),e);
		}
	}

	/* (non-Javadoc)
	 * @see org.kingson.Ims.identity.service.IndentityService#bindopertion(java.lang.Long, java.lang.String, java.lang.String, org.springframework.web.context.request.WebRequest)
	 */
	@Override
	public void bindopertion(Long roleId, String pCode, String codes, WebRequest request) {
		//将数据库中绑定的权限删除
		popedomDao.deletePopedom(roleId,pCode);
		if(StringUtils.isNotEmpty(codes)) {
			
			String [] mcodes=codes.split(",");
			//指定角色信息
			Role r = new Role();
			r.setId(roleId);
			
			//指定三级模块信息
			Module module =  new Module();
			module.setCode(pCode);
			
			for (int i = 0; i < mcodes.length; i++) {
				Popedom popedom = new Popedom();
				popedom.setModule(module);
				popedom.setRole(r);
				Module module2 = new Module();
				module2.setCode(mcodes[i]);
				popedom.setOpera(module2);
			 popedom.setCreateDate(new Date());
			 popedom.setCreater((User) request.getAttribute(ConstantUtil.SESSION_USER, 1));
			popedomDao.save(popedom);
			
			}
					
		}
	}

	/* (non-Javadoc)
	 * @see org.kingson.Ims.identity.service.IndentityService#findLeftMenuOptions(org.springframework.web.context.request.WebRequest)
	 */
	@Override
	public Map<Module, List<Module>> findLeftMenuOptions(WebRequest request) {
		      
		try {
			//获取session中的信息
			User  user  = (User) request.getAttribute(ConstantUtil.SESSION_USER, 1);
			//根据用户id获取该用户拥有的哪些二级模块操作权限
		List<String> codes=	popedomDao.findLeftMenuOperas(user.getUserId());
		System.out.println(codes);
		//根据获取到你的模块信息对数据进行封装
		if(codes!=null &&codes.size()>0) {
			Map<Module, List<Module>> mapModules= new HashMap<>();
			//定义list集合用户存放二级模块
			List<Module> list = null;
			
			for (int i = 0; i < codes.size(); i++) {
				//获取二级模块的code
				String code= codes.get(i);
			Module module=	moduleDao.get(Module.class, code);
			//根据二级模块的code获取一级模块的code
			String fCode= code.substring(0, 4);
			//根据一级模块的code获取一级模块
			Module fModule=moduleDao.get(Module.class, fCode);
			//判断一级模块是否存在map集合中
			if(!mapModules.containsKey(fModule)) {
				//如果不存在就创建
				list=new ArrayList<>();
				mapModules.put(fModule, list);
			}
			//将二级模块存在list集合中去
			list.add(module);
			}
			
			return mapModules;
		}
		
		return null;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException("查询异常"+e.getMessage(),e);
		}
	}

	/* (non-Javadoc)
	 * @see org.kingson.Ims.identity.service.IndentityService#findLeftOptions(org.springframework.web.context.request.WebRequest)
	 */
	@Override
	public List<String> findLeftOptions(WebRequest request) {
		//获取session信息
	User user =	(User) request.getAttribute(ConstantUtil.SESSION_USER, WebRequest.SCOPE_SESSION);
		       //根据用户获取该用户拥有的哪些三级操作权限
	
	       try {
	    	   //根据用户获取该用户拥有的哪些三级操作权限
	    	   
	 List<String>  codes=  popedomDao.findPageOpers(user.getUserId());
	 System.out.println(codes);
	 List<String> urList=new ArrayList<>();
	          for (int i = 0; i < codes.size(); i++) {
				
	        	  String url=moduleDao.get(Module.class, codes.get(i)).getUrl();
	        	  urList.add(url);
			}
	          
	          return urList;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
			throw new RuntimeException("查询异常"+e.getMessage());
		}
	
	}

	/* (non-Javadoc)
	 * @see org.kingson.Ims.identity.service.IndentityService#getLogType(org.kingson.Ims.logMenager.domain.Diary, org.kingson.commrs.pager.PageModel)
	 */
	@Override
	public List<Diary> getLogType(Diary diary, PageModel pageModel) {
		
		try {
			//创建集合封装查询相关参数
			List<Object> params= new ArrayList<>();
			StringBuilder hql = new StringBuilder();
			hql.append(" select d  from Diary d where delFlag='1' ");
			    if(StringUtils.isNotEmpty(diary.getTitle())) {
			    	hql.append(" and d.title like ?");
			    	params.add("%" +diary.getTitle()+"%");
			    }
			return diaryDao.findByPage(hql.toString(), pageModel, params);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException("查询异常"+e.getMessage(),e);
		}
	}

	/* (non-Javadoc)
	 * @see org.kingson.Ims.identity.service.IndentityService#saveLogDiary(org.kingson.Ims.logMenager.domain.Diary, org.springframework.web.context.request.WebRequest)
	 */
	@Override
	public void saveLogDiary(Diary diary, WebRequest request) {
		try {
			
			diary.setCreateDate(new Date());	
			diary.setCreater((User) request.getAttribute(ConstantUtil.SESSION_USER, WebRequest.SCOPE_SESSION));
		
		   diaryDao.save(diary);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException("添加日记失败"+e.getMessage(),e);
		}
		
	}

	/* (non-Javadoc)
	 * @see org.kingson.Ims.identity.service.IndentityService#deleteDiary(java.lang.String)
	 */
	@Override
	public void deleteDiary(String ids) {

           try {
        	String[]  id=  ids.split(",");
        	   diaryDao.deleteDiary(id);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException("删除异常"+e.getMessage(),e);
		}
		
	}

	/* (non-Javadoc)
	 * @see org.kingson.Ims.identity.service.IndentityService#getDiary(java.lang.Long)
	 */
	@Override
	public Diary getDiary(Long id) {
		   
		try {
			return diaryDao.get(Diary.class, id);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException("查询异常"+e.getMessage(),e);
		}
		
	}

	/* (non-Javadoc)
	 * @see org.kingson.Ims.identity.service.IndentityService#getAllLeave(org.kingson.Ims.vacation.domain.LeaveType, org.kingson.commrs.pager.PageModel)
	 */
	@Override
	public List<LeaveType> getAllLeave(LeaveType leaveType, PageModel pageModel) {
		
		try {
			return leaveDao.findBypages(leaveType,pageModel);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException("查询异常"+e.getMessage(),e);
		}
	}

}