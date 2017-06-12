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
import com.mertaydar.springmvcsecurity.dao.MailSend;
import com.mertaydar.springmvcsecurity.dao.RulesDAO;
import com.mertaydar.springmvcsecurity.dao.StudentDAO;
import com.mertaydar.springmvcsecurity.dao.StudentLessonDAO;
import com.mertaydar.springmvcsecurity.dao.SubstituteLessonDAO;
import com.mertaydar.springmvcsecurity.dao.TakingLessonDAO;
import com.mertaydar.springmvcsecurity.dao.UniDAO;
import com.mertaydar.springmvcsecurity.dao.UserDAO;
import com.mertaydar.springmvcsecurity.dao.UserRoleDAO;
import com.mertaydar.springmvcsecurity.model.DeptInfo;
import com.mertaydar.springmvcsecurity.model.JSPLessonFormat;
import com.mertaydar.springmvcsecurity.model.JSPStudentFormat;
import com.mertaydar.springmvcsecurity.model.MailInfo;
import com.mertaydar.springmvcsecurity.model.RulesInfo;
import com.mertaydar.springmvcsecurity.model.StudentInfo;
import com.mertaydar.springmvcsecurity.model.StudentLessonInfo;
import com.mertaydar.springmvcsecurity.model.SubstituteLessonInfo;
import com.mertaydar.springmvcsecurity.model.TakingLessonInfo;
import com.mertaydar.springmvcsecurity.model.UniInfo;
import com.mertaydar.springmvcsecurity.model.UserInfo;
import com.mertaydar.springmvcsecurity.model.UserRoleInfo;

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
	private UserRoleDAO userRoleDAO;
	
	@Autowired
	private StudentDAO studentDAO;
	
	@Autowired
	private StudentLessonDAO studentLessonDAO;
	
	@Autowired
	private MailSend mailSend;
	
/* Student Section */
	
	@RequestMapping(value = "/setToStudent", method = RequestMethod.POST)
	public String setToStudent(Model model, @RequestParam("id") Integer id,
			@ModelAttribute("userForm") @Validated UserInfo userInfo, //
			BindingResult result, //
			final RedirectAttributes redirectAttributes) {
		UserInfo user = this.userDAO.findUserInfo(userInfo.getStudentId());
		user.setStudentId(id);
		this.userDAO.saveUser(user);
		return "redirect:/getStudentData?id="+id.toString();
	}
	
	@RequestMapping(value = "/setUserToStudent", method = RequestMethod.GET)
	public String setUserToStudent(Model model, @RequestParam("id") Integer id,
			final RedirectAttributes redirectAttributes) {
		if (id == null) {
			return "listNewUser?pageid=1";
		}
		UserInfo user = this.userDAO.findUserInfo(id);
		userRoleDAO.saveUserRole(new UserRoleInfo(null, user.getId(), "USER"));
		return "redirect:/listUser?pageid=1";
	}
	
	@RequestMapping(value = "/setUserToAdmin", method = RequestMethod.GET)
	public String setUserToAdmin(Model model, @RequestParam("id") Integer id,
			final RedirectAttributes redirectAttributes) {
		if (id == null) {
			return "listNewUser?pageid=1";
		}
		UserInfo user = this.userDAO.findUserInfo(id);
		userRoleDAO.saveUserRole(new UserRoleInfo(null, user.getId(), "ADMIN"));
		return "redirect:/listAdmin?pageid=1";
	}
	
	@RequestMapping(value = "/setAdminToUser", method = RequestMethod.GET)
	public String setAdminToUser(Model model, @RequestParam("id") Integer id,
			final RedirectAttributes redirectAttributes) {
		if (id == null) {
			return "listUser?pageid=1";
		}
		UserInfo userInfo = this.userDAO.findUserInfo(id);
		if (userRoleDAO.getUserRoles(userInfo.getId()).contains("ADMIN")) {
			List<UserRoleInfo> list = userRoleDAO.listUserRoleInfos();
			for (UserRoleInfo l : list) {
				if (l.getUserId() == userInfo.getId() && l.getRole().equals("ADMIN")) {
					userRoleDAO.deleteUserRole(l.getId());
				}
			}
		}
		return "redirect:/listNewUser?pageid=1";
	}
	
	@RequestMapping(value = "/setStudentToUser", method = RequestMethod.GET)
	public String setStudentToUser(Model model, @RequestParam("id") Integer id,
			final RedirectAttributes redirectAttributes) {
		if (id == null) {
			return "listUser?pageid=1";
		}
		UserInfo userInfo = this.userDAO.findUserInfo(id);
		if (userRoleDAO.getUserRoles(userInfo.getId()).contains("USER")) {
			List<UserRoleInfo> list = userRoleDAO.listUserRoleInfos();
			for (UserRoleInfo l : list) {
				if (l.getUserId() == userInfo.getId() && l.getRole().equals("USER")) {
					userRoleDAO.deleteUserRole(l.getId());
				}
			}
		}
		return "redirect:/listNewUser?pageid=1";
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
	public String myAdapt(Model model, Principal principal) {
		UserInfo user = this.userDAO.findLoginUserInfo(principal.getName());
		if (user.getStudentId() != null)
			return "redirect:/getStudentData?id="+user.getStudentId().toString();
		return "welcomePage";
	}
	
	@RequestMapping(value = "/sendMail", method = RequestMethod.POST)
	public String sendMail(Model model, Principal principal, @ModelAttribute("mailForm") @Validated MailInfo mailInfo, final RedirectAttributes redirectAttributes) {
		UserInfo user = this.userDAO.findLoginUserInfo(principal.getName());
		String decodedToUTF8;
		try {
			decodedToUTF8 = new String(mailInfo.getSubject().getBytes("ISO-8859-1"), "UTF-8");
			mailInfo.setSubject(decodedToUTF8);
			decodedToUTF8 = new String(mailInfo.getText().getBytes("ISO-8859-1"), "UTF-8");
			mailInfo.setText(decodedToUTF8);
		} catch (UnsupportedEncodingException e) {
			System.out.println("Mail cannot converted.");
			e.printStackTrace();
		}
		String text = "Kullanıcı: "+principal.getName()+"\n\nE-mail: "+user.getEmail()+"\nTelefon: "+user.getTel()+"\nMesaj: ";
		text = text.concat(mailInfo.getText());
		mailSend.sendSimpleMessage(mailInfo.getTo(), mailInfo.getSubject(), text);
		redirectAttributes.addFlashAttribute("message", "Mail Gönderildi.");
		return "redirect:/getStudentData?id="+user.getStudentId().toString();
	}
	
	@RequestMapping(value = "/report", method = RequestMethod.GET)
	public String getReport(Model model, Principal principal) {

		return "report";
	}
	
	@RequestMapping(value = "/setAdvisor", method = RequestMethod.GET)
	public String setAdvisor(Model model, Principal principal, @RequestParam(value = "advisorId") Integer advisorId, @RequestParam(value = "id") Integer id, final RedirectAttributes redirectAttributes) {
		if (id == null || advisorId == null) {
			return "redirect:/Student";
		}
		StudentInfo stuInfo = this.studentDAO.findStudentInfo(id);
		stuInfo.setAdvisorId(advisorId);
		this.studentDAO.saveStudent(stuInfo);
		redirectAttributes.addFlashAttribute("message", "Danışman atandı.");
		return "redirect:/Student";
	}
	
	@RequestMapping(value = "/calcAdp", method = RequestMethod.GET)
	public String calcAdp(Model model, @RequestParam("id") Integer id, @RequestParam("coef") Integer coef, @RequestParam("type") String type) {
		if (type.equals("Akts"))
			studentDAO.calcAdpAkts(id, coef);
		if (type.equals("Credit"))
			studentDAO.calcAdpCredit(id, coef);
		return "redirect:/getStudentData?id="+id;
	}
	
	@RequestMapping("/getStudentData")
	public String getStudentData(Model model, @RequestParam("id") Integer id) {
		StudentInfo stu = this.studentDAO.findStudentInfo(id);
		DeptInfo dept = null;
		UniInfo uni = null; 
		int count = 0;
		int count2 = 0;
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
			 model.addAttribute("advisor", userDAO.findUserInfo(stu.getAdvisorId()));
			 for (StudentLessonInfo tmp : listStuLes){
				 SubstituteLessonInfo tmpSub = this.substituteLessonDAO.findSubstituteLessonInfo(tmp.getSubstituteLessonId());
				 TakingLessonInfo tmpTak = this.takingLessonDAO.findTakingLessonInfo(tmp.getTakingLessonId());
				 if (tmpSub.getBase() != null) {
					 List<SubstituteLessonInfo> tmpSub2 = this.substituteLessonDAO.listSubstituteLessonInfos();
					 for (SubstituteLessonInfo t : tmpSub2) {
						 if (tmpSub.getBase().equals(t.getBase()+"H")) {
							 tmpSub.setTerm(t.getTerm());
							 break;
						 }
					 }
					 // MS1
					 for (SubstituteLessonInfo t : tmpSub2) {
						 if (tmpSub.getBase().equals("MS1H") && tmpSub.getBase().equals(t.getBase()+"H")) {
							 if (count == 0) {
								 tmpSub.setTerm(t.getTerm());
								 break;
							 }
							 else {
								 count--;
							 }
						 }
					 }
					 // MS2
					 for (SubstituteLessonInfo t : tmpSub2) {
						 if (tmpSub.getBase().equals("MS2H") && tmpSub.getBase().equals(t.getBase()+"H")) {
							 if (count2 == 0) {
								 tmpSub.setTerm(t.getTerm());
								 break;
							 }
							 else {
								 count2--;
							 }
						 }
					 }
				 }
				 JSPLessonFormat temp = new JSPLessonFormat(tmp.getId(),tmpSub, tmpTak, tmp.getOrgMark(), tmp.getConvMark());
				 list.add(temp);
			 }
		}
		List<UserInfo> students = userDAO.listUserInfosRoleUser();
		model.addAttribute("students", students);
		model.addAttribute("lessons", list);
		
		List<UserInfo> admins = userDAO.listUserInfosRoleAdmin();
		model.addAttribute("admins", admins);
		
		List<UserInfo> managers = userDAO.listUserInfosRoleManager();
		model.addAttribute("managers", managers);
		return "addStudentLesson";
	}
	
	@RequestMapping("/getStudentData2")
	public String getStudentData2(Model model, @RequestParam("id") Integer id) {
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
			 model.addAttribute("advisor", userDAO.findUserInfo(stu.getAdvisorId()));
			 for (StudentLessonInfo tmp : listStuLes){
				 SubstituteLessonInfo tmpSub = this.substituteLessonDAO.findSubstituteLessonInfo(tmp.getSubstituteLessonId());
				 TakingLessonInfo tmpTak = this.takingLessonDAO.findTakingLessonInfo(tmp.getTakingLessonId());
				 JSPLessonFormat temp = new JSPLessonFormat(tmp.getId(),tmpSub, tmpTak, tmp.getOrgMark(), tmp.getConvMark());
				 list.add(temp);
			 }
		}
		List<UserInfo> students = userDAO.listUserInfosRoleUser();
		model.addAttribute("students", students);
		model.addAttribute("lessons", list);
		
		List<UserInfo> admins = userDAO.listUserInfosRoleAdmin();
		model.addAttribute("admins", admins);
		
		List<UserInfo> managers = userDAO.listUserInfosRoleManager();
		model.addAttribute("managers", managers);
		return "addStudentLesson";
	}
	
	@RequestMapping(value = "/addStudent", method = RequestMethod.GET)
	public String addStudent(Model model) {
		
		return "addStudent";
	}
	
	@RequestMapping(value = "/Student", method = RequestMethod.GET)
	public String Student(Model model, Principal principal, @RequestParam Integer pageid, @RequestParam(value = "searchTerm", required = false) String search, @RequestParam(value = "category", required = false) String category) {
		
		List<JSPStudentFormat> list = new ArrayList<JSPStudentFormat>();
		int total = 10;  
		Integer pageSize=null;
        if (pageid == 1){
        	
        }  
        else {  
            pageid = (pageid-1)*total+1;  
        }
        List<StudentInfo> listStu = null;
        if (search == null || search.isEmpty()) {
        	if (userRoleDAO.getUserRoles(userDAO.findLoginUserInfo(principal.getName()).getId()).contains("ADMIN")){
        		listStu = studentDAO.listStudentInfos(pageid,total);
        		pageSize = studentDAO.listStudentInfos().size();
        	}
        	else if (userRoleDAO.getUserRoles(userDAO.findLoginUserInfo(principal.getName()).getId()).contains("MANAGER")) {
        		listStu = studentDAO.listStudentInfosForAdv(pageid,total,userDAO.findLoginUserInfo(principal.getName()).getId());
        		pageSize = studentDAO.listStudentInfosForAdvSize(userDAO.findLoginUserInfo(principal.getName()).getId()).size();
        	}
        }
        else {
        	String decodedToUTF8;
    		try {
    			decodedToUTF8 = new String(search.getBytes("ISO-8859-1"), "UTF-8");
    			search = (decodedToUTF8);
    		} catch (UnsupportedEncodingException e) {
    			System.out.println("Search term name cannot converted.");
    			e.printStackTrace();
    		}
    		if (userRoleDAO.getUserRoles(userDAO.findLoginUserInfo(principal.getName()).getId()).contains("ADMIN")) {
    			listStu = studentDAO.listStudentInfosBySearch(pageid,total,search,category);
    			pageSize = studentDAO.listStudentInfosBySearchSize(search, category).size();
    			System.out.println("admin searchs..total page"+total+" "+pageid);
        	}
    		else if (userRoleDAO.getUserRoles(userDAO.findLoginUserInfo(principal.getName()).getId()).contains("MANAGER")) {
        		listStu = studentDAO.listStudentInfosBySearchForAdv(pageid,total,search,category,userDAO.findLoginUserInfo(principal.getName()).getId());
        		pageSize = studentDAO.listStudentInfosBySearchForAdvSize(search, category, userDAO.findLoginUserInfo(principal.getName()).getId()).size();
    		}
        }
		for (StudentInfo tmp : listStu){
			 DeptInfo dept = this.deptDAO.findDeptInfo(tmp.getDeptId());
			 UniInfo uni = this.uniDAO.findUniInfo(dept.getUniId());
			 UserInfo user = this.userDAO.findUserInfo(tmp.getAdvisorId());
			 JSPStudentFormat temp = new JSPStudentFormat();
			 if (user == null) {
				 temp = new JSPStudentFormat(tmp.getId(), uni.getId(), uni.getName(), dept.getId(), dept.getName(), tmp.getName(), tmp.getSurname(), tmp.getNo(), tmp.getAdpScore(), tmp.getRecordYear(), "Danışman atanmamış." );
			 }
			 else {
				 temp = new JSPStudentFormat(tmp.getId(), uni.getId(), uni.getName(), dept.getId(), dept.getName(), tmp.getName(), tmp.getSurname(), tmp.getNo(), tmp.getAdpScore(), tmp.getRecordYear(), user.getUsername() );
			 }
			 list.add(temp);
		 }
		pageSize = (int) Math.ceil(pageSize / (float)total);
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
			model.addAttribute("formTitle", "Yeni Öğrenci Kaydı");
		} else {
			model.addAttribute("formTitle", "Öğrenci Formu");
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
		List<UserInfo> admins = userDAO.listUserInfosRoleManager();
		System.out.println("admins size:"+admins.size());
		model.addAttribute("advisorId", studentInfo.getAdvisorId());
		model.addAttribute("advisors", admins);
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
	public String saveStudent(Model model, Principal principal, //
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
		UserInfo user = userDAO.findLoginUserInfo(principal.getName());
		List<String> userRoles = userRoleDAO.getUserRoles(user.getId());
		boolean isManager = false;
		for (String u : userRoles) {
			if (u.contains("MANAGER")) {
				isManager = true;
			}
		}
		if (isManager) {
			studentInfo.setAdvisorId(user.getId());
		}
		this.studentDAO.saveStudent(studentInfo);
		
		

		// Important!!: Need @EnableWebMvc
		// Add message to flash scope
		redirectAttributes.addFlashAttribute("message5", "Öğrenci güncellendi.");
	

//		return "redirect:/studentList";
		return "redirect:/Student?pageid=1";
	}
	
}
