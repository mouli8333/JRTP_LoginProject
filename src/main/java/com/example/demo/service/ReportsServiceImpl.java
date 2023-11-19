package com.example.demo.service;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;


import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.example.demo.Repository.EligibilityDetailsRepo;
import com.example.demo.entity.EligibilityDetails;
import com.example.demo.request.RequestObject;
import com.example.demo.response.ResponseObject;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfTable;
import com.lowagie.text.pdf.PdfWriter;

@Service
public class ReportsServiceImpl implements EligibilityDetailsService {

	@Autowired
	private EligibilityDetailsRepo eligrepo;
	@Override
	public List<ResponseObject> search(RequestObject request) {		
		List<ResponseObject> res=new ArrayList<>();
		//Preparing Dynamic query using Query by Example
		EligibilityDetails queryBuilder=new EligibilityDetails();
		if(request.getPlanName()!=null&&!request.getPlanName().isEmpty()) {
			queryBuilder.setPlanName(request.getPlanName());
		}
		if(request.getPlanStatus()!=null&&!request.getPlanStatus().isEmpty()) {
			queryBuilder.setPlanStatus(request.getPlanStatus());
		}
		if(request.getPlanStartDate()!=null) {
			queryBuilder.setPlanStartDate(request.getPlanStartDate());
		}
		if(request.getPlanEndDate()!=null) {
			queryBuilder.setPlanEndDate(request.getPlanEndDate());
		}
		Example<EligibilityDetails> example=Example.of(queryBuilder);
		List<EligibilityDetails> result = eligrepo.findAll(example);
		for(EligibilityDetails x:result) {
			ResponseObject response=new ResponseObject();
			BeanUtils.copyProperties(res, result);
			res.add(response);
		}
		
		return res;
	}

	@Override
	public List<String> getUniqueName() {
		List<String> findPlanNames = eligrepo.findPlanNames();
		return findPlanNames;
	}

	@Override
	public List<String> getUniqueStatus() {
		return eligrepo.findPlanStatus();
	}

	@Override
	public void generateExcel(HttpServletResponse response) throws IOException {
		List<EligibilityDetails> entities = eligrepo.findAll();
		HSSFWorkbook workBook=new HSSFWorkbook();
		HSSFSheet sheet=workBook.createSheet();
		HSSFRow headerRow=sheet.createRow(0);
		headerRow.createCell(0).setCellValue("Name");
		headerRow.createCell(1).setCellValue("Mobile");
		headerRow.createCell(2).setCellValue("Gender");
		headerRow.createCell(3).setCellValue("SSN");
		int i[]= {1};
		entities.forEach(entity->{
			HSSFRow row=sheet.createRow(i[0]);
			row.createCell(0).setCellValue(entity.getPlanName());
			row.createCell(1).setCellValue(entity.getMobile());
			row.createCell(2).setCellValue(entity.getGender());
			row.createCell(3).setCellValue(entity.getSsn());
		    i[0]++;
		});
		ServletOutputStream outputStream = response.getOutputStream();
		workBook.write(outputStream);
		workBook.close();
		outputStream.close();
	}

	@Override
	public void generatePdf(HttpServletResponse response) throws DocumentException, IOException {
		List<EligibilityDetails> entities = eligrepo.findAll();
		Document document=new Document(PageSize.A4);
		PdfWriter.getInstance(document,response.getOutputStream());
		document.open();
		Font font=FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setColor(Color.blue);
		font.setSize(18);
		
		Paragraph p=new Paragraph("Search Report",font);
		p.setAlignment(Paragraph.ALIGN_CENTER);
		document.add(p);
		PdfPTable table = new PdfPTable(5);
		 table.setWidthPercentage(100f);
	     table.setWidths(new float[] {1.5f, 3.5f, 3.0f, 3.0f, 1.5f});
	     table.setSpacingBefore(10);
	     
	     PdfPCell cell = new PdfPCell();
	      cell.setBackgroundColor(Color.BLUE);
	        cell.setPadding(5);
	        

	        font = FontFactory.getFont(FontFactory.HELVETICA);
	        font.setColor(Color.WHITE);
	         
	        cell.setPhrase(new Phrase("Name", font)); 
	        table.addCell(cell);
	         
	        cell.setPhrase(new Phrase("E-mail", font));
	        table.addCell(cell);
	         
	        cell.setPhrase(new Phrase("Phoneno", font));
	        table.addCell(cell);
	         
	        cell.setPhrase(new Phrase("Gender", font));
	        table.addCell(cell);
	         
	        cell.setPhrase(new Phrase("SSN", font));
	        table.addCell(cell);
	        for (EligibilityDetails entity : entities) {
	            table.addCell(entity.getPlanName());
	            table.addCell(entity.getEmail());
	            table.addCell(entity.getMobile().toString());
	            table.addCell(entity.getGender().toString());
	            table.addCell(entity.getSsn().toString());
	        }
	        document.add(table);
	        document.close();
	}

}
