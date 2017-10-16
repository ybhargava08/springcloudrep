package com.yb.retreiveandsave.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yb.retreiveandsave.service.RetreivalService;

import resourcebean.ResourceBean;

@RestController
public class RetreivalAndSaveController {
	
	private static final String APPLICATION_JSON = "application/json";
	
	@Autowired
	private RetreivalService service;
	
	@GetMapping(value="/fetchDataBasedOnInput/{input}",produces=APPLICATION_JSON)
	public List<ResourceBean> fetchDataBasedOnInput(@PathVariable String input){
		
		return service.fetchDataBasedOnInput(input);
	}
	
	@GetMapping(value="/getLatestData",produces=APPLICATION_JSON)
	public ResourceBean getLatestData(){
		return service.getLatestData();
	}
	
	@GetMapping(value="/getTagCloudData",produces=APPLICATION_JSON)
	public List<ResourceBean> getTagCloudData(){
		return service.getTagCloudData();
	}
	
	@RequestMapping(value="/save",method=RequestMethod.POST,produces=APPLICATION_JSON,consumes=APPLICATION_JSON)
	public void saveRequest(@RequestBody ResourceBean resourceBean){
		 service.saveData(resourceBean);
	}
	
	@GetMapping("/deleteAll")
	public String deleteAll(){
		service.deleteAllData();
		return "deletedAll";
	}
}
