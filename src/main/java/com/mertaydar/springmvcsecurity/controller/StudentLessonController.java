package com.mertaydar.springmvcsecurity.controller;

import java.util.ArrayList;
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
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.mertaydar.springmvcsecurity.dao.DeptDAO;
import com.mertaydar.springmvcsecurity.dao.StudentDAO;
import com.mertaydar.springmvcsecurity.dao.StudentLessonDAO;
import com.mertaydar.springmvcsecurity.dao.SubstituteLessonDAO;
import com.mertaydar.springmvcsecurity.dao.TakingLessonDAO;
import com.mertaydar.springmvcsecurity.dao.UniDAO;
import com.mertaydar.springmvcsecurity.model.DeptInfo;
import com.mertaydar.springmvcsecurity.model.JSPLessonFormat;
import com.mertaydar.springmvcsecurity.model.StudentInfo;
import com.mertaydar.springmvcsecurity.model.StudentLessonInfo;
import com.mertaydar.springmvcsecurity.model.SubstituteLessonInfo;
import com.mertaydar.springmvcsecurity.model.TakingLessonInfo;
import com.mertaydar.springmvcsecurity.model.UniInfo;

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
	private StudentDAO studentDAO;

	@Autowired
	private TakingLessonDAO takingLessonDAO;
	
	@Autowired
	private SubstituteLessonDAO substituteLessonDAO;
	
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
			BindingResult result) {


		if (result.hasErrors()) {
			return this.formStudentLesson(model, studentLessonInfo);
		}
		
		if (this.studentLessonDAO.isDuplicate(studentLessonInfo)) {
			model.addAttribute("message", "Zaten eklenmiş.");
		}
		else {
			this.studentLessonDAO.saveStudentLesson(studentLessonInfo);
		}
		

		StudentInfo stu = this.studentDAO.findStudentInfo(studentLessonInfo.getStudentId());
		System.out.println(stu.getName()+studentLessonInfo.getStudentId());
		DeptInfo dept = null;
		UniInfo uni = null; 
		List<StudentLessonInfo> listStuLes = this.studentLessonDAO.listStudentLessonInfosForStudent(studentLessonInfo.getStudentId());
		List<JSPLessonFormat> list = new ArrayList<JSPLessonFormat>();
		if (studentLessonInfo.getStudentId() != null) {
			 dept = this.deptDAO.findDeptInfo(stu.getDeptId());
			 uni = this.uniDAO.findUniInfo(dept.getUniId());
			 model.addAttribute("id", stu.getId());
			 model.addAttribute("uni", uni.getName());
			 model.addAttribute("dept", dept.getName());
			 model.addAttribute("deptId", dept.getId());
			 model.addAttribute("name", stu.getName());
			 model.addAttribute("surname", stu.getSurname());
			 model.addAttribute("no", stu.getNo());
			 model.addAttribute("recordYear", stu.getRecordYear());
			 model.addAttribute("adpScore", stu.getAdpScore());
			 for (StudentLessonInfo tmp : listStuLes){
				 SubstituteLessonInfo tmpSub = this.substituteLessonDAO.findSubstituteLessonInfo(tmp.getSubstituteLessonId());
				 TakingLessonInfo tmpTak = this.takingLessonDAO.findTakingLessonInfo(tmp.getTakingLessonId());
				 JSPLessonFormat temp = new JSPLessonFormat(tmpSub, tmpTak, tmp.getOrgMark(), tmp.getConvMark());
				 list.add(temp);
			 }
		}
		model.addAttribute("lessons", list);
		
		System.out.println("sa"+list.size());

//		return "redirect:/studentLessonList";
		return "addStudentLesson";
	}
}