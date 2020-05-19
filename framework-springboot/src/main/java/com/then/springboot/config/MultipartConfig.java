package com.then.springboot.config;

import javax.servlet.MultipartConfigElement;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import com.then.core.constants.Constants;

@Configuration
public class MultipartConfig {
	
	@Value("${spring.servlet.multipart.max-file-size:" + Constants.DEFAULT_MAX_FILE_SIZE + "}")
	private String maxFileSize;

	@Value("${spring.servlet.multipart.max-request-size:" + Constants.DEFAULT_MAX_REQUEST_SIZE + "}")
	private String maxRequestSize;
	
	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		
		factory.setMaxFileSize(DataSize.parse(maxFileSize)); // KB,MB
		factory.setMaxRequestSize(DataSize.parse(maxRequestSize));
		return factory.createMultipartConfig();
	}
}
