package com.mertaydar.springmvcsecurity.controller;

import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.List;
import java.util.Random;

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
import com.mertaydar.springmvcsecurity.dao.MailSend;
import com.mertaydar.springmvcsecurity.dao.PassactivationDAO;
import com.mertaydar.springmvcsecurity.dao.StudentDAO;
import com.mertaydar.springmvcsecurity.dao.UserDAO;
import com.mertaydar.springmvcsecurity.dao.UserRoleDAO;
import com.mertaydar.springmvcsecurity.entity.Activation;
import com.mertaydar.springmvcsecurity.model.StudentInfo;
import com.mertaydar.springmvcsecurity.model.UserInfo;
import com.mertaydar.springmvcsecurity.model.UserRoleInfo;

import com.mertaydar.springmvcsecurity.entity.Passactivation;

@Controller
//Enable Hibernate Transaction.
@Transactional
//Need To use RedirectAttributes
@EnableWebMvc
public class UserController {
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private StudentDAO studentDAO;
	
	@Autowired
	private UserRoleDAO userRoleDAO;
	
	@Autowired
	private PassactivationDAO passactivationDAO;
	
	@Autowired
	private ActivationDAO activationDAO;
	
	@Autowired
	private MailSend mailSend;
	
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
		if (userInfo.getTel() == null) {
			model.addAttribute("signupMsg", "Telefon girilmedi.");
			System.out.println("Telefon girilmedi.");
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
	
	@RequestMapping(value = "/changePass", method = RequestMethod.POST)
	public String changePass(Model model, Principal principal, @ModelAttribute("userForm") @Validated UserInfo userInfo,final RedirectAttributes redirectAttributes) {
		if(userInfo.getPassword().equals(userInfo.getPasswordConf())) {
			System.out.println("Pass equals");
			UserInfo user = userDAO.findLoginUserInfo(principal.getName());
			PasswordEncoder encoder = new BCryptPasswordEncoder();
			user.setPassword(encoder.encode(userInfo.getPassword()));
			userDAO.saveUser(user);
			redirectAttributes.addFlashAttribute("msg", "Şifreniz değiştirildi.");
			return "redirect:/userInfo";
		}
		redirectAttributes.addFlashAttribute("msg", "Şifre eşleşmedi. Girdileri kontrol edin");
		return "redirect:/welcome";
	}
	
	@RequestMapping(value = "/forgotPass", method = RequestMethod.POST)
	public String forgotPass(Model model, @ModelAttribute ("userForm") @Validated UserInfo userInfo,	final RedirectAttributes redirectAttributes) {
		if (userDAO.findLoginUserInfoWithEmail(userInfo.getEmail()) == null) {
			redirectAttributes.addFlashAttribute("msg", "E-mail bulunamadı.");
			return "redirect:/login";
		}
		else {
			String code = getSaltString();
			passactivationDAO.savePassactivation(new Passactivation(null, userDAO.findLoginUserInfoWithEmail(userInfo.getEmail()).getId(), code ));
			mailSend.sendSimpleMessage(userInfo.getEmail(), "Şifre yenileme", "http://localhost:8080/AMS/repass?code="+code);
			redirectAttributes.addFlashAttribute("signupMsg", "Şifre yenileme linki yollandı.");;
			return "redirect:/login";
		}
	}
	
	@RequestMapping(value = "/repass", method = RequestMethod.GET)
	public String repass(Model model, @RequestParam String code,	final RedirectAttributes redirectAttributes) {
		Passactivation act = passactivationDAO.findPassactivationWithCode(code);
		if (act != null) {
			System.out.println("act uid:"+act.getUserId());
			UserInfo user = userDAO.findUserInfo(act.getUserId());
			System.out.println(user.getUsername());
			model.addAttribute("user", user);
			model.addAttribute("signupMsgSuccess", "Yeni şifrenizi  giriniz.");
			return "newPass";
		}
		else {
			return "redirect:/403";
		}
	}
	
	@RequestMapping(value = "/refreshPass", method = RequestMethod.POST)
	public String refreshPass(Model model, Principal principal, @ModelAttribute("userForm") @Validated UserInfo userInfo,final RedirectAttributes redirectAttributes) {
		if(userInfo.getPassword().equals(userInfo.getPasswordConf())) {
			System.out.println("Pass equals");
			UserInfo user = userDAO.findUserInfo(userInfo.getId());
			PasswordEncoder encoder = new BCryptPasswordEncoder();
			user.setPassword(encoder.encode(userInfo.getPassword()));
			userDAO.saveUser(user);
			redirectAttributes.addFlashAttribute("signupMsg", "Şifreniz değiştirildi.");
			passactivationDAO.deletePassactivationWithUser(userInfo.getId());
			return "redirect:/login";
		}
		redirectAttributes.addFlashAttribute("signupMsg", "Şifreniz değiştirilemedi.");
		return "redirect:/login";
	}
	
	protected String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

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
			if (userRoleDAO.listUserRoleInfos().size() == 0){
				userRoleDAO.saveUserRole(new UserRoleInfo(null, user.getId(), "ADMIN"));
			}
			else {
//				userRoleDAO.saveUserRole(new UserRoleInfo(null, user.getId(), "USER"));	
			}
			redirectAttributes.addFlashAttribute("signupMsg", "Hesabınız aktif edildi.");
			return "redirect:/login";
		}
		else {
			return "redirect:/403";
		}
	}
	
	@RequestMapping(value = "/listUser", method = RequestMethod.GET)
	public String listUser(Model model, Principal principal, @RequestParam Integer pageid, @RequestParam(value = "searchTerm", required = false) String search, @RequestParam(value = "category", required = false) String category) {
		int total = 10;  
		Integer pageSize;
        if (pageid == 1){
        	
        }  
        else {  
            pageid = (pageid-1)*total+1;  
        }
        UserInfo user = userDAO.findLoginUserInfo(principal.getName());
        model.addAttribute("user", user);
		List<UserInfo> users = null;
        if (search == null || search.isEmpty()) {
        	users = userDAO.listUserInfosRoleUser(pageid,total);
        	pageSize = userDAO.listUserInfosRoleUser().size();
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
        	users = userDAO.listUserInfosBySearchRoleUser(pageid,total,search,category);
        	pageSize = userDAO.listUserInfosBySearchSizeRoleUser(search, category).size();
        }
		pageSize = (int) Math.ceil(pageSize / (float)total);
		model.addAttribute("pageSize", pageSize);
		for (UserInfo u : users) {
			if (userRoleDAO.isManager(u.getId())) {
				u.setManager(true);
			}
			else {
				u.setManager(false);
			}
		}
		model.addAttribute("users", users);
		return "listUser";
	}
	
	@RequestMapping(value = "/listNewUser", method = RequestMethod.GET)
	public String listNewUser(Model model, Principal principal, @RequestParam Integer pageid, @RequestParam(value = "searchTerm", required = false) String search, @RequestParam(value = "category", required = false) String category) {
		int total = 10;  
		Integer pageSize;
        if (pageid == 1){
        	
        }  
        else {  
            pageid = (pageid-1)*total+1;  
        }
        UserInfo user = userDAO.findLoginUserInfo(principal.getName());
        model.addAttribute("user", user);
		List<UserInfo> users = null;
        if (search == null || search.isEmpty()) {
        	users = userDAO.listUserInfosRoleNewUser(pageid,total);
        	pageSize = userDAO.listUserInfosRoleNewUser().size();
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
        	users = userDAO.listUserInfosBySearchRoleNewUser(pageid,total,search,category);
        	pageSize = userDAO.listUserInfosBySearchSizeRoleNewUser(search, category).size();
        }
		pageSize = (int) Math.ceil(pageSize / (float)total);
		model.addAttribute("pageSize", pageSize);
		for (UserInfo u : users) {
			if (userRoleDAO.isManager(u.getId())) {
				u.setManager(true);
			}
			else {
				u.setManager(false);
			}
		}
		model.addAttribute("users", users);
		return "listNewUser";
	}
	
	@RequestMapping(value = "/listManager", method = RequestMethod.GET)
	public String listManager(Model model, Principal principal, @RequestParam Integer pageid, @RequestParam(value = "searchTerm", required = false) String search, @RequestParam(value = "category", required = false) String category) {
		int total = 10;  
		Integer pageSize;
        if (pageid == 1){
        	
        }  
        else {  
            pageid = (pageid-1)*total+1;  
        }
        UserInfo user = userDAO.findLoginUserInfo(principal.getName());
        model.addAttribute("user", user);
		List<UserInfo> users = null;
        if (search == null || search.isEmpty()) {
        	users = userDAO.listUserInfosRoleManager(pageid,total);
        	pageSize = userDAO.listUserInfosRoleManager().size();
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
        	users = userDAO.listUserInfosBySearchRoleManager(pageid,total,search,category);
        	pageSize = userDAO.listUserInfosBySearchSizeRoleManager(search, category).size();
        }
		pageSize = (int) Math.ceil(pageSize / (float)total);
		model.addAttribute("pageSize", pageSize);
		for (UserInfo u : users) {
			if (userRoleDAO.isManager(u.getId())) {
				u.setManager(true);
				u.setTotalWeight(studentDAO.listStudentInfosForAdvSize(u.getId()).size());
			}
			else {
				u.setManager(false);
			}
		}
		model.addAttribute("users", users);
		return "listManager";
	}
	
	@RequestMapping(value = "/listAdmin", method = RequestMethod.GET)
	public String listAdmin(Model model, Principal principal, @RequestParam Integer pageid, @RequestParam(value = "searchTerm", required = false) String search, @RequestParam(value = "category", required = false) String category) {
		int total = 10;  
		Integer pageSize;
        if (pageid == 1){
        	
        }  
        else {  
            pageid = (pageid-1)*total+1;  
        }
        UserInfo user = userDAO.findLoginUserInfo(principal.getName());
        model.addAttribute("user", user);
		List<UserInfo> users = null;
        if (search == null || search.isEmpty()) {
        	users = userDAO.listUserInfosRoleAdmin(pageid,total);
        	pageSize = userDAO.listUserInfosRoleAdmin().size();
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
        	users = userDAO.listUserInfosBySearchRoleAdmin(pageid,total,search,category);
        	pageSize = userDAO.listUserInfosBySearchSizeRoleAdmin(search, category).size();
        }
		pageSize = (int) Math.ceil(pageSize / (float)total);
		model.addAttribute("pageSize", pageSize);
		for (UserInfo u : users) {
			if (userRoleDAO.isManager(u.getId())) {
				u.setManager(true);
			}
			else {
				u.setManager(false);
			}
		}
		model.addAttribute("users", users);
		return "listAdmin";
	}
	
	@RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
	public String deleteUser(Model model, @RequestParam(value = "id") Integer id, final RedirectAttributes redirectAttributes) {
		if (id == null) {
			return "listUser";
		}
		UserInfo user = userDAO.findUserInfo(id);
		List<String> roles = userRoleDAO.getUserRoles(user.getId());
		this.userDAO.deleteUser(id);
		redirectAttributes.addFlashAttribute("message", "Kullanıcı silindi.");
		for (String r : roles) {
			if (r.equals("ADMIN")){
				return "redirect:/listAdmin?pageid=1";
			}
			if (r.equals("MANAGER")){
				return "redirect:/listManager?pageid=1";
			}
			if (r.equals("USER")){
				return "redirect:/listUser?pageid=1";
			}
		}
		return "redirect:/listNewUser?pageid=1";
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
	
	@RequestMapping(value = "/setManager", method = RequestMethod.GET)
	public String setManager(Model model, @RequestParam(value = "id") Integer id, final RedirectAttributes redirectAttributes) {
		if (id == null) {
			return "listUser";
		}
		UserInfo userInfo = this.userDAO.findUserInfo(id);
		if (!userRoleDAO.getUserRoles(userInfo.getId()).contains("MANAGER")) {
			userRoleDAO.saveUserRole(new UserRoleInfo(null, userInfo.getId(), "MANAGER"));
			List<UserRoleInfo> list = userRoleDAO.listUserRoleInfos();
			for (UserRoleInfo l : list) {
				if (l.getUserId() == userInfo.getId() && l.getRole().equals("USER")) {
					userRoleDAO.deleteUserRole(l.getId());
				}
				if (l.getUserId() == userInfo.getId() && l.getRole().equals("ADMIN")) {
					userRoleDAO.deleteUserRole(l.getId());
				}
			}
			redirectAttributes.addFlashAttribute("message", "İntibak görevlisi olarak atandı.");
			return "redirect:/listManager?pageid=1";
		}else {
			List<UserRoleInfo> list = userRoleDAO.listUserRoleInfos();
			for (UserRoleInfo l : list) {
				if (l.getUserId() == userInfo.getId() && l.getRole().equals("MANAGER")) {
					userRoleDAO.deleteUserRole(l.getId());
				}
			}
//			userRoleDAO.saveUserRole(new UserRoleInfo(null, userInfo.getId(), "ADMIN"));
			redirectAttributes.addFlashAttribute("message", "İntibak görevi geri alındı.");
		}
		this.userDAO.saveUser(userInfo);
		
		return "redirect:/listNewUser?pageid=1";
	}
}
