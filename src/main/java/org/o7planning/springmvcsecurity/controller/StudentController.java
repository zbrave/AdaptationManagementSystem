package org.o7planning.springmvcsecurity.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.o7planning.springmvcsecurity.dao.DeptDAO;
import org.o7planning.springmvcsecurity.dao.RulesDAO;
import org.o7planning.springmvcsecurity.dao.StudentDAO;
import org.o7planning.springmvcsecurity.dao.StudentLessonDAO;
import org.o7planning.springmvcsecurity.dao.SubstituteLessonDAO;
import org.o7planning.springmvcsecurity.dao.TakingLessonDAO;
import org.o7planning.springmvcsecurity.dao.UniDAO;
import org.o7planning.springmvcsecurity.model.DeptInfo;
import org.o7planning.springmvcsecurity.model.JSPLessonFormat;
import org.o7planning.springmvcsecurity.model.JSPStudentFormat;
import org.o7planning.springmvcsecurity.model.RulesInfo;
import org.o7planning.springmvcsecurity.model.StudentInfo;
import org.o7planning.springmvcsecurity.model.StudentLessonInfo;
import org.o7planning.springmvcsecurity.model.SubstituteLessonInfo;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
//Enable Hibernate Transaction.
@Transactional
//Need To use RedirectAttributes
@EnableWebMvc
public class StudentController {
	
	@Autowired
	private UniDAO uniDAO;

	@Autowired
	private DeptDAO deptDAO;

	@Autowired
	private TakingLessonDAO takingLessonDAO;
	
	@Autowired
	private SubstituteLessonDAO substituteLessonDAO;
	
	@Autowired
	private RulesDAO rulesDAO;
	
	@Autowired
	private StudentDAO studentDAO;
	
	@Autowired
	private StudentLessonDAO studentLessonDAO;
	
/* Student Section */
	
	@RequestMapping(value="/getStudent",method = RequestMethod.GET, produces = "text/plain; charset=UTF-8")
	@ResponseBody
	public String getStudent() {
		String res = "<option id=-1 value=-1>Öğrenci seçin.</option>";
		List<StudentInfo> list = this.studentDAO.listStudentInfos();
		for (StudentInfo tmp : list) {
			res = res.concat("<option "+"id="+tmp.getId()+" value="+tmp.getId()+">"+tmp.getName()+"</option>");
		}
		return res;
	}
	
	@RequestMapping(value="/getUniDept",method = RequestMethod.GET, produces = "text/plain; charset=UTF-8")
	@ResponseBody
	public String getUniDept(@RequestParam("id") Integer id) {
		
		StudentInfo stu = this.studentDAO.findStudentInfo(id);
		DeptInfo dept = this.deptDAO.findDeptInfo(stu.getDeptId());
		UniInfo uni = this.uniDAO.findUniInfo(dept.getUniId());
		
		String res = "<label class=\"control-label\">Üniversite</label><select id=\"uniId\" class=\"form-control\" name=\"uniId\" ><option id=-1 value=-1>Üniversite seçin.</option>";
		List<UniInfo> list = this.uniDAO.listUniInfos();
		for (UniInfo tmp : list) {
			if (tmp.getId() == uni.getId()) {
				res = res.concat("<option "+"id="+tmp.getId()+" value="+tmp.getId()+" selected>"+tmp.getName()+"</option>");
			}
			else {
				res = res.concat("<option "+"id="+tmp.getId()+" value="+tmp.getId()+">"+tmp.getName()+"</option>");
			}
		}
		res = res.concat("</select>");
		res = res.concat("<label class=\"control-label\">Bölüm</label><select id=\"deptId\" class=\"form-control\" name=\"deptId\" ><option id=-1 value=-1>Bölüm seçin.</option>");
		List<DeptInfo> list2 = this.deptDAO.listDeptFromUni(dept.getUniId());
		for (DeptInfo tmp : list2) {
			if (tmp.getId() == dept.getId()) {
				res = res.concat("<option "+"id="+tmp.getId()+" value="+tmp.getId()+" selected>"+tmp.getName()+"</option>");
			}
			else {
				res = res.concat("<option "+"id="+tmp.getId()+" value="+tmp.getId()+">"+tmp.getName()+"</option>");
			}
		}
		res = res.concat("</select>");
		return res;
	}
	
	
	@RequestMapping("/getStudentData")
	public String getStudentData(Model model, @RequestParam("id") Integer id) {
		StudentInfo stu = this.studentDAO.findStudentInfo(id);
		DeptInfo dept = null;
		UniInfo uni = null; 
		List<StudentLessonInfo> listStuLes = this.studentLessonDAO.listStudentLessonInfosForStudent(id);
		List<SubstituteLessonInfo> listSubstituteLesson = this.substituteLessonDAO.listSubstituteLessonInfos();
		List<JSPLessonFormat> list = new ArrayList<JSPLessonFormat>();
		if (id != null) {
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

		model.addAttribute("subLes", listSubstituteLesson);
		return "addStudentLesson";
	}
	
	@RequestMapping(value = "/addStudent", method = RequestMethod.GET)
	public String addStudent(Model model) {
		
		return "addStudent";
	}
	
	@RequestMapping(value = "/Student", method = RequestMethod.GET)
	public String Student(Model model) {
		
		List<JSPStudentFormat> list = new ArrayList<JSPStudentFormat>();
		List<StudentInfo> listStu = studentDAO.listStudentInfos();
		
		for (StudentInfo tmp : listStu){
			 DeptInfo dept = this.deptDAO.findDeptInfo(tmp.getDeptId());
			 UniInfo uni = this.uniDAO.findUniInfo(dept.getUniId());
			 JSPStudentFormat temp = new JSPStudentFormat(tmp.getId(), uni.getId(), uni.getName(), dept.getId(), dept.getName(), tmp.getName(), tmp.getSurname(), tmp.getNo(), tmp.getAdpScore(), tmp.getRecordYear() );
			 list.add(temp);
		 }
		model.addAttribute("students", list);
		return "Student";
	}
	
	@RequestMapping("/studentList")
	public String studentList(Model model) {
		List<StudentInfo> list = studentDAO.listStudentInfos();
		model.addAttribute("studentInfos", list);
		return "studentList";
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

	private String formStudent(Model model, StudentInfo studentInfo) {
		model.addAttribute("studentForm", studentInfo);

		Map<Integer, String> uniMap = this.uniList();

		model.addAttribute("uniMap", uniMap);
		
		Map<Integer, String> deptMap = this.deptList();

		model.addAttribute("deptMap", deptMap);
		
		Map<Integer, String> takingLessonMap = this.takingLessonList();

		model.addAttribute("takingLessonMap", takingLessonMap);
		

		//				       List<String> list = dataForSkills();
		//				       model.addAttribute("skills", list);

		if (studentInfo.getId() == null) {
			model.addAttribute("formTitle", "Create Student");
		} else {
			model.addAttribute("formTitle", "Edit Student");
		}

		return "formStudent";
	}

	@RequestMapping("/createStudent")
	public String createStudent(Model model) {

		StudentInfo studentInfo = new StudentInfo();

		return this.formStudent(model, studentInfo);
	}

	@RequestMapping("/editStudent")
	public String editStudent(Model model, @RequestParam("id") Integer id) {
		StudentInfo studentInfo = null;
		if (id != null) {
			studentInfo = this.studentDAO.findStudentInfo(id);
		}
		if (studentInfo == null) {
			System.out.println("Bu id li öğrenci yok.");
			return "redirect:/studentList";
		}

		return this.formStudent(model, studentInfo);
	}

	@RequestMapping("/deleteStudent")
	public String deleteStudent(Model model, @RequestParam("id") Integer id) {
		if (id != null) {
			this.studentDAO.deleteStudent(id);
		}
		return "redirect:/Student";
	}

	@RequestMapping(value = "/saveStudent", method = RequestMethod.POST)
	public String saveStudent(Model model, //
			@ModelAttribute("studentForm") @Validated StudentInfo studentInfo, //
			BindingResult result, //
			final RedirectAttributes redirectAttributes) {


		if (result.hasErrors()) {
			return this.formStudent(model, studentInfo);
		}
//		if (studentDAO.isDuplicate(studentInfo)){
//			redirectAttributes.addFlashAttribute("message5", "Öğrenci zaten eklenmiş.");
//		}
//		else {
			this.studentDAO.saveStudent(studentInfo);

			// Important!!: Need @EnableWebMvc
			// Add message to flash scope
			redirectAttributes.addFlashAttribute("message5", "Öğrenci Eklendi.");
//		}
		

//		return "redirect:/studentList";
		return "redirect:/Student";
	}
	
}
