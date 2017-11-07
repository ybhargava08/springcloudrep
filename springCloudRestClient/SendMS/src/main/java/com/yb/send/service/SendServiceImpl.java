package com.yb.send.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.yb.send.feignSave.FeignSaveClient;

import resourcebean.ResourceBean;

@Service
public class SendServiceImpl implements SendService {

	@Autowired
    private FeignSaveClient saveClient;
	
	@Autowired
	private SimpMessagingTemplate template;
	
	private static final String EMPTY_STRING="";
	
	@Override
	@HystrixCommand(defaultFallback="defaultFallBack")
	public ResourceBean sendHttpRequest(ResourceBean bean) {
		if("get".equalsIgnoreCase(bean.getRequestType())){
			return sendGetRequest(bean);
		}else{
			return sendPostRequest(bean);
		}
	}

	private ResourceBean  sendPostRequest(ResourceBean bean){
		HttpClient client = null;
		boolean containsAuthCredentials = containsAuthCred(bean);
		if(containsAuthCredentials){
			CredentialsProvider provider = new BasicCredentialsProvider();
			UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(getUserNamePassword(bean,"username"), getUserNamePassword(bean,"password"));
			provider.setCredentials(AuthScope.ANY, credentials);
			client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
			bean.setUrlCred(null);
		}else{
			client = HttpClientBuilder.create().build();
		}
		HttpPost httpPost =  new HttpPost(bean.getUrl().trim());
		InputStream is=null;
		try{
			if(null!=bean.getRequestBody() && !EMPTY_STRING.equalsIgnoreCase(bean.getRequestBody())){
			 is = new ByteArrayInputStream(
	        		bean.getRequestBody().getBytes());
			 InputStreamEntity reqEntity = new InputStreamEntity(is, -1);
				reqEntity.setChunked(true);
				httpPost.setEntity(reqEntity);
			}
			httpPost.setHeaders(createHttpHeaders(bean.getHeaders().trim()));
			RequestConfig config = RequestConfig.custom().setSocketTimeout(1500).setConnectTimeout(1500).setConnectionRequestTimeout(5000).build();
			httpPost.setConfig(config);
			HttpResponse response  = client.execute(httpPost);
			HttpEntity entity = response.getEntity();
			String resp  = EntityUtils.toString(entity,"UTF-8");
			if(!StringUtils.isEmpty(resp)){
				bean.setResponseBody(resp.trim());
			}
			bean.setStatusCodeAndReason(String.valueOf(response.getStatusLine().getStatusCode())+"  "+response.getStatusLine().getReasonPhrase());			
			if(200==response.getStatusLine().getStatusCode()){
				bean.setLastExecutionDate(System.currentTimeMillis());
				String requestCategory = bean.getRequestBody().startsWith("{")?"json":"xml";
				bean.setRequestCategory(requestCategory);
				persistData(bean);
			}
			return bean;
		}catch(Exception ex){
			ex.printStackTrace();
			 bean.setStatusCodeAndReason(String.valueOf(500)+"  ERROR");
				bean.setResponseBody("Some Error Occured . Please Retry");
		}finally{
			try {
				if(null!=is)
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				 bean.setStatusCodeAndReason(String.valueOf(500)+"  ERROR");
					bean.setResponseBody("Some Error Occured . Please Retry");
			}
		}
		return bean;
	}
	
	public ResourceBean defaultFallBack(){
		
		 ResourceBean bean = new ResourceBean();
		 bean.setStatusCodeAndReason(String.valueOf(500)+"  ERROR");
		bean.setResponseBody("Some Error Occured . Please Retry");
		return bean;
	}
	
	@HystrixCommand(defaultFallback="defaultFallBack")
	private ResourceBean sendGetRequest(ResourceBean bean){
		HttpClient client = null;
		boolean containsAuthCredentials = containsAuthCred(bean);
		if(containsAuthCredentials){
			CredentialsProvider provider = new BasicCredentialsProvider();
			UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(getUserNamePassword(bean,"username"), getUserNamePassword(bean,"password"));
			provider.setCredentials(AuthScope.ANY, credentials);
			client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
			bean.setUrlCred(null);
		}else{
			client = HttpClientBuilder.create().build();
		}
		HttpGet httpGet =  new HttpGet(bean.getUrl().trim());
		try{
			RequestConfig config = RequestConfig.custom().setSocketTimeout(1500).setConnectTimeout(1500).setConnectionRequestTimeout(5000).build();
			httpGet.setConfig(config);
			httpGet.setHeaders(createHttpHeaders(bean.getHeaders().trim()));
			HttpResponse response  = client.execute(httpGet);
			HttpEntity entity = response.getEntity();
			String resp  = EntityUtils.toString(entity, "UTF-8");
			if(!StringUtils.isEmpty(resp)){
				bean.setResponseBody(resp.trim());
				
			}
			bean.setStatusCodeAndReason(String.valueOf(response.getStatusLine().getStatusCode())+"  "+response.getStatusLine().getReasonPhrase());
		
			if(200==response.getStatusLine().getStatusCode()){
				bean.setLastExecutionDate(System.currentTimeMillis());
				bean.setRequestCategory("");
				persistData(bean);
			}
			return bean;
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{}
		return bean;
	}
	
	private Header[] createHttpHeaders(String headers){
		Header[] header = null;
		if(headers.contains("\n")){
			StringTokenizer stk = new StringTokenizer("\n");
		       header = new Header[stk.countTokens()];
			int i=0;
			if(stk.hasMoreTokens()){
				String headerString = stk.nextToken().trim();
				header[i] = new BasicHeader(headerString.split(":")[0].trim(), headerString.split(":")[1].trim());
				i++;
			}
		}else{
			header=new Header[1];
			header[0] = new BasicHeader(headers.split(":")[0].trim(), headers.split(":")[1].trim());
		}
		
		return header;
	}

  @Async	
  public void persistData(ResourceBean bean){
	  template.convertAndSend("/topic/eventMostFrequentPoller", saveClient.saveData(bean));
  }
  

@Override
public ResourceBean save(ResourceBean bean) {
	createHttpHeaders(bean.getHeaders());
	List<ResourceBean> list = saveClient.saveData(bean);
	bean.setResponseBody("response is good");
	bean.setStatusCodeAndReason(String.valueOf(200)+"  good");
	sendEventTagData(list);
	return bean;
}

@Override
@Async
public void sendEventTagData(List<ResourceBean> list) {
	template.convertAndSend("/topic/eventMostFrequentPoller", list);
}



/*private boolean compareLists(List<ResourceBean> oldList,List<ResourceBean> newList){

    
   Equator<ResourceBean> eq = new Equator<ResourceBean>() {

		@Override
		public boolean equate(ResourceBean beanone, ResourceBean beantwo) {
			
			return (beanone.getTag().equalsIgnoreCase(beantwo.getTag()));
		}

		@Override
		public int hash(ResourceBean arg0) {
			// TODO Auto-generated method stub
			return 0;
		}
		
	};
	
	return CollectionUtils.isEqualCollection(oldList, newList,eq);
}*/

private boolean containsAuthCred(ResourceBean bean){
	if(bean.getUrlCred()!=null && !EMPTY_STRING.equalsIgnoreCase(bean.getUrlCred())){
		return true;
	}
	return false;
}

private String getUserNamePassword(ResourceBean bean,String param){
	
	String cred = bean.getUrlCred();
	
	String[] paramArray = cred.split(",");
	
	if(paramArray.length>1 && "username".equalsIgnoreCase(param)){
		return paramArray[0];
	}else if(paramArray.length>1 && "password".equalsIgnoreCase(param)){
		return paramArray[1];
	}
	return "";
}
}
