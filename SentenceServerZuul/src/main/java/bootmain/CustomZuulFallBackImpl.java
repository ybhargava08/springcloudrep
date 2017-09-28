package bootmain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.route.ZuulFallbackProvider;
import org.springframework.context.annotation.Bean;

public class CustomZuulFallBackImpl implements CustomZuulFallBack{

	private final Logger logger = LoggerFactory.getLogger(CustomZuulFallBackImpl.class);
	/*@Bean
	public ZuulFallbackProvider adjectiveZuulFallBack() {
		CustomZuulFallbackProvider fallBackProvider = new CustomZuulFallbackProvider();
		fallBackProvider.setRoute("adjectiveRoute");
		return fallBackProvider;
	}*/

	@Bean
	public ZuulFallbackProvider articleZuulFallBack() {
		CustomZuulFallbackProvider fallBackProvider = new CustomZuulFallbackProvider();
		fallBackProvider.setRoute("article");
		return fallBackProvider;
	}

	@Bean
	public ZuulFallbackProvider nounZuulFallBack() {
		CustomZuulFallbackProvider fallBackProvider = new CustomZuulFallbackProvider();
		fallBackProvider.setRoute("noun");
		return fallBackProvider;
	}

	@Bean
	public ZuulFallbackProvider subjectZuulFallBack() {
		CustomZuulFallbackProvider fallBackProvider = new CustomZuulFallbackProvider();
		fallBackProvider.setRoute("subject");
		return fallBackProvider;
	}

	@Bean
	public ZuulFallbackProvider verbZuulFallBack() {
		CustomZuulFallbackProvider fallBackProvider = new CustomZuulFallbackProvider();
		fallBackProvider.setRoute("verb");
		return fallBackProvider;
	}

}
