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
import com.bijenkorf.configuration.DatabaseConfiguration;
import com.bijenkorf.domain.Image;
import com.bijenkorf.enumerator.ApplicationMessageKey;
import com.bijenkorf.exception.CustomImageException;
import com.bijenkorf.repository.ImageRepository;
import com.bijenkorf.repository.PredefinedImageTypeRepository;

@Component
public class ImageResizingServiceImpl implements ImageResizingService {

	private AmazonS3Service amazonS3Service;	

	private static final Logger LOGGER = LoggerFactory.getLogger(ImageResizingServiceImpl.class);
	private DatabaseConfiguration databaseConfiguration;
	private ImageRepository imageRepository;
	private PredefinedImageTypeRepository predefinedImageTypeRepository;



	@Autowired
	public ImageResizingServiceImpl(AmazonS3Service amazonS3Service, DatabaseConfiguration databaseConfiguration, ImageRepository imageRepository, PredefinedImageTypeRepository predefinedImageTypeRepository) {		
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
			optimizeOriginalImage(reference);
		}

		return inputStream;
	}


	@Override
	public void uploadImage(BufferedImage bufferedImage, Image image) throws CustomImageException {		
		try {
			ByteArrayOutputStream outstream = new ByteArrayOutputStream();
			ImageIO.write(bufferedImage, image.getPredefinedType().getType().toString(), outstream);
			byte[] buffer = outstream.toByteArray();
			InputStream inputStream = new ByteArrayInputStream(buffer);
			amazonS3Service.uploadImage(inputStream, image.getPredefinedType().getSourceName(), image.getReference());
		} catch (IOException e) {
			LOGGER.warn ("action:{}, request:{}", "uploadImage", image.getReference()); 
			throw new CustomImageException(ApplicationMessageKey.NOT_FOUND);
		} 
	}

	@Override
	public void deleteImage(String predefinedTypeName, String reference) {
		amazonS3Service.deleteImage(predefinedTypeName, reference);
	}


	@Override
	public void hasPredefinedTypeName(String predefinedTypeName) throws CustomImageException {	

		if (predefinedImageTypeRepository.findByPredefinedImageTypeName(predefinedTypeName) == null) {
			LOGGER.info("action:{}, request:{}", "hasPredefinedTypeName", predefinedTypeName); 
			throw new CustomImageException(ApplicationMessageKey.NOT_FOUND);			
		}		
	}

	@Override
	public BufferedImage downloadOriginalImageS3(String reference) throws CustomImageException {				
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
	public BufferedImage downloadOriginalImage(String reference) throws CustomImageException {
		BufferedImage originalImage = null;
		try {
			String absoluteFilePath = databaseConfiguration.getEndpoint() + File.separator + reference;
			originalImage = ImageIO.read(new File(absoluteFilePath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	

		return originalImage;
	}

	private boolean hasOriginalImageS3(String reference) {	
		return amazonS3Service.hasOriginalImageS3(reference);
	}

	private void optimizeOriginalImage(String reference) throws CustomImageException {
		BufferedImage originalImage = null;
		
		if (hasOriginalImageS3(reference)) {
			originalImage = downloadOriginalImageS3(reference);
		}else {
			originalImage = downloadOriginalImage(reference);
		}
		
		if (originalImage == null) {
			throw new CustomImageException(ApplicationMessageKey.NOT_FOUND);
		} else {
			Image image = findImageByReference(reference);
			BufferedImage bufferedImage = resizeImage(originalImage, image);	

			uploadImage(bufferedImage, image);
		}		
	}

	@Override
	public void hasImageByReference(String reference) throws CustomImageException {

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


	private void basicValidationCheck(String predefinedTypeName, String reference) throws CustomImageException {
		hasImageByReference(reference);
		hasPredefinedTypeName(predefinedTypeName);
	}

	@Override
	public Image findImageByReference(String reference) {
		Image image = imageRepository.findOne(reference);
		return image;
	}
}