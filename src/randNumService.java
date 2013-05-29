import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;


public class randNumService {
	public final static String SERVERS_FILE = "C:\\Users\\Administrator\\workspace\\Final\\src\\Instances";
	public final static String STATS_FILE = "C:\\Users\\Administrator\\workspace\\Final\\src\\Stats";
	
	
	public static String getServer(){
		ArrayList<String> servers =new ArrayList<String>();
		int winner = 0;
		try{
			  // Open the file that is the first 
			  // command line parameter
			  FileInputStream fstream = new FileInputStream(SERVERS_FILE);
			  // Get the object of DataInputStream
			  DataInputStream in = new DataInputStream(fstream);
			  BufferedReader br = new BufferedReader(new InputStreamReader(in));
			  String strLine;
			  //Read File Line By Line
			  while ((strLine = br.readLine()) != null)   {
			  // add to server array
			  servers.add(strLine);
			  }
			  //Close the input stream
			  in.close();
		    }catch (Exception e){//Catch exception if any
		  System.err.println("Error: " + e.getMessage());
		  }
		if(servers.size()>1){
			Random generator = new Random();
			winner = generator.nextInt(servers.size()-1);
		}
		return servers.get(winner);
	}
	
	
	public static long getStats(){
		long results = 0;
		try{
		// Open the file that is the first 
		  // command line parameter
		  FileInputStream fstream = new FileInputStream(STATS_FILE);
		  // Get the object of DataInputStream
		  DataInputStream in = new DataInputStream(fstream);
		  BufferedReader br = new BufferedReader(new InputStreamReader(in));
		  String strLine;
		  //Read File Line By Line
		  while ((strLine = br.readLine()) != null)   {
		  // add to server array
		  results=Long.parseLong(strLine);
		  }
		  //Close the input stream
		  in.close();
	    }catch (Exception e){//Catch exception if any
	  System.err.println("Error: " + e.getMessage());
	  }
		return results;
	}
	
	public synchronized static void writeStats(long data){
		try{
			  // Create file 
			  FileWriter fstream = new FileWriter(STATS_FILE);
			  BufferedWriter out = new BufferedWriter(fstream);
			  out.write(Long.toString(data));
			  //Close the output stream
			  out.close();
		  }catch (Exception e){//Catch exception if any
		  System.err.println("Error: " + e.getMessage());
		  }
	}
	
	public synchronized static void newServer(){
		// if server was added in the last 5 minutes ignore newServer request
		File f = new File(SERVERS_FILE);
        long datetime = f.lastModified();
        long now = System.currentTimeMillis();
        if(now-datetime > 60000){
        	try {
				ScaleUpDown.scaleUp();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
	}
	
	public synchronized void removeServer(){
	// if server was added in the last 5 minutes ignore newServer request
			File f = new File(SERVERS_FILE);
	        long datetime = f.lastModified();
	        long now = System.currentTimeMillis();
	        if(now-datetime > 300000){
	        	ScaleUpDown.scaleDown();
	        }
	}

}
