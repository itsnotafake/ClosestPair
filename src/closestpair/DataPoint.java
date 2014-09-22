package closestpair;

import java.awt.geom.Point2D;

public class DataPoint {
	
	public DataPoint(){
	}
	
	double x_coord;
	double y_coord;
	
	public DataPoint(double x, double y){
		x_coord = x;
		y_coord = y;
		
	}
	
	public double getX(){
		return x_coord;
	}
	
	public double getY(){
		return y_coord;
	}
	
	public static double calcDist(DataPoint p1, DataPoint p2){
		return Point2D.distance(p1.x_coord, p2.x_coord, p1.y_coord, p2.y_coord);
	}
	
	
	public static String toString(DataPoint p){
		String x = String.valueOf(p.x_coord);
		String y = String.valueOf(p.y_coord);
		String to_String = x + "," + y;
		return to_String;
	}
	
	public void printDataSet(DataPoint[] dp){
		for(int i = 0; i < dp.length; i++){
			String s = DataPoint.toString(dp[i]);
			System.out.println(s);
		}
	}

}
