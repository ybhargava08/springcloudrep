package feignclients;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("lab-4-subject")
public interface SubjectClient {

	@GetMapping("/")
	public String getWord();
	
	static class HystrixClientFallback implements SubjectClient{

		@Override
		public String getWord() {
			
			return "defaultSubjectInterface";
		}
		
	}
}
