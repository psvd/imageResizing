package com.bijenkorf.service;

import java.awt.image.BufferedImage;
import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.services.appstream.model.Application;
import com.bijenkorf.domain.Image;
import com.bijenkorf.enumerator.ApplicationMessageKey;
import com.bijenkorf.exception.CustomImageException;

@Component
public class ImageResizingServiceImpl implements ImageResizingService {

	private AmazonS3Service amazonS3Service;	
	private static final Logger LOGGER = LoggerFactory.getLogger(ImageResizingServiceImpl.class);

	@Autowired
	public ImageResizingServiceImpl(AmazonS3Service amazonS3Service) {		
		this.amazonS3Service = amazonS3Service;		
	}

	@Override
	public File getImage(String predefinedTypeName, String dummySeoName, String reference) {
		File optimizedImage = null;
		if (isPredefinedTypeExist(predefinedTypeName)) {
			//optimizedImage = amazonS3Service.getImage(predefinedTypeName, dummySeoName, reference);

			if (optimizedImage == null) {
				//optimizedImage = optimizeImage(reference);
			}
			
			//http://www.javaroots.com/2013/05/how-to-upload-and-download-images-in.html

		}
		return optimizedImage;
	}

	@Override
	public void deleteImage(String predefinedTypeName, String reference) {
		amazonS3Service.deleteImage(predefinedTypeName, reference);
	}

	private byte[] getOriginalImage(String reference) {		
		byte[] originalImage = amazonS3Service.getImage("original", null, reference);
		if (originalImage == null) {
			//TODO - Implement method to get the original image from DB
		}		
		return originalImage;
	}

	@Override
	public BufferedImage optimizeImage(BufferedImage originalImage, Image image, int type) {
		BufferedImage resizedImage = new BufferedImage(image.getPredefinedType().getPredefinedImageTypeAttribute().getWidth(), 
				image.getPredefinedType().getPredefinedImageTypeAttribute().getWidth(), type);

		return resizedImage;
	}

	@Override
	public boolean isPredefinedTypeExist(String predefinedTypeName) {
		Boolean result = false;
		if (!result) {
			//throw new CustomImageException(ApplicationMessageKey.NOT_FOUND);
			LOGGER.info("action:{}, request:{}", "isPredefinedTypeExist", predefinedTypeName); 
		}

		return false;
	}

	@Override
	public void updatePredefinedImageType(String predefinedImageTypeName) {
		// TODO Auto-generated method stub

	}
}