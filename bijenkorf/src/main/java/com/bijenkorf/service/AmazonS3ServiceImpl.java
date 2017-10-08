package com.bijenkorf.service;

import java.io.IOException;
import java.io.InputStream;

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
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
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
	public S3ObjectInputStream downloadImage(String predefinedTypeName, String reference) {
		return downloadImageFromS3(predefinedTypeName, reference);
	}

	@Override
	public void deleteImage(String predefinedTypeName, String reference) {
		amazonS3Delete(getDirectoryStructure(predefinedTypeName, reference));
	}

	@Override
	public void uploadImage(InputStream file, String predefinedTypeName, String reference) throws IOException {	
		amazonS3Put(getDirectoryStructure(predefinedTypeName, reference), file);
	}	

	@Override
	public boolean hasOriginalImageS3(String reference) {
		if (downloadImageFromS3("öriginal", reference) != null) {
			return true;			
		}
		return false;
	}

	@Override
	public S3ObjectInputStream downloadOriginalImage(String reference) {
		return downloadImageFromS3("original", reference);
	}

	private S3ObjectInputStream downloadImageFromS3(String predefinedTypeName, String reference) {
		S3Object s3object = getClient().getObject(amazonS3Config.getBucketName(), getDirectoryStructure(predefinedTypeName, reference));
		S3ObjectInputStream inputStream = s3object.getObjectContent();
		return inputStream;
	}


	private void amazonS3Put(String keyName, InputStream resizedImage) {
		ObjectMetadata meta = new ObjectMetadata();		
		runS3Command(keyName, (client) -> {					
			LOGGER.info("Uploading a new object to S3 from a file: " + keyName);
			client.putObject(amazonS3Config.getBucketName(), keyName, resizedImage, meta);
		});
	}

	private void amazonS3Delete(String keyName) {
		runS3Command(keyName, (client) -> {
			LOGGER.info("Deleting an object from S3: " + keyName);
			client.deleteObject(new DeleteObjectRequest(amazonS3Config.getBucketName(), keyName));
		});
	}

	private AmazonS3 getClient() {
		return amazonS3;
	}

	private interface S3Command {
		public void run(AmazonS3 client);
	}

	private void runS3Command(String keyName, S3Command command) {

		AmazonS3 s3Client = getClient();
		try {
			LOGGER.info("Running an S3 command:");
			command.run(s3Client);

		} catch (AmazonServiceException ase) {
			LOGGER.error("Caught an AmazonServiceException, which means your request made it "
					+ "to Amazon S3, but was rejected with an error response for some reason.");
			LOGGER.error("Error Message:    " + ase.getMessage());
			LOGGER.error("HTTP Status Code: " + ase.getStatusCode());
			LOGGER.error("AWS Error Code:   " + ase.getErrorCode());
			LOGGER.error("Error Type:       " + ase.getErrorType());
			LOGGER.error("Request ID:       " + ase.getRequestId());
			//TRHOW NEW EXCEPTION			
		} catch (AmazonClientException ace) {
			LOGGER.error("Caught an AmazonClientException, which means the client encountered "
					+ "an internal error while trying to communicate with S3, "
					+ "such as not being able to access the network.");
			LOGGER.error("Error Message: " + ace.getMessage());
			//TRHOW NEW EXCEPTION
		}
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
