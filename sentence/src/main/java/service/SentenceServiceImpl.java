package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import feignclients.AdjectiveClient;
import feignclients.ArticleClient;
import feignclients.NounClient;
import feignclients.SubjectClient;
import feignclients.VerbClient;

@Service
public class SentenceServiceImpl implements SentenceService{

	@Autowired
	AdjectiveClient adjectiveClientService;
	@Autowired
	ArticleClient articleClientService;
	@Autowired
	NounClient nounClientService;
	@Autowired
	SubjectClient subjectClientService;
	@Autowired
	VerbClient verbClientService;
	
	@Override
	@HystrixCommand(fallbackMethod="getAdjectiveFallback")
	public String getAdjective() {
		return adjectiveClientService.getWord();
	}

	public String getAdjectiveFallback(){
		return "defaultAdjective";
	}
	
	@Override
	@HystrixCommand(fallbackMethod="getArticleFallback")
	public String getArticle() {
		
		return articleClientService.getWord();
	}

	public String getArticleFallback(){
		return "defaultArticle";
	}
	
	@Override
	@HystrixCommand(fallbackMethod="getNounFallback")
	public String getNoun() {
		
		return nounClientService.getWord();
	}
	
	public String getNounFallback(){
		return "defaultNoun";
	}

	@Override
	@HystrixCommand(fallbackMethod="getSubjectFallback")
	public String getSubject() {
		
		return subjectClientService.getWord();
	}

	
	public String getSubjectFallback(){
		return "defaultSubject";
	}
	@Override
	@HystrixCommand(fallbackMethod="getVerbFallback")
	public String getVerb() {
		return verbClientService.getWord();
	}
	
	public String getVerbFallback(){
		return "defaultVerb";
	}
}
