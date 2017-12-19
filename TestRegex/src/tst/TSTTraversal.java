package tst;

import java.util.ArrayList;
import java.util.List;

public class TSTTraversal {

	public static boolean isFound=false;
	private static Node root=null;
	static double totalSize=0.0;
	
	private static Node insertTST(String word,Node curr,int ptr,int arrayPos){
			if(word.length()>0){
				if(null==curr){
					curr = new Node(word.charAt(ptr));
					
					//nodeCount++;
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
						//curr.arrayPos = arrayPos;
					}else{
						curr.isWordEnd=curr.isLeaf=true;
						//curr.arrayPos = arrayPos;
					}
				}
			}
		return curr;
	}
	
	private static void searchTST(String prefix,Node curr,int ptr,Node[] searchResult){
		
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
	
		private static void getMatchedTST(String prefix,Node curr,List<String> list,int ptr,List<String> inputList){

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
		
		private static void getAllSuffixes(/*StringBuffer sb,*/Node curr,List<String> list,List<String> inputList){
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
		private static Node deleteAllNodesTST(Node curr){
	        if(null!=curr){
	        	curr.left = deleteAllNodesTST(curr.left);
	        	curr.right=deleteAllNodesTST(curr.right);
	        	curr.middle=deleteAllNodesTST(curr.middle);	
	        	System.out.println("returning null for  "+curr.val);
	        }
	        
	        return null;
		}
		
		public static void deleteAll(){
			//inputList.clear();
			root = deleteAllNodesTST(root);
		}

		public static void putTST(String key,String value,List<String> inputList){
			isFound=false;
			long startSearch=System.currentTimeMillis();
				Node[] searchResult = new Node[1];
				searchTST (key,root,0,searchResult);
				if(null!=searchResult[0]){
                    String oldValue = inputList.get(searchResult[0].arrayPos);
					inputList.set(searchResult[0].arrayPos, value);
					long diffSearch = System.currentTimeMillis()-startSearch;
					System.out.println("found "+key+" in "+diffSearch+" ms");
					//System.out.println("Updated old value : "+oldValue+" to "+inputList.get(searchResult[0].arrayPos)+" for key "+key);
				}else{
					System.out.println("cound not find key "+key+ " will add now");
					addInTSTKeyValuePair(key,value,inputList);
				}
			
		}
		
		public static void addInTSTAll(List<String> inputList){
			long start = System.currentTimeMillis();
			isFound=false;
			for(int i=0;i<inputList.size();i++){
				/*String spliceArray[] = inputList.get(i).split("-", 2);
				if(spliceArray.length>1){
		
						root = insertTST(spliceArray[0]+"-"+spliceArray[1],root,0,i);
						root = insertTST(spliceArray[1]+"-"+spliceArray[0],root,0,i);
					
				}else{
		*/			root = insertTST(inputList.get(i),root,0,i);
		          //  count++;
				//}
				//System.out.println("total size: "+totalSize+" bytes"/*+ " new nodes : "+nodeCount+" new ref :"+newReference+ " for: "+inputList.get(i)*/);
				//nodeCount=newReference=0;
		            
			}
			long diff = System.currentTimeMillis()-start;
			System.out.println("time taken to add in TST bloated "+diff+" ms");
		}
		
		private static void addInTSTKeyValuePair(String key,String value,List<String> inputList){
			isFound=false; 
	      
	            inputList.add(key);
	            int indexToAdd = inputList.size()-1;
				String spliceArray[] = key.split("-", 2);
				if(spliceArray.length>1){
		
						root = insertTST(spliceArray[0]+"-"+spliceArray[1],root,0,indexToAdd);
						root = insertTST(spliceArray[1]+"-"+spliceArray[0],root,0,indexToAdd);
					
				}else{
					root = insertTST(key,root,0,indexToAdd);
				}
			
		}
		
		public static List<String> getMatchedResults(String prefix,List<String> inputList){
			
			List<String> searchResult = new ArrayList<String>(5);
				getMatchedTST(prefix,root,searchResult,0,inputList); 
			return searchResult;
		}
		
		public static void traverseTST(){
			long start=System.currentTimeMillis();
			traversalRecusrsion(root);
			System.out.println("total size bloated TST : "+(totalSize/(1024*1024))+" MB");
			long diff = System.currentTimeMillis()-start;
			System.out.println("Time taken to traverse TST bloated "+diff+" ms");
		}
		
		private static void traversalRecusrsion(Node curr){
			if(null!=curr){
				totalSize+=18.25;
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
}
