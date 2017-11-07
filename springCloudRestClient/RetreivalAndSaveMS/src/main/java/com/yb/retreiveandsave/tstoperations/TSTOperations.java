package com.yb.retreiveandsave.tstoperations;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yb.retreiveandsave.controller.RetreivalAndSaveController;

import resourcebean.ResourceBean;

public class TSTOperations {

	public static boolean isFound=false;
	private static TSTNode root=null;
	
	private static List<ResourceBean> inputList = new ArrayList<ResourceBean>(10);
	private static List<ResourceBean> prefixMatchedResults = new ArrayList<ResourceBean>(5);
	
	static Logger logger = LoggerFactory.getLogger(RetreivalAndSaveController.class);
	
	public static void putTST(String key,ResourceBean value){
		isFound=false;
		TSTNode[] searchResult = new TSTNode[1];
			searchTST (key,root,0,searchResult);
			
			if(null!=searchResult[0]){
				logger.info("Found search result : "+searchResult[0].val+" "+searchResult[0].arrayPos);
				inputList.set(searchResult[0].arrayPos, value);
			}else{
				addInTSTKeyValuePair(key,value);
			}
		
	}
	
	public static void addInTSTAll(List<ResourceBean> masterInputList){
		inputList.clear();
		inputList.addAll(masterInputList);
		isFound=false;
		for(int i=0;i<inputList.size();i++){
			String spliceArray[] = inputList.get(i).getTag().split("-", 2);
			if(spliceArray.length>1){
	
					root = insertTST(spliceArray[0]+"-"+spliceArray[1],root,0,i);
					root = insertTST(spliceArray[1]+"-"+spliceArray[0],root,0,i);
				
			}else{
				root = insertTST(inputList.get(i).getTag(),root,0,i);
			}
		}
	}
	
	private static void addInTSTKeyValuePair(String key,ResourceBean value){
		isFound=false; 
      
            inputList.add(value);
            int indexToAdd = inputList.size()-1;
			String spliceArray[] = key.split("-", 2);
			
			if(spliceArray.length>1){
				logger.info("adding key value pair : "+(spliceArray[0]+"-"+spliceArray[1])+" at index : "+indexToAdd);
					root = insertTST(spliceArray[0]+"-"+spliceArray[1],root,0,indexToAdd);
					logger.info("adding key value pair : "+(spliceArray[1]+"-"+spliceArray[0])+" at index : "+indexToAdd);
					root = insertTST(spliceArray[1]+"-"+spliceArray[0],root,0,indexToAdd);
				
			}else{
				logger.info("adding key value pair : "+key+" at index : "+indexToAdd);
				root = insertTST(key,root,0,indexToAdd);
			}
		
	}
	
	public static List<ResourceBean> getMatchedResults(String prefix){
		isFound=false;
		prefixMatchedResults.clear();
			getMatchedTST(prefix,root,prefixMatchedResults,0,inputList); 
		return prefixMatchedResults;
	}
	
	
	private static TSTNode insertTST(String word,TSTNode curr,int ptr,int arrayPos){
		if(word.length()>0){
			if(null==curr){
				curr = new TSTNode(word.charAt(ptr));
			}	
			if(word.charAt(ptr)<curr.val){
				curr.left=insertTST(word,curr.left,ptr,arrayPos);
			}else if(word.charAt(ptr)>curr.val){
				curr.right=insertTST(word,curr.right,ptr,arrayPos);
			}else{
				if(ptr<word.length()-1){
					ptr++;
					curr.isLeaf=false;
				curr.middle = insertTST(word,curr.middle,ptr,arrayPos);
				
				}else if(ptr==word.length()-1 && curr.isWordEnd && !curr.isLeaf){
					curr.arrayPos = arrayPos;
				}else{
					curr.isWordEnd=curr.isLeaf=true;
					curr.arrayPos = arrayPos;
				}
			}
		}
	return curr;
}

private static void searchTST(String prefix,TSTNode curr,int ptr,TSTNode[] searchResult){
	
	if(!isFound){
	
		if(null!=curr){
						if(ptr==prefix.length()-1 && curr.isWordEnd){
							isFound=true;
							searchResult[0] = curr;
						}
						
						if(ptr<prefix.length() && prefix.charAt(ptr)==curr.val){
							ptr++;
							searchTST(prefix,curr.middle,ptr,searchResult);
						}else if(ptr<prefix.length()){
							searchTST(prefix,curr.left,ptr,searchResult);
							searchTST(prefix,curr.right,ptr,searchResult);
						}
			}
	}
}

	private static void getMatchedTST(String prefix,TSTNode curr,List<ResourceBean> list,int ptr,List<ResourceBean> inputList){

	if(null==curr){
		isFound = false;
	}
	else{
					if(prefix.charAt(ptr)==curr.val && ptr == prefix.length()-1){
						ptr++;
						isFound=true;
						/*StringBuffer sb = new StringBuffer();
						sb.append(prefix);*/
						if(curr.isWordEnd){
							/*list.add(sb.toString());*/
							/*while(!curr.posQueue.isEmpty()){
								list.add(inputArr[curr.posQueue.poll()]);	
							}*/
							list.add(inputList.get(curr.arrayPos));
						}				
						   if(!curr.isLeaf){
							getAllSuffixes(curr.middle,list,inputList);
						   }
					}
					
					if(ptr<prefix.length() && prefix.charAt(ptr)==curr.val){
						ptr++;
						getMatchedTST(prefix,curr.middle,list,ptr,inputList);
						
					}else if(ptr<prefix.length()){
						getMatchedTST(prefix,curr.left,list,ptr,inputList);
						getMatchedTST(prefix,curr.right,list,ptr,inputList);	
					}
		}

}
	
	private static void getAllSuffixes(/*StringBuffer sb,*/TSTNode curr,List<ResourceBean> list,List<ResourceBean> inputList){
		  if(null!=curr){
			//sb.append(curr.val);
			
			if(curr.isWordEnd /*&& null!=sb*/){
				/*while(!curr.posQueue.isEmpty()){
					list.add(inputArr[curr.posQueue.poll()]);	
				}*/
				list.add(inputList.get(curr.arrayPos));
				//list.add(sb.toString());
			}
			  getAllSuffixes(curr.middle,list,inputList);
			  /*This is done because when coming out of recursion TST places left and right child one node down.. also to retain substring
			   * Eg : input - process , procreate ; prefix to search is pro so r in create would be right branch of e in process hence we will have to 
			   * come back till proc to form procreate
			   */
			 /* if(sb.length()>0)
			  sb.setLength(sb.length()-1);
			 */ 
			getAllSuffixes(curr.left,list,inputList);
			getAllSuffixes(curr.right,list,inputList);
		}
			
	}
	private static TSTNode deleteAllNodesTST(TSTNode curr){
        if(null!=curr){
        	curr.left=deleteAllNodesTST(curr.left);
        	curr.right=deleteAllNodesTST(curr.right);
        	curr.middle=deleteAllNodesTST(curr.middle);
        }
        return null;
	}
	
	public static void deleteAll(){
		isFound=false;
		inputList.clear();
		root=deleteAllNodesTST(root);
	}

	
}
