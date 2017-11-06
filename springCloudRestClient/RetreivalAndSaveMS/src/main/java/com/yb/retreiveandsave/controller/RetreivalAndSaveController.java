package com.yb.retreiveandsave.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.yb.retreiveandsave.service.RetreivalService;
import com.yb.retreiveandsave.tagidrules.TagIDBean;

import resourcebean.ResourceBean;

@RestController
public class RetreivalAndSaveController {
	
	Logger logger = LoggerFactory.getLogger(RetreivalAndSaveController.class);
	
	@Autowired
	private RetreivalService service;
	
	@RequestMapping(value="/fetchDataBasedOnInput",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE)
	public List<ResourceBean> fetchDataBasedOnInput(@RequestBody String input){
		logger.info("url received from path input : "+input);
		return service.fetchDataBasedOnInput(input);
	}
	
	@GetMapping(value="/getLatestData",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResourceBean getLatestData(){
		return service.getLatestData();
	}
	
	@GetMapping(value="/getTagCloudData",produces=MediaType.APPLICATION_JSON_VALUE)
	public List<ResourceBean> getTagCloudData(){
		return service.getTagCloudData();
	}
	
	@RequestMapping(value="/save",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public List<ResourceBean> saveRequest(@RequestBody ResourceBean resourceBean){
		 return service.saveData(resourceBean);
	}
	
	@GetMapping("/deleteAll")
	public String deleteAll(){
		service.deleteAllData();
		return "deletedAll";
	}
	
	@RequestMapping(value="/generateUniqueTagId",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public TagIDBean generateUniqueTagId(@RequestBody ResourceBean bean){
		return service.generateUniqueTagId(bean);
	}
}
