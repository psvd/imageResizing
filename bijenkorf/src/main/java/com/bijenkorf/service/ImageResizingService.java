package com.bijenkorf.service;

import java.awt.image.BufferedImage;
import java.io.File;

import com.bijenkorf.domain.Image;

public interface ImageResizingService {		
	
	File getImage(String predefinedTypeName, String dummySeoName, String reference);	
	void deleteImage(String predefinedTypeName, String reference);	
	BufferedImage optimizeImage(BufferedImage originalImage, Image image, int type);
	boolean isPredefinedTypeExist(String predefinedTypeName);	
	public void updatePredefinedImageType(String predefinedImageTypeName);
}
