package sorts;

public class QuickSort {
	
	public void doQuickSort(int arr[]) {
		long start = System.currentTimeMillis();
		quickSort(0,arr.length-1,arr);
		long diff = System.currentTimeMillis()-start;
		System.out.println("time taken for quick sort: "+diff+" ms");
	}
	
	public void quickSort(int indexLow,int indexHigh,int arr[]) {
		if(indexLow<indexHigh){
			int indexPartition = partition(indexLow,indexHigh,arr);
			quickSort(indexLow,indexPartition-1, arr);
			quickSort(indexPartition+1,indexHigh, arr);
		}
	}
	
	public int partition(int indexLow,int indexHigh,int arr[]) {	
		int pivot = arr[indexHigh];
		//int pivot = (arr[low]+arr[high])/2;
		int indexWall = indexLow-1;
		
		for(int i=indexLow;i<=indexHigh-1;i++){
			if(arr[i]<pivot){
				indexWall++;
				swapElements(arr,i,indexWall);
			}
		}
		swapElements(arr,indexHigh,indexWall+1);
		return indexWall+1;
	
	}
	
	public void swapElements(int arr[],int pos1,int pos2) {
		 int temp = arr[pos1];
		 arr[pos1] = arr[pos2];
		 arr[pos2] = temp;
	}
}
