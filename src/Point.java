package graph;


import java.util.*;

public class Point {
	int x,y,z;
	//store coordinates of sequence
	Point(int x,int y,int z){
		this.x=x;
		this.y=y;
		this.z=z;
	}
	//to create object of point class
	public Point() {
		// TODO Auto-generated constructor stub
	}
	// comapre toow point
	public int compareTo(Point  comparisonPoint) {
		// TODO Auto-generated method stub
		if(this.x< comparisonPoint.x) {
			//if the this point's coordinated is less than comparisionPoint's coordinates
			return -1;
		}else if(this.x> comparisonPoint.x) {
			//if the this point's coordinated is greate than comparisionPoint's coordinates
			return 1;
		}else {
			if(this.y< comparisonPoint.y) {
				return -1;
			}
			else if(this.y> comparisonPoint.y) {
				return 1;
			}
			else {
				if(this.z< comparisonPoint.z) {
					return -1;
				}
				else if(this.z> comparisonPoint.z) {
					return 1;
				}
				else {
					//if the this point's coordinated is same as comparisionPoint's coordinates
					return 0;
				}
			}
		}
	}
}
