package com.mertaydar.springmvcsecurity.controller;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mertaydar.springmvcsecurity.dao.DeptDAO;
import com.mertaydar.springmvcsecurity.dao.RulesDAO;
import com.mertaydar.springmvcsecurity.dao.TakingLessonDAO;
import com.mertaydar.springmvcsecurity.dao.UniDAO;
import com.mertaydar.springmvcsecurity.model.DeptInfo;
import com.mertaydar.springmvcsecurity.model.RulesInfo;
import com.mertaydar.springmvcsecurity.model.TakingLessonInfo;
import com.mertaydar.springmvcsecurity.model.UniInfo;

@Controller
//Enable Hibernate Transaction.
@Transactional
//Need To use RedirectAttributes
@EnableWebMvc
public class RulesController {
	
	@Autowired
	private UniDAO uniDAO;

	@Autowired
	private DeptDAO deptDAO;

	@Autowired
	private TakingLessonDAO takingLessonDAO;
	
	@Autowired
	private RulesDAO rulesDAO;
	
/* Rules Section */
	
	@RequestMapping(value = "/addRules", method = RequestMethod.GET)
	public String addRules(Model model) {

		return "addRules";
	}
	
	@RequestMapping("/rulesList")
	public String rulesList(Model model) {
		List<RulesInfo> list = rulesDAO.listRulesInfos();
		model.addAttribute("rulesInfos", list);
		return "rulesList";
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
		if (rulesDAO.isDuplicate(rulesInfo)){
			redirectAttributes.addFlashAttribute("message5", "Kural zaten eklenmiş.");
		}
		else {
			this.rulesDAO.saveRules(rulesInfo);

			// Important!!: Need @EnableWebMvc
			// Add message to flash scope
			redirectAttributes.addFlashAttribute("message5", "Verilen Kural Eklendi.");
		}
		

//		return "redirect:/rulesList";
		return "redirect:/addRules";
	}
}
