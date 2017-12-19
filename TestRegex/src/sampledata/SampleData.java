package sampledata;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SampleData {

	private static final int ascii=97;
	private static final int minRange=4;
	private static final int maxRange=10;
	private static List<String> inputList = new ArrayList<String>(10);
	
	static{
		inputList.add("processes");
		inputList.add("process");
		/*inputList.add("process-orange-abcd");
		inputList.add("preach-orange-abcd");
		inputList.add("procreate-orange-abcd");*/
		inputList.add("pream");
		inputList.add("apple");
		inputList.add("procreation");
		inputList.add("procreating");
		inputList.add("prompt");
	}
	
	public static List<String> getInputlist(){
		return inputList;
	}
	
	public static String[] createLargeArray(long paramFactor){
		 List<String> output = createLargeList(paramFactor);	 
		 return output.toArray(new String[output.size()]);
	}
	
	public static List<String> createLargeList(long paramFactor){
		
		long start = System.currentTimeMillis();		  
		for(int i=0;i<paramFactor;i++){
			Double d = Math.ceil(Math.random()*maxRange);
			int stringLength = d.intValue();
			stringLength= (stringLength<minRange)?minRange:stringLength;
			String str="";
			for(int j=0;j<stringLength;j++){
				Double dd = Math.floor(Math.random()*26);
			   int randValue = dd.intValue()+ascii;
			   char ch = (char) randValue;
			   str+=String.valueOf(ch);
			}
			inputList.add(str);
		}
		inputList.add("yash");
		inputList.add("yashwant");
		inputList.add("yashi");
		inputList.add("yashu");
		System.out.println("List Size : "+inputList.size());
		long diff = System.currentTimeMillis()-start;
		System.out.println("time taken to create large array "+diff+" ms");
        
		inputList = inputList.stream().distinct().collect(Collectors.toList());
		long startDup = System.currentTimeMillis();
		System.out.println("List Size after distinct: "+inputList.size());
		long diffDup = System.currentTimeMillis()-startDup;
		System.out.println("Time taken to remove dups from large list : "+diffDup+" ms");
		return inputList;
	}
	
}
