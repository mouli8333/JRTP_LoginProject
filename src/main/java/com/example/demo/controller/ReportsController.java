package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.coyote.http11.Http11AprProtocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.request.RequestObject;
import com.example.demo.response.ResponseObject;
import com.example.demo.service.EligibilityDetailsService;

@RestController
public class ReportsController {
	@Autowired
	private EligibilityDetailsService service;
	
	@GetMapping("/plans")
	public ResponseEntity<List<String>> getPlanNames(){
		List<String> uniqueName = service.getUniqueName();
	   return new ResponseEntity<>(uniqueName,HttpStatus.OK);
	}
	@GetMapping("/status")
	public ResponseEntity<List<String>> getPlanStatus(){
		List<String> uniqueStatus = service.getUniqueStatus();
		return new ResponseEntity<>(uniqueStatus,HttpStatus.OK);
	}
	@PostMapping("/search")
	public ResponseEntity<List<ResponseObject>> getSearchRes(@RequestBody RequestObject request){
		List<ResponseObject> search = service.search(request);
		return new ResponseEntity<>(search,HttpStatus.OK);
	}
	
	@GetMapping("/excel")
	public void excelReport(HttpServletResponse response) throws Exception {
		response.setContentType("application/octet-stream");
		String headerKey="Content-Disposition";
		String headerValue="attachment;filename=data.xls";
		response.setHeader(headerKey, headerValue);
		service.generateExcel(response);
	}
	@GetMapping("/pdf")
	public void pdfreport(HttpServletResponse response) throws Exception{
		response.setContentType("application/pdf");
		String headerKey="Content-Disposition";
		String headerValue="attachment;filename=data.pdf";
		response.setHeader(headerKey, headerValue);
		service.generatePdf(response);
	}

}
