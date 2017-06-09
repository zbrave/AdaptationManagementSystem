package com.mertaydar.springmvcsecurity.controller;

import java.io.UnsupportedEncodingException;
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

import com.mertaydar.springmvcsecurity.dao.UniDAO;
import com.mertaydar.springmvcsecurity.model.UniInfo;

@Controller
//Enable Hibernate Transaction.
@Transactional
//Need To use RedirectAttributes
@EnableWebMvc
public class UniController {
	
	@Autowired
	private UniDAO uniDAO;
	
	/* Uni Controller Section */
	@RequestMapping("/uniList")
	public String uniList(Model model) {
		List<UniInfo> list = uniDAO.listUniInfos();
		model.addAttribute("uniInfos", list);
		return "uniList";
	}

	@SuppressWarnings("unused")
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
			res = res.concat("<option "+"id=\""+tmp.getId()+"\" value=\""+tmp.getId()+"\">"+tmp.getName()+"</option>");
		}
		return res;
	}

	@RequestMapping(value="/getUnis",method = RequestMethod.GET, produces = "text/plain; charset=UTF-8")
	@ResponseBody
	public String getUnis() {
		String res = "";
		List<UniInfo> list = this.uniDAO.listUniInfos();
		for (UniInfo tmp : list) {
			res = res.concat("<tr><td>"+tmp.getId()+"</td><td>"+tmp.getName()+"</td><td><a href=\"deleteUni?id="+tmp.getId()+"\" class=\"btn btn-danger btn-xs\"><span class=\"glyphicon glyphicon-remove\"></span> Sil</a></td></tr>");
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
			return "redirect:/addRules";
		}

		return this.formUni(model, uniInfo);
	}

	@RequestMapping("/deleteUni")
	public String deleteUni(Model model, @RequestParam("id") Integer id, final RedirectAttributes redirectAttributes) {
		if (id != null) {
			this.uniDAO.deleteUni(id);
			redirectAttributes.addFlashAttribute("message1", "Üniversite silindi.");
		}
		return "redirect:/addRules";
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
}
