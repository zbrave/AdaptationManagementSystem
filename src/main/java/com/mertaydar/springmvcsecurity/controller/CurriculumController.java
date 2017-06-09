package com.mertaydar.springmvcsecurity.controller;

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

import com.mertaydar.springmvcsecurity.dao.CurriculumDAO;
import com.mertaydar.springmvcsecurity.model.CurriculumInfo;

@Controller
//Enable Hibernate Transaction.
@Transactional
//Need To use RedirectAttributes
@EnableWebMvc
public class CurriculumController {
	
	@Autowired
	private CurriculumDAO curriculumDAO;
	
	@RequestMapping("/deleteCurriculum")
	public String deleteCurriculum(Model model, @RequestParam("id") Integer id, final RedirectAttributes redirectAttributes) {
		if (id != null) {
			this.curriculumDAO.deleteCurriculum(id);
			redirectAttributes.addFlashAttribute("message1", "Müfredat yılı silindi.");
		}
		return "redirect:/addRules#substituteLessonTab";
	}
	
	@RequestMapping("/doActive")
	public String doActive(Model model, @RequestParam("id") Integer id, final RedirectAttributes redirectAttributes) {
		if (id != null) {
			System.out.println("Curriculum year null");
		}
		CurriculumInfo cur = curriculumDAO.findCurriculumInfo(id);
		cur.setEnabled(true);
		curriculumDAO.saveCurriculum(cur);
		redirectAttributes.addFlashAttribute("message1", "Müfredat yılı değiştirildi.");
		return "redirect:/addRules#substituteLessonTab";
	}

	@RequestMapping(value = "/saveCurriculum", method = RequestMethod.POST)
	public String saveCurriculum(Model model, //
			@ModelAttribute("curriculumForm") @Validated CurriculumInfo curriculumInfo, //
			BindingResult result, //
			final RedirectAttributes redirectAttributes) {


		if (result.hasErrors()) {
			System.out.println("Error curriculum delete.");
		}
		
		this.curriculumDAO.saveCurriculum(curriculumInfo);

		// Important!!: Need @EnableWebMvc
		// Add message to flash scope
		redirectAttributes.addFlashAttribute("message1", "Müfredat yılı eklendi.");

//		return "redirect:/curriculumList";
		return "redirect:/addRules#substituteLessonTab";
	}
}
