package com.qa.name_crud_api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.qa.name_crud_api.persistence.domain.BabyName;
import com.qa.name_crud_api.persistence.domain.BabyNamePOJO;
import com.qa.name_crud_api.persistence.repository.NameRepository;
import com.qa.name_crud_api.util.exceptions.BabyNameNotFoundException;

@Service
public class BabyNameServiceImpl implements BabyNameService {

	@Autowired
	private NameRepository repo;
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Value("${queue.deleteFromDB}")
	private String deleteQueue;
	
	public BabyName getName(Long id) {
		Optional<BabyName> optionalName = repo.findById(id);
		
		return optionalName.orElseThrow( () -> new BabyNameNotFoundException("Baby Name with id: " + id + " not found"));
	}

	public List<BabyName> getAllNames() {
		return repo.findAll();
	}

	public ResponseEntity<Object> deleteName(Long id) {
		Optional<BabyName> optionalName = repo.findById(id);
		
		if (!optionalName.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		repo.deleteById(id);
		
		BabyNamePOJO nameToSend = new BabyNamePOJO(optionalName.get().getId().toString(), optionalName.get().getName());
		jmsTemplate.convertAndSend(deleteQueue, nameToSend);
		
		return ResponseEntity.ok().build();
	}

	public ResponseEntity<Object> updateName(BabyName name, Long id) {
		Optional<BabyName> optionalName = repo.findById(id);
		
		if (!optionalName.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		name.setId(id);
		repo.save(name);
		
		return ResponseEntity.ok().build();
	}

	public BabyName storeName(BabyName name) {
		return repo.save(name);
	}
}
