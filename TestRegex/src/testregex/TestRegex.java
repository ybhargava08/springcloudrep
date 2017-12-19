package testregex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestRegex {

	public static final String REGEX_HOSTNAME_RULE_ONE = "(?i)(http|https)://([a-zA-Z.-]+)";
	public static final String REGEX_HOSTNAME_RULE_TWO = "(?i)(http|https)://([0-9.:-]+)/";
	public static final String REGEX_JSON_RULE_ONE = "/([a-zA-Z0-9]+)$";
	
	public static void main(String[] args) {
		Pattern pattern = Pattern.compile(REGEX_JSON_RULE_ONE);
		
		String url = "https://retreiveandsavems.cfapps.io/bus/refresh";
		Matcher matcher = pattern.matcher(url);
		
		if(matcher.find()){
			System.out.println(removeDots(matcher.group(1),true));
		}else{
			System.out.println("not found");
		}
		
	}
	
	private static String removeDots(String input,boolean needFirst){
		if(input.contains(".")){
			String str[] = input.split("\\.");
			return needFirst?str[0]:str[str.length-1];
		}
		return input;
	}

	}
