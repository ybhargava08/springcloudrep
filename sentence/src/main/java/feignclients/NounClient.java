package feignclients;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("lab-4-noun")
public interface NounClient {

	@GetMapping("/")
	public String getWord();
	
	static class HystrixClientFallback implements NounClient{

		@Override
		public String getWord() {
			
			return "defaultNounInterface";
		}
		
	}
}
