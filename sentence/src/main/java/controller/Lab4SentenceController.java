package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import service.SentenceService;

@RestController
public class Lab4SentenceController {
	
	@Autowired
	DiscoveryClient discoveryClient;
	@Autowired
	SentenceService sentenceService;
	
	@GetMapping("/sentence")
	//@HystrixCommand(fallbackMethod="getSentenceFallback")
	public String getSentence(){
	     /*return getWord("lab-4-subject")+ " "+getWord("lab-4-verb")+ " "+getWord("lab-4-article")
	    		 + " "+getWord("lab-4-adjective")sentenceService.getAdjective()+ " "+getWord("lab-4-noun")+ " .";*/
		
		return sentenceService.getSubject()+" "+sentenceService.getVerb()+" "+sentenceService.getArticle()+" "+sentenceService.getAdjective()+" "+
		sentenceService.getNoun()+" .";
		
	}
	
	public String getSentenceFallback(){
		return "This is a default sentence";
	}
	
	/*private String getWord(String service){
		List<ServiceInstance> serviceList = discoveryClient.getInstances(service.toUpperCase());
		
		if(null!=serviceList && serviceList.size()>0){
			URI uri = serviceList.get(0).getUri();
			
			if(null!=uri){
				return (new RestTemplate()).getForObject(uri, String.class); 
			}
		}
		return null;
	}*/
	
	
	
}
