package com.yb.send.feignSave;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import resourcebean.ResourceBean;

@FeignClient(name="retreivalandsavems",configuration=FeignJacksonEncoderConfig.class)
public interface FeignSaveClient {
	
	@RequestMapping(value="/save",method=RequestMethod.POST,consumes="application/json",produces="application/json")
	public List<ResourceBean> saveData(ResourceBean bean);

}
