package feignclients;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;


@FeignClient(name="lab-4-article")
public interface ArticleClient {
	
	@GetMapping("/")
	public String getWord();
}
