package com.bijenkorf.service;

import java.io.IOException;
import java.io.InputStream;

import com.amazonaws.services.s3.model.S3ObjectInputStream;

public interface AmazonS3Service {
	
	void deleteImage(String predefinedTypeName, String reference);
	void uploadImage(InputStream file, String predefinedTypeName, String reference) throws IOException;
	S3ObjectInputStream downloadImage(String predefinedTypeName, String reference);
	S3ObjectInputStream downloadOriginalImage(String reference);
	boolean hasOriginalImageS3(String reference);	
}
