package com.sg.springboot.entity;

import java.io.Serializable;

public class Data implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String docType;
	private String docCategory;
	private String docSpecies;
	
	public String getDocType() {
		return docType;
	}
	public void setDocType(String docType) {
		this.docType = docType;
	}
	public String getDocCategory() {
		return docCategory;
	}
	public void setDocCategory(String docCategory) {
		this.docCategory = docCategory;
	}
	public String getDocSpecies() {
		return docSpecies;
	}
	public void setDocSpecies(String docSpecies) {
		this.docSpecies = docSpecies;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
