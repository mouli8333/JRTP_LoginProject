package com.example.demo.runners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.example.demo.Repository.EligibilityDetailsRepo;
import com.example.demo.entity.EligibilityDetails;

@Component
public class Runner implements ApplicationRunner {

	@Autowired
	private EligibilityDetailsRepo repo;
	@Override
	public void run(ApplicationArguments args) throws Exception {
		for(int i=0;i<=10;i++) {
		EligibilityDetails entity1=new EligibilityDetails();
		entity1.setEligId(i);
		entity1.setName("sai");
		entity1.setMobile((long) 833937327);
		entity1.setGender('M');
		entity1.setSsn((long) 8888888);
		entity1.setPlanName("KCPD");
		entity1.setPlanStatus("success");
		repo.save(entity1);
		}	
	}

}
