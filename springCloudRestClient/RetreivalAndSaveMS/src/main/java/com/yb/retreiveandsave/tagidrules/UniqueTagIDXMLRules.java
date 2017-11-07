package com.yb.retreiveandsave.tagidrules;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UniqueTagIDXMLRules {

	private static Pattern pattern =null;
	
	public static String getXMLTagID(String requestBody,String url){
		String output = ruleone(requestBody);
				
				if(null==output){
					output = ruleTwo(requestBody);
				}
				
				if(null==output){
					output = ruleThree(requestBody);
				}	
				
				if(null!=output){
					String ruleForUrlOutput = ruleForUrl(url);
					output = (null!=ruleForUrlOutput && !"".equalsIgnoreCase(ruleForUrlOutput))?(ruleForUrlOutput+output):output;
				}
				
				return output;
	}
	
	private  static String ruleone(String input){
		
		 pattern = Pattern.compile(RegexConstants.REGEX_XML_RULE_ONE);
		
		Matcher matcher = pattern.matcher(input);
		
		if(matcher.find()){
		return filterColon(matcher.group(2));
		}
		return null;
	}
	
	private  static String ruleTwo(String input){
		
		 pattern = Pattern.compile(RegexConstants.REGEX_XML_RULE_TWO);
		
		Matcher matcher = pattern.matcher(input);
		
		if(matcher.find()){
			return filterColon(matcher.group(1));
			}
			return null;
		
	}
	
	private static String ruleThree(String input){
		
		 pattern = Pattern.compile(RegexConstants.REGEX_XML_RULE_THREE);
		
		Matcher matcher = pattern.matcher(input);
		
		if(matcher.find()){
			return filterColon(matcher.group(1));
			}
			return null;
	}
	
	private static String ruleForUrl(String url){
		 pattern = Pattern.compile(RegexConstants.REGEX_XML_URL_RULE);
		 
		 Matcher matcher = pattern.matcher(url);
		 
		 if(matcher.find()){
			 return matcher.group(1)+"-";
		 }
		 return null;
	}
	
	private static String filterColon(String input){
		if(input.contains(":")){
			String str[] = input.split(":");
			return str[str.length-1];
		}
		return input;
	}
}
