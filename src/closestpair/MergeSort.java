package closestpair;

public class MergeSort {
	
	DataPoint dp = new DataPoint();
	
	public MergeSort(){
	}
	
	public DataPoint[] MergeSortGo(DataPoint[] dp_arr){
		if(dp_arr.length < 1){
			return dp_arr;
		}
		int new_length = dp_arr.length/2;
		DataPoint[] dp_arr_left = new DataPoint[new_length];
		DataPoint[] dp_arr_right = new DataPoint[dp_arr.length - new_length];
		dp_arr_left = copyDP(dp_arr, dp_arr_left, 0, new_length-1);
		dp.printDataSet(dp_arr_left);
		System.out.println();
		dp_arr_right = copyDP(dp_arr, dp_arr_right, new_length, dp_arr.length-1);
		dp.printDataSet(dp_arr_right);
		System.out.println();
		
		dp_arr_left = MergeSortGo(dp_arr_left, new_length);
		dp_arr_right = MergeSortGo(dp_arr_right, dp_arr.length - new_length );
		return Merge(dp_arr_left, dp_arr_right);
	}
		
	public DataPoint[] MergeSortGo(DataPoint[] dp_arr, int length){
		if(dp_arr.length < 2){
			return dp_arr;
		}
		int new_length = dp_arr.length/2;
		DataPoint[] dp_arr_left = new DataPoint[new_length];
		DataPoint[] dp_arr_right = new DataPoint[dp_arr.length - new_length];
		dp_arr_left = copyDP(dp_arr, dp_arr_left, 0, new_length-1);
		dp.printDataSet(dp_arr_left);
		System.out.println();
		dp_arr_right = copyDP(dp_arr, dp_arr_right, new_length, dp_arr.length-1);
		dp.printDataSet(dp_arr_right);
		System.out.println();
		
		dp_arr_left = MergeSortGo(dp_arr_left, new_length);
		dp_arr_right = MergeSortGo(dp_arr_right, dp_arr.length - new_length );
		return Merge(dp_arr_left, dp_arr_right);
	}
	
	public DataPoint[] Merge(DataPoint[] dp_arr_left,DataPoint[] dp_arr_right){
		int left_holder = 0;
		int right_holder = 0;
		DataPoint[] dp_arr = new DataPoint[dp_arr_left.length + dp_arr_right.length];
		for(int i = 0; i < dp_arr.length; i++){
			if(left_holder >= dp_arr_left.length){
				dp_arr[i] = dp_arr_right[right_holder];
				right_holder++;
			}
			else if(right_holder >= dp_arr_right.length){
				dp_arr[i] = dp_arr_left[left_holder];
				left_holder++;
			}
			else if(dp_arr_left[left_holder].x_coord >= dp_arr_right[right_holder].x_coord){
				dp_arr[i] = dp_arr_left[left_holder];
				left_holder++;
			}
			else{
				dp_arr[i] = dp_arr_right[right_holder];
				right_holder++;
			}
		}
		return dp_arr;
	}
	
	public DataPoint[] copyDP(DataPoint[] dp_arr, DataPoint[] newGuy, int begin, int end){
		int marker = 0;
		for(int i = begin; i <= end; i++){
			newGuy[marker] = dp_arr[i];
			marker++;
		}
		return newGuy;
	}
		
	

}
