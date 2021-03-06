package com.mertaydar.springmvcsecurity.controller;

import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mertaydar.springmvcsecurity.dao.DeptDAO;
import com.mertaydar.springmvcsecurity.dao.TakingLessonDAO;
import com.mertaydar.springmvcsecurity.dao.UniDAO;
import com.mertaydar.springmvcsecurity.model.DeptInfo;
import com.mertaydar.springmvcsecurity.model.TakingLessonInfo;
import com.mertaydar.springmvcsecurity.model.UniInfo;

@Controller
//Enable Hibernate Transaction.
@Transactional
//Need To use RedirectAttributes
@EnableWebMvc
public class TakingLessonController {

	@Autowired
	private UniDAO uniDAO;
	
	@Autowired
	private DeptDAO deptDAO;
	
	@Autowired
	private TakingLessonDAO takingLessonDAO;
	
	/* TakingLesson Controller Section */
	@RequestMapping("/takingLessonList")
	public String takingLessonList(Model model) {
		List<TakingLessonInfo> list = takingLessonDAO.listTakingLessonInfos();
		model.addAttribute("takingLessonInfos", list);
		return "takingLessonList";
	}
	
	@RequestMapping(value="/getTakingLesson",method = RequestMethod.GET, produces = "text/plain; charset=UTF-8")
	@ResponseBody
	public String getTakingLesson(@RequestParam Integer id) {
		String res = "<option id=-1 value=-1>Alınan ders seçin.</option>";
		List<TakingLessonInfo> list = this.takingLessonDAO.listTakingLessonFromDept(id);
		for (TakingLessonInfo tmp : list) {
			res = res.concat("<option "+"id="+tmp.getId()+" value="+tmp.getId()+">"+tmp.getCode()+" "+tmp.getName()+">Kredi: "+tmp.getCredit()+"</option>");
		}
		return res;
	}
	
	@RequestMapping(value="/getTakingLessons",method = RequestMethod.GET, produces = "text/plain; charset=UTF-8")
	@ResponseBody
	public String getTakingLessons(@RequestParam Integer id) {
		String res = "";
		List<TakingLessonInfo> list = this.takingLessonDAO.listTakingLessonFromDept(id);
		for (TakingLessonInfo tmp : list) {
			res = res.concat("<tr><td>"+tmp.getId()+"</td><td>"+tmp.getName()+"</td><td>"+tmp.getCode()+"</td><td>"+tmp.getLang()+"</td><td>"+tmp.getCredit()+"</td><td>"+tmp.getLab()+"</td><td>"+tmp.getAkts()+"</td><td>"+tmp.getTerm()+"</td><td><a href=\"editTakingLesson?id="+tmp.getId()+"\" class=\"btn btn-info btn-xs\"><span class=\"glyphicon glyphicon-edit\"></span> Değiştir</a><a href=\"deleteTakingLesson?id="+tmp.getId()+"\" class=\"btn btn-danger btn-xs\"><span class=\"glyphicon glyphicon-remove\"></span> Sil</a></td></tr>");
		}
		return res;
	}
	
	@SuppressWarnings("unused")
	private Map<Integer, String> takingLessonList() {
		Map<Integer, String> takingLessonMap = new LinkedHashMap<Integer, String>();
		List<TakingLessonInfo> takingLessonList = this.takingLessonDAO.listTakingLessonInfos();
		for (TakingLessonInfo tmp : takingLessonList) {
			takingLessonMap.put(tmp.getId(), tmp.getCode());
		}

		return takingLessonMap;
	}
	
	private Map<Integer, String> deptList() {
		Map<Integer, String> deptMap = new LinkedHashMap<Integer, String>();
		List<DeptInfo> deptList = this.deptDAO.listDeptInfos();
		for (DeptInfo tmp : deptList) {
			deptMap.put(tmp.getId(), tmp.getName());
		}

		return deptMap;
	}
	
	private Map<Integer, String> uniList() {
		Map<Integer, String> uniMap = new LinkedHashMap<Integer, String>();
		List<UniInfo> uniList = this.uniDAO.listUniInfos();
		for (UniInfo tmp : uniList) {
			uniMap.put(tmp.getId(), tmp.getName());
		}

		return uniMap;
	}

	private String formTakingLesson(Model model, TakingLessonInfo takingLessonInfo) {
		model.addAttribute("takingLessonForm", takingLessonInfo);

		Map<Integer, String> uniMap = this.uniList();

		model.addAttribute("uniMap", uniMap);
		
		Map<Integer, String> deptMap = this.deptList();

		model.addAttribute("deptMap", deptMap);



		//			       List<String> list = dataForSkills();
		//			       model.addAttribute("skills", list);

		if (takingLessonInfo.getId() == null) {
			model.addAttribute("formTitle", "Create TakingLesson");
		} else {
			model.addAttribute("formTitle", "Edit TakingLesson");
		}

		return "formTakingLesson";
	}

	@RequestMapping("/createTakingLesson")
	public String createTakingLesson(Model model) {

		TakingLessonInfo takingLessonInfo = new TakingLessonInfo();

		return this.formTakingLesson(model, takingLessonInfo);
	}

	@RequestMapping("/editTakingLesson")
	public String editTakingLesson(Model model, @RequestParam("id") Integer id) {
		TakingLessonInfo takingLessonInfo = null;
		if (id != null) {
			takingLessonInfo = this.takingLessonDAO.findTakingLessonInfo(id);
		}
		if (takingLessonInfo == null) {
			return "redirect:/addRules#takingLessonTab";
		}

		return this.formTakingLesson(model, takingLessonInfo);
	}

	@RequestMapping("/deleteTakingLesson")
	public String deleteTakingLesson(Model model, @RequestParam("id") Integer id, final RedirectAttributes redirectAttributes) {
		if (id != null) {
			TakingLessonInfo tak = takingLessonDAO.findTakingLessonInfo(id);
			DeptInfo dep = deptDAO.findDeptInfo(tak.getDeptId());
			this.takingLessonDAO.deleteTakingLesson(id);
			redirectAttributes.addFlashAttribute("message3", "Ders Silindi.");
			return "redirect:/addRules?id="+dep.getUniId()+"&id2="+dep.getId()+"#takingLessonTab";
		}
		return "redirect:/addRules#takingLessonTab";
	}

	@RequestMapping(value = "/saveTakingLesson", method = RequestMethod.POST)
	public String saveTakingLesson(Model model, //
			@ModelAttribute("takingLessonForm") @Validated TakingLessonInfo takingLessonInfo, //
			BindingResult result, //
			final RedirectAttributes redirectAttributes) {


		if (result.hasErrors()) {
			return this.formTakingLesson(model, takingLessonInfo);
		}
		String decodedToUTF8;
		try {
			decodedToUTF8 = new String(takingLessonInfo.getName().getBytes("ISO-8859-1"), "UTF-8");
			takingLessonInfo.setName(decodedToUTF8);
		} catch (UnsupportedEncodingException e) {
			System.out.println("Dept name cannot converted.");
			e.printStackTrace();
		}

		this.takingLessonDAO.saveTakingLesson(takingLessonInfo);

		// Important!!: Need @EnableWebMvc1
		// Add message to flash scope
		redirectAttributes.addFlashAttribute("message3", "Ders eklendi/güncellendi.");

//		return "redirect:/takingLessonList";
		return "redirect:/addRules?id="+deptDAO.findDeptInfo(takingLessonInfo.getDeptId()).getUniId()+"&id2="+takingLessonInfo.getDeptId()+"#takingLessonTab";
	}
}
