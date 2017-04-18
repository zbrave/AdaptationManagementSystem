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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mertaydar.springmvcsecurity.dao.MarkDAO;
import com.mertaydar.springmvcsecurity.dao.UniDAO;
import com.mertaydar.springmvcsecurity.model.MarkInfo;
import com.mertaydar.springmvcsecurity.model.UniInfo;

@Controller
//Enable Hibernate Transaction.
@Transactional
//Need To use RedirectAttributes
@EnableWebMvc
public class MarkController {

	@Autowired
	private UniDAO uniDAO;
	
	@Autowired
	private MarkDAO markDAO;
	
	/* Mark Controller Section */
	@RequestMapping("/markList")
	public String markList(Model model) {
		List<MarkInfo> list = markDAO.listMarkInfos();
		model.addAttribute("markInfos", list);
		return "markList";
	}
	
	@RequestMapping(value="/getMark",method = RequestMethod.GET, produces = "text/plain; charset=UTF-8")
	@ResponseBody
	public String getMark(@RequestParam Integer id) {
		System.out.println("id:"+id);
		String res = "<option id=-1 value=-1>Üniversite seçin.</option>";
		List<MarkInfo> list = this.markDAO.listMarkFromUni(id);
		for (MarkInfo tmp : list) {
//			res = res.concat("<option "+"id="+tmp.getId()+" value="+tmp.getId()+">"+tmp.getName()+"</option>");
		}
		return res;
	}

	@SuppressWarnings("unused")
	private Map<Integer, String> markList() {
		Map<Integer, String> markMap = new LinkedHashMap<Integer, String>();
		List<MarkInfo> markList = this.markDAO.listMarkInfos();
		for (MarkInfo tmp : markList) {
			markMap.put(tmp.getId(), tmp.getFrom());
		}

		return markMap;
	}
	
	private Map<Integer, String> uniList() {
		Map<Integer, String> uniMap = new LinkedHashMap<Integer, String>();
		List<UniInfo> uniList = this.uniDAO.listUniInfos();
		for (UniInfo tmp : uniList) {
			uniMap.put(tmp.getId(), tmp.getName());
		}

		return uniMap;
	}
	
	private String formMark(Model model, MarkInfo markInfo) {
		model.addAttribute("markForm", markInfo);

		Map<Integer, String> uniMap = this.uniList();

		model.addAttribute("uniMap", uniMap);

		//		       List<String> list = dataForSkills();
		//		       model.addAttribute("skills", list);

		if (markInfo.getId() == null) {
			model.addAttribute("formTitle", "Create Mark");
		} else {
			model.addAttribute("formTitle", "Edit Mark");
		}

		return "formMark";
	}

	@RequestMapping("/createMark")
	public String createMark(Model model) {

		MarkInfo markInfo = new MarkInfo();

		return this.formMark(model, markInfo);
	}

	@RequestMapping("/editMark")
	public String editMark(Model model, @RequestParam("id") Integer id) {
		MarkInfo markInfo = null;
		if (id != null) {
			markInfo = this.markDAO.findMarkInfo(id);
		}
		if (markInfo == null) {
			return "redirect:/markList";
		}

		return this.formMark(model, markInfo);
	}

	@RequestMapping("/deleteMark")
	public String deleteMark(Model model, @RequestParam("id") Integer id) {
		if (id != null) {
			this.markDAO.deleteMark(id);
		}
		return "redirect:/markList";
	}

	@RequestMapping(value = "/saveMark", method = RequestMethod.POST)
	public String saveMark(Model model, //
			@ModelAttribute("markForm") @Validated MarkInfo markInfo, //
			BindingResult result, //
			final RedirectAttributes redirectAttributes) {


		if (result.hasErrors()) {
			return this.formMark(model, markInfo);
		}
		
//		String decodedToUTF8;
//		try {
//			decodedToUTF8 = new String(markInfo.getName().getBytes("ISO-8859-1"), "UTF-8");
//			markInfo.setName(decodedToUTF8);
//		} catch (UnsupportedEncodingException e) {
//			System.out.println("Mark name cannot converted.");
//			e.printStackTrace();
//		}

		this.markDAO.saveMark(markInfo);

		// Important!!: Need @EnableWebMvc
		// Add message to flash scope
		redirectAttributes.addFlashAttribute("message2", "Bölüm eklendi.");

//		return "redirect:/markList";
		return "redirect:/addRules";
	}
}
