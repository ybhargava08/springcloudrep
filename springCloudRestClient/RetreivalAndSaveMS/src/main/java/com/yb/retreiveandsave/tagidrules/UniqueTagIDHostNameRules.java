package com.yb.retreiveandsave.tagidrules;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UniqueTagIDHostNameRules {

	private static Pattern pattern =null;
	
	public static String getHostName(String url){
		
		String output = hostNameRuleOne(url);
		
		if(null==output){
			output = hostNameRuleTwo(url);
		}
		
		return output;
	}
	
	public static String hostNameRuleOne(String url){
		
		pattern= Pattern.compile(RegexConstants.REGEX_HOSTNAME_RULE_ONE);
			
			Matcher matcher = pattern.matcher(url);
			
			if(matcher.find()){
				return removeDots(matcher.group(2),true);
			}
		
		return null;
	}
	
	private static String hostNameRuleTwo(String url){
		
		  pattern = Pattern.compile(RegexConstants.REGEX_HOSTNAME_RULE_TWO);
			
			Matcher matcher = pattern.matcher(url);
			
			if(matcher.find()){
				return "IP"+removeDots(matcher.group(1),false).replaceAll(":", "");
			}
			return null;
	}
	
	private static String removeDots(String input,boolean needFirst){
		if(input.contains(".")){
			String str[] = input.split("\\.");
			return needFirst?str[0]:str[str.length-1];
		}
		return input;
	}
	
}
