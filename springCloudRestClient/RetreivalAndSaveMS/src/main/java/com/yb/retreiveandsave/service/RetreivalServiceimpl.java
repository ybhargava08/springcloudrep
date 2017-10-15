package com.yb.retreiveandsave.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.yb.retreiveandsave.customeventhandlers.CustomSaveEvent;
import com.yb.retreiveandsave.feign.FeignSendEventClient;
import com.yb.retreiveandsave.mongorepository.CustomRep;
import com.yb.retreiveandsave.mongorepository.MongoTempRepo;

import resourcebean.ResourceBean;

@Service
public class RetreivalServiceimpl implements RetreivalService {

	@Autowired
	CustomRep customRepo;
	
	@Autowired
	MongoTempRepo mongoRepo;
	
	@Autowired
	FeignSendEventClient feignClient;
	
	@Autowired
	private ApplicationEventPublisher eventPublisher;
	
	@Override
	public List<ResourceBean> fetchDataBasedOnInput(String input) {
		 return customRepo.findByTagORUrl(input);
	}

	@Override
	@Cacheable("latestData")
	public ResourceBean getLatestData() {
		
		List<ResourceBean> list = mongoRepo.fetchLatestData();
		
		if(null!=list && !list.isEmpty()){
			return list.get(0);
		}
		
		return new ResourceBean();
	}

	@Override
	@Cacheable("tagData")
	public List<ResourceBean> getTagCloudData() {
		return mongoRepo.getTagCloudData();
	}

	@Override
	@CacheEvict(value={"latestData","tagData"},allEntries=true)
	public void saveData(ResourceBean bean) {
		
			bean.setLastExecutionDate(System.currentTimeMillis());
		
		if(mongoRepo.saveData(bean)){
			
			eventPublisher.publishEvent(new CustomSaveEvent(this,getTagCloudData()));
		}
	}

	@Override
	@CacheEvict(value={"latestData","tagData"},allEntries=true)
	public void deleteAllData() {
	    customRepo.deleteAll();
	    eventPublisher.publishEvent(new CustomSaveEvent(this,null));
	}

	@Override
	public void sendEventTagData() {
		feignClient.sendEventTagData(mongoRepo.getTagCloudData());
	}
	
}
