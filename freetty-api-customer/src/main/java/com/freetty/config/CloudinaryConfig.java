package com.freetty.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Configuration
public class CloudinaryConfig {
	@Value("${cloudinary.name}")
	private String name;

	@Value("${cloudinary.apiKey}")
	private String apiKey;

	@Value("${cloudinary.apiSecret}")
	private String apiSecret;
	
	@Bean
	public Cloudinary cloudinary() {
		return new Cloudinary(ObjectUtils.asMap("cloud_name", name, "api_key", apiKey, "api_secret", apiSecret));
	}

}
