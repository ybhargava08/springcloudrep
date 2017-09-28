package bootmain;

import org.springframework.cloud.netflix.zuul.filters.route.ZuulFallbackProvider;

public interface CustomZuulFallBack {

	//public ZuulFallbackProvider adjectiveZuulFallBack();
	
	public ZuulFallbackProvider articleZuulFallBack();
	
	public ZuulFallbackProvider nounZuulFallBack();
	
	public ZuulFallbackProvider subjectZuulFallBack();
	
	public ZuulFallbackProvider verbZuulFallBack();
	
}
