package com.yb.send.feignSave;

import org.springframework.context.annotation.Bean;

import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;

public class FeignJacksonEncoderConfig {

	@Bean
	public Encoder encoder(){
		return new JacksonEncoder();
	}
	
	@Bean
	public Decoder decoder(){
		return new JacksonDecoder();
	}
}
