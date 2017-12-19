package testregex;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestRegexClass {
	
	public  String readFromFile(String filePath){

		BufferedReader br = null;
		try{
			//String filePath = "C:\\Users\\ybhargava\\Desktop\\ESL_ERROR_RESPONSES\\"+fileName+".txt";
		 br= new BufferedReader(new FileReader(filePath));
	     
		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();

		    while (line != null) {
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        line = br.readLine();
		    }
		    String everything = sb.toString();
		   return everything;
	     }catch(Exception e){
	    	 e.printStackTrace();
	     }finally{
	    	 if(null!=br){
	    		 try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	 }
	     }
		return null;
	}
	
	private  String ruleone(String input){
		String regex = "((?i)msgname)=\"([a-zA-Z]+)\"";
		Pattern pattern = Pattern.compile(regex);
		
		Matcher matcher = pattern.matcher(input);
		
		if(matcher.find()){
		return filterColon(matcher.group(2));
		}
		return null;
	}
	
	private  String ruleTwo(String input){
		
		String regex = "</([a-zA-Z:]+)></([a-zA-Z:-]*(?i)body)>";
		//String regex = "</(CredentialsAuthorizationRequest)></(SOAP-ENV:(?i)body)>";
		
		Pattern pattern = Pattern.compile(regex);
		
		Matcher matcher = pattern.matcher(input);
		
		if(matcher.find()){
			return filterColon(matcher.group(1));
			}
			return null;
		
	}
	
	private  String ruleThree(String input){
		String regex = "</([a-zA-Z:]+)>$";
		Pattern pattern = Pattern.compile(regex);
		
		Matcher matcher = pattern.matcher(input);
		
		if(matcher.find()){
			return filterColon(matcher.group(1));
			}
			return null;
		
	}
	
	public void executeRulesInOrder(String input){
		String output = ruleone(input);
		
		if(null==output){
			output = ruleTwo(input);
		}
		
		if(null==output){
			output = ruleThree(input);
		}
		
		if(null==output){
			System.out.println("nothing found");
		}else{
			System.out.println(output);
		}
	}

	private String filterColon(String input){
		if(input.contains(":")){
			String str[] = input.split(":");
			return str[str.length-1];
		}
		return input;
	}
}
