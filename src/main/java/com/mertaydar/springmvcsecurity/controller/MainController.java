package com.mertaydar.springmvcsecurity.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.mertaydar.springmvcsecurity.dao.CurriculumDAO;
import com.mertaydar.springmvcsecurity.dao.DeptDAO;
import com.mertaydar.springmvcsecurity.dao.StudentDAO;
import com.mertaydar.springmvcsecurity.dao.StudentLessonDAO;
import com.mertaydar.springmvcsecurity.dao.SubstituteLessonDAO;
import com.mertaydar.springmvcsecurity.dao.UniDAO;
import com.mertaydar.springmvcsecurity.dao.UserDAO;
import com.mertaydar.springmvcsecurity.model.CurriculumInfo;
import com.mertaydar.springmvcsecurity.model.DeptInfo;
import com.mertaydar.springmvcsecurity.model.StudentInfo;
import com.mertaydar.springmvcsecurity.model.StudentLessonInfo;
import com.mertaydar.springmvcsecurity.model.SubstituteLessonInfo;
import com.mertaydar.springmvcsecurity.model.UniInfo;
import com.mertaydar.springmvcsecurity.model.UserInfo;

@Controller
//Enable Hibernate Transaction.
@Transactional
//Need To use RedirectAttributes
@EnableWebMvc
public class MainController {

	/*	@Autowired
	private UniValidator uniValidator;*/
	
	@Autowired
	private StudentDAO studentDAO;

	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private UniDAO uniDAO;
	
	@Autowired
	private DeptDAO deptDAO;
	
	@Autowired
	private StudentLessonDAO studentLessonDAO;
	
	@Autowired
	private SubstituteLessonDAO substituteLessonDAO;
	
	@Autowired
	private CurriculumDAO curriculumDAO;
	
	@RequestMapping(value = { "/", "/welcome" }, method = RequestMethod.GET)
	public String welcomePage(Model model) {
		model.addAttribute("title", "İntibak Yönetim Sistemi");
		List<UserInfo> users = userDAO.listUserInfosRoleManager();
		String res1 = "";
		String res2 = "";
		String res3 = "";
		int counter = 0, total = 0;
		int i = 0;
		for (UserInfo u : users) {
			if (i == 0) {
				res1 = res1.concat("\""+u.getUsername()+"\"");
				res2 = res2.concat("\""+studentDAO.listStudentInfosForAdvSize(u.getId()).size()+"\"");
				res3 = res3.concat("\""+randColor()+"\"");
				counter+=studentDAO.listStudentInfosForAdvSize(u.getId()).size();
				i++;
			}
			else {
				res1 = res1.concat(", \""+u.getUsername()+"\"");
				res2 = res2.concat(", \""+studentDAO.listStudentInfosForAdvSize(u.getId()).size()+"\"");
				res3 = res3.concat(", \""+randColor()+"\"");
				counter+=studentDAO.listStudentInfosForAdvSize(u.getId()).size();
				i++;
			}
			
		}
		total = studentDAO.listStudentInfos().size();
		res1 = res1.concat(", \"Boşta\"");
		total = total - counter;
		res2 = res2.concat(", \""+total+"\"");
		res3 = res3.concat(", \""+randColor()+"\"");
		model.addAttribute("users", res1);
		model.addAttribute("colors", res3);
		model.addAttribute("numbers", res2);
		return "welcomePage";
	}
	
	public String randColor() {
		// create random object - reuse this as often as possible
        Random random = new Random();

        // create a big random number - maximum is ffffff (hex) = 16777215 (dez)
        int nextInt = random.nextInt(256*256*256);

        // format it as hexadecimal string (with hashtag and leading zeros)
        String colorCode = String.format("#%06x", nextInt);

        // print it
        return colorCode;
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
	
	@RequestMapping(value = "/word", method = RequestMethod.GET)
	public String word(Model model, Principal principal, @RequestParam Integer id) throws InvalidFormatException, IOException {
		if (id == null) {
			System.out.println("Id is null");
			return "welcome";
		}
		StudentInfo stuInfo = studentDAO.findStudentInfo(id);
		XWPFDocument doc2 = new XWPFDocument(OPCPackage.open("D:\\input.docx"));
		doc2.write(new FileOutputStream(new File("D:\\copy.docx")));
		doc2.close();
		XWPFDocument doc = new XWPFDocument(OPCPackage.open("D:\\copy.docx"));
	/*	for (XWPFParagraph p : doc.getParagraphs()) {
		    List<XWPFRun> runs = p.getRuns();
		    if (runs != null) {
		        for (XWPFRun r : runs) {
		            String text = r.getText(0);
		            if (text != null && text.contains("needle")) {
		                text = text.replace("needle", "haystack");
		                r.setText(text, 0);
		            }
		        }
		    }
		}*/
		List<StudentLessonInfo> list = studentLessonDAO.listStudentLessonInfosForStudentNoDuplicate(stuInfo.getId());
		List<SubstituteLessonInfo> responsible = new ArrayList<SubstituteLessonInfo>();
		CurriculumInfo act = curriculumDAO.findCurriculumInfoActive();
		List<SubstituteLessonInfo> all = substituteLessonDAO.listSubstituteLessonInfos(act.getId());
		for (SubstituteLessonInfo a : all) {
			boolean exist = false;
			for (StudentLessonInfo l : list) {
				if (l.getSubstituteLessonId() == a.getId()) {
					exist=true;
					break;
				}
			}
			if (!exist && (a.getBase() == null || !a.getBase().contains("H"))) {
				responsible.add(a);
			}
		}
		String rType="";
		List<Integer> processed = new ArrayList<Integer>();
		HashMap<Integer, String> hmap = new HashMap<Integer, String>();
		// Mesleki seçimlik 1
		for (SubstituteLessonInfo r : responsible) {
			System.out.println("Resp:"+r.getName());
			if (r.getName().equals("Mesleki Seçimlik 1 - 1")) {
				rType = r.getBase();
			}
			System.out.println("rType:"+rType);
			boolean isSet = false;
			for (int i=1; i<5; i++) {
				System.out.println("r getname: "+r.getName());
				if (r.getName().equals("Mesleki Seçimlik 1 - "+i)) {
					for(StudentLessonInfo l : list) {
						SubstituteLessonInfo sub = substituteLessonDAO.findSubstituteLessonInfo(l.getSubstituteLessonId());
						System.out.println("aAa:"+sub.getBase()+" "+rType);
						if (sub.getBase() != null && !processed.contains(sub.getId()) && sub.getBase().equals(rType+"H")) {
							System.out.println("MEsleki set 1 - "+i);
							processed.add(sub.getId());
							r.setAkts(sub.getAkts());
							r.setBase(sub.getBase());
							r.setCode(sub.getCode());
							r.setConditionId(sub.getConditionId());
							r.setCredit(sub.getCredit());
							r.setCurriculumId(sub.getCurriculumId());
							r.setId(sub.getId());
							r.setLab(sub.getLab());
							r.setLang(sub.getLang());
							r.setName(sub.getName());
							hmap.put(r.getId(), l.getConvMark());
							isSet = true;
						}
						if (isSet){
							break;
						}
					}
				}
				if (isSet){
					break;
				}
			}
		}
		// Mesleki Seçimlik 2
		for (SubstituteLessonInfo r : responsible) {
			System.out.println("Resp:"+r.getName());
			if (r.getName().equals("Mesleki Seçimlik 2 - 1")) {
				rType = r.getBase();
			}
			System.out.println("rType:"+rType);
			boolean isSet = false;
			for (int i=1; i<7; i++) {
				System.out.println("r getname: "+r.getName());
				if (r.getName().equals("Mesleki Seçimlik 2 - "+i)) {
					for(StudentLessonInfo l : list) {
						SubstituteLessonInfo sub = substituteLessonDAO.findSubstituteLessonInfo(l.getSubstituteLessonId());
						System.out.println("aAa:"+sub.getBase()+" "+rType);
						if (sub.getBase() != null && !processed.contains(sub.getId()) && sub.getBase().equals(rType+"H")) {
							System.out.println("Mesleki set 2 - "+i);
							processed.add(sub.getId());
							r.setAkts(sub.getAkts());
							r.setBase(sub.getBase());
							r.setCode(sub.getCode());
							r.setConditionId(sub.getConditionId());
							r.setCredit(sub.getCredit());
							r.setCurriculumId(sub.getCurriculumId());
							r.setId(sub.getId());
							r.setLab(sub.getLab());
							r.setLang(sub.getLang());
							r.setName(sub.getName());
							hmap.put(r.getId(), l.getConvMark());
							isSet = true;
						}
						if (isSet){
							break;
						}
					}
				}
				if (isSet){
					break;
				}
			}
		}
		// Seçimlik 1
		for (SubstituteLessonInfo r : responsible) {
			System.out.println("Resp:"+r.getName());
			if (r.getName().equals("Seçmeli 1 - 1")) {
				rType = r.getBase();
			}
			System.out.println("rType:"+rType);
			boolean isSet = false;
			for (int i=1; i<2; i++) {
				System.out.println("r getname: "+r.getName());
				if (r.getName().equals("Seçmeli 1 - "+i)) {
					for(StudentLessonInfo l : list) {
						SubstituteLessonInfo sub = substituteLessonDAO.findSubstituteLessonInfo(l.getSubstituteLessonId());
						System.out.println("aAa:"+sub.getBase()+" "+rType);
						if (sub.getBase() != null && !processed.contains(sub.getId()) && sub.getBase().equals(rType+"H")) {
							System.out.println("Seçmeli 1 - "+i);
							processed.add(sub.getId());
							r.setAkts(sub.getAkts());
							r.setBase(sub.getBase());
							r.setCode(sub.getCode());
							r.setConditionId(sub.getConditionId());
							r.setCredit(sub.getCredit());
							r.setCurriculumId(sub.getCurriculumId());
							r.setId(sub.getId());
							r.setLab(sub.getLab());
							r.setLang(sub.getLang());
							r.setName(sub.getName());
							hmap.put(r.getId(), l.getConvMark());
							isSet = true;
						}
						if (isSet){
							break;
						}
					}
				}
				if (isSet){
					break;
				}
			}
		}
		// Seçimlik 2
		for (SubstituteLessonInfo r : responsible) {
			System.out.println("Resp:"+r.getName());
			if (r.getName().equals("Seçmeli 2 - 1")) {
				rType = r.getBase();
			}
			System.out.println("rType:"+rType);
			boolean isSet = false;
			for (int i=1; i<2; i++) {
				System.out.println("r getname: "+r.getName());
				if (r.getName().equals("Seçmeli 2 - "+i)) {
					for(StudentLessonInfo l : list) {
						SubstituteLessonInfo sub = substituteLessonDAO.findSubstituteLessonInfo(l.getSubstituteLessonId());
						System.out.println("aAa:"+sub.getBase()+" "+rType);
						if (sub.getBase() != null && !processed.contains(sub.getId()) && sub.getBase().equals(rType+"H")) {
							System.out.println("Seçmeli 2 - "+i);
							processed.add(sub.getId());
							r.setAkts(sub.getAkts());
							r.setBase(sub.getBase());
							r.setCode(sub.getCode());
							r.setConditionId(sub.getConditionId());
							r.setCredit(sub.getCredit());
							r.setCurriculumId(sub.getCurriculumId());
							r.setId(sub.getId());
							r.setLab(sub.getLab());
							r.setLang(sub.getLang());
							r.setName(sub.getName());
							hmap.put(r.getId(), l.getConvMark());
							isSet = true;
						}
						if (isSet){
							break;
						}
					}
				}
				if (isSet){
					break;
				}
			}
		}
		// Seçimlik 3
		for (SubstituteLessonInfo r : responsible) {
			System.out.println("Resp:"+r.getName());
			if (r.getName().equals("Seçmeli 3 - 1")) {
				rType = r.getBase();
			}
			System.out.println("rType:"+rType);
			boolean isSet = false;
			for (int i=1; i<2; i++) {
				System.out.println("r getname: "+r.getName());
				if (r.getName().equals("Seçmeli 3 - "+i)) {
					for(StudentLessonInfo l : list) {
						SubstituteLessonInfo sub = substituteLessonDAO.findSubstituteLessonInfo(l.getSubstituteLessonId());
						System.out.println("aAa:"+sub.getBase()+" "+rType);
						if (sub.getBase() != null && !processed.contains(sub.getId()) && sub.getBase().equals(rType+"H")) {
							System.out.println("Seçmeli 3 - "+i);
							processed.add(sub.getId());
							r.setAkts(sub.getAkts());
							r.setBase(sub.getBase());
							r.setCode(sub.getCode());
							r.setConditionId(sub.getConditionId());
							r.setCredit(sub.getCredit());
							r.setCurriculumId(sub.getCurriculumId());
							r.setId(sub.getId());
							r.setLab(sub.getLab());
							r.setLang(sub.getLang());
							r.setName(sub.getName());
							hmap.put(r.getId(), l.getConvMark());
							isSet = true;
						}
						if (isSet){
							break;
						}
					}
				}
				if (isSet){
					break;
				}
			}
		}
		// Seçimlik 4
		for (SubstituteLessonInfo r : responsible) {
			System.out.println("Resp:"+r.getName());
			if (r.getName().equals("Seçmeli 4 - 1")) {
				rType = r.getBase();
			}
			System.out.println("rType:"+rType);
			boolean isSet = false;
			for (int i=1; i<2; i++) {
				System.out.println("r getname: "+r.getName());
				if (r.getName().equals("Seçmeli 4 - "+i)) {
					for(StudentLessonInfo l : list) {
						SubstituteLessonInfo sub = substituteLessonDAO.findSubstituteLessonInfo(l.getSubstituteLessonId());
						System.out.println("aAa:"+sub.getBase()+" "+rType);
						if (sub.getBase() != null && !processed.contains(sub.getId()) && sub.getBase().equals(rType+"H")) {
							System.out.println("Seçmeli 4 - "+i);
							processed.add(sub.getId());
							r.setAkts(sub.getAkts());
							r.setBase(sub.getBase());
							r.setCode(sub.getCode());
							r.setConditionId(sub.getConditionId());
							r.setCredit(sub.getCredit());
							r.setCurriculumId(sub.getCurriculumId());
							r.setId(sub.getId());
							r.setLab(sub.getLab());
							r.setLang(sub.getLang());
							r.setName(sub.getName());
							hmap.put(r.getId(), l.getConvMark());
							isSet = true;
						}
						if (isSet){
							break;
						}
					}
				}
				if (isSet){
					break;
				}
			}
		}
		// Seçimlik 5
		for (SubstituteLessonInfo r : responsible) {
			System.out.println("Resp:"+r.getName());
			if (r.getName().equals("Seçmeli 5 - 1")) {
				rType = r.getBase();
			}
			System.out.println("rType:"+rType);
			boolean isSet = false;
			for (int i=1; i<2; i++) {
				System.out.println("r getname: "+r.getName());
				if (r.getName().equals("Seçmeli 5 - "+i)) {
					for(StudentLessonInfo l : list) {
						SubstituteLessonInfo sub = substituteLessonDAO.findSubstituteLessonInfo(l.getSubstituteLessonId());
						System.out.println("aAa:"+sub.getBase()+" "+rType);
						if (sub.getBase() != null && !processed.contains(sub.getId()) && sub.getBase().equals(rType+"H")) {
							System.out.println("Seçmeli 5 - "+i);
							processed.add(sub.getId());
							r.setAkts(sub.getAkts());
							r.setBase(sub.getBase());
							r.setCode(sub.getCode());
							r.setConditionId(sub.getConditionId());
							r.setCredit(sub.getCredit());
							r.setCurriculumId(sub.getCurriculumId());
							r.setId(sub.getId());
							r.setLab(sub.getLab());
							r.setLang(sub.getLang());
							r.setName(sub.getName());
							hmap.put(r.getId(), l.getConvMark());
							isSet = true;
						}
						if (isSet){
							break;
						}
					}
				}
				if (isSet){
					break;
				}
			}
		}
		// Seçimlik 6
		for (SubstituteLessonInfo r : responsible) {
			System.out.println("Resp:"+r.getName());
			if (r.getName().equals("Seçmeli 6 - 1")) {
				rType = r.getBase();
			}
			System.out.println("rType:"+rType);
			boolean isSet = false;
			for (int i=1; i<2; i++) {
				System.out.println("r getname: "+r.getName());
				if (r.getName().equals("Seçmeli 6 - "+i)) {
					for(StudentLessonInfo l : list) {
						SubstituteLessonInfo sub = substituteLessonDAO.findSubstituteLessonInfo(l.getSubstituteLessonId());
						System.out.println("aAa:"+sub.getBase()+" "+rType);
						if (sub.getBase() != null && !processed.contains(sub.getId()) && sub.getBase().equals(rType+"H")) {
							System.out.println("Seçmeli 6 - "+i);
							processed.add(sub.getId());
							r.setAkts(sub.getAkts());
							r.setBase(sub.getBase());
							r.setCode(sub.getCode());
							r.setConditionId(sub.getConditionId());
							r.setCredit(sub.getCredit());
							r.setCurriculumId(sub.getCurriculumId());
							r.setId(sub.getId());
							r.setLab(sub.getLab());
							r.setLang(sub.getLang());
							r.setName(sub.getName());
							hmap.put(r.getId(), l.getConvMark());
							isSet = true;
						}
						if (isSet){
							break;
						}
					}
				}
				if (isSet){
					break;
				}
			}
		}
		// Seçimlik 7
		for (SubstituteLessonInfo r : responsible) {
			System.out.println("Resp:"+r.getName());
			if (r.getName().equals("Seçmeli 7 - 1")) {
				rType = r.getBase();
			}
			System.out.println("rType:"+rType);
			boolean isSet = false;
			for (int i=1; i<2; i++) {
				System.out.println("r getname: "+r.getName());
				if (r.getName().equals("Seçmeli 7 - "+i)) {
					for(StudentLessonInfo l : list) {
						SubstituteLessonInfo sub = substituteLessonDAO.findSubstituteLessonInfo(l.getSubstituteLessonId());
						System.out.println("aAa:"+sub.getBase()+" "+rType);
						if (sub.getBase() != null && !processed.contains(sub.getId()) && sub.getBase().equals(rType+"H")) {
							System.out.println("Seçmeli 7 - "+i);
							processed.add(sub.getId());
							r.setAkts(sub.getAkts());
							r.setBase(sub.getBase());
							r.setCode(sub.getCode());
							r.setConditionId(sub.getConditionId());
							r.setCredit(sub.getCredit());
							r.setCurriculumId(sub.getCurriculumId());
							r.setId(sub.getId());
							r.setLab(sub.getLab());
							r.setLang(sub.getLang());
							r.setName(sub.getName());
							hmap.put(r.getId(), l.getConvMark());
							isSet = true;
						}
						if (isSet){
							break;
						}
					}
				}
				if (isSet){
					break;
				}
			}
		}
        System.out.println("List: "+list.size());
		for (XWPFTable tbl : doc.getTables()) {
		   for (XWPFTableRow row : tbl.getRows()) {
		      for (XWPFTableCell cell : row.getTableCells()) {
		         for (XWPFParagraph p : cell.getParagraphs()) {
		            for (XWPFRun r : p.getRuns()) {
		              String text = r.getText(0);
//		              System.out.println(text);
		              if (text != null && text.contains("&date")) {
		            	  SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yy HH:mm:ss");
		            	  Date today = new Date();
		            	  String result = formatter.format(today);
		            	  text = text.replace("&date", result);
		            	  r.setText(text,0);
		              }
		              if (text != null && text.contains("&no")) {
		            	  text = text.replace("&no", stuInfo.getNo());
		            	  r.setText(text,0);
			          }
		              if (text != null && text.contains("&ad")) {
		            	  text = text.replace("&ad", stuInfo.getName());
		            	  r.setText(text,0);
			          }
		              if (text != null && text.contains("&soyad")) {
		            	  text = text.replace("&soyad", stuInfo.getSurname());
		            	  r.setText(text,0);
			          }
		              if (text != null && text.contains("&intibak")) {
		            	  if (stuInfo.getAdpScore() != null) {
		            		  text = text.replace("&intibak", stuInfo.getAdpScore().toString());
		            	  }
		            	  r.setText(text,0);
			          }
		              if (text != null && text.contains("&yil")) {
		            	  text = text.replace("&yil", stuInfo.getRecordYear().toString());
		            	  r.setText(text,0);
			          }
		              if (text != null && text.contains("&universite")) {
		            	  UniInfo uni = uniDAO.findUniInfo(deptDAO.findDeptInfo(stuInfo.getDeptId()).getUniId());
		            	  text = text.replace("&universite", uni.getName());
		            	  r.setText(text,0);
			          }
		              if (text != null && text.contains("&bolum")) {
		            	  DeptInfo dept = deptDAO.findDeptInfo(stuInfo.getDeptId());
		            	  text = text.replace("&bolum", dept.getName());
		            	  r.setText(text,0);
			          }
		              int i;
		              int count1 = 0;
		              int count2 = 0;
		              int count3 = 0;
		              int count4 = 0;
		              int count5 = 0;
		              int count6 = 0;
		              int count7 = 0;
		              int count8 = 0;
		              // passed lesson set
		              for (i = 0 ; i < 8; i++) {
		            	  for(StudentLessonInfo l : list) {
		            		  SubstituteLessonInfo sub = substituteLessonDAO.findSubstituteLessonInfo(l.getSubstituteLessonId());
		            		  int count = 0;
		            		  if (sub.getTerm() == 1) {
		            			  count = count1;
		            		  }else if (sub.getTerm() == 2) {
		            			  count = count2;
		            		  }else if (sub.getTerm() == 3) {
		            			  count = count3;
		            		  }else if (sub.getTerm() == 4) {
		            			  count = count4;
		            		  }else if (sub.getTerm() == 5) {
		            			  count = count5;
		            		  }else if (sub.getTerm() == 6) {
		            			  count = count6;
		            		  }else if (sub.getTerm() == 7) {
		            			  count = count7;
		            		  }else if (sub.getTerm() == 8) {
		            			  count = count8;
		            		  }
		            		  if (!processed.contains(sub.getId()) && sub.getTerm() == i+1) {
		            			  if (text != null && text.contains("&"+(i+1)+""+count+"d")) {
		    		            	  System.out.print(" Text old: "+text);
		            				  text = text.replace("&"+(i+1)+""+count+"d", sub.getName());
		    		            	  System.out.print(" Text new: "+text);
		    		            	  r.setText(text,0);
		    			          }
		            			  if (text != null && text.contains("&"+(i+1)+""+count+"l")) {
		            				  System.out.print(" Text old: "+text);
		    		            	  text = text.replace("&"+(i+1)+""+count+"l", sub.getLang());
		    		            	  System.out.print(" Text new: "+text);
		    		            	  r.setText(text,0);
		    			          }
		            			  if (text != null && text.contains("&"+(i+1)+""+count+"k")) {
		            				  System.out.print(" Text old: "+text);
		    		            	  text = text.replace("&"+(i+1)+""+count+"k", sub.getCredit().toString());
		    		            	  System.out.print(" Text new: "+text);
		    		            	  r.setText(text,0);
		    			          }
		            			  if (text != null && text.contains("&"+(i+1)+""+count+"a")) {
		            				  System.out.print(" Text old: "+text);
		    		            	  text = text.replace("&"+(i+1)+""+count+"a", sub.getAkts().toString());
		    		            	  System.out.print(" Text new: "+text);
		    		            	  r.setText(text,0);
		    			          }
		            			  if (text != null && text.contains("&"+(i+1)+""+count+"n")) {
		            				  System.out.print(" Text old: "+text);
		    		            	  text = text.replace("&"+(i+1)+""+count+"n", l.getConvMark());
		    		            	  System.out.println(" Text new: "+text);
		    		            	  r.setText(text,0);
		    			          }
		            			  if (text != null && text.contains("&"+(i+1)+""+count)) {
		            				  System.out.print(" Text old: "+text);
		    		            	  text = text.replace("&"+(i+1)+""+count, sub.getCode());
		    		            	  System.out.print(" Text new: "+text);
		    		            	  r.setText(text,0);
		    			          }
		            			  count++;
		            		  }
		            		  if (sub.getTerm() == 1) {
		            			  count1 = count;
		            		  }else if (sub.getTerm() == 2) {
		            			  count2 = count;
		            		  }else if (sub.getTerm() == 3) {
		            			  count3 = count;
		            		  }else if (sub.getTerm() == 4) {
		            			  count4 = count;
		            		  }else if (sub.getTerm() == 5) {
		            			  count5 = count;
		            		  }else if (sub.getTerm() == 6) {
		            			  count6 = count;
		            		  }else if (sub.getTerm() == 7) {
		            			  count7 = count;
		            		  }else if (sub.getTerm() == 8) {
		            			  count8 = count;
		            		  }
		            	  }
		              }
		              // not passed set
		              for (i = 0 ; i < 8; i++) {
		            	  for(SubstituteLessonInfo sub : responsible) {
		            		  int count = 0;
		            		  if (sub.getTerm() == 1) {
		            			  count = count1;
		            		  }else if (sub.getTerm() == 2) {
		            			  count = count2;
		            		  }else if (sub.getTerm() == 3) {
		            			  count = count3;
		            		  }else if (sub.getTerm() == 4) {
		            			  count = count4;
		            		  }else if (sub.getTerm() == 5) {
		            			  count = count5;
		            		  }else if (sub.getTerm() == 6) {
		            			  count = count6;
		            		  }else if (sub.getTerm() == 7) {
		            			  count = count7;
		            		  }else if (sub.getTerm() == 8) {
		            			  count = count8;
		            		  }
		            		  if (sub.getTerm() == i+1) {
		            			  if (text != null && text.contains("&"+(i+1)+""+count+"d")) {
		    		            	  System.out.print(" Text old: "+text);
		            				  text = text.replace("&"+(i+1)+""+count+"d", sub.getName());
		    		            	  System.out.print(" Text new: "+text);
		    		            	  r.setText(text,0);
		    			          }
		            			  if (text != null && text.contains("&"+(i+1)+""+count+"l")) {
		            				  System.out.print(" Text old: "+text);
		    		            	  text = text.replace("&"+(i+1)+""+count+"l", sub.getLang());
		    		            	  System.out.print(" Text new: "+text);
		    		            	  r.setText(text,0);
		    			          }
		            			  if (text != null && text.contains("&"+(i+1)+""+count+"k")) {
		            				  System.out.print(" Text old: "+text);
		    		            	  text = text.replace("&"+(i+1)+""+count+"k", sub.getCredit().toString());
		    		            	  System.out.print(" Text new: "+text);
		    		            	  r.setText(text,0);
		    			          }
		            			  if (text != null && text.contains("&"+(i+1)+""+count+"a")) {
		            				  System.out.print(" Text old: "+text);
		    		            	  text = text.replace("&"+(i+1)+""+count+"a", sub.getAkts().toString());
		    		            	  System.out.print(" Text new: "+text);
		    		            	  r.setText(text,0);
		    			          }
		            			  if (text != null && text.contains("&"+(i+1)+""+count+"n")) {
		            				  System.out.print(" Text old: "+text);
		            				  if (hmap.get(sub.getId()) != null){
		            					  text = text.replace("&"+(i+1)+""+count+"n", hmap.get(sub.getId()));
		            				  }else {
		            					  text = text.replace("&"+(i+1)+""+count+"n", "Sorumlu");
		            				  }
		    		            	  
		    		            	  System.out.println(" Text new: "+text);
		    		            	  r.setText(text,0);
		    			          }
		            			  if (text != null && text.contains("&"+(i+1)+""+count)) {
		            				  System.out.print(" Text old: "+text);
		    		            	  text = text.replace("&"+(i+1)+""+count, sub.getCode());
		    		            	  System.out.print(" Text new: "+text);
		    		            	  r.setText(text,0);
		    			          }
		            			  count++;
		            		  }
		            		  if (sub.getTerm() == 1) {
		            			  count1 = count;
		            		  }else if (sub.getTerm() == 2) {
		            			  count2 = count;
		            		  }else if (sub.getTerm() == 3) {
		            			  count3 = count;
		            		  }else if (sub.getTerm() == 4) {
		            			  count4 = count;
		            		  }else if (sub.getTerm() == 5) {
		            			  count5 = count;
		            		  }else if (sub.getTerm() == 6) {
		            			  count6 = count;
		            		  }else if (sub.getTerm() == 7) {
		            			  count7 = count;
		            		  }else if (sub.getTerm() == 8) {
		            			  count8 = count;
		            		  }
		            	  }
		              }
		              // delete blanks
		              for (i = 0 ; i < 8; i++) {
		            	  for (int j = 0; j < 8; j++) {
		            			  if (text != null && text.contains("&"+(i+1)+""+j+"d")) {
		    		            	  System.out.print(" Text old: "+text);
		            				  text = text.replace("&"+(i+1)+""+j+"d", "");
		    		            	  System.out.print(" Text new: "+text);
		    		            	  r.setText(text,0);
		    			          }
		            			  if (text != null && text.contains("&"+(i+1)+""+j+"l")) {
		            				  System.out.print(" Text old: "+text);
		    		            	  text = text.replace("&"+(i+1)+""+j+"l", "");
		    		            	  System.out.print(" Text new: "+text);
		    		            	  r.setText(text,0);
		    			          }
		            			  if (text != null && text.contains("&"+(i+1)+""+j+"k")) {
		            				  System.out.print(" Text old: "+text);
		    		            	  text = text.replace("&"+(i+1)+""+j+"k", "");
		    		            	  System.out.print(" Text new: "+text);
		    		            	  r.setText(text,0);
		    			          }
		            			  if (text != null && text.contains("&"+(i+1)+""+j+"a")) {
		            				  System.out.print(" Text old: "+text);
		    		            	  text = text.replace("&"+(i+1)+""+j+"a", "");
		    		            	  System.out.print(" Text new: "+text);
		    		            	  r.setText(text,0);
		    			          }
		            			  if (text != null && text.contains("&"+(i+1)+""+j+"n")) {
		            				  System.out.print("Text old: "+text);
		    		            	  text = text.replace("&"+(i+1)+""+j+"n", "");
		    		            	  System.out.println(" Text new: "+text);
		    		            	  r.setText(text,0);
		    			          }
		            			  if (text != null && text.contains("&"+(i+1)+""+j)) {
		            				  System.out.print(" Text old: "+text);
		    		            	  text = text.replace("&"+(i+1)+""+j, "");
		    		            	  System.out.print(" Text new: "+text);
		    		            	  r.setText(text,0);
		    			          }
		            	  }
		              }
		            }
		         }
		      }
		   }
		}
		doc.write(new FileOutputStream(new File("D:\\output.docx")));
		doc.close();
//		FileInputStream in=new FileInputStream("D:\\output.docx");
//		  XWPFDocument document=new XWPFDocument(in);
//		  File outFile=new File("D:\\output.pdf");
//		  OutputStream out=new FileOutputStream(outFile);
//		  PdfOptions options=null;
//		  PdfConverter.getInstance().convert(document,out,options);
		return "redirect:/download/external";
	}
	
	@RequestMapping(value = "/word2", method = RequestMethod.GET)
	public String word2(Model model, Principal principal, @RequestParam Integer id) throws InvalidFormatException, IOException {
		if (id == null) {
			System.out.println("Id is null");
			return "welcome";
		}
		StudentInfo stuInfo = studentDAO.findStudentInfo(id);
		XWPFDocument doc2 = new XWPFDocument(OPCPackage.open("D:\\input2.docx"));
		doc2.write(new FileOutputStream(new File("D:\\copy2.docx")));
		doc2.close();
		XWPFDocument doc = new XWPFDocument(OPCPackage.open("D:\\copy2.docx"));
	/*	for (XWPFParagraph p : doc.getParagraphs()) {
		    List<XWPFRun> runs = p.getRuns();
		    if (runs != null) {
		        for (XWPFRun r : runs) {
		            String text = r.getText(0);
		            if (text != null && text.contains("needle")) {
		                text = text.replace("needle", "haystack");
		                r.setText(text, 0);
		            }
		        }
		    }
		}*/
		List<StudentLessonInfo> list = studentLessonDAO.listStudentLessonInfosForStudentNoDuplicate(stuInfo.getId());
		List<SubstituteLessonInfo> responsible = new ArrayList<SubstituteLessonInfo>();
		CurriculumInfo act = curriculumDAO.findCurriculumInfoActive();
		List<SubstituteLessonInfo> all = substituteLessonDAO.listSubstituteLessonInfos(act.getId());
		for (SubstituteLessonInfo a : all) {
			boolean exist = false;
			for (StudentLessonInfo l : list) {
				if (l.getSubstituteLessonId() == a.getId()) {
					exist=true;
					break;
				}
			}
			if (!exist && (a.getBase() == null || !a.getBase().contains("H"))) {
				responsible.add(a);
			}
		}
		String rType="";
		List<Integer> processed = new ArrayList<Integer>();
		HashMap<Integer, String> hmap = new HashMap<Integer, String>();
		for (SubstituteLessonInfo r : responsible) {
			System.out.println("Resp:"+r.getName());
			if (r.getName().equals("Mesleki Seçimlik 1 - 1")) {
				rType = r.getBase();
			}
			System.out.println("rType:"+rType);
			boolean isSet = false;
			for (int i=1; i<5; i++) {
				System.out.println("r getname: "+r.getName());
				if (r.getName().equals("Mesleki Seçimlik 1 - "+i)) {
					for(StudentLessonInfo l : list) {
						SubstituteLessonInfo sub = substituteLessonDAO.findSubstituteLessonInfo(l.getSubstituteLessonId());
						System.out.println("aAa:"+sub.getBase()+" "+rType);
						if (sub.getBase() != null && !processed.contains(sub.getId()) && sub.getBase().equals(rType+"H")) {
							System.out.println("MEsleki set 1 - "+i);
							processed.add(sub.getId());
							r.setAkts(sub.getAkts());
							r.setBase(sub.getBase());
							r.setCode(sub.getCode());
							r.setConditionId(sub.getConditionId());
							r.setCredit(sub.getCredit());
							r.setCurriculumId(sub.getCurriculumId());
							r.setId(sub.getId());
							r.setLab(sub.getLab());
							r.setLang(sub.getLang());
							r.setName(sub.getName());
							hmap.put(r.getId(), l.getConvMark());
							isSet = true;
						}
						if (isSet){
							break;
						}
					}
				}
				if (isSet){
					break;
				}
			}
		}
        System.out.println("List: "+list.size());
		for (XWPFTable tbl : doc.getTables()) {
		   for (XWPFTableRow row : tbl.getRows()) {
		      for (XWPFTableCell cell : row.getTableCells()) {
		         for (XWPFParagraph p : cell.getParagraphs()) {
		            for (XWPFRun r : p.getRuns()) {
		              String text = r.getText(0);
//		              System.out.println(text);
		              if (text != null && text.contains("&no")) {
		            	  text = text.replace("&no", stuInfo.getNo());
		            	  r.setText(text,0);
			          }
		              if (text != null && text.contains("&ad")) {
		            	  text = text.replace("&ad", stuInfo.getName());
		            	  r.setText(text,0);
			          }
		              if (text != null && text.contains("&soyad")) {
		            	  text = text.replace("&soyad", stuInfo.getSurname());
		            	  r.setText(text,0);
			          }
		              if (text != null && text.contains("&intibak")) {
		            	  if (stuInfo.getAdpScore() != null) {
		            		  text = text.replace("&intibak", stuInfo.getAdpScore().toString());
		            	  }
		            	  r.setText(text,0);
			          }
		              if (text != null && text.contains("&yil")) {
		            	  text = text.replace("&yil", stuInfo.getRecordYear().toString());
		            	  r.setText(text,0);
			          }
		              if (text != null && text.contains("&bolum")) {
		            	  DeptInfo dept = deptDAO.findDeptInfo(stuInfo.getDeptId());
		            	  text = text.replace("&bolum", dept.getName());
		            	  r.setText(text,0);
			          }
		              int i;
		              int count1 = 0;
		              int count2 = 0;
		              int count3 = 0;
		              int count4 = 0;
		              int count5 = 0;
		              int count6 = 0;
		              int count7 = 0;
		              int count8 = 0;
		              // passed lesson set
		              for (i = 0 ; i < 8; i++) {
		            	  for(StudentLessonInfo l : list) {
		            		  SubstituteLessonInfo sub = substituteLessonDAO.findSubstituteLessonInfo(l.getSubstituteLessonId());
		            		  int count = 0;
		            		  if (sub.getTerm() == 1) {
		            			  count = count1;
		            		  }else if (sub.getTerm() == 2) {
		            			  count = count2;
		            		  }else if (sub.getTerm() == 3) {
		            			  count = count3;
		            		  }else if (sub.getTerm() == 4) {
		            			  count = count4;
		            		  }else if (sub.getTerm() == 5) {
		            			  count = count5;
		            		  }else if (sub.getTerm() == 6) {
		            			  count = count6;
		            		  }else if (sub.getTerm() == 7) {
		            			  count = count7;
		            		  }else if (sub.getTerm() == 8) {
		            			  count = count8;
		            		  }
		            		  if (!processed.contains(sub.getId()) && sub.getTerm() == i+1) {
		            			  if (text != null && text.contains("&"+(i+1)+""+count+"0d")) {
		    		            	  System.out.print(" Text old: "+text);
		            				  text = text.replace("&"+(i+1)+""+count+"0d", sub.getName());
		    		            	  System.out.print(" Text new: "+text);
		    		            	  r.setText(text,0);
		    			          }
		            			  if (text != null && text.contains("&"+(i+1)+""+count+"0l")) {
		            				  System.out.print(" Text old: "+text);
		    		            	  text = text.replace("&"+(i+1)+""+count+"0l", sub.getLang());
		    		            	  System.out.print(" Text new: "+text);
		    		            	  r.setText(text,0);
		    			          }
		            			  if (text != null && text.contains("&"+(i+1)+""+count+"0k")) {
		            				  System.out.print(" Text old: "+text);
		    		            	  text = text.replace("&"+(i+1)+""+count+"0k", sub.getCredit().toString());
		    		            	  System.out.print(" Text new: "+text);
		    		            	  r.setText(text,0);
		    			          }
		            			  if (text != null && text.contains("&"+(i+1)+""+count+"0a")) {
		            				  System.out.print(" Text old: "+text);
		    		            	  text = text.replace("&"+(i+1)+""+count+"0a", sub.getAkts().toString());
		    		            	  System.out.print(" Text new: "+text);
		    		            	  r.setText(text,0);
		    			          }
		            			  if (text != null && text.contains("&"+(i+1)+""+count+"0n")) {
		            				  System.out.print(" Text old: "+text);
		    		            	  text = text.replace("&"+(i+1)+""+count+"0n", l.getConvMark());
		    		            	  System.out.println(" Text new: "+text);
		    		            	  r.setText(text,0);
		    			          }
		            			  if (text != null && text.contains("&"+(i+1)+""+count+"0")) {
		            				  System.out.print(" Text old: "+text);
		    		            	  text = text.replace("&"+(i+1)+""+count+"0", sub.getCode());
		    		            	  System.out.print(" Text new: "+text);
		    		            	  r.setText(text,0);
		    			          }
		            			  count++;
		            		  }
		            		  if (sub.getTerm() == 1) {
		            			  count1 = count;
		            		  }else if (sub.getTerm() == 2) {
		            			  count2 = count;
		            		  }else if (sub.getTerm() == 3) {
		            			  count3 = count;
		            		  }else if (sub.getTerm() == 4) {
		            			  count4 = count;
		            		  }else if (sub.getTerm() == 5) {
		            			  count5 = count;
		            		  }else if (sub.getTerm() == 6) {
		            			  count6 = count;
		            		  }else if (sub.getTerm() == 7) {
		            			  count7 = count;
		            		  }else if (sub.getTerm() == 8) {
		            			  count8 = count;
		            		  }
		            	  }
		              }
		              // not passed set
		              for (i = 0 ; i < 8; i++) {
		            	  for(SubstituteLessonInfo sub : responsible) {
		            		  int count = 0;
		            		  if (sub.getTerm() == 1) {
		            			  count = count1;
		            		  }else if (sub.getTerm() == 2) {
		            			  count = count2;
		            		  }else if (sub.getTerm() == 3) {
		            			  count = count3;
		            		  }else if (sub.getTerm() == 4) {
		            			  count = count4;
		            		  }else if (sub.getTerm() == 5) {
		            			  count = count5;
		            		  }else if (sub.getTerm() == 6) {
		            			  count = count6;
		            		  }else if (sub.getTerm() == 7) {
		            			  count = count7;
		            		  }else if (sub.getTerm() == 8) {
		            			  count = count8;
		            		  }
		            		  if (sub.getTerm() == i+1) {
		            			  if (text != null && text.contains("&"+(i+1)+""+count+"0d")) {
		    		            	  System.out.print(" Text old: "+text);
		            				  text = text.replace("&"+(i+1)+""+count+"0d", sub.getName());
		    		            	  System.out.print(" Text new: "+text);
		    		            	  r.setText(text,0);
		    			          }
		            			  if (text != null && text.contains("&"+(i+1)+""+count+"0l")) {
		            				  System.out.print(" Text old: "+text);
		    		            	  text = text.replace("&"+(i+1)+""+count+"0l", sub.getLang());
		    		            	  System.out.print(" Text new: "+text);
		    		            	  r.setText(text,0);
		    			          }
		            			  if (text != null && text.contains("&"+(i+1)+""+count+"0k")) {
		            				  System.out.print(" Text old: "+text);
		    		            	  text = text.replace("&"+(i+1)+""+count+"0k", sub.getCredit().toString());
		    		            	  System.out.print(" Text new: "+text);
		    		            	  r.setText(text,0);
		    			          }
		            			  if (text != null && text.contains("&"+(i+1)+""+count+"0a")) {
		            				  System.out.print(" Text old: "+text);
		    		            	  text = text.replace("&"+(i+1)+""+count+"0a", sub.getAkts().toString());
		    		            	  System.out.print(" Text new: "+text);
		    		            	  r.setText(text,0);
		    			          }
		            			  if (text != null && text.contains("&"+(i+1)+""+count+"0n")) {
		            				  System.out.print(" Text old: "+text);
		            				  if (hmap.get(sub.getId()) != null){
		            					  text = text.replace("&"+(i+1)+""+count+"0n", hmap.get(sub.getId()));
		            				  }else {
		            					  text = text.replace("&"+(i+1)+""+count+"0n", "Sorumlu");
		            				  }
		    		            	  
		    		            	  System.out.println(" Text new: "+text);
		    		            	  r.setText(text,0);
		    			          }
		            			  if (text != null && text.contains("&"+(i+1)+""+count+"0")) {
		            				  System.out.print(" Text old: "+text);
		    		            	  text = text.replace("&"+(i+1)+""+count+"0", sub.getCode());
		    		            	  System.out.print(" Text new: "+text);
		    		            	  r.setText(text,0);
		    			          }
		            			  count++;
		            		  }
		            		  if (sub.getTerm() == 1) {
		            			  count1 = count;
		            		  }else if (sub.getTerm() == 2) {
		            			  count2 = count;
		            		  }else if (sub.getTerm() == 3) {
		            			  count3 = count;
		            		  }else if (sub.getTerm() == 4) {
		            			  count4 = count;
		            		  }else if (sub.getTerm() == 5) {
		            			  count5 = count;
		            		  }else if (sub.getTerm() == 6) {
		            			  count6 = count;
		            		  }else if (sub.getTerm() == 7) {
		            			  count7 = count;
		            		  }else if (sub.getTerm() == 8) {
		            			  count8 = count;
		            		  }
		            	  }
		              }
		              // delete blanks
		              for (i = 0 ; i < 8; i++) {
		            	  for (int j = 0; j < 8; j++) {
		            			  if (text != null && text.contains("&"+(i+1)+""+j+"0d")) {
		    		            	  System.out.print(" Text old: "+text);
		            				  text = text.replace("&"+(i+1)+""+j+"0d", "");
		    		            	  System.out.print(" Text new: "+text);
		    		            	  r.setText(text,0);
		    			          }
		            			  if (text != null && text.contains("&"+(i+1)+""+j+"0l")) {
		            				  System.out.print(" Text old: "+text);
		    		            	  text = text.replace("&"+(i+1)+""+j+"0l", "");
		    		            	  System.out.print(" Text new: "+text);
		    		            	  r.setText(text,0);
		    			          }
		            			  if (text != null && text.contains("&"+(i+1)+""+j+"0k")) {
		            				  System.out.print(" Text old: "+text);
		    		            	  text = text.replace("&"+(i+1)+""+j+"0k", "");
		    		            	  System.out.print(" Text new: "+text);
		    		            	  r.setText(text,0);
		    			          }
		            			  if (text != null && text.contains("&"+(i+1)+""+j+"0a")) {
		            				  System.out.print(" Text old: "+text);
		    		            	  text = text.replace("&"+(i+1)+""+j+"0a", "");
		    		            	  System.out.print(" Text new: "+text);
		    		            	  r.setText(text,0);
		    			          }
		            			  if (text != null && text.contains("&"+(i+1)+""+j+"0n")) {
		            				  System.out.print("Text old: "+text);
		    		            	  text = text.replace("&"+(i+1)+""+j+"0n", "");
		    		            	  System.out.println(" Text new: "+text);
		    		            	  r.setText(text,0);
		    			          }
		            			  if (text != null && text.contains("&"+(i+1)+""+j+"0")) {
		            				  System.out.print(" Text old: "+text);
		    		            	  text = text.replace("&"+(i+1)+""+j+"0", "");
		    		            	  System.out.print(" Text new: "+text);
		    		            	  r.setText(text,0);
		    			          }
		            	  }
		              }
		            }
		         }
		      }
		   }
		}
		doc.write(new FileOutputStream(new File("D:\\output2.docx")));
		doc.close();
//		FileInputStream in=new FileInputStream("D:\\output.docx");
//		  XWPFDocument document=new XWPFDocument(in);
//		  File outFile=new File("D:\\output.pdf");
//		  OutputStream out=new FileOutputStream(outFile);
//		  PdfOptions options=null;
//		  PdfConverter.getInstance().convert(document,out,options);
		return "/download/external";
	}
	
	private static final String INTERNAL_FILE="D:/output.docx";
    private static final String EXTERNAL_FILE_PATH="D:/output.docx";
	@RequestMapping(value="/download/{type}", method = RequestMethod.GET)
    public void downloadFile(HttpServletResponse response, @PathVariable("type") String type) throws IOException {
     
        File file = null;
         
        if(type.equalsIgnoreCase("internal")){
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            file = new File(classloader.getResource(INTERNAL_FILE).getFile());
        }else{
            file = new File(EXTERNAL_FILE_PATH);
        }
         
        if(!file.exists()){
            String errorMessage = "Sorry. The file you are looking for does not exist";
            System.out.println(errorMessage);
            OutputStream outputStream = response.getOutputStream();
            outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
            outputStream.close();
            return;
        }
         
        String mimeType= URLConnection.guessContentTypeFromName(file.getName());
        if(mimeType==null){
            System.out.println("mimetype is not detectable, will take default");
            mimeType = "application/octet-stream";
        }
         
        System.out.println("mimetype : "+mimeType);
         
        response.setContentType(mimeType);
         
        /* "Content-Disposition : inline" will show viewable types [like images/text/pdf/anything viewable by browser] right on browser 
            while others(zip e.g) will be directly downloaded [may provide save as popup, based on your browser setting.]*/
        response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() +"\""));
 
         
        /* "Content-Disposition : attachment" will be directly download, may provide save as popup, based on your browser setting*/
        //response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
         
        response.setContentLength((int)file.length());
 
        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
 
        //Copy bytes from source to destination(outputstream in this example), closes both streams.
        FileCopyUtils.copy(inputStream, response.getOutputStream());
    }

}