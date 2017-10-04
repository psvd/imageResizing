package com.bijenkorf.service;

import com.bijenkorf.domain.Image;

public interface AmazonS3Service {	
	
	void deleteImage(String predefinedTypeName, String reference);
	void saveImage(Image optimizedImage);
	Image getImage(String predefinedTypeName, String dummySeoName, String reference);
}
