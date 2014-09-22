package closestpair;

public class DataSet {
	
	DataPoint pp1;
	DataPoint pp2;
	double d;
	DataPoint dp = new DataPoint();
	
	public DataSet(DataPoint p1, DataPoint p2, double distance){
		pp1 = p1;
		pp2 = p2;
		d = distance;
	}
	
	public DataSet(DataPoint p1){
		pp1 = p1;
		d = 0;
	}
	
	public DataSet(){
	}
	
	public DataPoint getDataPoint1(){
		return pp1;
	}
	
	public DataPoint getDataPoint2(){
		return pp2;
	}
	
	public double getDistance(){
		if(d == 0){
			return 9999999999.8;
		}
		return d;
	}
	
	public void printDataSet(){
		String s1 = dp.toString(pp1);
		String s2 = dp.toString(pp2);
		String s3 = String.valueOf(d);
		System.out.println("DataSet point1 is: (" + s1 + "), Dataset point2 is: (" + s2 + "), distance is: " + s3);
	}
}
