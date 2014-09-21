package closestpair;

import org.apache.commons.lang3.ArrayUtils;

public class RadixSort {

		public DataPoint[] radixSortX(DataPoint[] dp_arr){
			int number_positive = 0;
			int number_negative = 0;
			
			for(int i = 0; i < dp_arr.length; i++){
				if(dp_arr[i].x_coord < 0){
					number_negative++;
				}
				else{
					number_positive++;
				}
			}
			DataPoint[] positive_portion = new DataPoint[number_positive];
			DataPoint[] negative_portion = new DataPoint[number_negative];
			number_positive = 0;
			number_negative = 0;
			for(int i = 0; i < dp_arr.length; i++){
				if(dp_arr[i].x_coord > 0){
					positive_portion[number_positive] = dp_arr[i];
				}
				else{
					negative_portion[number_negative] = dp_arr[i];
				}
			} //ending here, we have completed Step 1 of RadixSort
			
			
			
			int index = findIndexX(positive_portion); //Step 1 of positive portion
			
			int longestDecimal = findLongestDecimal(positive_portion); //Step 2 of positive portion
			for(int i = 0; i < positive_portion.length; i++){
				positive_portion[i] = addDecimalValues(positive_portion[i], longestDecimal);
			}
			
			for(int i = 0; i < index; i++){//Step 3 of positive portion
				int[] bucket_list = noteBucket(positive_portion, (index - (i+1)), (i+1));
				positive_portion = rearrangePos(positive_portion, bucket_list, (index - (i+1)), (i+1)); //rearrange completes Step 4 and 5 of positive portion
			}
			
			//Here begins the negative portion section
			
			for(int i = 0; i < negative_portion.length; i++){ //Step 1 of negative portion
				String s = String.valueOf(negative_portion[i].x_coord);
				s = s.replace("-", "");
				negative_portion[i].x_coord = Double.valueOf(s);
			}
			
			int negative_index = findIndexX(negative_portion); //Step 2.1 negative portion
			
			int longestDecimal_negative = findLongestDecimal(negative_portion); //Step 2.2 negative portion
			for(int i = 0; i < negative_portion.length; i++){
				negative_portion[i] = addDecimalValues(negative_portion[i], longestDecimal_negative);
			}
			
			for(int i = 0; i < negative_index; i++){//Step 2.3 of negative portion
				int[] bucket_list = noteBucket(negative_portion, (index - (i+1)), (i+1));
				negative_portion = rearrangeNeg(negative_portion, bucket_list, (index- (i+1)), (i+1));
			}
			
			for(int i = 0; i < negative_portion.length; i++){ //Step 3 of negative portion
				String s = String.valueOf(negative_portion[i].x_coord);
				s = "-" + s;
				negative_portion[i].x_coord = Double.valueOf(s);
				
			}
			
			dp_arr = ArrayUtils.addAll(negative_portion, positive_portion); //Step 2 of Radix Sort
			return dp_arr;

		}
		
		public int[] noteBucket(DataPoint[] dp_arr, int index, int iteration){ // returns an array that represents number of digits in each bucket ( 0 - 9 )
			int[] bucket = {0,0,0,0,0,0,0,0,0,0};
			for(int i = 0; i < dp_arr.length; i++){
				int j = getDigit(dp_arr[i].x_coord, index, iteration);
				bucket[j] = bucket[j] + 1;	
			}
			return bucket;
		}
		
		public int getDigit(double x_coord, int index, int iteration){ // using index as a reference as to which digit to return, returns a specific digit in an integer
			String dp_string = String.valueOf(x_coord);
			if(dp_string.contains(".")){
				dp_string = dp_string.replace(".", "");
			}
			char[] dp_char_array = dp_string.toCharArray();
			if(dp_char_array.length < iteration){
				return 0;
			}
			return (int)dp_char_array[index];
		}
		
		
		public int findIndexX(DataPoint[] dp_arr){ //returns the length of the longest value to be used as the index
			String largest = "";
			for(int i = 0; i < dp_arr.length; i++){
				String dp_arr_string = String.valueOf(dp_arr[i].x_coord);
				if(dp_arr_string.contains(".")){
					dp_arr_string.replace(".", "");
				}
				if(dp_arr_string.length() > largest.length()){
					largest = dp_arr_string;
				}
			}
			return largest.length();
		}
		
		public int findLongestDecimal(DataPoint[] dp_arr){ // will return length of longest decimal. 5 if longest is 23.00001
			double longestDecimal = 0.0;
			for(int i = 0; i < dp_arr.length; i++){
				String dp_arr_string = String.valueOf(dp_arr[i].x_coord);
				if(dp_arr_string.contains(".")){
					String longestDecimal_string = String.valueOf(longestDecimal);
						
					int decimal_index = dp_arr_string.lastIndexOf(".");
					int longestDecimal_decimal_index = longestDecimal_string.lastIndexOf(".");
					
					String dp_arr_string_decimal_portion = dp_arr_string.substring(decimal_index);
					String longestDecimal_string_decimal_portion = longestDecimal_string.substring(longestDecimal_decimal_index);
					
					if(dp_arr_string_decimal_portion.length() > longestDecimal_string_decimal_portion.length()){
						longestDecimal = dp_arr[i].x_coord;
					}
					
				}
			}
			String s = String.valueOf(longestDecimal);
			int decimal_marker = s.lastIndexOf(".");
			s = s.substring(decimal_marker);
			return s.length() - 1;
		}
		
		public DataPoint addDecimalValues(DataPoint dp, int longestDecimal){ //adds 0s to the end of x_coord values so all values have same amount of 0s (decimal)
			String s = String.valueOf(dp.x_coord);
			int dp_dec = s.lastIndexOf(".");
			String s2 = s.substring(dp_dec);
			int s2_length = s2.length() - 1;
			while((longestDecimal - s2_length) > 0){
				s.concat("0");
				s2_length++;
			}
			double new_x_coord = Double.valueOf(s);
			dp.x_coord = new_x_coord;
			return dp;
		}
		
		public DataPoint[] rearrangePos(DataPoint[] dp_arr, int[] bucket_list, int index, int iteration){
			DataPoint[] zero_list = new DataPoint[bucket_list[0]];
			DataPoint[] one_list = new DataPoint[bucket_list[1]];
			DataPoint[] two_list = new DataPoint[bucket_list[2]];
			DataPoint[] three_list = new DataPoint[bucket_list[3]];	
			DataPoint[] four_list = new DataPoint[bucket_list[4]];
			DataPoint[] five_list = new DataPoint[bucket_list[5]];
			DataPoint[] six_list = new DataPoint[bucket_list[6]];
			DataPoint[] seven_list = new DataPoint[bucket_list[7]];
			DataPoint[] eight_list = new DataPoint[bucket_list[8]];
			DataPoint[] nine_list = new DataPoint[bucket_list[9]];
			
			int zero_marker = 0;
			int one_marker = 0;
			int two_marker = 0;
			int three_marker = 0;
			int four_marker = 0;
			int five_marker = 0;
			int six_marker = 0;
			int seven_marker = 0;
			int eight_marker = 0;
			int nine_marker = 0;
			
			for(int i = 0; i < dp_arr.length; i++){
				int digit = getDigit(dp_arr[i].x_coord, index, iteration);
				if(digit == 0){
					zero_list[zero_marker] = dp_arr[i];
					zero_marker++;
				}
				else if(digit == 1){
					one_list[one_marker] = dp_arr[i];
					one_marker++;
				}
				else if(digit == 2){
					two_list[two_marker] = dp_arr[i];
					two_marker++;
				}
				else if(digit == 3){
					three_list[three_marker] = dp_arr[i];
					three_marker++;
				}
				else if(digit == 4){
					four_list[four_marker] = dp_arr[i];
					four_marker++;
				}
				else if(digit == 5){
					five_list[five_marker] = dp_arr[i];
					five_marker++;
				}
				else if(digit == 6){
					six_list[six_marker] = dp_arr[i];
					six_marker++;
				}	
				else if(digit == 7){
					seven_list[seven_marker] = dp_arr[i];
					seven_marker++;
				}
				else if(digit == 8){
					eight_list[eight_marker] = dp_arr[i];
					eight_marker++;
				}
				else{
					nine_list[nine_marker] = dp_arr[i];
					nine_marker++;
				}
			}
			dp_arr = (DataPoint[]) ArrayUtils.addAll(zero_list, one_list, two_list, three_list, four_list, 
					five_list, six_list, seven_list, eight_list, nine_list);
			return dp_arr;
			
		}
		
		
		public DataPoint[] rearrangeNeg(DataPoint[] dp_arr, int[] bucket_list, int index, int iteration){
			DataPoint[] zero_list = new DataPoint[bucket_list[0]];
			DataPoint[] one_list = new DataPoint[bucket_list[1]];
			DataPoint[] two_list = new DataPoint[bucket_list[2]];
			DataPoint[] three_list = new DataPoint[bucket_list[3]];	
			DataPoint[] four_list = new DataPoint[bucket_list[4]];
			DataPoint[] five_list = new DataPoint[bucket_list[5]];
			DataPoint[] six_list = new DataPoint[bucket_list[6]];
			DataPoint[] seven_list = new DataPoint[bucket_list[7]];
			DataPoint[] eight_list = new DataPoint[bucket_list[8]];
			DataPoint[] nine_list = new DataPoint[bucket_list[9]];
			
			int zero_marker = 0;
			int one_marker = 0;
			int two_marker = 0;
			int three_marker = 0;
			int four_marker = 0;
			int five_marker = 0;
			int six_marker = 0;
			int seven_marker = 0;
			int eight_marker = 0;
			int nine_marker = 0;
			
			for(int i = 0; i < dp_arr.length; i++){
				int digit = getDigit(dp_arr[i].x_coord, index, iteration);
				if(digit == 0){
					zero_list[zero_marker] = dp_arr[i];
					zero_marker++;
				}
				else if(digit == 1){
					one_list[one_marker] = dp_arr[i];
					one_marker++;
				}
				else if(digit == 2){
					two_list[two_marker] = dp_arr[i];
					two_marker++;
				}
				else if(digit == 3){
					three_list[three_marker] = dp_arr[i];
					three_marker++;
				}
				else if(digit == 4){
					four_list[four_marker] = dp_arr[i];
					four_marker++;
				}
				else if(digit == 5){
					five_list[five_marker] = dp_arr[i];
					five_marker++;
				}
				else if(digit == 6){
					six_list[six_marker] = dp_arr[i];
					six_marker++;
				}	
				else if(digit == 7){
					seven_list[seven_marker] = dp_arr[i];
					seven_marker++;
				}
				else if(digit == 8){
					eight_list[eight_marker] = dp_arr[i];
					eight_marker++;
				}
				else{
					nine_list[nine_marker] = dp_arr[i];
					nine_marker++;
				}
			}
			dp_arr = (DataPoint[]) ArrayUtils.addAll(nine_list, eight_list, seven_list, six_list, five_list, four_list, three_list,
					two_list, one_list, zero_list);
			return dp_arr;
			
		}
		
		
}

