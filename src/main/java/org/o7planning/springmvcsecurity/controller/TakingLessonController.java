package org.o7planning.springmvcsecurity.controller;

import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.o7planning.springmvcsecurity.dao.DeptDAO;
import org.o7planning.springmvcsecurity.dao.TakingLessonDAO;
import org.o7planning.springmvcsecurity.dao.UniDAO;
import org.o7planning.springmvcsecurity.model.DeptInfo;
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
		System.out.println("id:"+id);
		String res = "<option id=-1 value=-1>Alınan ders seçin.</option>";
		List<TakingLessonInfo> list = this.takingLessonDAO.listTakingLessonFromDept(id);
		for (TakingLessonInfo tmp : list) {
			res = res.concat("<option "+"id="+tmp.getId()+" value="+tmp.getId()+">"+tmp.getName()+"</option>");
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
}
