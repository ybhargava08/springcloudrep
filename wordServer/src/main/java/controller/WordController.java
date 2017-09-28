package controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class WordController {

	@Value("${words}")
	String words;
	
	@GetMapping(value="/",produces="application/json")
	public Word getWord(){
		String wordArray[] = words.split(",");
		 int i = (int)Math.round(Math.random() * (wordArray.length - 1));
		 Word word = new Word();
		 word.setWord(wordArray[i]);
		    return word;
	}
}
