package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;

import com.example.demo.entity.EligibilityDetails;
import com.example.demo.request.RequestObject;
import com.example.demo.response.ResponseObject;

import javax.servlet.http.HttpServletResponse;

public interface EligibilityDetailsService {

	public List<ResponseObject> search(RequestObject request);//This is best practice
//	public List<EligibilityDetails> searchResult(String PlanName,String PlanStatus,LocalDate startDate,LocalDate endDate);
	public List<String> getUniqueName();
	public List<String> getUniqueStatus();
	public void generateExcel(HttpServletResponse response)throws Exception;
	public void generatePdf(HttpServletResponse response)throws Exception;
	
	
	
	
}
