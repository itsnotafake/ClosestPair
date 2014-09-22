package closestpair;

public class CPAlgorithm {
	
	MergeSort ms = new MergeSort();
	MergeSortY msY = new MergeSortY();
	DataPoint data_point = new DataPoint();
	
	double minDist;
	
	public DataSet ClosestPair(DataPoint[] dp_arr_sortX, DataPoint[] dp_arr_sortY){
		if(dp_arr_sortX.length == 1){
			System.out.println("Your DataSet only has one point, unable to find closestDistance");
			return new DataSet(dp_arr_sortX[0]);
		}
		if(dp_arr_sortX.length <= 3){
			DataSet ds = bruteDistance(dp_arr_sortX);
			System.out.println();
			ds.printDataSet();
			return ds;
		}
		///////////////////////////////////////////////////////////////// Stuff above works on arrays of size 3 or less
		DataSet best = ClosestPair(dp_arr_sortX);
		return best;
	}
		
	private DataSet ClosestPair(DataPoint[] dp_arr_sortX){
		if(dp_arr_sortX.length == 1){
			return new DataSet(dp_arr_sortX[0]);
		}
		if(dp_arr_sortX.length <= 3){
			DataSet ds = bruteDistance(dp_arr_sortX);
			System.out.println();
			ds.printDataSet();
			return ds;
		}
		///////////////////////////////////////////////////////////////// Stuff above works on arrays of size 3 or less

		int new_length = dp_arr_sortX.length/2;
		DataPoint[] new1 = copyDPA(dp_arr_sortX, 0, new_length - 1, new_length);
		DataPoint[] new2 = copyDPA(dp_arr_sortX, new_length, dp_arr_sortX.length - 1, dp_arr_sortX.length - new_length);
		DataSet ds1 = ClosestPair(new1);
		DataSet ds2 = ClosestPair(new2);
		DataSet best = new DataSet();
		if(ds1.getDistance() <= ds2.getDistance()){
			best = ds1;
		}
		else{
			best = ds2;
		}
		int length_counter = 0;
		
		for(int i = 0; i < new1.length; i++){
			double check_dist = data_point.calcDist(new1[i], dp_arr_sortX[new_length]);
			if(check_dist < best.getDistance()) length_counter++;
		}
		
		for(int i = 0; i < new2.length; i++){
			double check_dist = data_point.calcDist(new2[i], dp_arr_sortX[new_length]);
			if(check_dist < best.getDistance()) length_counter++;
		}
		DataPoint[] check_These_X = new DataPoint[length_counter];
		////////////////////////////////////////////////////////////
		length_counter = 0;
		for(int i = 0; i < new1.length; i++){
			double check_dist = data_point.calcDist(new1[i], dp_arr_sortX[new_length]);
			if(check_dist < best.getDistance()){
				check_These_X[length_counter] = new1[i];
				length_counter++;
			}
		}
		for(int i = 0; i < new2.length; i++){
			double check_dist = data_point.calcDist(new1[i], dp_arr_sortX[new_length]);
			if(check_dist < best.getDistance()) {
				check_These_X[length_counter] = new2[i];
				length_counter++;
			}
		}
		////////////////////////////////////////////////////////////////
		
		check_These_X = msY.MergeSortGo(check_These_X);
		DataSet best_2 = bruteDistance(check_These_X);
		if(best_2.getDistance() <= best.getDistance()){
			best = best_2;
		}
		return best;
	}
	
		
	private DataSet bruteDistance(DataPoint[] dp){
		double minDist = 999999.9;
		DataPoint p1 = null;
		DataPoint p2 = null;
		if(dp.length == 2){
			double temp_dist = data_point.calcDist(dp[0], dp[1]);
			if(temp_dist < minDist){
				minDist = temp_dist;
				p1 = new DataPoint(dp[0].x_coord, dp[0].y_coord, minDist);
				p2 = new DataPoint(dp[1].x_coord, dp[1].y_coord, minDist);
			}
		}
		else{
			for(int i = 0; i < dp.length -1; i++){
				for(int j = i + 1; j < dp.length; j++){
					double d = data_point.calcDist(dp[i], dp[j]);
					if (d < minDist){
						minDist = d;
						p1 = dp[i];
						p2 = dp[j];
					}
				}
			}
		}
		return new DataSet(p1, p2, minDist);
	}
	
	private DataPoint[] copyDPA(DataPoint[] dp_arr, int begin, int end, int length){
		DataPoint[] newGuy = new DataPoint[length];
		for(int i = begin; i <= end; i++){
			newGuy[i - begin] = dp_arr[i];
		}
		return newGuy;
	}
	
	
}
