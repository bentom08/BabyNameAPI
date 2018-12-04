package com.qa.name_crud_api;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.qa.name_crud_api.persistence.domain.BabyName;
import com.qa.name_crud_api.rest.NameRest;
import com.qa.name_crud_api.service.BabyNameService;

@RunWith(MockitoJUnitRunner.class)
public class EndpointTests {
	
	private static final BabyName MOCK_NAME = new BabyName(1L, "Alvin");
	private static final List<BabyName> MOCK_LIST = Arrays.asList(MOCK_NAME);
	private static final ResponseEntity<Object> MOCK_ENTITY = new ResponseEntity<>(HttpStatus.OK);

	@InjectMocks
	private NameRest rest;
	
	@Mock
	private BabyNameService service;
	
	@Test
	public void getNameTest() {
		Mockito.when(service.getName(1L)).thenReturn(MOCK_NAME);
		assertEquals(MOCK_NAME, rest.getName(1L));
		Mockito.verify(service).getName(1L);
	}
	
	@Test
	public void getAllNamesTest() {
		Mockito.when(service.getAllNames()).thenReturn(MOCK_LIST);
		assertEquals(MOCK_LIST, rest.getAllNames());
		Mockito.verify(service).getAllNames();
	}
	
	@Test
	public void deleteNameTest() {
		Mockito.when(service.deleteName(1L)).thenReturn(MOCK_ENTITY);
		assertEquals(MOCK_ENTITY, rest.deleteName(1L));
		Mockito.verify(service).deleteName(1L);
	}
}
