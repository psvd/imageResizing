package com.bijenkorf.service;

import com.bijenkorf.domain.Image;

public interface AmazonS3Service {	
	
	void deleteImage(String predefinedTypeName, String reference);
	void saveImage(Image optimizedImage);
	byte[] getImage(String predefinedTypeName, String dummySeoName, String reference);
}
