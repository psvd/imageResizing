package com.bijenkorf.service;

import java.awt.image.BufferedImage;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.bijenkorf.configuration.AmazonS3Config;

@Component
public class AmazonS3ServiceImpl implements AmazonS3Service {

	private AmazonS3Config amazonS3Config;
	private AmazonS3 amazonS3;
	private static final Logger LOGGER = LoggerFactory.getLogger(AmazonS3ServiceImpl.class);

	@Autowired
	public AmazonS3ServiceImpl(AmazonS3Config amazonS3Config) {
		this(amazonS3Config, AmazonS3ClientBuilder.standard()
				.withEndpointConfiguration(new EndpointConfiguration(amazonS3Config.getEndpoint(), null))
				.build(), LoggerFactory.getLogger(AmazonS3ServiceImpl.class));
	}

	protected AmazonS3ServiceImpl(AmazonS3Config amazonS3Config, AmazonS3 amazonS3,
			Logger logger) {
		this.amazonS3Config = amazonS3Config;
		this.amazonS3 = amazonS3;
	}

	@Override
	public void downloadImage(String predefinedTypeName, String reference) {
		try {


		} catch (AmazonServiceException ase) {
			LOGGER.error("Caught an AmazonServiceException from GET requests, rejected reasons:");
			LOGGER.error("Error Message:    " + ase.getMessage());
			LOGGER.error("HTTP Status Code: " + ase.getStatusCode());
			LOGGER.error("AWS Error Code:   " + ase.getErrorCode());
			LOGGER.error("Error Type:       " + ase.getErrorType());
			LOGGER.error("Request ID:       " + ase.getRequestId());
		} catch (AmazonClientException ace) {
			LOGGER.error("Caught an AmazonClientException: ");
			LOGGER.error("Error Message: " + ace.getMessage());
		}

	}

	@Override
	public void deleteImage(String predefinedTypeName, String reference) {
		// TODO Auto-generated method stub
	}

	@Override
	public void uploadImage(BufferedImage bufferedImage, String predefinedTypeName, String reference) throws IOException {	
		// TODO Auto-generated method stub
	}	

	@Override
	public boolean isOriginalImageExist(String predefinedTypeName, String reference) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public S3Object downloadOriginalImage(String reference) {
		// TODO Auto-generated method stub
		return null;
	}

	private AmazonS3 getClient() {
		return amazonS3;
	}


	/**
	 * Method to define a AWS S3 directory structure
	 **/
	private static String getDirectoryStructure(String predefinedTypeName, String reference) {		
		if (reference.contains("/")) {
			reference = reference.replace("/", "_");
		}

		StringBuilder directoryStructure = new StringBuilder();		
		String fileNameWithOutExt = FilenameUtils.removeExtension(reference);

		directoryStructure.append(predefinedTypeName).append("/");

		if (fileNameWithOutExt.length() >= 4) {
			String first4Chars = fileNameWithOutExt.substring(0, 4); 
			directoryStructure.append(first4Chars).append("/");
		}
		if (fileNameWithOutExt.length() > 8) {
			String next4Chars = fileNameWithOutExt.substring(4, 8);
			directoryStructure.append(next4Chars).append("/");
		}		
		
		directoryStructure.append(reference);

		return directoryStructure.toString();
	}

}
