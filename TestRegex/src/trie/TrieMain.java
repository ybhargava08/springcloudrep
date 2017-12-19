package trie;

import java.util.List;

import sampledata.SampleData;
import tst.TSTTraversal;
import tststring.TSTTraversalString;

public class TrieMain {

	public static void main(String[] args) {
		
		Double d = Math.pow(10,7);
		String prefix = "procreating";
		List<String> input = SampleData.createLargeList(d.longValue());
		//List<String> input = SampleData.getInputlist();
		
		/*if(input.contains(prefix)){
			System.out.println("list contains :"+prefix+" at "+input.indexOf(prefix));
		}*/
		//String[] inputArray = SampleData.createLargeArray(d.longValue());
 		
		/*TSTTraversal.addInTSTAll(input);
		TSTTraversal.traverseTST();
		*/
		
		
		TSTTraversalString.addInTSTAll(input);
		TSTTraversalString.traverseTSTString();
		
		System.out.println("===================================================");
	     
			TSTTraversalString.search(prefix);	
			TSTTraversalString.getMatchedTSTString(prefix);
	   
			
	/*	BloatedTrieOperations.insertBloatedTrie(input);
		BloatedTrieOperations.traverseBloatedTrie();
		System.out.println("===================================================");
		BloatedTrieOperations.search(prefix);
		BloatedTrieOperations.getMatchForPrefix(prefix);*/
		
		/*CompressedTrieOperations.insert(input);
		CompressedTrieOperations.traverse();
		System.out.println("===================================================");
		CompressedTrieOperations.search(prefix);
		CompressedTrieOperations.getMatchedPrefixCompressedtrie(prefix);*/
		
	}
}
