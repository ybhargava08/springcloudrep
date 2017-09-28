package bootmain;

import javax.servlet.Filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.route.ZuulFallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

@SpringBootApplication
@EnableZuulProxy
@ComponentScan({"bootmain","controller"})
public class Application {

	private final Logger logger = LoggerFactory.getLogger(CustomZuulFallBackImpl.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

	}

	
	public Filter shallowEtagHeaderFilter(){
		return new ShallowEtagHeaderFilter();
	}
	
	@Bean
	public ZuulFallbackProvider adjectiveZuulFallBack() {
		CustomZuulFallbackProvider fallBackProvider = new CustomZuulFallbackProvider();
		fallBackProvider.setRoute("adjectiveRoute");
		return fallBackProvider;
	}
	
}
