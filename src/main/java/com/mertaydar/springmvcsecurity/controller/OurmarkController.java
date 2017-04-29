package com.mertaydar.springmvcsecurity.controller;

import java.util.List;

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

import com.mertaydar.springmvcsecurity.dao.OurmarkDAO;
import com.mertaydar.springmvcsecurity.model.OurmarkInfo;

@Controller
//Enable Hibernate Transaction.
@Transactional
//Need To use RedirectAttributes
@EnableWebMvc
public class OurmarkController {
	
	@Autowired
	private OurmarkDAO ourmarkDAO;
	
	@RequestMapping(value = "/Ourmark", method = RequestMethod.GET)
	public String Ourmark(Model model) {
		
		List<OurmarkInfo> list = ourmarkDAO.listOurmarkInfos();
	
		
		model.addAttribute("ourmarks", list);
		return "ourmark";
	}
	
	@RequestMapping("/editOurmark")
	public String editOurmark(Model model, @RequestParam("id") Integer id) {
		OurmarkInfo ourmarkInfo = null;
		if (id != null) {
			ourmarkInfo = this.ourmarkDAO.findOurmarkInfo(id);
		}
		if (ourmarkInfo == null) {
			System.out.println("Bu id li not yok.");
			return "redirect:/ourmarkList";
		}

		return this.formOurmark(model, ourmarkInfo);
	}
	
	private String formOurmark(Model model, OurmarkInfo ourmarkInfo) {
		model.addAttribute("ourmarkForm", ourmarkInfo);

		if (ourmarkInfo.getId() == null) {
			model.addAttribute("formTitle", "Create Ourmark");
		} else {
			model.addAttribute("formTitle", "Edit Ourmark");
		}

		return "formOurmark";
	}
	
	@RequestMapping("/deleteOurmark")
	public String deleteOurmark(Model model, @RequestParam("id") Integer id) {
		if (id != null) {
			this.ourmarkDAO.deleteOurmark(id);
		}
		return "redirect:/Ourmark";
	}
	
	@RequestMapping(value = "/saveOurmark", method = RequestMethod.POST)
	public String saveOurmark(Model model, //
			@ModelAttribute("ourmarkForm") @Validated OurmarkInfo ourmarkInfo, //
			BindingResult result, //
			final RedirectAttributes redirectAttributes) {


		if (result.hasErrors()) {
			return this.formOurmark(model, ourmarkInfo);
		}
		this.ourmarkDAO.saveOurmark(ourmarkInfo);

			// Important!!: Need @EnableWebMvc
			// Add message to flash scope
		redirectAttributes.addFlashAttribute("message5", "Öğrenci Eklendi.");
		
		

//		return "redirect:/ourmarkList";
		return "redirect:/Ourmark";
	}
}
