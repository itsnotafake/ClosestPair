package closestpair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
	
	static int number_of_lines = 0;
	static DataPoint[] dp_array;
	
	public static void main(String[] Args) throws Exception{
		getNOL(Args[0]);
		dp_array = new DataPoint[number_of_lines];
		readFile(Args[0]);
		for(int i = 0; i < dp_array.length; i++){
			String s = DataPoint.toString(dp_array[i]);
			System.out.println(s);
		}
	}
	
	public static void getNOL(String s) throws Exception{
		try{
			Scanner newFile = new Scanner(new File(s));
			while(newFile.hasNextLine()){
				++number_of_lines;
				newFile.nextLine();
			}
				newFile.close();
		}
			catch(FileNotFoundException exception){
				System.out.println("Missing File");
			}
	}
	
	public static void readFile(String s){
		int i = 0;
		try{
			Scanner newFile = new Scanner(new File(s));
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
