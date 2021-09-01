

import java.io.*;
import java.util.*;

public class main {
		
			private static String getEndingString(Scanner userInput ) {
				String userArgument = null;

				userArgument = userInput.nextLine();
				userArgument = userArgument.trim();

				// Include a "hack" to provide null and empty strings for testing
				if (userArgument.equalsIgnoreCase("empty")) {
					userArgument = "";
				} else if (userArgument.equalsIgnoreCase("null")) {
					userArgument = null;
				}

				return userArgument;
			}
			
			public static void main(String args[]) throws FileNotFoundException, IOException {
				String readCommand = "read";
				String reachableCommand = "reachable";
				String springsCommand="spring";
				String quitCommand="quit";
				System.out.println("Commands available:");
				System.out.println("  " + readCommand + " <File name>" );
				System.out.println("  " + reachableCommand + " <Source> <Destination>");
				System.out.println("  " + springsCommand + " <Point>");
				System.out.println("  " + quitCommand);
				String UserCommand="";
				Scanner userInput=new Scanner(System.in);
				String Command;
				River itemToHandle=new River();
				do {
					UserCommand=userInput.next();
					
					if(UserCommand.equalsIgnoreCase(readCommand)) {
						Command= getEndingString( userInput );
						System.out.println("eneterd");
						try {
							int result=itemToHandle.load(new BufferedReader(new FileReader(Command)));
							System.out.println(result);
						}
						catch(IllegalArgumentException e) {
							System.out.println(e);
						}
					}
					else if(UserCommand.equalsIgnoreCase(reachableCommand))
					{
						Scanner reachableip=new Scanner(System.in);
						String source=null,destination=null;
						System.out.println("enter source:");
						source=reachableip.nextLine();
						System.out.println("enter Destination:");
						destination=reachableip.nextLine();	
						String s2[]=source.split("\\s+");
						String s3[]=destination.split("\\s+");
						int x=Integer.parseInt(s2[0]);
						int y=Integer.parseInt(s2[1]);
						int z=Integer.parseInt(s2[2]);
						System.out.println(x+" "+y+" "+z);
						
						int x1=Integer.parseInt(s3[0]);
						int y1=Integer.parseInt(s3[1]);
						int z1=Integer.parseInt(s3[2]);
						System.out.println(x1+" "+y1+" "+z1);
						Point e=new Point(x,y,z);
						Point e1=new Point(x1,y1,z1);
						System.out.println(e +"  "+e1);
						
						try {
							boolean result=itemToHandle.reachable(e,e1);
							System.out.println(result);
						}catch(IllegalArgumentException ex) {
							System.out.println(ex);
						}
						
					}else if(UserCommand.equalsIgnoreCase(springsCommand)) {
						List<Point> result=itemToHandle.springs(new Point(50,70,20));
						if(result.size()!=0) {
							for(int i=0;i<result.size();i++) {
								System.out.println(result.get(i).x+" "+result.get(i).y+" "+result.get(i).z);
							}
						}else {
							System.out.println(result);
						}
						
					} else if(UserCommand.equalsIgnoreCase(quitCommand)) {
						System.out.println("exit from the Systsem");
					}
					else {
						System.out.println("Bad Command");
					}
				
				
				}while(!UserCommand.equalsIgnoreCase(quitCommand));
			}
			
}
