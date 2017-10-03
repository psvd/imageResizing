package com.bijenkorf.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "IRS_IMAGE")
public class Image {
	
	@Id
	@Column(name = "TX_REFERENCE", length = 255, nullable = true)
	private String reference;
	
	@ManyToOne
	@JoinColumn(name = "ID_IMAGE_TYPE")
	private PredefinedImageType predefinedType;
	
	@Column(name = "TX_DUMMY_SEO_NAME", length = 255, nullable = true)
	private String dummySeoName;
	
	public PredefinedImageType getPredefinedType() {
		return predefinedType;
	}
	public void setPredefinedType(PredefinedImageType predefinedType) {
		this.predefinedType = predefinedType;
	}
	public String getDummySeoName() {
		return dummySeoName;
	}
	public void setDummySeoName(String dummySeoName) {
		this.dummySeoName = dummySeoName;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
}
