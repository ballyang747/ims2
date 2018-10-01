/**
 * 
 */
package org.kingson.Ims.vacation.contrller;

import java.util.List;

import org.kingson.Ims.identity.service.IndentityService;
import org.kingson.Ims.vacation.domain.LeaveType;
import org.kingson.commrs.pager.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * alt+shift+j
 * 
 * @author kingson 2018年8月10日 org.kingson.Ims.vacation.contrller Imsn2
 * @version 1.0
 * 
 * @email 1606656167@qq.com
 * @tel 15768607477 /vacation/leave/selectLeaveType.jspx
 */
@Controller
@RequestMapping("/vacation/leave")
@Transactional
public class LeaveContrller {
	@Autowired
	private IndentityService indentityService;

	@RequestMapping("/selectLeaveType.jspx")
	public String selectLeve(LeaveType leaveType, Model model, PageModel pageModel) {

		List<LeaveType> leaveTypes = indentityService.getAllLeave(leaveType, pageModel);
		model.addAttribute("leaveTypes", leaveTypes);
		return "vacation/leave/leave";
	}

	// 增加一个返回JSON字符串的方法
	@GetMapping("json.jspx")
	@ResponseBody
	public List<LeaveType> getAllTypes() {
		LeaveType leaveType = null;
		PageModel pageModel = new PageModel();
		pageModel.setPageIndex(1);
		pageModel.setPageSize(Integer.MAX_VALUE);
		List<LeaveType> leaveTypes = indentityService.getAllLeave(leaveType, pageModel);

		return leaveTypes;
	}
}
