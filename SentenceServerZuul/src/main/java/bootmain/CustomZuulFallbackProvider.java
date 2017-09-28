package bootmain;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.route.ZuulFallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;

public class CustomZuulFallbackProvider implements ZuulFallbackProvider{

	private final Logger logger = LoggerFactory.getLogger(CustomZuulFallBackImpl.class);

	private String route;
	private static final String DEFAULT = "default";
	private static HttpHeaders headers = null;
	
	public ClientHttpResponse fallbackResponse() {
		// TODO Auto-generated method stub
		return new ClientHttpResponse() {
			
			public HttpHeaders getHeaders() {
				if(null==headers){
					headers = new HttpHeaders();
					headers.setContentType(MediaType.APPLICATION_JSON);
				}
				return headers;
			}
			
			public InputStream getBody() throws IOException {
				
				return new ByteArrayInputStream(defaultPartOfSpeech().getBytes());
			}
			
			public String getStatusText() throws IOException {
				// TODO Auto-generated method stub
				return "defaultPartOfSpeech";
			}
			
			public HttpStatus getStatusCode() throws IOException {
				// TODO Auto-generated method stub
				return HttpStatus.SERVICE_UNAVAILABLE;
			}
			
			public int getRawStatusCode() throws IOException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public void close() {
				// TODO Auto-generated method stub
				
			}
		};
	}
	
	public String getRoute() {
		if(route==null || "".equals(route)){
			route="adjectiveRoute";
		}
		return route;
	}
	
	public void setRoute(String route){
		this.route = route;
	}

	
	public String defaultPartOfSpeech(){
		return "{\"word\":\""+DEFAULT+getRoute()+"\"}";
	}
}
