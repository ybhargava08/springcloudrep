package sorts;

public class SortMain {

	public static void main(String[] args) {     
          int arr[]=createLargeUnsortedArray(Math.pow(9, 9), false);
          
        // int arr[] = {4,10,3,5,1};
          isArraySorted(arr,arr.length);
          
          sortType(arr,"iterativequick");
          
          isArraySorted(arr,arr.length);
	}

	public static void sortType(int arr[],String sortType) {
		System.out.println("DOING "+sortType+" sort NOW !!");
		if(sortType.equalsIgnoreCase("insertion")) {
			InsertionSort is = new InsertionSort();
			is.doInsertionSort(arr);
		}else if(sortType.equalsIgnoreCase("merge")) {
			MergeSort ms = new MergeSort();
			ms.doMergeSort(arr);
		}else if(sortType.equalsIgnoreCase("quick")) {
			QuickSort qs = new QuickSort();
			qs.doQuickSort(arr);
		}else if(sortType.equalsIgnoreCase("iterativequick")) {
			QuickSortIterative qsi = new QuickSortIterative();
			qsi.doQuickSortIterative(arr);
		}else if(sortType.equalsIgnoreCase("heap")) {
			HeapSort hs = new HeapSort();
			hs.doHeapSort(arr);
		}
	}
	
	public static void isArraySorted(int arr[],int arrLength) {
		//long start = System.currentTimeMillis();
		boolean isSorted=true;
		int mid = arrLength/2;
		int i=0;
		int last = arrLength-1;
		
		while(i<=mid-1 || (last-i)>=mid) {
			if(!(arr[i]<arr[i+1]) || !(arr[last-i]>arr[last-i-1])) {
				isSorted=false;
				break;
			}
			i++;
		}
		/*long diff = System.currentTimeMillis()-start;
		System.out.println("Time taken to check if array is sorted: "+diff+" ms");*/
	    System.out.println("Is Array Sorted: "+isSorted);
	}
	
	public static int[] createLargeUnsortedArray(Double fac,boolean isAsc) {
		System.out.println("max memory "+java.lang.Runtime.getRuntime().maxMemory()/(1024*1024)+" Mb");
		System.out.println("memory taken by array: "+fac*4/(1024*1024)+" Mb");
		long start = System.currentTimeMillis();
		int factor = fac.intValue();
		int arr[] = new int[factor];
		
		if(isAsc) {
			for(int i=0;i<arr.length;i++) {
				arr[i] = i+1;
			}
		}else {
			for(int i=0;i<arr.length;i++) {
				arr[i] = factor-i;
			}	
		}
		
		long diff = System.currentTimeMillis()-start;
		System.out.println("Time taken to create large array: "+diff+" ms");
		return arr;
	}
}
