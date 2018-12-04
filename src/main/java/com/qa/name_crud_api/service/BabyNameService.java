package com.qa.name_crud_api.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.qa.name_crud_api.persistence.domain.BabyName;

public interface BabyNameService {

	BabyName getName(Long id);
	
	List<BabyName> getAllNames();
	
	ResponseEntity<Object> deleteName(Long id);
	
	ResponseEntity<Object> updateName(BabyName name, Long id);
	
	BabyName storeName(BabyName name);
}
