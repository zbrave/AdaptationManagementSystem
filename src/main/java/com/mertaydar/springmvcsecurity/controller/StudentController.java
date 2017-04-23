package com.mertaydar.springmvcsecurity.controller;

import java.io.UnsupportedEncodingException;
import java.security.Principal;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mertaydar.springmvcsecurity.dao.DeptDAO;
import com.mertaydar.springmvcsecurity.dao.RulesDAO;
import com.mertaydar.springmvcsecurity.dao.StudentDAO;
import com.mertaydar.springmvcsecurity.dao.StudentLessonDAO;
import com.mertaydar.springmvcsecurity.dao.SubstituteLessonDAO;
import com.mertaydar.springmvcsecurity.dao.TakingLessonDAO;
import com.mertaydar.springmvcsecurity.dao.UniDAO;
import com.mertaydar.springmvcsecurity.dao.UserDAO;
import com.mertaydar.springmvcsecurity.model.DeptInfo;
import com.mertaydar.springmvcsecurity.model.JSPLessonFormat;
import com.mertaydar.springmvcsecurity.model.JSPStudentFormat;
import com.mertaydar.springmvcsecurity.model.RulesInfo;
import com.mertaydar.springmvcsecurity.model.StudentInfo;
import com.mertaydar.springmvcsecurity.model.StudentLessonInfo;
import com.mertaydar.springmvcsecurity.model.SubstituteLessonInfo;
import com.mertaydar.springmvcsecurity.model.TakingLessonInfo;
import com.mertaydar.springmvcsecurity.model.UniInfo;
import com.mertaydar.springmvcsecurity.model.UserInfo;

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
	private UserDAO userDAO;
	
	@Autowired
	private StudentDAO studentDAO;
	
	@Autowired
	private StudentLessonDAO studentLessonDAO;
	
/* Student Section */
	
	@RequestMapping(value = "/setToStudent", method = RequestMethod.POST)
	public String saveDept(Model model, @RequestParam("id") Integer id,
			@ModelAttribute("userForm") @Validated UserInfo userInfo, //
			BindingResult result, //
			final RedirectAttributes redirectAttributes) {
		UserInfo user = this.userDAO.findUserInfo(userInfo.getStudentId());
		user.setStudentId(id);
		this.userDAO.saveUser(user);
		return "redirect:/getStudentData?id="+id.toString();
	}
	
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
	
	@RequestMapping(value = "/myAdapt", method = RequestMethod.GET)
	public String saveDept(Model model, Principal principal) {
		UserInfo user = this.userDAO.findLoginUserInfo(principal.getName());
		return "redirect:/getStudentData?id="+user.getStudentId().toString();
	}
	
	@RequestMapping(value = "/report", method = RequestMethod.GET)
	public String getReport(Model model, Principal principal) {

		return "report";
	}
	
	@RequestMapping("/getStudentData")
	public String getStudentData(Model model, @RequestParam("id") Integer id) {
		StudentInfo stu = this.studentDAO.findStudentInfo(id);
		DeptInfo dept = null;
		UniInfo uni = null; 
		List<StudentLessonInfo> listStuLes = this.studentLessonDAO.listStudentLessonInfosForStudent(id);
		List<JSPLessonFormat> list = new ArrayList<JSPLessonFormat>();
		if (id != -1) {
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
				 JSPLessonFormat temp = new JSPLessonFormat(tmp.getId(),tmpSub, tmpTak, tmp.getOrgMark(), tmp.getConvMark());
				 list.add(temp);
			 }
		}
		List<UserInfo> students = userDAO.listUserInfos();
		model.addAttribute("students", students);
		model.addAttribute("lessons", list);
		return "addStudentLesson";
	}
	
	@RequestMapping(value = "/addStudent", method = RequestMethod.GET)
	public String addStudent(Model model) {
		
		return "addStudent";
	}
	
	@RequestMapping(value = "/Student", method = RequestMethod.GET)
	public String Student(Model model,@RequestParam Integer pageid,@RequestParam(value = "searchTerm", required = false) String search) {
		
		List<JSPStudentFormat> list = new ArrayList<JSPStudentFormat>();
		int total = 2;  
        if (pageid == 1){
        	
        }  
        else {  
            pageid = (pageid-1)*total+1;  
        }
        List<StudentInfo> listStu = null;
        if (search == null || search.isEmpty()) {
        	listStu = studentDAO.listStudentInfos(pageid,total);
        }
        else {
        	listStu = studentDAO.listStudentInfosByNo(pageid,total,search);
        }
		Integer pageSize = studentDAO.listStudentInfos().size();
		pageSize = (int) Math.ceil(pageSize / (float)total);
		for (StudentInfo tmp : listStu){
			 DeptInfo dept = this.deptDAO.findDeptInfo(tmp.getDeptId());
			 UniInfo uni = this.uniDAO.findUniInfo(dept.getUniId());
			 JSPStudentFormat temp = new JSPStudentFormat(tmp.getId(), uni.getId(), uni.getName(), dept.getId(), dept.getName(), tmp.getName(), tmp.getSurname(), tmp.getNo(), tmp.getAdpScore(), tmp.getRecordYear() );
			 list.add(temp);
		 }
		model.addAttribute("students", list);
		model.addAttribute("pageSize", pageSize);
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
		DeptInfo dept = this.deptDAO.findDeptInfo(studentInfo.getDeptId());
		model.addAttribute("uniId", dept.getUniId());
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
		String decodedToUTF8;
		try {
			decodedToUTF8 = new String(studentInfo.getName().getBytes("ISO-8859-1"), "UTF-8");
			studentInfo.setName(decodedToUTF8);
			decodedToUTF8 = new String(studentInfo.getSurname().getBytes("ISO-8859-1"), "UTF-8");
			studentInfo.setSurname(decodedToUTF8);
		} catch (UnsupportedEncodingException e) {
			System.out.println("Uni name cannot converted.");
			e.printStackTrace();
		}
		if (studentDAO.isDuplicate(studentInfo)){
			redirectAttributes.addFlashAttribute("message5", "Öğrenci zaten eklenmiş.");
		}
		else {
			this.studentDAO.saveStudent(studentInfo);

			// Important!!: Need @EnableWebMvc
			// Add message to flash scope
			redirectAttributes.addFlashAttribute("message5", "Öğrenci Eklendi.");
		}
		

//		return "redirect:/studentList";
		return "redirect:/Student?pageid=1";
	}
	
}
