package sorts;

public class InsertionSort{
	
	public int[] doInsertionSort(int arr[]) {
		long start=System.currentTimeMillis();
		int length = arr.length;
		for(int i=1;i<length;i++) {
			
			int key = arr[i];
			int j=i-1;
			
			while(j>=0 && arr[j]>key) {
				arr[j+1] = arr[j];
				j--;
			}
			arr[j+1]=key;
		}
		long diff=System.currentTimeMillis()-start;
		System.out.println("time taken for insertion sort: "+diff+" ms");
		return arr;
	}

}
