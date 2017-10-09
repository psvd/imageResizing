package com.bijenkorf.service;

import java.awt.image.BufferedImage;
import java.io.InputStream;

import com.bijenkorf.domain.Image;
import com.bijenkorf.exception.CustomImageException;

public interface ImageResizingService {
		
	InputStream downloadImage(String predefinedTypeName, String reference) throws CustomImageException;
	Image uploadImage(String reference) throws CustomImageException;
	void deleteImage(String predefinedTypeName, String reference);		
	void hasPredefinedTypeName(String predefinedTypeName) throws CustomImageException;		
	BufferedImage downloadOriginalImageS3(String reference) throws CustomImageException;
	BufferedImage downloadOriginalImage(String reference) throws CustomImageException;
	void hasImageByReference(String reference) throws CustomImageException;
	Image findImageByReference(String reference);
}
