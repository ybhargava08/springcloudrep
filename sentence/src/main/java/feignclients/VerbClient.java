package feignclients;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("lab-4-verb")
public interface VerbClient {

	@GetMapping("/")
	public String getWord();
	
	static class HystrixClientFallback implements VerbClient{

		@Override
		public String getWord() {
			
			return "defaultVerbInterface";
		}
		
	}
}
