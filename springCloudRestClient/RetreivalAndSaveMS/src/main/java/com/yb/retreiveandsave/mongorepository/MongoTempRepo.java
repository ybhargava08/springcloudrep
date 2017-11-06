package com.yb.retreiveandsave.mongorepository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.WriteResult;

import resourcebean.ResourceBean;

@RefreshScope
@Repository
public class MongoTempRepo {

	Logger logger = LoggerFactory.getLogger(MongoTempRepo.class);
	
	@Value("${limit}")
	private int limit;
	
	@Autowired
	private MongoTemplate template;
	
	
	
	
	public List<ResourceBean> fetchLatestData(){
		Query query = new Query();
		query.with(new Sort(Sort.Direction.DESC,"lastExecutionDate"));
		query.limit(1);
		return template.find(query, ResourceBean.class);
	}
	
	public List<ResourceBean> getTagCloudData(){
		Query query = new Query();
		query.with(new Sort(Sort.Direction.DESC,"counts"));
		query.limit(limit);
		return template.find(query, ResourceBean.class);
	}
	
public boolean saveData(ResourceBean bean){
		
		Query query = new Query(Criteria.where("id").is(bean.getId()));
		
		Update update = new Update();
		
		update.set("tag",bean.getTag());
		update.set("url", bean.getUrl());
		update.set("requestBody", bean.getRequestBody());
		update.set("requestCategory",bean.getRequestCategory());
		update.set("headers", bean.getHeaders());
		update.set("lastExecutionDate", bean.getLastExecutionDate());
		update.set("requestType",bean.getRequestType());
		update.inc("counts", 1);
		
		WriteResult wr = template.upsert(query, update, ResourceBean.class);
		return wr.wasAcknowledged();
	}

/*public List<ResourceBean> getDataForPreprocessing(){
	Query query = new Query();
	query.fields().include("tag");
	query.fields().include("url");
	query.fields().include("requestCategory");
	query.fields().include("requestType");
	
	return template.find(query, ResourceBean.class);
	}*/
}
