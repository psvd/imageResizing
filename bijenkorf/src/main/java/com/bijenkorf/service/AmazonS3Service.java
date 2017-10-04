package com.bijenkorf.service;

import java.awt.image.BufferedImage;
import java.io.IOException;

import com.amazonaws.services.s3.model.S3Object;

public interface AmazonS3Service {
	
	void deleteImage(String predefinedTypeName, String reference);
	void uploadImage(BufferedImage bufferedImage, String predefinedTypeName, String reference) throws IOException;
	void downloadImage(String predefinedTypeName, String reference);
	S3Object downloadOriginalImage(String reference);
	boolean isOriginalImageExist(String predefinedTypeName, String reference);	
}
