package com.ss.scrumptious_auth.service;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;

@Service
@PropertySource("classpath:aws-config.properties")
public class FileServiceImpl {
    private static final Logger LOG = LoggerFactory.getLogger(FileServiceImpl.class);

	@Autowired
	private AmazonS3 awsS3;

    // Your bucket name.
    @Value("${amazon.s3.bucket.name}")
    private String bucketName; 

    

    // Getters for parents.
    protected AmazonS3 getClient() {
        return awsS3;
    }

    
    @Async
    private String generateUrl(String fileName, HttpMethod httpMethod) {    	
    	Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, 1); // Generated URL will be valid for 24 hours
        //LOG.info("Bucket Name: " + bucketName );
        return awsS3.generatePresignedUrl(bucketName, fileName, calendar.getTime(), httpMethod).toString();

    }

    @Async
    public String save(String extension) {
        String fileName = UUID.randomUUID().toString() + extension;
        return generateUrl(fileName, HttpMethod.PUT);
    }
}
