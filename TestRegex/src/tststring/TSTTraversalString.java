package tststring;

import java.util.ArrayList;
import java.util.List;

public class TSTTraversalString {


	public static boolean isexactMatchFound=false,isPrefixFound=false;
	private static NodeString root=null;
	static double totalSize=0.0;
	private static List<Object> prefixInterimResultList = new ArrayList<Object>(2);
	private static List<String> prefixFinalResultList = new ArrayList<String>();
	

	private static NodeString insertTSTString(String word,NodeString curr,int ptr,int arrayPos,boolean isAddToCurrentNode){
			if(word.length()>0){
			     if(null==curr){
					curr = new NodeString(word.charAt(ptr));
					//totalSize+=18.25;
			     }else if(null!=curr && isAddToCurrentNode){
			    	 curr.addCharToSB(word.charAt(ptr));
			    	// totalSize+=2;
			     }
			     
				if(curr.val.length()-1>=ptr && (word.charAt(ptr)<curr.val.charAt(ptr))){
					
					if(ptr>0){
						curr = breakCurrentNode(curr,word,false,ptr);
						curr = curr.middle;
						word = word.substring(ptr, word.length());
						  ptr=0;
					}
						curr.left=insertTSTString(word,curr.left,ptr,arrayPos,false);
						/*if(null!=curr.left && !curr.isLeftTraversed){
							totalSize+=8;
							curr.isLeftTraversed=true;
						}*/
				}else if(curr.val.length()-1>=ptr && (word.charAt(ptr)>curr.val.charAt(ptr))){
					
					if(ptr>0){
						curr = breakCurrentNode(curr,word,false,ptr);
						curr = curr.middle;
						word = word.substring(ptr, word.length());
						  ptr=0;
					}
					curr.right=insertTSTString(word,curr.right,ptr,arrayPos,false);
					/*if(null!=curr.left && !curr.isRightTraversed){
						totalSize+=8;
						curr.isRightTraversed=true;
					}*/
				}
				else{
					if(ptr<word.length()-1){
						ptr++;
						curr.isLeaf=false;
						/*this if will just parse the TST since we dont have to add eg : process is already there in TST and we are adding processes
						so substring till process exists and needs to be parsed*/
						if(curr.val.length()-1>=ptr){
							insertTSTString(word, curr, ptr, arrayPos,false);
						}
						/*this check will add chars to existing node string builder and not create a new node untill necessary*/
						else if(ptr>curr.val.length()-1 && !curr.isWordEnd && null==curr.middle){
							  insertTSTString(word, curr, ptr, arrayPos,true);
							/*  this check will create a new node since existing node had a word end hence we cannot add any new chars
							  in addition it'll start appending chars in new node after the parsed node 
							  Eg : process is there in TST and processes needs be added so new node should be created containing string 'es' and not 
							  complete processes and node with 'es' should be middle of node with 'process'*/	  
						  }else{
							  word = word.substring(ptr, word.length());
							  ptr=0;
							  curr.middle = insertTSTString(word,curr.middle,ptr,arrayPos,false);
							 /* if(null!=curr.left && !curr.isMiddleTravered){
									totalSize+=8;
									curr.isMiddleTravered=true;
								}*/
						  }	
					  }
						else{
							/*this check will break an existing node if a new word to be entered in TST is a prefix of word string in current
							node.
							For eg : 'processes' exists in node n and now 'process' has to be inserted so node with string 'processes' will be
							broken and converted to 'process' and new node containing string 'es' will be inserted .
							node with string 'process' will have a middle ref to node with 'es'*/
						  if(word.length()<curr.val.length()){
							  curr = breakCurrentNode(curr,word,true,-1);
						  }else{
							  curr.isWordEnd=curr.isLeaf=true;
						  }
					  }
				
				}
			}
		return curr;
	}
	
	private static NodeString breakCurrentNode(NodeString curr,String word,boolean markWordEnd ,int breakAtIndex){
		String remainingWord=null;
		if(-1==breakAtIndex){
			int diff = curr.val.length()-word.length();
			  remainingWord = curr.val.substring(curr.val.length()-diff, curr.val.length());
			 curr.val = new StringBuilder(word);
		}else{
			  remainingWord = curr.val.substring(breakAtIndex, curr.val.length());
			 curr.val = new StringBuilder(curr.val.substring(0, breakAtIndex));
		}
		NodeString newNode = new NodeString(remainingWord);
		//totalSize+=16.25;
		newNode.middle = curr.middle;
		/*if(null!=newNode.middle && !newNode.isMiddleTravered){
			totalSize+=8;
			newNode.isMiddleTravered=true;
		}*/
		newNode.isLeaf = (null==newNode.middle)?true:false;
		newNode.isWordEnd=curr.isWordEnd;
		
		curr.middle = newNode;
		/*if(null!=curr.middle && !curr.isMiddleTravered){
			totalSize+=8;
			curr.isMiddleTravered=true;
		}*/
		curr.isLeaf=false;
		curr.isWordEnd=markWordEnd;
		return curr;
	}
		
		public static void addInTSTAll(List<String> inputList){
			long start = System.currentTimeMillis();
			isexactMatchFound=false;
			for(int i=0;i<inputList.size();i++){
				/*String spliceArray[] = inputList.get(i).split("-", 2);
				if(spliceArray.length>1){
		
						root = insertTST(spliceArray[0]+"-"+spliceArray[1],root,0,i);
						root = insertTST(spliceArray[1]+"-"+spliceArray[0],root,0,i);
					
				}else{
		*/			
				//System.out.println("inserting : "+inputList.get(i));
				root = insertTSTString(inputList.get(i),root,0,i,false);
					
				//}
			}
			long diff = System.currentTimeMillis()-start;
			System.out.println("time taken to add in TST String "+diff+" ms");
		}
		
		private static void addInTSTKeyValuePair(String key,String value,List<String> inputList){
			isexactMatchFound=false; 
	      
	            inputList.add(key);
	            int indexToAdd = inputList.size()-1;
				String spliceArray[] = key.split("-", 2);
				if(spliceArray.length>1){
		
						root = insertTSTString(spliceArray[0]+"-"+spliceArray[1],root,0,indexToAdd,false);
						root = insertTSTString(spliceArray[1]+"-"+spliceArray[0],root,0,indexToAdd,false);
					
				}else{
					root = insertTSTString(key,root,0,indexToAdd,false);
				}
		}
		
		public static void traverseTSTString (){
			long start=System.currentTimeMillis();
			traversalRecusrsion(root);
			System.out.println("total size compressed TST : "+(totalSize/(1024*1024))+" MB");
			long diff = System.currentTimeMillis()-start;
			System.out.println("Time taken to traverse TST String "+diff+" ms");
		}
		
		private static void traversalRecusrsion(NodeString curr){
			if(null!=curr){
				totalSize+=16.25+(curr.val.length()*2);
				if(null!=curr.left){
					totalSize+=8;
				}
				if(null!=curr.right){
					totalSize+=8;
				}
				if(null!=curr.middle){
					totalSize+=8;
				}
				//System.out.println("size : "+totalSize+" bytes");
				//System.out.println(curr.val+" leaf : "+curr.isLeaf+" wordEnd : "+curr.isWordEnd);
				traversalRecusrsion(curr.left);
				traversalRecusrsion(curr.right);
				traversalRecusrsion(curr.middle);
			}
	}
		
		private static void searchTSTString(String prefix,int ptr,NodeString curr){
			
			if(!isexactMatchFound){
				
				if(null!=curr){
					
					if(ptr>=prefix.length() && prefix.length()==curr.val.length() && curr.isWordEnd){
						isexactMatchFound=true;
						
					}else if(ptr<prefix.length()){
						
						if(ptr<curr.val.length() && prefix.charAt(ptr)==curr.val.charAt(ptr)){
							ptr++;
							searchTSTString(prefix,ptr,curr);	
						}else if(ptr<curr.val.length() && prefix.charAt(ptr)<curr.val.charAt(ptr)){
							searchTSTString(prefix,ptr,curr.left);	
						}else if(ptr<curr.val.length() && prefix.charAt(ptr)>curr.val.charAt(ptr)){
							searchTSTString(prefix,ptr,curr.right);	
						}
						else if(ptr>=curr.val.length()){
							
							prefix = prefix.substring(ptr, prefix.length());
							ptr=0;
							searchTSTString(prefix,ptr,curr.middle);	
						}
					}
				}
			}
		}
		
		public static void search(String prefix){
			long start = System.currentTimeMillis();
			isexactMatchFound=false;
			searchTSTString(prefix,0,root);
			long diff = System.currentTimeMillis()-start;
			System.out.println("Time taken to find exact match in TST String "+diff+" ms");
			System.out.println("match found for: "+prefix+" "+isexactMatchFound);
		}
		
		
	private static void searchTSTStringPrefix(String prefix,int ptr,NodeString curr,StringBuilder completePrefix,List<Object> prefixInterimResultList){
			
			if(!isPrefixFound){
				
				if(null!=curr){
					
					if(ptr>=prefix.length()){
						isPrefixFound=true;
						completePrefix=(null==completePrefix)?new StringBuilder():completePrefix;
						completePrefix.append(curr.val);
						prefixInterimResultList.add(curr);
						prefixInterimResultList.add(completePrefix);
						
					}else if(ptr<prefix.length()){
						
						if(ptr<curr.val.length() && prefix.charAt(ptr)==curr.val.charAt(ptr)){
							ptr++;
							searchTSTStringPrefix(prefix,ptr,curr,completePrefix,prefixInterimResultList);	
						}else if(ptr<curr.val.length() && prefix.charAt(ptr)<curr.val.charAt(ptr)){
							searchTSTStringPrefix(prefix,ptr,curr.left,completePrefix,prefixInterimResultList);	
						}else if(ptr<curr.val.length() && prefix.charAt(ptr)>curr.val.charAt(ptr)){
							searchTSTStringPrefix(prefix,ptr,curr.right,completePrefix,prefixInterimResultList);	
						}
						else if(ptr>=curr.val.length()){
							completePrefix=(null==completePrefix)?new StringBuilder():completePrefix;
							completePrefix.append(prefix.substring(0,ptr));
							prefix = prefix.substring(ptr, prefix.length());
							ptr=0;
							searchTSTStringPrefix(prefix,ptr,curr.middle,completePrefix,prefixInterimResultList);	
						}
					}
				}
			}
		}
		
	   private static void getAllSuffixesForPrefixNode(NodeString curr,List<String> result,StringBuilder newPrefix){
		   
		   if(null!=curr){
			   newPrefix.append(curr.val);
			   if(curr.isWordEnd){
				   result.add(newPrefix.toString());
			   }
			   getAllSuffixesForPrefixNode(curr.middle,result,newPrefix);
			   
			   /*This is done because when coming out of recursion curr node value added to newPrefix has to be removed 
			    * For eg : newprefix initially was 'process' , first suffix with word end found is 'es' so that complete word is 'processes'
			    * now coming out of recurrsion new word should be 'process'+suffix . If below is not removed then new word would be 
			    * 'processes'+suffix
			    * */
			   if(newPrefix.length()>curr.val.length()){
				   newPrefix.setLength(newPrefix.length()-curr.val.length());
			   }
			   getAllSuffixesForPrefixNode(curr.left,result,newPrefix);
			   getAllSuffixesForPrefixNode(curr.right,result,newPrefix);
		   }
	   }
	   
		public static void getMatchedTSTString(String prefix){
			long start = System.currentTimeMillis();
			isPrefixFound=false;
			StringBuilder sb=null;
			prefixInterimResultList.clear();
			prefixFinalResultList.clear();
			/*This method will return the list containing node where part of string or complete string is matched and complete prefix 
			
			For eg : prefix is 'proce' and last node has value 'ess' from root (pr(ROOT)->o->c->ess) then list will contain node having value 'ess'
			and newprefix will have value 'process' so that all children of node containing value 'ess' will be fetched */
			searchTSTStringPrefix(prefix,0,root,sb,prefixInterimResultList);
			
			if(prefixInterimResultList.size()==2){
				NodeString current = (NodeString)prefixInterimResultList.get(0);
				StringBuilder newPrefix=(StringBuilder)prefixInterimResultList.get(1);
				//If newprefix returned is itself a word add to result list
				if(current.isWordEnd){
					prefixFinalResultList.add(newPrefix.toString());
				}
				getAllSuffixesForPrefixNode(current.middle,prefixFinalResultList,newPrefix);
			}
			System.out.println("Prefix for: "+prefix+ " "+prefixFinalResultList);
			long diff = System.currentTimeMillis()-start;
			System.out.println("Time taken to find all suffixes "+diff+" ms");
			
		}
}
