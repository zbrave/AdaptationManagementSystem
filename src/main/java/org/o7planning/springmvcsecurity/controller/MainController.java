package org.o7planning.springmvcsecurity.controller;

import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.o7planning.springmvcsecurity.dao.DeptDAO;
import org.o7planning.springmvcsecurity.dao.RulesDAO;
import org.o7planning.springmvcsecurity.dao.SubstituteLessonDAO;
import org.o7planning.springmvcsecurity.dao.TakingLessonDAO;
import org.o7planning.springmvcsecurity.dao.UniDAO;
import org.o7planning.springmvcsecurity.model.DeptInfo;
import org.o7planning.springmvcsecurity.model.RulesInfo;
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
public class MainController {

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

	/*	@Autowired
	private UniValidator uniValidator;*/

	@RequestMapping(value = { "/", "/welcome" }, method = RequestMethod.GET)
	public String welcomePage(Model model) {
		model.addAttribute("title", "İntibak Yönetim Sistemi");
		return "welcomePage";
	}

	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String adminPage(Model model) {
		return "adminPage";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage(Model model ) {
		return "loginPage";
	}

	@RequestMapping(value = "/logoutSuccessful", method = RequestMethod.GET)
	public String logoutSuccessfulPage(Model model) {
		model.addAttribute("title", "Logout");
		return "logoutSuccessfulPage";
	}

	@RequestMapping(value = "/userInfo", method = RequestMethod.GET)
	public String userInfo(Model model, Principal principal) {

		// After user login successfully.
		String userName = principal.getName();

		model.addAttribute("message", userName);
		return "userInfoPage";
	}

	@RequestMapping(value = "/addRules", method = RequestMethod.GET)
	public String addRules(Model model) {

		return "addRules";
	}

	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public String accessDenied(Model model, Principal principal) {

		if (principal != null) {
			model.addAttribute("message", "Hi " + principal.getName()
			+ "<br> You do not have permission to access this page!");
		} else {
			model.addAttribute("msg",
					"You do not have permission to access this page!");
		}
		return "403Page";
	}

	/* Uni Controller Section */
	@RequestMapping("/uniList")
	public String uniList(Model model) {
		List<UniInfo> list = uniDAO.listUniInfos();
		model.addAttribute("uniInfos", list);
		return "uniList";
	}

	private Map<Integer, String> uniList() {
		Map<Integer, String> uniMap = new LinkedHashMap<Integer, String>();
		List<UniInfo> uniList = this.uniDAO.listUniInfos();
		for (UniInfo tmp : uniList) {
			uniMap.put(tmp.getId(), tmp.getName());
		}

		return uniMap;
	}
	
	@RequestMapping(value="/getUni",method = RequestMethod.GET, produces = "text/plain; charset=UTF-8")
	@ResponseBody
	public String getUni() {
		String res = "<option id=-1 value=-1>Üniversite seçin.</option>";
		List<UniInfo> list = this.uniDAO.listUniInfos();
		for (UniInfo tmp : list) {
			res = res.concat("<option "+"id="+tmp.getId()+" value="+tmp.getId()+">"+tmp.getName()+"</option>");
		}
		return res;
	}

	private String formUni(Model model, UniInfo uniInfo) {
		model.addAttribute("uniForm", uniInfo);


		//	       List<String> list = dataForSkills();
		//	       model.addAttribute("skills", list);

		if (uniInfo.getId() == null) {
			model.addAttribute("formTitle", "Create Uni");
		} else {
			model.addAttribute("formTitle", "Edit Uni");
		}

		return "formUni";
	}

	@RequestMapping("/createUni")
	public String createUni(Model model) {

		UniInfo uniInfo = new UniInfo();

		return this.formUni(model, uniInfo);
	}

	@RequestMapping("/editUni")
	public String editUni(Model model, @RequestParam("id") Integer id) {
		UniInfo uniInfo = null;
		if (id != null) {
			uniInfo = this.uniDAO.findUniInfo(id);
		}
		if (uniInfo == null) {
			return "redirect:/uniList";
		}

		return this.formUni(model, uniInfo);
	}

	@RequestMapping("/deleteUni")
	public String deleteUni(Model model, @RequestParam("id") Integer id) {
		if (id != null) {
			this.uniDAO.deleteUni(id);
		}
		return "redirect:/uniList";
	}

	@RequestMapping(value = "/saveUni", method = RequestMethod.POST)
	public String saveUni(Model model, //
			@ModelAttribute("uniForm") @Validated UniInfo uniInfo, //
			BindingResult result, //
			final RedirectAttributes redirectAttributes) {


		if (result.hasErrors()) {
			return this.formUni(model, uniInfo);
		}
		String decodedToUTF8;
		try {
			decodedToUTF8 = new String(uniInfo.getName().getBytes("ISO-8859-1"), "UTF-8");
			uniInfo.setName(decodedToUTF8);
		} catch (UnsupportedEncodingException e) {
			System.out.println("Uni name cannot converted.");
			e.printStackTrace();
		}
		
		this.uniDAO.saveUni(uniInfo);

		// Important!!: Need @EnableWebMvc
		// Add message to flash scope
		redirectAttributes.addFlashAttribute("message1", "Üniversite Eklendi.");

//		return "redirect:/uniList";
		return "redirect:/addRules";
	}

	/* Dept Controller Section */
	@RequestMapping("/deptList")
	public String deptList(Model model) {
		List<DeptInfo> list = deptDAO.listDeptInfos();
		model.addAttribute("deptInfos", list);
		return "deptList";
	}

	private Map<Integer, String> deptList() {
		Map<Integer, String> deptMap = new LinkedHashMap<Integer, String>();
		List<DeptInfo> deptList = this.deptDAO.listDeptInfos();
		for (DeptInfo tmp : deptList) {
			deptMap.put(tmp.getId(), tmp.getName());
		}

		return deptMap;
	}

	private String formDept(Model model, DeptInfo deptInfo) {
		model.addAttribute("deptForm", deptInfo);

		Map<Integer, String> uniMap = this.uniList();

		model.addAttribute("uniMap", uniMap);

		//		       List<String> list = dataForSkills();
		//		       model.addAttribute("skills", list);

		if (deptInfo.getId() == null) {
			model.addAttribute("formTitle", "Create Dept");
		} else {
			model.addAttribute("formTitle", "Edit Dept");
		}

		return "formDept";
	}

	@RequestMapping("/createDept")
	public String createDept(Model model) {

		DeptInfo deptInfo = new DeptInfo();

		return this.formDept(model, deptInfo);
	}

	@RequestMapping("/editDept")
	public String editDept(Model model, @RequestParam("id") Integer id) {
		DeptInfo deptInfo = null;
		if (id != null) {
			deptInfo = this.deptDAO.findDeptInfo(id);
		}
		if (deptInfo == null) {
			return "redirect:/deptList";
		}

		return this.formDept(model, deptInfo);
	}

	@RequestMapping("/deleteDept")
	public String deleteDept(Model model, @RequestParam("id") Integer id) {
		if (id != null) {
			this.deptDAO.deleteDept(id);
		}
		return "redirect:/deptList";
	}

	@RequestMapping(value = "/saveDept", method = RequestMethod.POST)
	public String saveDept(Model model, //
			@ModelAttribute("deptForm") @Validated DeptInfo deptInfo, //
			BindingResult result, //
			final RedirectAttributes redirectAttributes) {


		if (result.hasErrors()) {
			return this.formDept(model, deptInfo);
		}
		
		String decodedToUTF8;
		try {
			decodedToUTF8 = new String(deptInfo.getName().getBytes("ISO-8859-1"), "UTF-8");
			deptInfo.setName(decodedToUTF8);
		} catch (UnsupportedEncodingException e) {
			System.out.println("Dept name cannot converted.");
			e.printStackTrace();
		}

		this.deptDAO.saveDept(deptInfo);

		// Important!!: Need @EnableWebMvc
		// Add message to flash scope
		redirectAttributes.addFlashAttribute("message2", "Bölüm eklendi.");

//		return "redirect:/deptList";
		return "redirect:/addRules";
	}

	/* TakingLesson Controller Section */
	@RequestMapping("/takingLessonList")
	public String takingLessonList(Model model) {
		List<TakingLessonInfo> list = takingLessonDAO.listTakingLessonInfos();
		model.addAttribute("takingLessonInfos", list);
		return "takingLessonList";
	}
	
	@RequestMapping(value="/getDept",method = RequestMethod.GET, produces = "text/plain; charset=UTF-8")
	@ResponseBody
	public String getDept(@RequestParam Integer id) {
		System.out.println("id:"+id);
		String res = "<option id=-1 value=-1>Bölüm seçin.</option>";
		List<DeptInfo> list = this.deptDAO.listDeptFromUni(id);
		for (DeptInfo tmp : list) {
			res = res.concat("<option "+"id="+tmp.getId()+" value="+tmp.getId()+">"+tmp.getName()+"</option>");
		}
		return res;
	}
	
	@RequestMapping(value="/getTakingLesson",method = RequestMethod.GET, produces = "text/plain; charset=UTF-8")
	@ResponseBody
	public String getTakingLesson(@RequestParam Integer id) {
		System.out.println("id:"+id);
		String res = "<option id=-1 value=-1>Alınan ders seçin.</option>";
		List<TakingLessonInfo> list = this.takingLessonDAO.listTakingLessonFromDept(id);
		for (TakingLessonInfo tmp : list) {
			res = res.concat("<option "+"id="+tmp.getId()+" value="+tmp.getId()+">"+tmp.getName()+"</option>");
		}
		return res;
	}
	
	private Map<Integer, String> takingLessonList() {
		Map<Integer, String> takingLessonMap = new LinkedHashMap<Integer, String>();
		List<TakingLessonInfo> takingLessonList = this.takingLessonDAO.listTakingLessonInfos();
		for (TakingLessonInfo tmp : takingLessonList) {
			takingLessonMap.put(tmp.getId(), tmp.getCode());
		}

		return takingLessonMap;
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
			return "redirect:/takingLessonList";
		}

		return this.formTakingLesson(model, takingLessonInfo);
	}

	@RequestMapping("/deleteTakingLesson")
	public String deleteTakingLesson(Model model, @RequestParam("id") Integer id) {
		if (id != null) {
			this.takingLessonDAO.deleteTakingLesson(id);
		}
		return "redirect:/takingLessonList";
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

		// Important!!: Need @EnableWebMvc
		// Add message to flash scope
		redirectAttributes.addFlashAttribute("message3", "Alınan ders eklendi.");

//		return "redirect:/takingLessonList";
		return "redirect:/addRules";
	}

	/* SubstituteLesson Controller Section */
	@RequestMapping("/substituteLessonList")
	public String substituteLessonList(Model model) {
		List<SubstituteLessonInfo> list = substituteLessonDAO.listSubstituteLessonInfos();
		model.addAttribute("substituteLessonInfos", list);
		return "substituteLessonList";
	}
	
	@RequestMapping(value="/getSubstituteLesson",method = RequestMethod.GET, produces = "text/plain; charset=UTF-8")
	@ResponseBody
	public String getSubstituteLesson() {
		String res = "<option id=-1 value=-1>Ders seçin.</option>";
		List<SubstituteLessonInfo> list = this.substituteLessonDAO.listSubstituteLessonInfos();
		for (SubstituteLessonInfo tmp : list) {
			res = res.concat("<option "+"id="+tmp.getId()+" value="+tmp.getId()+">"+tmp.getName()+"</option>");
		}
		return res;
	}

	private String formSubstituteLesson(Model model, SubstituteLessonInfo substituteLessonInfo) {
		model.addAttribute("substituteLessonForm", substituteLessonInfo);

		Map<Integer, String> uniMap = this.uniList();

		model.addAttribute("uniMap", uniMap);
		
		Map<Integer, String> deptMap = this.deptList();

		model.addAttribute("deptMap", deptMap);
		
		Map<Integer, String> takingLessonMap = this.takingLessonList();

		model.addAttribute("takingLessonMap", takingLessonMap);
		

		//				       List<String> list = dataForSkills();
		//				       model.addAttribute("skills", list);

		if (substituteLessonInfo.getId() == null) {
			model.addAttribute("formTitle", "Create SubstituteLesson");
		} else {
			model.addAttribute("formTitle", "Edit SubstituteLesson");
		}

		return "formSubstituteLesson";
	}

	@RequestMapping("/createSubstituteLesson")
	public String createSubstituteLesson(Model model) {

		SubstituteLessonInfo substituteLessonInfo = new SubstituteLessonInfo();

		return this.formSubstituteLesson(model, substituteLessonInfo);
	}

	@RequestMapping("/editSubstituteLesson")
	public String editSubstituteLesson(Model model, @RequestParam("id") Integer id) {
		SubstituteLessonInfo substituteLessonInfo = null;
		if (id != null) {
			substituteLessonInfo = this.substituteLessonDAO.findSubstituteLessonInfo(id);
		}
		if (substituteLessonInfo == null) {
			return "redirect:/substituteLessonList";
		}

		return this.formSubstituteLesson(model, substituteLessonInfo);
	}

	@RequestMapping("/deleteSubstituteLesson")
	public String deleteSubstituteLesson(Model model, @RequestParam("id") Integer id) {
		if (id != null) {
			this.substituteLessonDAO.deleteSubstituteLesson(id);
		}
		return "redirect:/substituteLessonList";
	}

	@RequestMapping(value = "/saveSubstituteLesson", method = RequestMethod.POST)
	public String saveSubstituteLesson(Model model, //
			@ModelAttribute("substituteLessonForm") @Validated SubstituteLessonInfo substituteLessonInfo, //
			BindingResult result, //
			final RedirectAttributes redirectAttributes) {


		if (result.hasErrors()) {
			return this.formSubstituteLesson(model, substituteLessonInfo);
		}
		String decodedToUTF8;
		try {
			decodedToUTF8 = new String(substituteLessonInfo.getName().getBytes("ISO-8859-1"), "UTF-8");
			substituteLessonInfo.setName(decodedToUTF8);
		} catch (UnsupportedEncodingException e) {
			System.out.println("Dept name cannot converted.");
			e.printStackTrace();
		}

		this.substituteLessonDAO.saveSubstituteLesson(substituteLessonInfo);

		// Important!!: Need @EnableWebMvc
		// Add message to flash scope
		redirectAttributes.addFlashAttribute("message4", "Verilen Ders Eklendi.");

//		return "redirect:/substituteLessonList";
		return "redirect:/addRules";
	}
	
	/* Rules Section */
	
	@RequestMapping("/rulesList")
	public String rulesList(Model model) {
		List<RulesInfo> list = rulesDAO.listRulesInfos();
		model.addAttribute("rulesInfos", list);
		return "rulesList";
	}

	private String formRules(Model model, RulesInfo rulesInfo) {
		model.addAttribute("rulesForm", rulesInfo);

		Map<Integer, String> uniMap = this.uniList();

		model.addAttribute("uniMap", uniMap);
		
		Map<Integer, String> deptMap = this.deptList();

		model.addAttribute("deptMap", deptMap);
		
		Map<Integer, String> takingLessonMap = this.takingLessonList();

		model.addAttribute("takingLessonMap", takingLessonMap);
		

		//				       List<String> list = dataForSkills();
		//				       model.addAttribute("skills", list);

		if (rulesInfo.getId() == null) {
			model.addAttribute("formTitle", "Create Rules");
		} else {
			model.addAttribute("formTitle", "Edit Rules");
		}

		return "formRules";
	}

	@RequestMapping("/createRules")
	public String createRules(Model model) {

		RulesInfo rulesInfo = new RulesInfo();

		return this.formRules(model, rulesInfo);
	}

	@RequestMapping("/editRules")
	public String editRules(Model model, @RequestParam("id") Integer id) {
		RulesInfo rulesInfo = null;
		if (id != null) {
			rulesInfo = this.rulesDAO.findRulesInfo(id);
		}
		if (rulesInfo == null) {
			return "redirect:/rulesList";
		}

		return this.formRules(model, rulesInfo);
	}

	@RequestMapping("/deleteRules")
	public String deleteRules(Model model, @RequestParam("id") Integer id) {
		if (id != null) {
			this.rulesDAO.deleteRules(id);
		}
		return "redirect:/rulesList";
	}

	@RequestMapping(value = "/saveRules", method = RequestMethod.POST)
	public String saveRules(Model model, //
			@ModelAttribute("rulesForm") @Validated RulesInfo rulesInfo, //
			BindingResult result, //
			final RedirectAttributes redirectAttributes) {


		if (result.hasErrors()) {
			return this.formRules(model, rulesInfo);
		}

		this.rulesDAO.saveRules(rulesInfo);

		// Important!!: Need @EnableWebMvc
		// Add message to flash scope
		redirectAttributes.addFlashAttribute("message5", "Verilen Kural Eklendi.");

//		return "redirect:/rulesList";
		return "redirect:/addRules";
	}
}