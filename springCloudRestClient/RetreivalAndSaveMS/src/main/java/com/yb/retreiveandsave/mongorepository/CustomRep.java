package com.yb.retreiveandsave.mongorepository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import resourcebean.ResourceBean;

public interface CustomRep extends MongoRepository<ResourceBean, String>{
 
	@Query("{'$or': [{'tag' : {$regex : ?0,$options : 'i'}} ,{'url' : {$regex : ?0,$options : 'i'}}]}")
	public List<ResourceBean> findByTagORUrl(String searchText);
	
	public ResourceBean findByTag(String tag);
	
	public List<ResourceBean> findAll();
}
