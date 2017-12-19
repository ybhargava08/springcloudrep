package tst;

public class TSTMain {

	public static void main(String[] args) {
      
	
		
		//String[] inputArr = TSTTraversal.createLargeArray(10000000).toArray(new String[]{});
	
		/* adding items in Ternary Search Tree
		*/
		
		//TSTTraversal.createLargeArray();
		long start = System.currentTimeMillis();
	
		
		//TSTTraversal.addInTSTAll();
		long diff = System.currentTimeMillis()-start;
		
		System.out.println("time taken for preprocessing millis "+diff+" ms");
		
		/* searching items in Ternary Search Tree
		*/
		
		/*long startSearch = System.currentTimeMillis();
		
		TSTTraversal.putTST("yash", "8");
		
		//TSTTraversal.putTST("proceed-orange-abcd", "proceed");
		
		long diffSearch = System.currentTimeMillis()-startSearch;
		
		System.out.println("time taken millis for search "+diffSearch+" ms");*/
		
		/* searching all suffixes in Ternary Search Tree */
		
		// deleting all nodes from TST
		/*long startDeletion = System.currentTimeMillis();
		TSTTraversal.deleteAll();
		
		long diffDeletion = System.currentTimeMillis()-startDeletion;
		
		System.out.println("total time taken for deletion : "+diffDeletion+" ms");
		
		// deleting all nodes from TST ends
		
		long startSuffixSearch = System.currentTimeMillis();
		
		String prefix = "ora";
		System.out.println("found prefix matches for "+prefix+" "+TSTTraversal.getMatchedResults(prefix)); 
		 
		long diffSuffixSearch = System.currentTimeMillis()-startSuffixSearch;
		
		System.out.println("time taken millis for suffix search "+diffSuffixSearch+" ms");	
		
		*/
	//	TSTTraversal.traverse();
	}
	
/*	private static void checkStartsWith(String input[],String prefix){
		long start = System.currentTimeMillis();
		List<String> searchResult = new ArrayList<String>();
		for(String str:input){
			if(str.startsWith(prefix)){
				searchResult.add(str);
			}
		}
		System.out.println("All search results for prefix using startsWith '"+prefix+"' are : "+searchResult+" size : "+searchResult.size());
		long diff = System.currentTimeMillis()-start;
		System.out.println("total time taken for startsWith "+diff+" ms");
	}*/
	
	}
