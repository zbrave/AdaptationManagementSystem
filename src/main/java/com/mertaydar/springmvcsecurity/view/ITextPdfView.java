package com.mertaydar.springmvcsecurity.view;

import java.text.DateFormat;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.mertaydar.springmvcsecurity.model.UniInfo;

public class ITextPdfView extends AbstractITextPdfView {

    private static final DateFormat DATE_FORMAT = DateFormat.getDateInstance(DateFormat.SHORT);

    @Override
    protected void buildPdfDocument(Map<String, Object> model,
                                    Document document, PdfWriter writer, HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {

        @SuppressWarnings("unchecked")
        List<UniInfo> courses = (List<UniInfo>) model.get("courses");

        PdfPTable table = new PdfPTable(3);
        table.setWidths(new int[]{10, 60, 30});

        table.addCell("ID");
        table.addCell("Name");
        table.addCell("Date");
        courses.add(new UniInfo(1, "asd"));
        courses.add(new UniInfo(2, "aasd"));
        courses.add(new UniInfo(3, "ssd"));
        for (UniInfo course : courses){
            table.addCell(String.valueOf(course.getId()));
            table.addCell(course.getName());
            table.addCell(DATE_FORMAT.format(Instant.now()));
        }



        document.add(table);
    }

}