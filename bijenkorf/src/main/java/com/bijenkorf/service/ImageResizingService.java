package com.bijenkorf.service;

import com.bijenkorf.domain.Image;

public interface ImageResizingService {		
	
	Image getImage(String predefinedTypeName, String dummySeoName, String reference);	
	void deleteImage(String predefinedTypeName, String reference);	
	Image optimizeImage(String reference);
	boolean isPredefinedTypeExist(String predefinedImageTypeName);	
	public void updatePredefinedImageType(String predefinedImageTypeName);
}
