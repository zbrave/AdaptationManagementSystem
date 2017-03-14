//package org.o7planning.springmvcsecurity.controller;
//
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.o7planning.springmvcsecurity.dao.UniDAO;
//import org.o7planning.springmvcsecurity.model.UniInfo;
//import org.o7planning.springmvcsecurity.validator.UniValidator;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.WebDataBinder;
//import org.springframework.web.bind.annotation.InitBinder;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//@Controller
////Enable Hibernate Transaction.
//@Transactional
////Need To use RedirectAttributes
//@EnableWebMvc
//public class MyController {
//	@Autowired
//	private UniDAO uniDAO;
//	
///*	@Autowired
//	private UniValidator uniValidator;*/
// 
//	@RequestMapping("/")
//	public String homePage(Model model) {
// 
//		return uniList(model);
//	}
//	
//	@RequestMapping("/uniList")
//	   public String uniList(Model model) {
//	       List<UniInfo> list = uniDAO.listUniInfos();
//	       model.addAttribute("uniInfos", list);
//	       return "uniList";
//	 }
//	 
//	/*   private Map<String, String> dataForPositions() {
//	       Map<String, String> positionMap = new LinkedHashMap<String, String>();
//	       positionMap.put("Developer", "Developer");
//	       positionMap.put("Leader", "Leader");
//	       positionMap.put("Tester", "Tester");
//	       return positionMap;
//	   }
//	 
//	   private List<String> dataForSkills() {
//	       List<String> list = new ArrayList<String>();
//	       list.add("Java");
//	       list.add("C/C++");
//	       list.add("C#");
//	       return list;
//	   }*/
//	 
//	   private String formUni(Model model, UniInfo uniInfo) {
//	       model.addAttribute("uniForm", uniInfo);
//	 
////	       Map<String, String> positionMap = this.dataForPositions();
//	 
////	       model.addAttribute("positionMap", positionMap);
//	 
////	       List<String> list = dataForSkills();
////	       model.addAttribute("skills", list);
//	 
//	       if (uniInfo.getId() == null) {
//	           model.addAttribute("formTitle", "Create Uni");
//	       } else {
//	           model.addAttribute("formTitle", "Edit Uni");
//	       }
//	 
//	       return "formUni";
//	   }
//	 
//	   @RequestMapping("/createUni")
//	   public String createUni(Model model) {
//	 
//	       UniInfo uniInfo = new UniInfo();
//	 
//	       return this.formUni(model, uniInfo);
//	   }
//	 
//	   @RequestMapping("/editUni")
//	   public String editUni(Model model, @RequestParam("id") Integer id) {
//	       UniInfo uniInfo = null;
//	       if (id != null) {
//	           uniInfo = this.uniDAO.findUniInfo(id);
//	       }
//	       if (uniInfo == null) {
//	           return "redirect:/uniList";
//	       }
//	 
//	       return this.formUni(model, uniInfo);
//	   }
//	 
//	   @RequestMapping("/deleteUni")
//	   public String deleteUni(Model model, @RequestParam("id") Integer id) {
//	       if (id != null) {
//	           this.uniDAO.deleteUni(id);
//	       }
//	       return "redirect:/uniList";
//	   }
//	 
//	   // Set a form validator
///*	   @InitBinder
//	   protected void initBinder(WebDataBinder dataBinder) {
//	       // Form target
//	       Object target = dataBinder.getTarget();
//	       if (target == null) {
//	           return;
//	       }
//	       System.out.println("Target=" + target);
//	 
//	       if (target.getClass() == UniInfo.class) {
//	           dataBinder.setValidator(uniValidator);
//	       }
//	   }*/
//	 
//	   // Save or update Applicant
//	   // 1. @ModelAttribute bind form value
//	   // 2. @Validated form validator
//	   // 3. RedirectAttributes for flash value
//	   @RequestMapping(value = "/saveUni", method = RequestMethod.POST)
//	   public String saveUni(Model model, //
//	           @ModelAttribute("uniForm") @Validated UniInfo uniInfo, //
//	           BindingResult result, //
//	           final RedirectAttributes redirectAttributes) {
//	 
//	    
//	       if (result.hasErrors()) {
//	           return this.formUni(model, uniInfo);
//	       }
//	 
//	       this.uniDAO.saveUni(uniInfo);
//	 
//	       // Important!!: Need @EnableWebMvc
//	       // Add message to flash scope
//	       redirectAttributes.addFlashAttribute("message", "Save Uni Successful");
//	 
//	       return "redirect:/uniList";
//	 
//	   }
//}
