package com.bijenkorf.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "IRS_IMAGE_TYPE")
public class PredefinedImageType {
	
	@Id
	private Long id;
	
	@Column(name = "X_IMAGE_TYPE", length = 255, nullable = false)
	private String predefinedImageTypeName;
	
	@ManyToOne
	@JoinColumn(name = "ID")
	private PredefinedImageTypeAttribute predefinedImageTypeAttribute;	
	
	public String getPredefinedImageTypeName() {
		return predefinedImageTypeName;
	}
	public void setPredefinedImageTypeName(String predefinedImageTypeName) {
		this.predefinedImageTypeName = predefinedImageTypeName;
	}
	public PredefinedImageTypeAttribute getPredefinedImageTypeAttribute() {
		return predefinedImageTypeAttribute;
	}
	public void setPredefinedImageTypeAttribute(PredefinedImageTypeAttribute predefinedImageTypeAttribute) {
		this.predefinedImageTypeAttribute = predefinedImageTypeAttribute;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
}