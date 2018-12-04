package com.qa.name_crud_api.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.qa.name_crud_api.persistence.domain.BabyName;
import com.qa.name_crud_api.persistence.domain.BabyNamePOJO;
import com.qa.name_crud_api.service.BabyNameService;

@CrossOrigin
@RestController
public class NameRest {

	@Autowired
	private BabyNameService service;
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${url.nameGen}")
	private String nameGenURL;
	
	@Value("${path.nameGen}")
	private String nameGenPath;
	
	@Value("${queue.saveToDB}")
	private String storeQueue;
	
	@Value("${queue.updateDB}")
	private String updateQueue;
	
	@GetMapping("${path.get}")
	public BabyName getName(@PathVariable Long id) {
		return service.getName(id);
	}

	@GetMapping("${path.getAll}")
	public List<BabyName> getAllNames() {
		return service.getAllNames();
	}

	@DeleteMapping("${path.delete}")
	public ResponseEntity<Object> deleteName(@PathVariable Long id) {	
		return service.deleteName(id);
	}

	@PutMapping("${path.update}")
	public ResponseEntity<Object> updateName(@RequestBody BabyName name, @PathVariable Long id) {
		BabyNamePOJO nameToSend = new BabyNamePOJO(id.toString(), name.getName());
		
		jmsTemplate.convertAndSend(updateQueue, nameToSend);
		
		return service.updateName(name, id);
	}

	@PostMapping("${path.create}")
	public BabyName createName(@RequestBody BabyName name, @PathVariable byte length) {
		HttpEntity<BabyName> request = new HttpEntity<>(name);
		
		BabyName generatedName = restTemplate.postForObject(nameGenURL + nameGenPath + length, request, BabyName.class);
		
		BabyName storedName = service.storeName(generatedName);
		
		BabyNamePOJO nameToSend = new BabyNamePOJO(storedName.getId().toString(), storedName.getName());
		
		jmsTemplate.convertAndSend(storeQueue, nameToSend);
		
		return storedName;
	}
}
