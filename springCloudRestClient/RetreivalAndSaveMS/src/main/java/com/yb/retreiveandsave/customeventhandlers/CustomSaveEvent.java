package com.yb.retreiveandsave.customeventhandlers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import com.yb.retreiveandsave.service.RetreivalService;

import resourcebean.ResourceBean;

public class CustomSaveEvent extends ApplicationEvent{

	private List<ResourceBean> list =null;
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private RetreivalService service;

	public CustomSaveEvent(Object source,List<ResourceBean> list) {
		super(source);
		
		this.list = list;
	}
	
	public List<ResourceBean> getData(){
		return list;
	}
	

}
