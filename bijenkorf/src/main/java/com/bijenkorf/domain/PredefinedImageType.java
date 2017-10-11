package com.bijenkorf.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "IRS_IMAGE_TYPE")
public class PredefinedImageType {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Long id;
	@Column(name = "X_IMAGE_TYPE_NAME", length = 255, nullable = false)
	private String predefinedImageTypeName;
	@Column(name = "X_IMAGE_HEIGHT", nullable = false)
	private int height;
	@Column(name = "X_IMAGE_WIDTH", nullable = false)
	private int width;
	@Column(name = "X_IMAGE_QUALITY", nullable = false)
	private int quality;
	@Column(name = "X_IMAGE_TYPE", nullable = false)
	private String type;
	@Column(name = "X_IMAGE_SCALE", nullable = false)
	private String scaleType;
	@Column(name = "X_IMAGE_SOURCE_NAME", nullable = true)
	private String sourceName;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPredefinedImageTypeName() {
		return predefinedImageTypeName;
	}
	public void setPredefinedImageTypeName(String predefinedImageTypeName) {
		this.predefinedImageTypeName = predefinedImageTypeName;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getQuality() {
		return quality;
	}
	public void setQuality(int quality) {
		this.quality = quality;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getScaleType() {
		return scaleType;
	}
	public void setScaleType(String scaleType) {
		this.scaleType = scaleType;
	}
	public String getSourceName() {
		return sourceName;
	}
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}	
}