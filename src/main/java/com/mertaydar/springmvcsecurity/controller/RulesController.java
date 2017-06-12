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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mertaydar.springmvcsecurity.dao.CurriculumDAO;
import com.mertaydar.springmvcsecurity.dao.DeptDAO;
import com.mertaydar.springmvcsecurity.dao.RulesDAO;
import com.mertaydar.springmvcsecurity.dao.SubstituteLessonDAO;
import com.mertaydar.springmvcsecurity.dao.TakingLessonDAO;
import com.mertaydar.springmvcsecurity.dao.UniDAO;
import com.mertaydar.springmvcsecurity.model.CurriculumInfo;
import com.mertaydar.springmvcsecurity.model.DeptInfo;
import com.mertaydar.springmvcsecurity.model.RulesInfo;
import com.mertaydar.springmvcsecurity.model.SubstituteLessonInfo;
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
	private SubstituteLessonDAO substituteLessonDAO;
	
	@Autowired
	private RulesDAO rulesDAO;
	
	@Autowired
	private CurriculumDAO curriculumDAO;
	
/* Rules Section */
	
	@RequestMapping(value = "/addRules", method = RequestMethod.GET)
	public String addRules(Model model, Integer offset, Integer maxResults) {
//		List<RulesInfo> rules = rulesDAO.listRulesInfos();
//		model.addAttribute("rules", rules);
		List<CurriculumInfo> curList = curriculumDAO.listCurriculumInfos();
		model.addAttribute("curriculums", curList);
		CurriculumInfo active = curriculumDAO.findCurriculumInfoActive();
		List<SubstituteLessonInfo> list = new ArrayList<SubstituteLessonInfo>();
		SubstituteLessonInfo first = new SubstituteLessonInfo();
		first.setName("Şart yok.");
		list.add(first);
		List<SubstituteLessonInfo> list2 = substituteLessonDAO.listSubstituteLessonInfosPagination(active.getId(), offset, maxResults);
		List<SubstituteLessonInfo> list3 = substituteLessonDAO.listSubstituteLessonInfos();
		list.addAll(list3);
		model.addAttribute("subLes", list);
		model.addAttribute("subLes2", list2);
		List<String> pool = substituteLessonDAO.listPoolLessonsByCurriculum(active.getId());
		model.addAttribute("pool", pool);
		model.addAttribute("count", substituteLessonDAO.count(active.getId()));
		model.addAttribute("offset", offset);
		return "addRules";
	}
	
	@RequestMapping(value = "/addRules2", method = RequestMethod.GET)
	public String addRules2(Model model, Integer offset, Integer maxResults) {
//		List<RulesInfo> rules = rulesDAO.listRulesInfos();
//		model.addAttribute("rules", rules);
		List<CurriculumInfo> curList = curriculumDAO.listCurriculumInfos();
		model.addAttribute("curriculums", curList);
		CurriculumInfo active = curriculumDAO.findCurriculumInfoActive();
		List<SubstituteLessonInfo> list = new ArrayList<SubstituteLessonInfo>();
		SubstituteLessonInfo first = new SubstituteLessonInfo();
		first.setName("Şart yok.");
		list.add(first);
		List<SubstituteLessonInfo> list2 = substituteLessonDAO.listSubstituteLessonInfosPagination(active.getId(), offset, maxResults);
		List<SubstituteLessonInfo> list3 = substituteLessonDAO.listSubstituteLessonInfos();
		list.addAll(list3);
		model.addAttribute("subLes", list);
		model.addAttribute("subLes2", list2);
		List<String> pool = substituteLessonDAO.listPoolLessonsByCurriculum(active.getId());
		model.addAttribute("pool", pool);
		model.addAttribute("count", substituteLessonDAO.count(active.getId()));
		model.addAttribute("offset", offset);
		return "redirect:/addRules#substituteLessonTab";
	}
	
	@RequestMapping(value="/getPoolLesson",method = RequestMethod.GET, produces = "text/plain; charset=UTF-8")
	@ResponseBody
	public String getPoolLesson(@RequestParam Integer id) {
		String res = "";
		List<String> pool = substituteLessonDAO.listPoolLessonsByCurriculum(id);
		for (String tmp : pool) {
				String tmp2 = tmp.concat("H");
				res = res.concat("<option "+"id=\""+tmp+"\" value=\""+tmp2+"\">"+tmp+"</option>");
		}
		return res;
	}
	
	@RequestMapping(value="/getRules",method = RequestMethod.GET, produces = "text/plain; charset=UTF-8")
	@ResponseBody
	public String getRules(@RequestParam Integer id) {
		System.out.println("id:"+id);
		String res = "";
		List<TakingLessonInfo> tl = takingLessonDAO.listTakingLessonFromDept(id);
		CurriculumInfo curriculumInfo = curriculumDAO.findCurriculumInfoActive();
		for (TakingLessonInfo t : tl) {
			List<RulesInfo> rules = rulesDAO.listRulesForLesson(t.getId());
			for (RulesInfo r : rules) {
				if (substituteLessonDAO.findSubstituteLessonInfo(r.getSubstituteLessonId()).getCurriculumId() == curriculumInfo.getId())
					res = res.concat("<tr><td>"+r.getId()+"</td>"+"<td>"+t.getName()+"</td><td>"+substituteLessonDAO.findSubstituteLesson(r.getSubstituteLessonId()).getName()+"</td><td><a href=\"deleteRules?id="+r.getId()+"\" class=\"btn btn-danger btn-xs\"><span class=\"glyphicon glyphicon-remove\"></span> Sil</a></td></tr>");
			}
		}
		return res;
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
	public String deleteRules(Model model, @RequestParam("id") Integer id, final RedirectAttributes redirectAttributes) {
		if (id != null) {
			RulesInfo rul = rulesDAO.findRulesInfo(id);
			TakingLessonInfo tak = takingLessonDAO.findTakingLessonInfo(rul.getTakingLessonId());
			DeptInfo dep = deptDAO.findDeptInfo(tak.getDeptId());
			this.rulesDAO.deleteRules(id);
			redirectAttributes.addFlashAttribute("message5", "Kural silindi.");
			return "redirect:/addRules?id="+dep.getUniId()+"&id2="+dep.getId();
		}
		return "redirect:/addRules";
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
		else if (substituteLessonDAO.findSubstituteLessonInfo(rulesInfo.getSubstituteLessonId()).getLab() > takingLessonDAO.findTakingLessonInfo(rulesInfo.getTakingLessonId()).getLab()) {
			redirectAttributes.addFlashAttribute("message5", "Lab. saati eşit veya büyük olmalı.");
		}
		else {
			this.rulesDAO.saveRules(rulesInfo);

			// Important!!: Need @EnableWebMvc
			// Add message to flash scope
			redirectAttributes.addFlashAttribute("message5", "Kural eklendi.");
		}
		int deptId = takingLessonDAO.findTakingLessonInfo(rulesInfo.getTakingLessonId()).getDeptId();; 
		int uniId = deptDAO.findDeptInfo(deptId).getUniId();
		
//		return "redirect:/rulesList";
		return "redirect:/addRules?id="+uniId+"&id2="+deptId;
	}
}
