package testregex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestRegexUrlJson {

	public static void main(String[] args) {

		  String input ="http://10.18.71.45:8105/RxCRestFulWSProducer/resources/retailPharmacyNotificationService/processActionNoteDeliveryFailure";
		   
		    String output = ruleOne(input);
		    
		    if(null==output || output.length()<5){
		    	output = ruleTwo(input);
		    }
		    
		    if(null==output || output.length()<5){
		    	output = ruleThree(input);
		    }
		    
		    System.out.println(output);
		    
			}
	
	private static String ruleOne(String url){
		String regex = "/([a-zA-Z0-9]+)$";
		 Pattern pattern = Pattern.compile(regex);
			
			Matcher matcher = pattern.matcher(url);
			
			if(matcher.find()){
				return matcher.group(1);
			}
			return null;
	}
	
	private static String ruleTwo(String url){
		String regex = "/([a-zA-Z0-9]+)/([a-zA-Z0-9]+)$";
		 Pattern pattern = Pattern.compile(regex);
			
			Matcher matcher = pattern.matcher(url);
			
			if(matcher.find()){
				return matcher.group(1)+matcher.group(2);
			}
			return null;
	}

	private static String ruleThree(String url){
		String regex = "(?i)resources/(.+)$";
		 Pattern pattern = Pattern.compile(regex);
			
			Matcher matcher = pattern.matcher(url);
			
			if(matcher.find()){
				return matcher.group(1).replaceAll("/", "");
			}
			return null;
	}
	}

