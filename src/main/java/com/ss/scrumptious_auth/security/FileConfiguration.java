package com.ss.scrumptious_auth.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.ss.scrumptious_auth.service.FileServiceImpl;

@Configuration
@PropertySource("classpath:aws-config.properties")
public class FileConfiguration {
    private static final Logger LOG = LoggerFactory.getLogger(FileServiceImpl.class);

	// The IAM access key.
    @Value("${amazon.s3.access.key}")
    private String accessKey; 

    // The IAM secret key.
    @Value("${amazon.s3.secret.key}")
    private String secretKey; 
    
    // The bucket region name.
    @Value("${amazon.s3.region.name}")
    private String s3RegionName;
    
    @Bean
    public AmazonS3 getAmazonS3Client() {
    	//LOG.info("Access: " + accessKey + " Secret: " + secretKey);
        final BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(accessKey, secretKey);
        
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        clientConfiguration.setSignerOverride("AWSS3V4SignerType");
        // Get Amazon S3 client and return the S3 client object
        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                .withRegion(s3RegionName)
                .withClientConfiguration(clientConfiguration)
                .build();
    }
}

