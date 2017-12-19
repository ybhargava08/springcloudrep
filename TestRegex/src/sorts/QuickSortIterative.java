package sorts;

import java.util.Stack;

public class QuickSortIterative {
	
	public void doQuickSortIterative(int arr[]) {
		long start = System.currentTimeMillis();
		Stack<Integer> stack = new Stack<Integer>();
		stack.push(0);
		stack.push(arr.length-1);
		quickSortIterative(arr,stack,arr.length);
		long diff = System.currentTimeMillis()-start;
		System.out.println("time taken for quick sort iterative: "+diff+" ms");
	}
	
	public void quickSortIterative(int arr[],Stack<Integer> stack,int arrLength) {
		
		while(!stack.isEmpty()) {
			int indexHigh = stack.pop();
			int indexLow= stack.pop();
			
			if(indexLow<indexHigh){
				int indexPartition = partitionMid(indexLow,indexHigh,arr);
				//first insert low then insert high
				if(indexPartition-1>indexLow) {
					stack.push(indexLow);
					stack.push(indexPartition-1);	
				}
				
				if(indexHigh>indexPartition+1) {
					stack.push(indexPartition+1);
					stack.push(indexHigh);	
				}
				//System.out.println(stack.size());				
			}	
		}
		
	}
	
	public static int partitionLast(int indexLow,int indexHigh,int arr[]) {	
		int pivot = arr[indexHigh];
		//int pivot = (arr[indexLow]+arr[indexHigh])/2;
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
	
	public static int partitionRandom(int indexLow,int indexHigh,int arr[]) {	
		int pivotIndex = randomWithRange(indexLow,indexHigh);
		int pivot = arr[pivotIndex];
		int indexWall = indexLow-1;
		
		for(int i=indexLow;i<=indexHigh;i++){
			if(i!=pivotIndex && arr[i]<pivot){
				indexWall++;
				pivotIndex=swapReturnPivotIndex(arr,i,indexWall,pivotIndex);
			}
		}
		pivotIndex=swapReturnPivotIndex(arr,pivotIndex,indexWall+1,pivotIndex);
			//return indexWall+1;	
		return pivotIndex;
	}
	
	public static int partitionFirst(int indexLow,int indexHigh,int arr[]) {	
		int pivotIndex = indexLow;
		int pivot = arr[pivotIndex];
		int indexWall = indexLow-1;
		
		for(int i=indexLow;i<=indexHigh;i++){
			if(i!=pivotIndex && arr[i]<pivot){
				indexWall++;
				pivotIndex=swapReturnPivotIndex(arr,i,indexWall,pivotIndex);
			}
		}
		pivotIndex=swapReturnPivotIndex(arr,pivotIndex,indexWall+1,pivotIndex);
			//return indexWall+1;	
		return pivotIndex;
	}
	
	public static int partitionMid(int indexLow,int indexHigh,int arr[]) {	
		int pivotIndex = (indexLow+indexHigh)/2;
		int pivot = arr[pivotIndex];
		int indexWall = indexLow-1;
		
		for(int i=indexLow;i<=indexHigh;i++){
			if(i!=pivotIndex && arr[i]<pivot){
				indexWall++;
				pivotIndex=swapReturnPivotIndex(arr,i,indexWall,pivotIndex);
			}
		}
		pivotIndex=swapReturnPivotIndex(arr,pivotIndex,indexWall+1,pivotIndex);
			//return indexWall+1;	
		return pivotIndex;
	}
	
	private static int randomWithRange(int min,int max) {
	    int range = (max-min)+1;
	    return (int)(Math.random()*range)+min;
	}
	
	public static void swapElements(int arr[],int pos1,int pos2) {
		if(pos1!=pos2) {	
			 int temp = arr[pos1];
			 arr[pos1] = arr[pos2];
			 arr[pos2] = temp;
		}
	}
	
	public static int swapReturnPivotIndex(int arr[],int pos1,int pos2,int pivotIndex) {
		if(pos1!=pos2) {
			if(pos1==pivotIndex) {
				pivotIndex=pos2;
			}else if(pos2==pivotIndex) {
				pivotIndex=pos1;
			}
			 int temp = arr[pos1];
			 arr[pos1] = arr[pos2];
			 arr[pos2] = temp;
		}
		return pivotIndex;
	}

}
