package closestpair;

import org.apache.commons.lang3.ArrayUtils;

public class RadixSort {
	
		public RadixSort(){
		}

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
					number_positive++;
				}
				else{
					negative_portion[number_negative] = dp_arr[i];
					number_negative++;
				}
			} //ending here, we have completed Step 1 of RadixSort
			DataPoint.printDataSet(positive_portion);
			System.out.println();
			DataPoint.printDataSet(negative_portion);
			System.out.println();
			
			
			
			int longestLength = findLongestLength(positive_portion); //Step 1 of positive portion
			System.out.println("Index/longest length is: " + longestLength);
			
			int longestDecimal = findLongestDecimal(positive_portion); //Step 2 of positive portion
			System.out.println("Longest Decimal is: " + longestDecimal);
			int longestNonDecimal = findLongestNonDecimal(positive_portion); //Step 2 of positive portion
			System.out.println("Longest NonDecimal is: " + longestNonDecimal);
			/*for(int i = 0; i < positive_portion.length; i++){
				addDecimalValues(positive_portion[i], longestDecimal);
			}
			DataPoint.printDataSet(positive_portion);
			System.out.println();*/
			
			for(int i = 0; i < longestDecimal + longestNonDecimal - 1; i++){//Step 3 of positive portion
				int[] bucket_list = noteBucket(positive_portion, i, longestDecimal, longestLength, longestNonDecimal); 
				positive_portion = rearrangePos(positive_portion, bucket_list, i, longestDecimal, longestLength, longestNonDecimal); //rearrange completes Step 4 and 5 of positive portion
			}
			
			//Here begins the negative portion section
			
			for(int i = 0; i < negative_portion.length; i++){ //Step 1 of negative portion
				String s = String.valueOf(negative_portion[i].x_coord);
				s = s.replace("-", "");
				negative_portion[i].x_coord = Double.valueOf(s);
			}
			
			int negative_longestLength = findLongestLength(negative_portion); //Step 2.1 negative portion
			
			int longestDecimal_negative = findLongestDecimal(negative_portion); //Step 2.2 negative portion
			int negative_longestNonDecimal = findLongestNonDecimal(negative_portion); //Step 2.2 negative portion
			
			for(int i = 0; i < longestDecimal_negative + negative_longestNonDecimal - 1; i++){//Step 2.3 of negative portion
				int[] bucket_list = noteBucket(negative_portion, i, longestDecimal_negative, negative_longestLength, negative_longestNonDecimal);
				negative_portion = rearrangeNeg(negative_portion, bucket_list, i, longestDecimal_negative, negative_longestLength, negative_longestNonDecimal);
			}
			
			for(int i = 0; i < negative_portion.length; i++){ //Step 3 of negative portion
				String s = String.valueOf(negative_portion[i].x_coord);
				s = "-" + s;
				negative_portion[i].x_coord = Double.valueOf(s);
				
			}
			
			return ArrayUtils.addAll(negative_portion, positive_portion); //Step 2 of Radix Sort
		}
		
		private int[] noteBucket(DataPoint[] dp_arr, int index, int longestDecimal, int longestLength, int longestNonDecimal){ // returns an array that represents number of digits in each bucket ( 0 - 9 )
			int[] bucket = {0,0,0,0,0,0,0,0,0,0};
			for(int i = 0; i < dp_arr.length; i++){
				int j = getDigit(dp_arr[i].x_coord, index, longestDecimal, longestLength, longestNonDecimal);
				System.out.println("Digit is: " + j);
				bucket[j] = bucket[j] + 1;	
			}
			return bucket;
		}
		
		private int getDigit(double x_coord, int index, int longestDecimal, int longestLength, int longestNonDecimal){ // using index as a reference as to which digit to return, returns a specific digit in an integer
			if(index)
			if(index >= longestDecimal){
				String s = String.valueOf(x_coord);
				if(s.contains(".")){
					int decimal_marker = s.indexOf(".");
					String s_after_decimal = s.substring(decimal_marker + 1);
					int current_decimal_length = s_after_decimal.length();
					if(current_decimal_length < index - longestDecimal){
						return 0;
					}
					char c = s.charAt(index-longestDecimal);
					String s2 = String.valueOf(c);
					double digit = Double.valueOf(s2);
					return (int) digit; 	
				}
				return 69;
			}
			else{
				String s = String.valueOf(x_coord);
				int decimal_marker = s.indexOf(".");
				String s_before_decimal = s.substring(0, decimal_marker - 1);
				int current_value = Integer.parseInt(s_before_decimal);
				if(current_value < (longestNonDecimal - index - 1) * 10) return 0;
				char c = s.charAt(index);
				String s2 = String.valueOf(c);
				double digit = Double.valueOf(s2);
				return (int) digit; 
			}
		}
		
		
		private int findLongestLength(DataPoint[] dp_arr){ //returns the length of the longest value to be used as the index
			String longest = "";
			System.out.println("length is " + dp_arr.length); //SOPL RIGHT HERE!!!
			for(int i = 0; i < dp_arr.length; i++){
				String dp_arr_string = String.valueOf(dp_arr[i].x_coord);
				if(dp_arr_string.contains(".")){
					dp_arr_string = dp_arr_string.replace(".", "");
				}
				if(dp_arr_string.length() > longest.length()){
					longest = dp_arr_string;
					System.out.println(longest); //SOPL HERE!!!
				}
			}
			System.out.println("longest length is: " + longest.length()); //SOPL HERE !!!!
			return longest.length();
		}
		
		private int findLongestDecimal(DataPoint[] dp_arr){ // will return length of longest decimal. 5 if longest is 23.00001
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
		
		private int findLongestNonDecimal(DataPoint[] dp_arr){ //adds 0s to the end of x_coord values so all values have same amount of 0s (decimal)
			int longestNonDecimal = 0;
			for(int i = 0; i < dp_arr.length; i++){
				String dp_arr_string = String.valueOf(dp_arr[i].x_coord);
				int decimal_index = dp_arr_string.lastIndexOf(".");
				String nonDecimal = dp_arr_string.substring(0, decimal_index - 1);
				if(nonDecimal.length() > longestNonDecimal) longestNonDecimal = nonDecimal.length();
			}
			return longestNonDecimal;
		}
		
		private DataPoint[] rearrangePos(DataPoint[] dp_arr, int[] bucket_list, int index, int longestDecimal, int LongestLength, int longestNonDecimal){
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
				int digit = getDigit(dp_arr[i].x_coord, index, longestDecimal, LongestLength, longestNonDecimal);
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
			dp_arr = addArrays(zero_list, one_list, two_list, three_list, four_list, 
					five_list, six_list, seven_list, eight_list, nine_list);
			return dp_arr;
			
		}
		
		
		private DataPoint[] rearrangeNeg(DataPoint[] dp_arr, int[] bucket_list, int index, int longestDecimal, int longestLength, int longestNonDecimal){
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
				int digit = getDigit(dp_arr[i].x_coord, index, longestDecimal, longestLength, longestNonDecimal);
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
			dp_arr = addArrays(nine_list, eight_list, seven_list, six_list, five_list, four_list, three_list,
					two_list, one_list, zero_list);
			return dp_arr;
			
		}
		
		private DataPoint[] addArrays(DataPoint[] dp_a, DataPoint[] dp_b, DataPoint[] dp_c, DataPoint[] dp_d, DataPoint[] dp_e, DataPoint[] dp_f, DataPoint[] dp_g, DataPoint[] dp_h, 
				DataPoint[] dp_i, DataPoint[] dp_j){
			DataPoint[] a_b = (DataPoint[]) ArrayUtils.addAll(dp_a, dp_b);
			DataPoint[] c_d = (DataPoint[]) ArrayUtils.addAll(dp_c, dp_d);
			DataPoint[] e_f = (DataPoint[]) ArrayUtils.addAll(dp_e, dp_f);
			DataPoint[] g_h = (DataPoint[]) ArrayUtils.addAll(dp_g, dp_h);
			DataPoint[] i_j = (DataPoint[]) ArrayUtils.addAll(dp_i, dp_j);
			
			DataPoint[] a_b_c_d = (DataPoint[]) ArrayUtils.addAll(a_b, c_d);
			DataPoint[] e_f_g_h = (DataPoint[]) ArrayUtils.addAll(e_f, g_h);
			
			DataPoint[] a_b_c_d_e_f_g_h = (DataPoint[]) ArrayUtils.addAll(a_b_c_d, e_f_g_h);
			
			DataPoint[] a_b_c_d_e_f_g_h_i_j = (DataPoint[]) ArrayUtils.addAll(a_b_c_d_e_f_g_h, i_j);
			return a_b_c_d_e_f_g_h_i_j;
		}
		
		
}

