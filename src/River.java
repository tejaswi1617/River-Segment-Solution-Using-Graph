package graph;

import java.io.*;

import java.util.*;

public class River {
	//To store graph as adjacency List
	private Map<Point, List<Point>> adjacency_list;
	//To store all the segments in file for creating egdes
	private ArrayList<Point> endpoints;
	//To sotre end points of segments
	private ArrayList<Point> edges; 
	
	private int riversegment=0,flag=0;
	
//initialize objects
 public River() {
	 
	 adjacency_list  =new HashMap<Point, List<Point>>();
	 edges=new ArrayList<Point>();
	 endpoints=new ArrayList<Point>();
	
 }
 int load(BufferedReader inputStream) throws IOException {
		String line="";
		
		line=inputStream.readLine();
		//check given input has data or not
		if(line==null || line.isEmpty()) {
			throw new  IllegalArgumentException();
		}
		//to make first segment's first sequence is endpoint
		edges.add(null);
		//read input and create points of river network from the sequences of segments
		while(line.compareToIgnoreCase("done")!=0)
		{
			//consider all the sequences in single segment till black line encounters 
			if(!line.isEmpty()) {
				flag=0;
				//store x,y,z cordinates by splitting string
				String[] vertexV=line.split("\\s+");
				//each point on river network must contains exactly 3 coordinates 
				if(vertexV.length!=3) {
					throw new  IllegalArgumentException();
				}else
				{
					//convert all the coordinated from string to integer
					int x=Integer.parseInt(vertexV[0]);
					int y=Integer.parseInt(vertexV[1]);
					int z=Integer.parseInt(vertexV[2]);
					Point g=new Point();
					//create point which stores x, y, z coordinates
					g=new Point(x,y,z);
					Point findver=new Point();
					//check whether the given x, y, z values is already present in any points stored in Map
					findver= find(g);
					if(findver==null) {
						//store that point to create edges later stage 
						 edges.add(g);
						//Point(vertex) is new and store as key in adjacency_list 
						adjacency_list.put(g, new ArrayList<>()); 
					}
					else {
						//point is already exists in river network,ignore it will not store as key in Map
						//store that point to create edges later stage 
						edges.add(findver);
					}
				}
			}	
			else {
				//flag=0 means two segments are separated by single black line
				if(flag==0) {
					//it will use when two segments are separated by multiple black lines
					flag=1;
					//keep track of successfully added segments in river network 
					riversegment++;
					//add null between two river segments
					edges.add(null);
				}
			}
			line=inputStream.readLine();
		}
		//count river segment if "done" is given without black line 
		if(flag==0) {
			riversegment++;
		}
		edges.add(null);
		
		//stores points(egdes) connected to current point 
		for(int i=0;i<edges.size()-1;i++) 
		{   	
				//null is used to separate two segments, which it ignores
				if(edges.get(i+1)!=null && edges.get(i)!=null) 
				{
					//before creating edge, check sequences are valid or not
					if((edges.get(i).z>=edges.get(i+1).z) && (edges.get(i)!=edges.get(i+1)))
					{
						//edge is already created it will ignore(usefull for duplicate segments)
						if(adjacency_list.get(edges.get(i)).contains(edges.get(i+1))) 
						{
							continue;
						}
						else 
						{
								//for bidirectiobal graph
								//store connected point to current point's adjacency list
								adjacency_list.get(edges.get(i)).add(edges.get(i+1));
								//store cuurent point to connected point's adjacency list
								adjacency_list.get(edges.get(i+1)).add(edges.get(i));
						}
							
					}else {
						//unexpected input encounters in segment
						throw new IllegalArgumentException();
					}
					
				}
			}
		//store start sequence and end sequence of all the segments 
		for(int i=1;i<edges.size()-1;i++) 
		{
			if(edges.get(i)==null) 
			{
				continue;
			}
			//either its start or end sequence of the segments
			if(edges.get(i-1)==null || edges.get(i+1)==null) 
			{
				if(!endpoints.contains(edges.get(i))) 
				{
					endpoints.add(edges.get(i));
				}
			}
		}
		//return number of successfully added river segments
		return riversegment;
		
	}

	public boolean reachable(Point start, Point destination) {
		//check start point is present in river network
		start=find(start);
		//check destination point is present in river network
		destination=find(destination);
		//throw exception if one of them not present in river segment
		if(start==null || destination==null) {
			throw new IllegalArgumentException();
		}
		//check start and destination points are endpoints of any segments
		if(endpoints.contains(start) && endpoints.contains(destination)) {
			//to keep track of visited vertex
			ArrayList<Point> visited=new ArrayList<Point>();
			//to perfrom DFS
			Stack<Point> stack = new Stack<Point>();
	        stack.push(start);
			while (!stack.isEmpty()) {
				//remove cuurent vertx
		        Point vertex = stack.pop();
		        //check current vertex is destination point
		        if(destination.compareTo(vertex)==0) {
		        	//return true if it determine navigation from start to destination 
		        	return true;
		        }
		        
		        if (!visited.contains(vertex)) {
		        	//point is visited first time in river network
		            visited.add(vertex);
		            //get all the points connected to current point
		            for (Point v : adjacency_list.get(vertex)) {
		            	//not travese point which is visited once 
		            	if(!visited.contains(v)) {
		            			//push all the points connected to cuurent point
		           				stack.push(v);
		            		}
		            	}
		            }  
		      
		    }
				
		}
		else {
			//return false if the start and the destination point is middle point of any segments 
			return false;
		}
		//return false if it can't determine navigation from start to destination 
		return false;
	}
	
	List<Point> springs( Point origin ){
		//check  point is present in river network
		origin=find(origin);
		// store origin points from where water is originated
		List<Point> springsV=new ArrayList<Point>();
		//origin point no found in river network
		if(origin==null) {
			return springsV;
		}
		//poit is end point of any segments
		if(endpoints.contains(origin)){
			//to perform DFS
			Stack<Point> stack = new Stack<Point>();
			
			stack.push(origin);
			int flag=0;
			while(!stack.empty()) {
				flag=0;
				//get current node we visiting
				Point vertex=stack.pop();
				//get all the points connected to current point
				for(Point v: adjacency_list.get(vertex)) {
					//always traverse upstream as water from river flows upstream to downstream
					if(vertex.z<v.z) {
						flag=1;
						stack.push(v);
					}
									
				}
				//if point has high z value mean no other sequence having high z value if connected to it
				if(flag==0) {
					//store as origin point
					springsV.add(vertex);	
				}
				}
				//origin point is endpoint of a river segment
				if(springsV.contains(origin)){
					throw new IllegalArgumentException();
				}else {
					//list of origins from wheere water is originated thal flows to given point
					return springsV;
				}
				
			}	
		else {
			return springsV;
		}
		}
	
	private  Point find(Point vertex) {
		//use to  get all the vertices(Points) stored as key to traverse whole graph 
		ArrayList<Point> keys=new ArrayList<Point>();
		for(Map.Entry<Point,List<Point>> key:adjacency_list.entrySet()) 
		{
			//store all the key stored in Map
			keys.add(key.getKey());
		}
		//compare given point with all the points stored in Map as key
		for(int i=0;i<keys.size();i++) {
			if(vertex.compareTo(keys.get(i))==0) 
			{
					//return object if coordinated of vertex is sotred in Map 
					return keys.get(i);
			}
		}
		//return null coordinated of vertx is not resent in Map
		return null;
	}
		
}
