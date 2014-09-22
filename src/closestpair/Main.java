package closestpair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
	
	static int number_of_lines;
	static DataPoint[] dp_array;
	
	public static void main(String[] Args) throws Exception{
		readFile(Args[0]);
		for(int i = 0; i < dp_array.length; i++){
			String s = DataPoint.toString(dp_array[i]);
			System.out.println(s);
		}
		
		RadixSort rs = new RadixSort();
		dp_array = rs.radixSortX(dp_array);
	}
	
	public static void readFile(String s){
		int i = 0;
		try{
			Scanner newFile = new Scanner(new File(s));
			number_of_lines = newFile.nextInt();
			dp_array = new DataPoint[number_of_lines];
			newFile.nextLine();
			while(newFile.hasNextLine()){
				double x = newFile.nextDouble();
				double y = newFile.nextDouble();
				DataPoint new_dp = new DataPoint(x,y);
				dp_array[i] = new_dp;
				i++;
			}
			newFile.close();
		}
		catch(FileNotFoundException exception){
			System.out.println("Missing file");
		}
		catch(ArrayIndexOutOfBoundsException exception){
			System.out.println("Array Index out of Bounds");
		}
	}

}
