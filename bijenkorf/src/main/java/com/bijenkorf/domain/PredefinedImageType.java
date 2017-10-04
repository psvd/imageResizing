package com.bijenkorf.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.bijenkorf.enumerator.ScaleType;
import com.bijenkorf.enumerator.Type;

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
	private Type type;
	@Column(name = "X_IMAGE_SCALE", nullable = false)
	private ScaleType scaleType;
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
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public ScaleType getScaleType() {
		return scaleType;
	}
	public void setScaleType(ScaleType scaleType) {
		this.scaleType = scaleType;
	}
	public String getSourceName() {
		return sourceName;
	}
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}	
}