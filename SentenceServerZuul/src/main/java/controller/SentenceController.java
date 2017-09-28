package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import bootmain.CustomZuulFallBackImpl;

@Controller
public class SentenceController {

	private final Logger logger = LoggerFactory.getLogger(CustomZuulFallBackImpl.class);

	@GetMapping("/sentence")
	public String getSentence(){
		return "sentence";	
		}
}
