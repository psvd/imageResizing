package com.bijenkorf.service;

import java.awt.image.BufferedImage;
import java.io.InputStream;

import com.bijenkorf.domain.Image;

public interface ImageResizingService {
		
	InputStream downloadImage(String predefinedTypeName, String reference);
	Image uploadImage(String reference);
	void deleteImage(String predefinedTypeName, String reference);
	BufferedImage getOriginalImageFromS3(String reference);
	BufferedImage getOriginalImageFromSource(String reference);
	Image findImageByReference(String reference);	
}
