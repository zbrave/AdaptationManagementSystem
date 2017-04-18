package com.mertaydar.springmvcsecurity.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.mertaydar.springmvcsecurity.dao.DeptDAO;
import com.mertaydar.springmvcsecurity.model.DeptInfo;
import com.mertaydar.springmvcsecurity.model.UniInfo;

@Controller
//Enable Hibernate Transaction.
@Transactional
//Need To use RedirectAttributes
@EnableWebMvc
public class FormController {
	
	@Autowired
	private DeptDAO deptDAO;
	
	/* Dept Controller Section */
	@RequestMapping("/formAdaptation")
	public String deptList(Model model) {
		List<DeptInfo> list = deptDAO.listDeptInfos();
		model.addAttribute("courses", list);
		try {
            Document document=new Document();
            PdfWriter.getInstance(document,new FileOutputStream("D:\\myfile.pdf"));
            document.open();  
            document.add(new Paragraph("This is my file"));
            document.close();
        } catch (Exception e) {
            System.out.println("error"+e.getMessage());
        }
		return "formAdaptation";
	}
}
