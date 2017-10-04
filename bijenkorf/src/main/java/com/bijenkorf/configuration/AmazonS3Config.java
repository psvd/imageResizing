package com.bijenkorf.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonS3Config {	
	
	@Value("${aws.s3.bucketName}")
	private String bucketName;
	@Value("${aws.s3.endpoint}")
	private String endpoint;	
	@Value("${aws.accesskey}")
	private String accesskey;
	@Value("${aws.secretkey}")
	private String secretkey;

	
	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getAccesskey() {
		return accesskey;
	}

	public void setAccesskey(String accesskey) {
		this.accesskey = accesskey;
	}

	public String getSecretkey() {
		return secretkey;
	}

	public void setSecretkey(String secretkey) {
		this.secretkey = secretkey;
	}
}
