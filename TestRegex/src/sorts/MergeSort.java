package sorts;

public class MergeSort {
	
	
	public void doMergeSort(int arr[]) {
		long start = System.currentTimeMillis();
		mergeSort(arr,0,arr.length-1);
		long diff = System.currentTimeMillis()-start;
		System.out.println("time taken for merge sort: "+diff+" ms");
	}
	
	public void mergeSort(int inputArray[],int low,int high) {
		
		if(low<high) {
			int mid = (low+high)/2;
			
			mergeSort(inputArray,low,mid);
			
			mergeSort(inputArray,mid+1,high);
			
			merge(inputArray,low,mid,high);
		}
		
	}
	
	private void merge(int inputArray[],int low,int mid,int high) {
		
		int tempArrayOne[] = new int[mid-low+1];
		int tempArrayTwo[] = new int[high-mid];
		
		
		for(int i=0;i<tempArrayOne.length;i++) {
			tempArrayOne[i] = inputArray[low+i];
		}
		
		for(int j=0;j<tempArrayTwo.length;j++) {
			tempArrayTwo[j] = inputArray[mid+1+j];
		}
		
		int k=low,i=0,j=0;
		
		while(i<tempArrayOne.length && j<tempArrayTwo.length) {
			
			if(tempArrayOne[i]<=tempArrayTwo[j]) {
				inputArray[k] = tempArrayOne[i];
				i++;
			}else {
				inputArray[k] = tempArrayTwo[j];
				j++;
			}
			k++;	
		}
		
		while(i<tempArrayOne.length) {
			inputArray[k] = tempArrayOne[i];
			i++;
			k++;
		}
		
		while(i<tempArrayTwo.length) {
			inputArray[k] = tempArrayTwo[j];
			j++;
			k++;
		}
		tempArrayOne=tempArrayTwo=null;
	}

}
