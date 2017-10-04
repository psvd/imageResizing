package com.bijenkorf.service;

import java.awt.image.BufferedImage;
import java.io.File;

import com.bijenkorf.domain.Image;
import com.bijenkorf.exception.CustomImageException;

public interface ImageResizingService {
		
	File downloadImage(String predefinedTypeName, String reference) throws CustomImageException;
	void uploadImage(BufferedImage bufferedImage, String predefinedTypeName, String reference);
	void deleteImage(String predefinedTypeName, String reference);		
	void checkPredefinedTypeExist(String predefinedTypeName) throws CustomImageException;	
	void updatePredefinedImageType(String predefinedImageTypeName);
	File downloadOriginalImage(String reference);
	Image findImageByReference(String reference);	
}
