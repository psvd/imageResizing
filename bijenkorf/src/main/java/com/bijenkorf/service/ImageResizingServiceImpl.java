package com.bijenkorf.service;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.bijenkorf.configuration.ImageResizingSourceConfiguration;
import com.bijenkorf.domain.Image;
import com.bijenkorf.domain.PredefinedImageType;
import com.bijenkorf.enumerator.ApplicationMessageKey;
import com.bijenkorf.exception.CustomImageException;
import com.bijenkorf.repository.ImageRepository;
import com.bijenkorf.repository.PredefinedImageTypeRepository;

@Component
public class ImageResizingServiceImpl implements ImageResizingService {

	private AmazonS3Service amazonS3Service;	

	private static final Logger LOGGER = LoggerFactory.getLogger(ImageResizingServiceImpl.class);
	private ImageResizingSourceConfiguration databaseConfiguration;
	private ImageRepository imageRepository;
	private PredefinedImageTypeRepository predefinedImageTypeRepository;


	@Autowired
	public ImageResizingServiceImpl(AmazonS3Service amazonS3Service, ImageResizingSourceConfiguration databaseConfiguration, ImageRepository imageRepository, PredefinedImageTypeRepository predefinedImageTypeRepository) {		
		this.amazonS3Service = amazonS3Service;		
		this.databaseConfiguration = databaseConfiguration;
		this.imageRepository = imageRepository;
		this.predefinedImageTypeRepository = predefinedImageTypeRepository;
	}

	@Override
	public InputStream downloadImage(String predefinedTypeName, String reference) throws CustomImageException {	
		basicValidationCheck(predefinedTypeName, reference);
		InputStream inputStream = amazonS3Service.downloadImage(predefinedTypeName, reference);		

		if (inputStream == null) {
			inputStream = optimizeOriginalImage(reference);
		}

		return inputStream;
	}


	@Override
	public Image uploadImage(String reference) {
		Image image = findImageByReference(reference);
		optimizeOriginalImage(reference);
		return image;
	}

	@Override
	public void deleteImage(String predefinedTypeName, String reference) {
		amazonS3Service.deleteImage(predefinedTypeName, reference);
	}


	private void hasPredefinedTypeName(String predefinedTypeName) {	
		if (predefinedImageTypeRepository.findByPredefinedImageTypeName(predefinedTypeName) == null) {
			LOGGER.info("action:{}, request:{}", "hasPredefinedTypeName", predefinedTypeName); 
			throw new CustomImageException(ApplicationMessageKey.NOT_FOUND);			
		}		
	}

	@Override
	public BufferedImage getOriginalImageFromS3(String reference) {				
		BufferedImage originalImageS33 = null;
		try {
			S3ObjectInputStream originalS3Image = amazonS3Service.downloadOriginalImage(reference);	
			originalImageS33 = ImageIO.read(originalS3Image);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	

		return originalImageS33;
	}

	@Override
	public BufferedImage getOriginalImageFromSource(String reference){
		BufferedImage originalImage = null;
		try {
			String absoluteFilePath = databaseConfiguration.getRootUrl() + File.separator + reference;
			originalImage = ImageIO.read(new File(absoluteFilePath));
		} catch (IOException e) {
			LOGGER.error("action:{}, request:{}", "getOriginalImageFromSource", reference); 
			throw new CustomImageException(ApplicationMessageKey.NOT_FOUND);
		}	

		return originalImage;
	}

	@Override
	public Image findImageByReference(String reference) {
		/*Image image = imageRepository.findOne(reference);
		if (image == null) {
			throw new CustomImageException(ApplicationMessageKey.NOT_FOUND);
		}*/

		Image image = new Image();
		image.setDummySeoName("LV");
		PredefinedImageType predefinedType = new PredefinedImageType();
		predefinedType.setPredefinedImageTypeName("original");
		predefinedType.setHeight(120);
		predefinedType.setWidth(120);
		predefinedType.setType("JPG");
		predefinedType.setScaleType("crop");
		predefinedType.setQuality(10);		
		image.setPredefinedType(predefinedType);
		image.setReference(reference);
		image.setPredefinedType(predefinedType);

		return image;
	}

	private boolean hasOriginalImageS3(String reference) {	
		//TODO - Implement method		
		return false;
	}

	private InputStream optimizeOriginalImage(String reference) {
		BufferedImage originalImage = null;

		if (hasOriginalImageS3(reference)) {
			originalImage = getOriginalImageFromS3(reference);
		}else {
			originalImage = getOriginalImageFromSource(reference);
		}

		if (originalImage == null) {
			throw new CustomImageException(ApplicationMessageKey.NOT_FOUND);
		} else {
			Image image = findImageByReference(reference);
			BufferedImage bufferedImage = resizeImage(originalImage, image);	

			InputStream inputStream = toInputStream(bufferedImage, image.getPredefinedType().getType());
			try {
				amazonS3Service.uploadImage(inputStream, image.getPredefinedType().getPredefinedImageTypeName(), image.getReference());
			} catch (IOException e) {
				LOGGER.warn("action:{}, request:{}", "uploadImage", reference); 
				throw new CustomImageException(ApplicationMessageKey.NOT_FOUND);
			}

			return inputStream;
		}		
	}


	private void hasImageByReference(String reference) {
		if (imageRepository.findOne(reference) == null) {
			LOGGER.info("action:{}, request:{}", "hasImageByReference", reference); 
			throw new CustomImageException(ApplicationMessageKey.NOT_FOUND);
		}
	}	

	private BufferedImage resizeImage(BufferedImage originalImage, Image image){
		BufferedImage resizedImage = new BufferedImage(image.getPredefinedType().getWidth(), 
				image.getPredefinedType().getHeight(), image.getPredefinedType().getQuality());
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, image.getPredefinedType().getWidth(), 
				image.getPredefinedType().getHeight(), null);
		g.dispose();

		return resizedImage;
	}


	private void basicValidationCheck(String predefinedTypeName, String reference) {
		hasImageByReference(reference);
		hasPredefinedTypeName(predefinedTypeName);
	}	

	private InputStream toInputStream(BufferedImage bufferedImage, String predefinedType) {
		ByteArrayOutputStream outstream = new ByteArrayOutputStream();
		try {
			ImageIO.write(bufferedImage, predefinedType, outstream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] buffer = outstream.toByteArray();
		InputStream inputStream = new ByteArrayInputStream(buffer);

		return inputStream;
	}


}