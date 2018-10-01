/**
 * 
 */
package org.kingson.Ims.logMenager.contrller;

import java.util.List;

import org.kingson.Ims.identity.service.IndentityService;
import org.kingson.Ims.logMenager.domain.Diary;
import org.kingson.commrs.pager.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

/**
 * alt+shift+j
 * 
 * @author kingson 2018年8月10日 org.kingson.Ims.logMenager.contrller Imsn2
 * @version 1.0
 * 
 * @email 1606656167@qq.com
 * @tel 15768607477
 * 
 */
@Controller
@RequestMapping("/log/logMenager")
@Transactional
public class LogContrller {
	@Autowired
	private IndentityService indentityService;

	@RequestMapping("/selecWorkLog.jspx")
	public String selectLog(Diary diary, PageModel pageModel, Model model) {
		List<Diary> diarys = indentityService.getLogType(diary, pageModel);
		model.addAttribute("diarys", diarys);
		/// log/logMenager/selecWorkLog.jspx
		return "log/logMenager/Loglist";

	}

	@RequestMapping("/showAddLogType.jspx")
	public String showAndLogType(PageModel pageModel, Model model) {

		return "log/logMenager/addLog";

	}

	@RequestMapping("/addDiaryType.jspx")
	public String addDiraryType(Diary diary, Model model, WebRequest request) {

		try {

			indentityService.saveLogDiary(diary, request);
			model.addAttribute("message", "添加成功");

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			model.addAttribute("message", "添加失败");
		}

		return "log/logMenager/addLog";

	}

	// 删除日记
	@RequestMapping("/deleteDiary.jspx")
	public String delectDiary(@RequestParam("ids") String ids, PageModel pageModel, Model model) {
		try {
			indentityService.deleteDiary(ids);
			model.addAttribute("message", "删除成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			model.addAttribute("message", "删除失败");
		}
		return "forward:/log/logMenager/selecWorkLog.jspx";

	}

	// 跳转到啊登录页面
	@RequestMapping("/updateDiary.jspx")
	public String uptateDiary(@RequestParam("id") Long id, Model model) {

		try {
			Diary diary = indentityService.getDiary(id);
			model.addAttribute("diary", diary);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "log/logMenager/updateDiary";
	}

	@RequestMapping("/preDiary.jspx")
	public String showPreDiary(@RequestParam("id") Long id, Model model) {
		try {
			Diary diary = indentityService.getDiary(id);
			model.addAttribute("diary", diary);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return "log/logMenager/preDiary";
	}

}