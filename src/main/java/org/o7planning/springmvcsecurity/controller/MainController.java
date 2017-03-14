package org.o7planning.springmvcsecurity.controller;
 
import java.security.Principal;
import java.util.List;

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
   
	@RequestMapping(value = "/newRule", method = RequestMethod.GET)
	public String newRulePage(Model model) {
	   
		return "newRulePage";
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
	
	private String formUni(Model model, UniInfo uniInfo) {
	       model.addAttribute("uniForm", uniInfo);
	 
//	       Map<String, String> positionMap = this.dataForPositions();
	 
//	       model.addAttribute("positionMap", positionMap);
	 
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
	 
	       this.uniDAO.saveUni(uniInfo);
	 
	       // Important!!: Need @EnableWebMvc
	       // Add message to flash scope
	       redirectAttributes.addFlashAttribute("message", "Save Uni Successful");
	 
	       return "redirect:/uniList";
	 
	   }
	   
	   /* Dept Controller Section */
		@RequestMapping("/deptList")
		public String deptList(Model model) {
		       List<DeptInfo> list = deptDAO.listDeptInfos();
		       model.addAttribute("deptInfos", list);
		       return "deptList";
		}
		
		private String formDept(Model model, DeptInfo deptInfo) {
		       model.addAttribute("deptForm", deptInfo);
		 
//		       Map<String, String> positionMap = this.dataForPositions();
		 
//		       model.addAttribute("positionMap", positionMap);
		 
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
		 
		       this.deptDAO.saveDept(deptInfo);
		 
		       // Important!!: Need @EnableWebMvc
		       // Add message to flash scope
		       redirectAttributes.addFlashAttribute("message", "Save Dept Successful");
		 
		       return "redirect:/deptList";
		 
		   }
		   
		   /* TakingLesson Controller Section */
			@RequestMapping("/takingLessonList")
			public String takingLessonList(Model model) {
			       List<TakingLessonInfo> list = takingLessonDAO.listTakingLessonInfos();
			       model.addAttribute("takingLessonInfos", list);
			       return "takingLessonList";
			}
			
			private String formTakingLesson(Model model, TakingLessonInfo takingLessonInfo) {
			       model.addAttribute("takingLessonForm", takingLessonInfo);
			 
//			       Map<String, String> positionMap = this.dataForPositions();
			 
//			       model.addAttribute("positionMap", positionMap);
			 
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
			 
			       this.takingLessonDAO.saveTakingLesson(takingLessonInfo);
			 
			       // Important!!: Need @EnableWebMvc
			       // Add message to flash scope
			       redirectAttributes.addFlashAttribute("message", "Save TakingLesson Successful");
			 
			       return "redirect:/takingLessonList";
			 
			   }
			   
			   /* SubstituteLesson Controller Section */
				@RequestMapping("/substituteLessonList")
				public String substituteLessonList(Model model) {
				       List<SubstituteLessonInfo> list = substituteLessonDAO.listSubstituteLessonInfos();
				       model.addAttribute("substituteLessonInfos", list);
				       return "substituteLessonList";
				}
				
				private String formSubstituteLesson(Model model, SubstituteLessonInfo substituteLessonInfo) {
				       model.addAttribute("substituteLessonForm", substituteLessonInfo);
				 
//				       Map<String, String> positionMap = this.dataForPositions();
				 
//				       model.addAttribute("positionMap", positionMap);
				 
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
				 
				       this.substituteLessonDAO.saveSubstituteLesson(substituteLessonInfo);
				 
				       // Important!!: Need @EnableWebMvc
				       // Add message to flash scope
				       redirectAttributes.addFlashAttribute("message", "Save SubstituteLesson Successful");
				 
				       return "redirect:/substituteLessonList";
				 
				   }
}