package com.yb.retreiveandsave.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import com.yb.retreiveandsave.controller.RetreivalAndSaveController;
import com.yb.retreiveandsave.mongorepository.CustomRep;
import com.yb.retreiveandsave.mongorepository.MongoTempRepo;
import com.yb.retreiveandsave.tagidrules.TagIDBean;
import com.yb.retreiveandsave.tagidrules.UniqueTagIDHostNameRules;
import com.yb.retreiveandsave.tagidrules.UniqueTagIDJSONRules;
import com.yb.retreiveandsave.tagidrules.UniqueTagIDXMLRules;
import com.yb.retreiveandsave.tstoperations.TSTOperations;

import resourcebean.ResourceBean;

@Service
@RefreshScope
public class RetreivalServiceimpl implements RetreivalService {

	@Autowired
	CustomRep customRepo;
	
	@Autowired
	MongoTempRepo mongoRepo;
	
	@Value("${useTST}")
	private boolean useTST;
	
	Logger logger = LoggerFactory.getLogger(RetreivalAndSaveController.class);
	
	@Override
	public List<ResourceBean> fetchDataBasedOnInput(String input) {
		long start=System.currentTimeMillis(),diff=0;
		input = input.replaceAll("#", "/");
		logger.info("after replacing input is : "+input);	
		List<ResourceBean> resultList=null;
		if(useTST){
			resultList =TSTOperations.getMatchedResults(input);
		}else{
			resultList= customRepo.findByTagORUrl(input);
		}
		diff = System.currentTimeMillis()-start;
		
		logger.info("time taken to return searchResult : "+diff+" ms useTST : "+useTST);
		return resultList;
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
	public List<ResourceBean> saveData(ResourceBean bean) {
		
			bean.setLastExecutionDate(System.currentTimeMillis());
		
		if(mongoRepo.saveData(bean)){
			TSTOperations.putTST(bean.getTag(), bean);
			return mongoRepo.getTagCloudData();
		}
		return null;
	}

	@Override
	@CacheEvict(value={"latestData","tagData"},allEntries=true)
	public void deleteAllData() {
	    customRepo.deleteAll();
	    TSTOperations.deleteAll();
	}

	@Override
	public TagIDBean generateUniqueTagId(ResourceBean bean) {
		try{
		String url = bean.getUrl().trim();
		String tagId=null;
		String requestBody = (null!=bean && null!=bean.getRequestBody())?bean.getRequestBody().replaceAll("\\s", ""):null;
		String hostName=UniqueTagIDHostNameRules.getHostName(url);
		if(null==requestBody || "".equalsIgnoreCase(requestBody) || requestBody.startsWith("{")){
			tagId = UniqueTagIDJSONRules.getJSONTagID(url);
		}else if(requestBody.startsWith("<")){
			tagId = UniqueTagIDXMLRules.getXMLTagID(requestBody,url);
		}
		
		if(null!=tagId && !"".equalsIgnoreCase(tagId)){
			return new TagIDBean((null!=hostName)?(tagId+"-"+hostName):tagId);
		}
		return null;
		}
		catch(Exception ex){
			return null;
		}
	}

	@Override
	@PostConstruct
	public void TSTPreprocessing() {
		if(useTST){
			logger.info("creating TST "+useTST);
			TSTOperations.addInTSTAll(customRepo.findAll());
		}else{
			logger.info("not creating TST useTST is : "+useTST);
		}
	}	
}
