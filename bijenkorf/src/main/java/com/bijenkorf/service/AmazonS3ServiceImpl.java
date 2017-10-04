package com.bijenkorf.service;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.GetBucketLocationRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.bijenkorf.configuration.AmazonS3Config;
import com.bijenkorf.domain.Image;

@Component
public class AmazonS3ServiceImpl implements AmazonS3Service {

	private AmazonS3Config amazonS3Config;
	private AmazonS3 amazonS3;

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
	public void deleteImage(String predefinedTypeName, String reference) {
		// TODO Implement method to delete image from S3 bucket
	}

	@Override
	public void saveImage(Image optimizedImage) {
		//TODO Implement method to save image in AWS S3
	}	

	@Override
	public Image getImage(String predefinedTypeName, String dummySeoName, String reference) {
		// TODO Auto-generated method stub
		return null;
	}

	private AmazonS3 getClient() {
		return amazonS3;
	}	

	private static String getBucketName(String predefinedTypeName, String reference) {		
		if (reference.contains("/")) {
			reference = reference.replace("/", "_");
		}

		StringBuilder bucketName = new StringBuilder();		
		String fileNameWithOutExt = FilenameUtils.removeExtension(reference);

		bucketName.append(predefinedTypeName).append("/");

		if (fileNameWithOutExt.length() >= 4) {
			String first4Chars = fileNameWithOutExt.substring(0, 4); 
			bucketName.append(first4Chars).append("/");
		}
		if (fileNameWithOutExt.length() > 8) {
			String next4Chars = fileNameWithOutExt.substring(4, 8);
			bucketName.append(next4Chars).append("/");
		}

		bucketName.append(reference);

		return bucketName.toString();
	}
}
