package com.yb.send.service;

import java.util.List;

import resourcebean.ResourceBean;

public interface SendService {

	public ResourceBean sendHttpRequest(ResourceBean bean);
	public ResourceBean save(ResourceBean bean);
    public void sendEventTagData(List<ResourceBean> list);
}
