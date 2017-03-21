package org.o7planning.springmvcsecurity.controller;

import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.o7planning.springmvcsecurity.dao.DeptDAO;
import org.o7planning.springmvcsecurity.dao.SubstituteLessonDAO;
import org.o7planning.springmvcsecurity.dao.TakingLessonDAO;
import org.o7planning.springmvcsecurity.dao.UniDAO;
import org.o7planning.springmvcsecurity.model.DeptInfo;
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
public class SubstituteLessonController {
	
	@Autowired
	private UniDAO uniDAO;

	@Autowired
	private DeptDAO deptDAO;

	@Autowired
	private TakingLessonDAO takingLessonDAO;

	@Autowired
	private SubstituteLessonDAO substituteLessonDAO;
	
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
}
