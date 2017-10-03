package com.bijenkorf.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.bijenkorf.domain.Image;

@Component
public class ImageResizingServiceImpl implements ImageResizingService {

	private AmazonS3Service amazonS3Service;	
	private static final Logger LOGGER = LoggerFactory.getLogger(ImageResizingServiceImpl.class);
	
	public ImageResizingServiceImpl(AmazonS3Service amazonS3Service) {		
		this.amazonS3Service = amazonS3Service;		
	}

	@Override
	public Image getImage(String predefinedTypeName, String dummySeoName, String reference) {
		Image optimizedImage = amazonS3Service.getImage(predefinedTypeName, dummySeoName, reference);

		if (optimizedImage == null) {
			optimizedImage = optimizeImage(reference);
		}

		return optimizedImage;
	}

	@Override
	public void deleteImage(String predefinedTypeName, String reference) {
		amazonS3Service.deleteImage(predefinedTypeName, reference);
	}

	private Image getOriginalImage(String reference) {		
		Image originalImage = amazonS3Service.getImage("original", null, reference);
		if (originalImage == null) {
			//TODO - Implement method to get the original image from DB
		}
		
		return originalImage;
	}

	private Image optimizeImage(Image image) {
		//TODO - Implement method to optimize image
		return image;
	}

	@Override
	public Image optimizeImage(String reference) {
		Image optimizedImage = optimizeImage(getOriginalImage(reference));
		amazonS3Service.saveImage(optimizedImage);
		
		return optimizedImage;
	}




}