package com.bijenkorf.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.bijenkorf.configuration.AmazonS3Config;
import com.bijenkorf.domain.Image;

@Component
public class AmazonS3ServiceImpl implements AmazonS3Service {

	private AmazonS3Config amazonS3Config;
	private AmazonS3 amazonS3;
	private final Logger logger;

	@Autowired
	public AmazonS3ServiceImpl(AmazonS3Config amazonS3Config) {
		this(amazonS3Config, AmazonS3ClientBuilder.standard()
				.withEndpointConfiguration(new EndpointConfiguration(amazonS3Config.getEndpoint(), amazonS3Config.getRegion()))
				.build(), LoggerFactory.getLogger(AmazonS3ServiceImpl.class));
	}
	
	protected AmazonS3ServiceImpl(AmazonS3Config amazonS3Config, AmazonS3 amazonS3,
		      Logger logger) {
		    this.amazonS3Config = amazonS3Config;
		    this.amazonS3 = amazonS3;
		    this.logger = logger;
		  }

	@Override
	public void deleteImage(String predefinedTypeName, String reference) {
		// TODO Auto-generated method stub
	}

	@Override
	public void saveImage(Image optimizedImage) {
		//String predefinedTypeName, String first4Chars, String second4Chars,
		//String uniqueOriginalImageFilename
		// TODO Auto-generated method stub

	}	

	@Override
	public Image getImage(String predefinedTypeName, String dummySeoName, String reference) {
		// TODO Auto-generated method stub
		return null;
	}

}
