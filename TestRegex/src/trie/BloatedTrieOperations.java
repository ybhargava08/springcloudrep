package trie;

import java.util.ArrayList;
import java.util.List;

public class BloatedTrieOperations {

	
	private static BloatedTrie root = null;
	private static double totalSize=0.0;
	//private static String input[] = {"yash","yashwant"};
	private static boolean isExactMatchFound=false,isPrefixMatchFound=false;
	private static List<String> prefixMatchResult = new ArrayList<String>();
	
	private static void insertBloatedTrieRecursion(BloatedTrie curr,String word,int ptr){
           if(word.length()>0 && ptr<=word.length()-1){
        	   char ch = word.charAt(ptr);
        	  
        	   if(null!=curr.nodeArray && null!=curr.nodeArray[ch-97]){
        		   
        		   curr.nodeArray[ch-97].isWordEnd=(ptr==word.length()-1)?true:curr.nodeArray[ch-97].isWordEnd;
        		 
        		   curr = curr.nodeArray[ch-97];
        		     
        	   }else{
        		   if(null==curr.nodeArray){
        			   curr.nodeArray = new BloatedTrie[26];
        		   }
        		   BloatedTrie trie = new BloatedTrie(ch);
        		   trie.isLeaf=true;
        		   trie.isWordEnd=(ptr==word.length()-1)?true:false;
        		   
        		  curr.nodeArray[ch-97] = trie;
        		   curr.isLeaf=false;
        		   curr=curr.nodeArray[ch-97];
        	   } 
        	   ptr++;
        	   insertBloatedTrieRecursion(curr,word,ptr);
           }
 
	}
	
	
	public static void insertBloatedTrie(List<String> inputList){
		
		long start = System.currentTimeMillis();
		if(null==root){
			root = new BloatedTrie('\0');
		}
		for(String input : inputList){
			insertBloatedTrieRecursion(root,input,0);
		}	
		long diff = System.currentTimeMillis()-start;
		System.out.println("time taken for insertion bloated trie : "+diff+" ms");
	}
	
	public static void traverseBloatedTrie(){
		long start = System.currentTimeMillis();
		traverseBloatedTrie(root);
		System.out.println("total size bloated trie : "+(totalSize/(1024*1024))+" MB");
		long diff = System.currentTimeMillis()-start;
		System.out.println("time taken for traversal bloated trie : "+diff+" ms");
	}
	
	private static void traverseBloatedTrie(BloatedTrie node){
		if(null!=node){
			if(node.ch!='\0'){
				totalSize+=12;
				//System.out.println("Total Size after "+node.ch+" "+totalSize+" bytes");
				//System.out.println("value : "+node.ch+" isWordEnd : "+node.isWordEnd+" isLeaf : "+node.isLeaf);
			}
			if(null!=node.nodeArray){
				totalSize+=12;
				for(int i=0;i<node.nodeArray.length;i++){
					if(null!=node.nodeArray[i]){
						totalSize+=8;
						//System.out.println("Total Size for reference of "+node.ch+" "+totalSize+" bytes");
					}
					traverseBloatedTrie(node.nodeArray[i]);
				}
			}
		}
	}
	
	public static void search(String prefix){
		long start=System.currentTimeMillis();
		isExactMatchFound=false;
		searchBloatedTrie(root,prefix,0);
		System.out.println("found exact match "+prefix+" "+isExactMatchFound);
		long diff = System.currentTimeMillis()-start;
		System.out.println("Time taken to find exact match in Bloated Trie "+diff+" ms");
	}
	
	private static void searchBloatedTrie(BloatedTrie curr,String prefix,int ptr){
		if(!isExactMatchFound && ptr<=prefix.length()-1){
			
			if(null!=curr && null!=curr.nodeArray){
			
				if(null!=curr.nodeArray[prefix.charAt(ptr)-97] && curr.nodeArray[prefix.charAt(ptr)-97].isWordEnd){
					isExactMatchFound=true;	
				}else{
					curr = curr.nodeArray[prefix.charAt(ptr)-97];
				}
			}
			ptr++;
			searchBloatedTrie(curr,prefix,ptr);
		}
	}
	
	private static BloatedTrie searchBloatedTrieForPrefixNode(BloatedTrie curr,String prefix,int ptr,BloatedTrie prefixNode){
		if(!isPrefixMatchFound && ptr<=prefix.length()-1){
			
			if(null!=curr && null!=curr.nodeArray){
				
				if(ptr==prefix.length()-1){
					prefixNode=curr.nodeArray[prefix.charAt(ptr)-97];
					isPrefixMatchFound=true;
					return prefixNode;	
				}else{
					curr = curr.nodeArray[prefix.charAt(ptr)-97];
				}
			}
			ptr++;
			prefixNode = searchBloatedTrieForPrefixNode(curr,prefix,ptr,prefixNode);
		}
		return prefixNode;
	}
	
	public static void getPrefixNode(String prefix){
		BloatedTrie prefixNode = searchBloatedTrieForPrefixNode(root,prefix,0,null);
		if(null!=prefixNode){
			System.out.println("Prefix value : "+prefixNode.ch);
		}else{
			System.out.println("no prefix found");
		}
	}
	
	private static void getAllSuffixes(BloatedTrie curr,StringBuilder sb,List<String> prefixMatchResult,boolean isAddChar){

		  if(null!=curr){

			  //This is added bcoz stringbuilder sb contains the prefix so if prefix is full word then no need to append char
			  if(isAddChar){
				  sb.append(curr.ch);
			  }
			  
			  if(curr.isWordEnd){
					prefixMatchResult.add(sb.toString());
				}
			  
			if(null!=curr.nodeArray){
			   for(int i=0;i<curr.nodeArray.length;i++){
				   getAllSuffixes(curr.nodeArray[i],sb,prefixMatchResult,true);
			   }
			}
			/*This is done bcoz when coming out of recursion extra added chars to prefix to make complete word need to be removed else next chars
			 * will be appended to full word instead of prefix*/
			 if(sb.length()>0){
				  sb.setLength(sb.length()-1);
			  }
		}
	}
	
	public static void getMatchForPrefix(String prefix){
		prefixMatchResult.clear();
		isPrefixMatchFound=false;
		BloatedTrie prefixNode = searchBloatedTrieForPrefixNode(root,prefix,0,null);
		StringBuilder sb = null;
		if(null!=prefixNode){
			sb = new StringBuilder(prefix);
			getAllSuffixes(prefixNode,sb,prefixMatchResult,false);
		}
		System.out.println("Found matches for: "+prefix+" "+prefixMatchResult);
	}
}
