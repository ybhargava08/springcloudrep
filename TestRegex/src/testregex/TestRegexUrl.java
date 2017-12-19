package testregex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestRegexUrl {

	public static void main(String[] args) {

     String input ="http://localhost:7004/RxCWebInterface/SoapServlet/PPIv106";
     String regex = "(?i)soapservlet/([a-zA-Z0-9]+)$";
     
     Pattern pattern = Pattern.compile(regex);
		
		Matcher matcher = pattern.matcher(input);
		
		if(matcher.find()){
			System.out.println(matcher.group(1));
		}
	}

}
