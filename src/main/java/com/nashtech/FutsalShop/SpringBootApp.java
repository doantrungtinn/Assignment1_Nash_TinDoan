package com.nashtech.FutsalShop;

import com.cloudinary.Cloudinary;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class SpringBootApp {
	public static void main(String[] args) {
		SpringApplication.run(SpringBootApp.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public Cloudinary cloudinaryConfig() {
		Cloudinary cloudinary;
		Map<String, String> config = new HashMap<>();
		config.put("cloud_name", "dx3dgugip");
		config.put("api_key", "289632383831392");
		config.put("api_secret", "3vGTPHBt1V_MUbCPUb8zlQDgyCw");
		cloudinary = new Cloudinary(config);
		return cloudinary;
	}
}



