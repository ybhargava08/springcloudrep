package com.yb.send.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yb.send.service.SendService;

import resourcebean.ResourceBean;

@RestController
public class SendMSController {

	
	@Autowired
	private SendService service;
	
	
	@RequestMapping(value="/sendRequest",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResourceBean sendRequest(@RequestBody ResourceBean resourceBean){
		return service.sendHttpRequest(resourceBean);
	}
	
	@RequestMapping(value="/save",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResourceBean save(@RequestBody ResourceBean bean){
		return service.save(bean);
	}
	
}
