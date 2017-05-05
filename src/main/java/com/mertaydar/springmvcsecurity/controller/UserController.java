package com.mertaydar.springmvcsecurity.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

import com.mertaydar.springmvcsecurity.dao.ActivationDAO;
import com.mertaydar.springmvcsecurity.dao.UserDAO;
import com.mertaydar.springmvcsecurity.dao.UserRoleDAO;
import com.mertaydar.springmvcsecurity.entity.Activation;
import com.mertaydar.springmvcsecurity.model.StudentInfo;
import com.mertaydar.springmvcsecurity.model.UserInfo;
import com.mertaydar.springmvcsecurity.model.UserRoleInfo;

@Controller
//Enable Hibernate Transaction.
@Transactional
//Need To use RedirectAttributes
@EnableWebMvc
public class UserController {
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private UserRoleDAO userRoleDAO;
	
	@Autowired
	private ActivationDAO activationDAO;
	
	@RequestMapping(value = "/saveUser", method = RequestMethod.POST)
	public String saveUser(Model model, //
			@ModelAttribute("userForm") @Validated UserInfo userInfo, //
			BindingResult result, //
			final RedirectAttributes redirectAttributes) {
			
		if (result.hasErrors()) {
			model.addAttribute("signupMsg", "Hatalı giriş!");
			System.out.println("Hata!");
		}
		String decodedToUTF8;
		try {
			decodedToUTF8 = new String(userInfo.getUsername().getBytes("ISO-8859-1"), "UTF-8");
			userInfo.setUsername(decodedToUTF8);
		} catch (UnsupportedEncodingException e) {
			System.out.println("Username cannot converted.");
			e.printStackTrace();
		}
		if (!userInfo.getPassword().equals(userInfo.getPasswordConf())) {
			model.addAttribute("signupMsg", "Parola eşleşmedi.");
			System.out.println("Parola eşleşmedi.");
			return "loginPage";
		}
		if (this.userDAO.findLoginUser(userInfo.getUsername()) != null) {
			model.addAttribute("signupMsg", "Kullanıcı mevcut.");
			System.out.println("Kullanıcı mevcut.");
			return "loginPage";
		}
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		userInfo.setPassword(encoder.encode(userInfo.getPassword()));
		System.out.println(userInfo.getUsername()+userInfo.getPassword()+userInfo.getEmail());
		this.userDAO.saveUser(userInfo);

		// Important!!: Need @EnableWebMvc
		// Add message to flash scope
		redirectAttributes.addFlashAttribute("signupMsg", "Hesabınızı mailize gelen aktivasyon linki ile aktive edin.");

//		return "redirect:/deptList";
		return "redirect:/login";
	}
	
	@RequestMapping(value = "/activate", method = RequestMethod.GET)
	public String activate(Model model, @RequestParam String code,	final RedirectAttributes redirectAttributes) {
		Activation act = activationDAO.findActivationWithCode(code);
		if (act != null) {
			UserInfo user = userDAO.findLoginUserInfo(act.getUsername());
			user.setEnabled(true);
			userDAO.saveUser(user);
			System.out.println(act.getId()+act.getUsername());
			activationDAO.deleteActivation(act.getId());
			userRoleDAO.saveUserRole(new UserRoleInfo(null, user, "USER"));
			redirectAttributes.addFlashAttribute("signupMsg", "Hesabınız aktif edildi.");
			return "redirect:/login";
		}
		else {
			return "redirect:/403";
		}
	}
	
	@RequestMapping(value = "/listUser", method = RequestMethod.GET)
	public String listUser(Model model, @RequestParam Integer pageid, @RequestParam(value = "searchTerm", required = false) String search, @RequestParam(value = "category", required = false) String category) {
		int total = 2;  
        if (pageid == 1){
        	
        }  
        else {  
            pageid = (pageid-1)*total+1;  
        }
		List<UserInfo> users = null;
        if (search == null || search.isEmpty()) {
        	users = userDAO.listUserInfos(pageid,total);
        }
        else {
        	String decodedToUTF8;
    		try {
    			decodedToUTF8 = new String(search.getBytes("ISO-8859-1"), "UTF-8");
    			search = (decodedToUTF8);
    		} catch (UnsupportedEncodingException e) {
    			System.out.println("Search term name cannot converted.");
    			e.printStackTrace();
    		}
        	users = userDAO.listUserInfosBySearch(pageid,total,search,category);
        }
		Integer pageSize = userDAO.listUserInfos().size();
		pageSize = (int) Math.ceil(pageSize / (float)total);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("users", users);
		return "listUser";
	}
	
	@RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
	public String deleteUser(Model model, @RequestParam(value = "id") Integer id, final RedirectAttributes redirectAttributes) {
		if (id == null) {
			return "listUser";
		}
		this.userDAO.deleteUser(id);
		redirectAttributes.addFlashAttribute("message", "Kullanıcı silindi.");
		return "redirect:/listUser?pageid=1";
	}
	
	@RequestMapping(value = "/banUser", method = RequestMethod.GET)
	public String banUser(Model model, @RequestParam(value = "id") Integer id, final RedirectAttributes redirectAttributes) {
		if (id == null) {
			return "listUser";
		}
		UserInfo userInfo = this.userDAO.findUserInfo(id);
		if (userInfo.isEnabled()) {
			userInfo.setEnabled(false);
			redirectAttributes.addFlashAttribute("message", "Kullanıcı yasaklandı.");
		}else {
			userInfo.setEnabled(true);
			redirectAttributes.addFlashAttribute("message", "Kullanıcı yasağı kaldırıldı.");
		}
		this.userDAO.saveUser(userInfo);
		
		return "redirect:/listUser?pageid=1";
	}
}
