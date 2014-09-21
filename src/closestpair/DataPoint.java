package closestpair;

import java.awt.geom.Point2D;

public class DataPoint {
	
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
	
	public static double calcDist(DataPoint x, DataPoint y){
		double distance;
		distance = Point2D.distance(x.x_coord, y.x_coord, x.y_coord, y.y_coord);
		return distance;
	}
	
	
	public static String toString(DataPoint p){
		String x = String.valueOf(p.x_coord);
		String y = String.valueOf(p.y_coord);
		String to_String = x + "," + y;
		return to_String;
	}

}
