package com.yb.retreiveandsave.service;

import java.util.List;

import com.yb.retreiveandsave.tagidrules.TagIDBean;
import resourcebean.ResourceBean;

public interface RetreivalService {

	public List<ResourceBean> fetchDataBasedOnInput(String input);
	
	public ResourceBean getLatestData();
	
	//will return top n records based on use
	public List<ResourceBean> getTagCloudData();
	
	public List<ResourceBean> saveData(ResourceBean bean);
	
	public void deleteAllData();
	
	public TagIDBean generateUniqueTagId(ResourceBean bean);
	
	public void TSTPreprocessing();
}
