package com.yb.retreiveandsave.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import resourcebean.ResourceBean;

@FeignClient(name="sendms",configuration=FeignJacksonEncoderConfig.class)
public interface FeignSendEventClient {
	
	@RequestMapping(value="/eventTagData",method=RequestMethod.POST,consumes="application/json",produces="application/json")
	public void sendEventTagData(List<ResourceBean> list);

}
