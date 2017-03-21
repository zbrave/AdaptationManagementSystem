package org.o7planning.springmvcsecurity.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.o7planning.springmvcsecurity.dao.DeptDAO;
import org.o7planning.springmvcsecurity.dao.StudentLessonDAO;
import org.o7planning.springmvcsecurity.dao.TakingLessonDAO;
import org.o7planning.springmvcsecurity.dao.UniDAO;
import org.o7planning.springmvcsecurity.model.DeptInfo;
import org.o7planning.springmvcsecurity.model.StudentLessonInfo;
import org.o7planning.springmvcsecurity.model.TakingLessonInfo;
import org.o7planning.springmvcsecurity.model.UniInfo;
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
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
//Enable Hibernate Transaction.
@Transactional
//Need To use RedirectAttributes
@EnableWebMvc
public class StudentLessonController {
	
	@Autowired
	private UniDAO uniDAO;

	@Autowired
	private DeptDAO deptDAO;

	@Autowired
	private TakingLessonDAO takingLessonDAO;
	
	@Autowired
	private StudentLessonDAO studentLessonDAO;
	
/* StudentLesson Section */
	
	@RequestMapping(value = "/addStudentLesson", method = RequestMethod.GET)
	public String addStudentLesson(Model model) {

		return "addStudentLesson";
	}
	
	@RequestMapping("/studentLessonList")
	public String studentLessonList(Model model) {
		List<StudentLessonInfo> list = studentLessonDAO.listStudentLessonInfos();
		model.addAttribute("studentLessonInfos", list);
		return "studentLessonList";
	}
	
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

	private String formStudentLesson(Model model, StudentLessonInfo studentLessonInfo) {
		model.addAttribute("studentLessonForm", studentLessonInfo);

		Map<Integer, String> uniMap = this.uniList();

		model.addAttribute("uniMap", uniMap);
		
		Map<Integer, String> deptMap = this.deptList();

		model.addAttribute("deptMap", deptMap);
		
		Map<Integer, String> takingLessonMap = this.takingLessonList();

		model.addAttribute("takingLessonMap", takingLessonMap);
		

		//				       List<String> list = dataForSkills();
		//				       model.addAttribute("skills", list);

		if (studentLessonInfo.getId() == null) {
			model.addAttribute("formTitle", "Create StudentLesson");
		} else {
			model.addAttribute("formTitle", "Edit StudentLesson");
		}

		return "formStudentLesson";
	}

	@RequestMapping("/createStudentLesson")
	public String createStudentLesson(Model model) {

		StudentLessonInfo studentLessonInfo = new StudentLessonInfo();

		return this.formStudentLesson(model, studentLessonInfo);
	}

	@RequestMapping("/editStudentLesson")
	public String editStudentLesson(Model model, @RequestParam("id") Integer id) {
		StudentLessonInfo studentLessonInfo = null;
		if (id != null) {
			studentLessonInfo = this.studentLessonDAO.findStudentLessonInfo(id);
		}
		if (studentLessonInfo == null) {
			return "redirect:/studentLessonList";
		}

		return this.formStudentLesson(model, studentLessonInfo);
	}

	@RequestMapping("/deleteStudentLesson")
	public String deleteStudentLesson(Model model, @RequestParam("id") Integer id) {
		if (id != null) {
			this.studentLessonDAO.deleteStudentLesson(id);
		}
		return "redirect:/studentLessonList";
	}

	@RequestMapping(value = "/saveStudentLesson", method = RequestMethod.POST)
	public String saveStudentLesson(Model model, //
			@ModelAttribute("studentLessonForm") @Validated StudentLessonInfo studentLessonInfo, //
			BindingResult result, //
			final RedirectAttributes redirectAttributes) {


		if (result.hasErrors()) {
			return this.formStudentLesson(model, studentLessonInfo);
		}
		this.studentLessonDAO.saveStudentLesson(studentLessonInfo);

		// Important!!: Need @EnableWebMvc
		// Add message to flash scope
		redirectAttributes.addFlashAttribute("message5", "Öğrenci Eklendi.");
		
		

//		return "redirect:/studentLessonList";
		return "redirect:/addStudentLesson";
	}
}
