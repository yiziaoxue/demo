package com.example.demo.bean;

public class Address {
	private String addName;
	private String addId;
	
	public Address(){}
	public Address(String addName,String addId){
		this.addName = addName;
		this.addId = addId;
	}
	public String getAddName() {
		return addName;
	}
	public void setAddName(String addName) {
		this.addName = addName;
	}
	public String getAddId() {
		return addId;
	}
	public void setAddId(String addId) {
		this.addId = addId;
	}
	
	
}
