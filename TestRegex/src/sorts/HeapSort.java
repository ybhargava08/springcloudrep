package sorts;

public class HeapSort {
	
	public void doHeapSort(int arr[]) {
		long start = System.currentTimeMillis();
		int length=arr.length;
		for(int i=0;i<=length/2-1;i++) {
			heapify(arr,length,i);
		}
		
		for(int j=length-1;j>=0;j--) {
			swap(arr,0,j);
			heapify(arr,j,0);
		}
		
		long diff = System.currentTimeMillis()-start;
		System.out.println("time taken for heap sort: "+diff+" ms");
	}
	
	private void heapify(int arr[],int maxIndex,int currIndex) {
		int indexLeft = 2*currIndex+1;
		int indexRight = 2*currIndex+2;
		int largestIndex = currIndex;
		
		if(indexLeft<maxIndex && arr[indexLeft]>arr[largestIndex]) {
			largestIndex = indexLeft;
		}
		
		if(indexRight<maxIndex && arr[indexRight]>arr[largestIndex]) {
			largestIndex = indexRight;
		}
		
		if(largestIndex!=currIndex) {
			swap(arr,largestIndex,currIndex);
			
			heapify(arr,maxIndex,largestIndex);
		}
	}
	
	private void swap(int arr[],int pos1,int pos2) {
		int temp = arr[pos1];
		arr[pos1] = arr[pos2];
		arr[pos2] = temp;
	}

}
