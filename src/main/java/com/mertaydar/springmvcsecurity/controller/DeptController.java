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

import com.mertaydar.springmvcsecurity.dao.DeptDAO;
import com.mertaydar.springmvcsecurity.dao.UniDAO;
import com.mertaydar.springmvcsecurity.model.DeptInfo;
import com.mertaydar.springmvcsecurity.model.TakingLessonInfo;
import com.mertaydar.springmvcsecurity.model.UniInfo;

@Controller
//Enable Hibernate Transaction.
@Transactional
//Need To use RedirectAttributes
@EnableWebMvc
public class DeptController {
	
	@Autowired
	private UniDAO uniDAO;
	
	@Autowired
	private DeptDAO deptDAO;
	
	/* Dept Controller Section */
	@RequestMapping("/deptList")
	public String deptList(Model model) {
		List<DeptInfo> list = deptDAO.listDeptInfos();
		model.addAttribute("deptInfos", list);
		return "deptList";
	}
	
	@RequestMapping(value="/getDept",method = RequestMethod.GET, produces = "text/plain; charset=UTF-8")
	@ResponseBody
	public String getDept(@RequestParam Integer id) {
		String res = "<option id=-1 value=-1>Bölüm seçin.</option>";
		List<DeptInfo> list = this.deptDAO.listDeptFromUni(id);
		for (DeptInfo tmp : list) {
			res = res.concat("<option "+"id="+tmp.getId()+" value="+tmp.getId()+">"+tmp.getName()+"</option>");
		}
		return res;
	}
	
	@RequestMapping(value="/getDepts",method = RequestMethod.GET, produces = "text/plain; charset=UTF-8")
	@ResponseBody
	public String getDepts(@RequestParam Integer id) {
		String res = "";
		List<DeptInfo> list = this.deptDAO.listDeptFromUni(id);
		for (DeptInfo tmp : list) {
			res = res.concat("<tr><td>"+tmp.getId()+"</td><td>"+tmp.getName()+"</td><td><a href=\"editDept?id="+tmp.getId()+"\" class=\"btn btn-danger btn-xs\"><span class=\"glyphicon glyphicon-remove\"></span> Değiştir</a><a href=\"deleteDept?id="+tmp.getId()+"\" class=\"btn btn-danger btn-xs\"><span class=\"glyphicon glyphicon-remove\"></span> Sil</a></td></tr>");
		}
		return res;
	}

	@SuppressWarnings("unused")
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
	
	private String formDept(Model model, DeptInfo deptInfo) {
		model.addAttribute("deptForm", deptInfo);

		Map<Integer, String> uniMap = this.uniList();

		model.addAttribute("uniMap", uniMap);

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
	public String deleteDept(Model model, @RequestParam("id") Integer id, final RedirectAttributes redirectAttributes) {
		if (id != null) {
			DeptInfo dep = deptDAO.findDeptInfo(id);
			this.deptDAO.deleteDept(id);
			redirectAttributes.addFlashAttribute("message2", "Bölüm silindi.");
			return "redirect:/addRules?id="+dep.getUniId()+"&id2="+dep.getId()+"#deptTab";
		}
		return "redirect:/addRules#deptTab";
	}

	@RequestMapping(value = "/saveDept", method = RequestMethod.POST)
	public String saveDept(Model model, //
			@ModelAttribute("deptForm") @Validated DeptInfo deptInfo, //
			BindingResult result, //
			final RedirectAttributes redirectAttributes) {


		if (result.hasErrors()) {
			return this.formDept(model, deptInfo);
		}
		
		String decodedToUTF8;
		try {
			decodedToUTF8 = new String(deptInfo.getName().getBytes("ISO-8859-1"), "UTF-8");
			deptInfo.setName(decodedToUTF8);
		} catch (UnsupportedEncodingException e) {
			System.out.println("Dept name cannot converted.");
			e.printStackTrace();
		}

		this.deptDAO.saveDept(deptInfo);

		// Important!!: Need @EnableWebMvc
		// Add message to flash scope
		redirectAttributes.addFlashAttribute("message2", "Bölüm eklendi.");

//		return "redirect:/deptList";
		return "redirect:/addRules?id="+deptInfo.getUniId()+"#deptTab";
	}
}
