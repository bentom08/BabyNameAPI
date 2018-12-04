package com.qa.name_crud_api.persistence.domain;

public class BabyNamePOJO {
	
	private String nameId;

	private String name;
	
	public BabyNamePOJO() {}
	
	public BabyNamePOJO(String id, String name) {
		this.name = name;
		this.nameId = id;
	}
	
	public String getId() {
		return nameId;
	}

	public void setId(String id) {
		this.nameId = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
