package testregex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestRegexURLHost {

	public static void main(String[] args) {
		
		String url = "http://10.252.34.34:9153/RxConnectWSProducerWeb/RxServices";
		
		String output = ruleOne(url);
		
		if(null==output){
			output=ruleTwo(url);
		}
		
		System.out.println(output);

	}

	private static String ruleOne(String url){
		String regex = "http://([a-zA-Z:-]+):";
		 Pattern pattern = Pattern.compile(regex);
			
			Matcher matcher = pattern.matcher(url);
			
			if(matcher.find()){
				return removeDots(matcher.group(1),true);
			}
			return null;
	}
	
	private static String ruleTwo(String url){
		String regex = "http://([0-9.:-]+)/";
		 Pattern pattern = Pattern.compile(regex);
			
			Matcher matcher = pattern.matcher(url);
			
			if(matcher.find()){
				return removeDots(matcher.group(1),false).replaceAll(":", "");
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
