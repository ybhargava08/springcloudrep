package feignclients;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import feign.hystrix.FallbackFactory;

@FeignClient("lab-4-adjective")
public interface AdjectiveClient {

	
	@GetMapping("/")
	public String getWord();
	
	
	@Component
	static class HystrixClientFallbackFactory implements FallbackFactory<AdjectiveClient>{


		@Override
		public AdjectiveClient create(final Throwable cause) {
			// TODO Auto-generated method stub
			return new AdjectiveClient(){
				public String getWord(){
					return "AdjectiveClientFallbackFactory Reason is : "+cause.getMessage();
				}
			};
		}
		
	}	
}


