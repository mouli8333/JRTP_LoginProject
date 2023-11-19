package com.example.demo.request;

import java.time.LocalDate;

import lombok.Data;

@Data
public class RequestObject {
	private String planName;
	private String planStatus;
	private LocalDate planStartDate;
	private LocalDate planEndDate;
}
