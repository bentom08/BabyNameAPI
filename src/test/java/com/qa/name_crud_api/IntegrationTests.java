package com.qa.name_crud_api;

import static org.junit.Assert.assertEquals;

import org.assertj.core.util.Arrays;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.qa.name_crud_api.persistence.domain.BabyName;
import com.qa.name_crud_api.rest.NameRest;
import com.qa.name_crud_api.service.BabyNameService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IntegrationTests {

	private TestRestTemplate testRest = new TestRestTemplate();
	
	@Autowired
	private NameRest rest;
	
	@Autowired
	private BabyNameService service;
	
	private static final String MOCK_URL = "http://localhost:8081/generateName/";
	private static final String MOCK_LENGTH = "8";
	private static final ResponseEntity<Object> response = new ResponseEntity<>(HttpStatus.OK);

	private static final BabyName MOCK_NAME = new BabyName(1L, "Alvin");
	private static final BabyName MOCK_UPDATED_NAME = new BabyName(1L, "SecondAlvin");
	
	@Test
	public void arestTemplateTest() {
		BabyName genName = testRest.postForObject(MOCK_URL + MOCK_LENGTH, new HttpEntity<BabyName>(new BabyName(1L, "Alvin")) , BabyName.class);
		assertEquals(8, genName.getName().length());
		assertEquals("Alvin", genName.getName().substring(0, 5));
	}
	
	@Test
	public void bstoreNameTest() {
		assertEquals(MOCK_NAME.toString(), service.storeName(MOCK_NAME).toString());
		assertEquals(MOCK_NAME.toString(), rest.getName(1L).toString());
	}
	
	@Ignore
	@Test
	public void cgetAllNamesTest() {
		assertEquals(Arrays.asList(MOCK_NAME).toString(), rest.getAllNames().toString());
	}
	
	@Test
	public void dupdateNameTest() {
		assertEquals(response, rest.updateName(MOCK_UPDATED_NAME, 1L));
		assertEquals(MOCK_UPDATED_NAME.toString(), rest.getName(1L).toString());
	}
	
	@Ignore
	@Test
	public void edeleteNameTest() {
		assertEquals(response, rest.deleteName(1L));
		assertEquals(Arrays.asList(null), rest.getName(1L));
	}
			
}
