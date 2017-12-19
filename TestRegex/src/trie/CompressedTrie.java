package trie;

public class CompressedTrie {

	CompressedTrie edgeLabel[]=new CompressedTrie[26];
	StringBuilder sb = null;
	boolean isWordEnd,isLeaf,hasChildren;
	
	public CompressedTrie(char ch){
		sb = (sb==null)?new StringBuilder():sb;
		sb.append(ch);
	}
	
	public CompressedTrie(String str){
		sb = (sb==null)?new StringBuilder():sb;
		sb.append(str);
	}
	
}
