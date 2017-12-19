package trie;

import java.util.ArrayList;
import java.util.List;

public class CompressedTrieOperations {

	private static CompressedTrie root=null;
	private static double totalSize=0.0;
	private static final int OBJECT_HEADER_SIZE=8;
	private static final int BOOLEAN_SIZE=1;
	private static final int ARRAY_SIZE=12;
	private static final int CHAR_SIZE=2;
	private static final int OBJ_REFERENCE_SIZE=8;
	private static boolean isExactMatchFound=false,isPrefixMatchFound=false;
	private static List<String> resultPrefixMatch = new ArrayList<String>();
		
	public static void insert(List<String> inputList){
		long start=System.currentTimeMillis();
		root = (null==root)?new CompressedTrie(""):root;
		
		for(String input: inputList){
			insertCompressedTrie(root,input,0);
		}
		long diff = System.currentTimeMillis()-start;
		System.out.println("time taken for insertion compressed trie : "+diff+" ms");
	}
	
	private static void insertCompressedTrie(CompressedTrie curr,String word,int ptr){
		if(word.length()>0 && ptr<=word.length()-1){
		    char ch = word.charAt(ptr);
			if(null!=curr.edgeLabel[ch-97] || (null!=curr.sb && curr.sb.length()>0 && ptr<=curr.sb.length())){
				
				if(0==curr.sb.length() && null!=curr.edgeLabel[ch-97]){
					curr = curr.edgeLabel[ch-97];
				}
				if(ptr<curr.sb.length() && ptr==word.length()-1){
					 ptr++;
					   if(curr.sb.length()==ptr){
						 curr.isWordEnd=true;   
					   }else{
						CompressedTrie newChild = breakCurrentCompressedTrieNode(ptr, curr);
				       	curr.sb = new StringBuilder(curr.sb.substring(0, ptr));
				       	curr.isLeaf=false;
				       	curr.isWordEnd=curr.hasChildren=true;
				       	curr.edgeLabel = new CompressedTrie[26];
				       	curr.edgeLabel[newChild.sb.charAt(0)-97] = newChild;
					   }
				}
				else if(ptr<curr.sb.length() && ch==curr.sb.charAt(ptr)){
					ptr++;
				}else if(ptr<curr.sb.length() && ch!=curr.sb.charAt(ptr)){
					 CompressedTrie newChild = breakCurrentCompressedTrieNode(ptr, curr);
					 curr.sb = new StringBuilder(curr.sb.substring(0, ptr));
				       curr.isLeaf=curr.isWordEnd=false;
				       curr.hasChildren=true;
				       curr.edgeLabel = new CompressedTrie[26];
				       curr.edgeLabel[newChild.sb.charAt(0)-97] = newChild;  
				       curr.edgeLabel[ch-97] = createNewCompressedTrieNode(ptr,word);
				       ptr += curr.edgeLabel[ch-97].sb.length();
				}
				else if(ptr==curr.sb.length() && null==curr.edgeLabel[ch-97]){
					
					curr.edgeLabel[ch-97] = createNewCompressedTrieNode(ptr,word);
					curr.isLeaf=false;
					curr.hasChildren=true;
					ptr += curr.edgeLabel[ch-97].sb.length();
				}else if(ptr==curr.sb.length() && null!=curr.edgeLabel[ch-97]){
					curr = curr.edgeLabel[ch-97];
					word = word.substring(ptr, word.length());
					ptr=0;
				}
				
			}else{
					curr.edgeLabel[ch-97] = createNewCompressedTrieNode(ptr,word);
					curr.isLeaf=false;
					curr.hasChildren=true;
					ptr = curr.edgeLabel[ch-97].sb.length();
					curr = curr.edgeLabel[ch-97];
				
			}
			insertCompressedTrie(curr,word,ptr);
		}
	}
	
	private static CompressedTrie createNewCompressedTrieNode(int ptr,String word){
		CompressedTrie trie = new CompressedTrie(word.substring(ptr, word.length()));
		trie.isLeaf=trie.isWordEnd=true;
		return trie;
	}
	
	private static CompressedTrie breakCurrentCompressedTrieNode(int ptr,CompressedTrie curr){
		CompressedTrie trie = new CompressedTrie(curr.sb.substring(ptr, curr.sb.length()));
		trie.hasChildren=curr.hasChildren;
		trie.isWordEnd=curr.isWordEnd;
		trie.isLeaf=curr.isLeaf;
		trie.edgeLabel=curr.edgeLabel;
		return trie;
	}
	
	public static void traverse(){
		long start = System.currentTimeMillis();
		traverseCompressedTrie(root);
		System.out.println("total size compressed trie : "+(totalSize/(1024*1024))+" MB");
		long diff = System.currentTimeMillis()-start;
		System.out.println("time taken for traversal bloated trie : "+diff+" ms");
	}
	
	private static void traverseCompressedTrie(CompressedTrie curr){
		if(null!=curr){
			String value=curr.sb.toString();
			totalSize+=OBJECT_HEADER_SIZE+BOOLEAN_SIZE*3+ARRAY_SIZE+value.length()*CHAR_SIZE;
			if(curr.sb.length()==0){
				value="ROOT";
			}
		//	System.out.println("|value "+value+" |isWordEnd "+curr.isWordEnd+" |isLeaf "+curr.isLeaf+" |hasChildren "+curr.hasChildren+" |");
			for(int i=0;i<curr.edgeLabel.length;i++){
				    if(null!=curr.edgeLabel[i]){
				    	totalSize+=OBJ_REFERENCE_SIZE;
						traverseCompressedTrie(curr.edgeLabel[i]);
				    }	
			}
		}
	}
	
	private static void searchCompressedTrie(CompressedTrie curr,String prefix,int ptr){
		
		if(!isExactMatchFound && ptr<=prefix.length()-1){
				char ch = prefix.charAt(ptr);
				
				
			if(null!=curr){
				
				//If root move to next node
				if(curr.sb.length()==0){
					searchCompressedTrie(curr.edgeLabel[ch-97],prefix,ptr);
				}
				
				if(curr.isWordEnd && ptr==prefix.length()-1 && prefix.length()==curr.sb.length()){
					isExactMatchFound=true;
				}
				
				//increment pointer if chars in string within current node keeps on matching
				if(ptr<=curr.sb.length()-1 && ch==curr.sb.charAt(ptr)){
						ptr++;
						searchCompressedTrie(curr,prefix,ptr);	
					}
				 //break prefix till matched and proceed from there for next node
					else{
						prefix = prefix.substring(ptr, prefix.length());
						ptr=0;
						searchCompressedTrie(curr.edgeLabel[ch-97],prefix,ptr);
					}
			}	
		}
	}
	
	public static void search(String prefix){
		long start =System.currentTimeMillis();
		isExactMatchFound=false;
		searchCompressedTrie(root,prefix,0);
		System.out.println("found exact match "+prefix+" "+isExactMatchFound);
		long diff = System.currentTimeMillis()-start;
		System.out.println("Time taken to find exact match in Compressed Trie "+diff+" ms");
	}
	
	public static void getMatchedPrefixCompressedtrie(String prefix){
		long start = System.currentTimeMillis();
		isPrefixMatchFound=false;
		resultPrefixMatch.clear();
		searchCompressedTrieForPrefixMatch(root,prefix,0,resultPrefixMatch,null);
		System.out.println("All suffixes for: "+prefix +" are: "+resultPrefixMatch);
		long diff = System.currentTimeMillis()-start;
		System.out.println("Time taken to find all suffixes in Compressed Trie "+diff+" ms");
	}
	
	private static void searchCompressedTrieForPrefixMatch(CompressedTrie curr,String prefix,int ptr,List<String> resultPrefixMatch,StringBuilder sb){
		if(!isPrefixMatchFound && ptr<=prefix.length()-1){
			char ch = prefix.charAt(ptr);
			
			if(null!=curr){

				//If root move to next node
				if(curr.sb.length()==0){
					searchCompressedTrieForPrefixMatch(curr.edgeLabel[ch-97],prefix,ptr,resultPrefixMatch,sb);
				}
				
				if(ptr==prefix.length()-1 && prefix.length()<=curr.sb.length()){
					isPrefixMatchFound=true;
					sb = (null==sb)?new StringBuilder():sb;
					 sb.append(curr.sb.toString());
					 getAllSuffixes(curr,sb,resultPrefixMatch,false);
					
				}
				
				//increment pointer if chars in string within current node keeps on matching
				if(ptr<=curr.sb.length()-1 && ch==curr.sb.charAt(ptr)){
						ptr++;
						searchCompressedTrieForPrefixMatch(curr,prefix,ptr,resultPrefixMatch,sb);	
					}
				 //break prefix till matched and proceed from there for next node
					else{
						sb = (null==sb)?new StringBuilder():sb;
						sb.append(prefix.substring(0, ptr));
						prefix = prefix.substring(ptr, prefix.length());
						ptr=0;
						searchCompressedTrieForPrefixMatch(curr.edgeLabel[ch-97],prefix,ptr,resultPrefixMatch,sb);
					}
			}
		}
	}
	
  private static void getAllSuffixes(CompressedTrie curr,StringBuilder prefix,List<String> resultPrefixMatch,boolean isAddNode){
	  
	  if(null!=curr){
		  
		  if(isAddNode){
			  prefix.append(curr.sb);
		  }
		  
		  if(curr.isWordEnd){
			  resultPrefixMatch.add(prefix.toString());
		  }
		  
		  for(int i=0;i<curr.edgeLabel.length;i++){
			  getAllSuffixes(curr.edgeLabel[i],prefix,resultPrefixMatch,true);
		  }
		  if(null!=prefix && prefix.length()>curr.sb.length()){
			  prefix.setLength(prefix.length()-curr.sb.length());
		  }
	  }
	  
	 
  }
}
