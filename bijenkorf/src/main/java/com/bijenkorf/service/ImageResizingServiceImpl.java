package com.bijenkorf.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bijenkorf.configuration.DatabaseConfiguration;
import com.bijenkorf.domain.Image;
import com.bijenkorf.enumerator.ApplicationMessageKey;
import com.bijenkorf.exception.CustomImageException;

@Component
public class ImageResizingServiceImpl implements ImageResizingService {

	private AmazonS3Service amazonS3Service;	

	private static final Logger LOGGER = LoggerFactory.getLogger(ImageResizingServiceImpl.class);
	DatabaseConfiguration databaseConfiguration;


	@Autowired
	public ImageResizingServiceImpl(AmazonS3Service amazonS3Service, DatabaseConfiguration databaseConfiguration) {		
		this.amazonS3Service = amazonS3Service;		
		this.databaseConfiguration = databaseConfiguration;
	}

	@Override
	public File downloadImage(String predefinedTypeName, String reference) throws CustomImageException {
		// TODO Auto-generated method stub		
		checkPredefinedTypeExist(predefinedTypeName);
		amazonS3Service.downloadImage(predefinedTypeName, reference);
		return null;
	}


	@Override
	public void uploadImage(BufferedImage bufferedImage, String predefinedTypeName, String reference) {
		try {
			amazonS3Service.uploadImage(bufferedImage, predefinedTypeName, reference);
		} catch (IOException e) {
			LOGGER.warn ("action:{}, request:{}", "checkPredefinedTypeExist", predefinedTypeName); 
		} 
	}

	@Override
	public void deleteImage(String predefinedTypeName, String reference) {
		amazonS3Service.deleteImage(predefinedTypeName, reference);
	}

	@Override
	public void updatePredefinedImageType(String predefinedImageTypeName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void checkPredefinedTypeExist(String predefinedTypeName) throws CustomImageException {	
		
		LOGGER.info("action:{}, request:{}", "checkPredefinedTypeExist", predefinedTypeName); 
		throw new CustomImageException(ApplicationMessageKey.NOT_FOUND);		
	}

	@Override
	public File downloadOriginalImage(String reference) {	
		return null;
	}
	
	@Override
	public Image findImageByReference(String reference) {
		// TODO Auto-generated method stub
		return null;
	}

	private boolean isOriginalImageExistS3(String reference) {	
		return false;
	}

	private BufferedImage optimizeImage(BufferedImage bufferedImage, String reference) {		
		return null;
	}	
}