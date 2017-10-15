package com.yb.retreiveandsave.customeventhandlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.yb.retreiveandsave.feign.FeignSendEventClient;

@Component
public class CustomSaveEventListener implements ApplicationListener<CustomSaveEvent>{
    
	Logger logger = LoggerFactory.getLogger(CustomSaveEventListener.class);
	
	@Autowired
	FeignSendEventClient feignClient;
	
	@Override
	public void onApplicationEvent(CustomSaveEvent event) {
		try{
		feignClient.sendEventTagData(event.getData());
		}catch(Exception ex){
			logger.error("Some error occured while publishing event to sendMS . Please check whether SendMS service is up and running: "+ex.getMessage());
		}
	}

}
