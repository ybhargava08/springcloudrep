package com.yb.retreiveandsave.tagidrules;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UniqueTagIDJSONRules {

	 private static Pattern pattern = null;
	
	public static String getJSONTagID(String url){
		 String output = ruleOne(url);
		    
		    if(null==output || output.length()<5){
		    	output = ruleTwo(url);
		    }
		    
		    if(null==output || output.length()<5){
		    	output = ruleThree(url);
		    }
		    
		    return output;
	}
	 
	private static String ruleOne(String url){
		
		  pattern = Pattern.compile(RegexConstants.REGEX_JSON_RULE_ONE);
			
			Matcher matcher = pattern.matcher(url);
			
			if(matcher.find()){
				return matcher.group(1);
			}
			return null;
	}
	
	private static String ruleTwo(String url){
		  pattern = Pattern.compile(RegexConstants.REGEX_JSON_RULE_TWO);
			
			Matcher matcher = pattern.matcher(url);
			
			if(matcher.find()){
				return matcher.group(1)+matcher.group(2);
			}
			return null;
	}

	private static String ruleThree(String url){

		  pattern = Pattern.compile(RegexConstants.REGEX_JSON_RULE_THREE);
			
			Matcher matcher = pattern.matcher(url);
			
			if(matcher.find()){
				return matcher.group(1).replaceAll("/", "");
			}
			return null;
	}
}
