import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.DescribeInstanceAttributeRequest;
import com.amazonaws.services.ec2.model.DescribeInstanceAttributeResult;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.RunInstancesRequest;
import com.amazonaws.services.ec2.model.RunInstancesResult;


public class ScaleUpDown {
	final static String AMI = "ami-a7fd70ce";
	
	public static void scaleUp() throws IOException, InterruptedException{
		AmazonEC2Client ec2 =AWSClients.getEC2Client();
		RunInstancesRequest request = new RunInstancesRequest();
		request.withInstanceType("t1.micro")
	    .withImageId(AMI)
	    .withMinCount(1)
	    .withMaxCount(1)
	    .withSecurityGroupIds("Ruby on Rails Deployment provided by JumpBox-jb v1.7.3 app v3.2.6[AutogenByAWSMP]")
	    .withKeyName("Harvard");
		RunInstancesResult results = ec2.runInstances(request);
		List<Instance> newServer = results.getReservation().getInstances();
		for(Instance i:newServer){
			String status = i.getState().getName();
			for(int timer=0;status.equals("pending");timer++){
				// if server not running in 5 minutes exit / otherwise add server to Instances
				if(timer==10){
					break;
				}
				// wait for 30 seconds
				Thread.sleep(30000);
				DescribeInstancesRequest describeInstancesRequest = new DescribeInstancesRequest();
				ArrayList<String> instanceIds = new ArrayList<String>();
				instanceIds.add(i.getInstanceId());
				describeInstancesRequest.setInstanceIds(instanceIds);
				status = ec2.describeInstances(describeInstancesRequest).getReservations().get(0).getInstances().get(0).getState().getName();
				
			}
		   if(status.equals("running")){
			 DescribeInstancesRequest describeInstancesRequest = new DescribeInstancesRequest();
			 ArrayList<String> instanceIds = new ArrayList<String>();
			 instanceIds.add(i.getInstanceId());
			 describeInstancesRequest.setInstanceIds(instanceIds);
			 ScaleUpDown.addInstance(ec2.describeInstances(describeInstancesRequest).getReservations().get(0).getInstances().get(0));
		   }
		}
	}
	
	private synchronized static void addInstance(Instance i){
		
		try{
    		String data = "\nhttp://" + i.getPublicDnsName();
    		//true = append file
    		FileWriter fileWritter = new FileWriter(randNumService.SERVERS_FILE,true);
    	        BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
    	        bufferWritter.write(data);
    	        bufferWritter.close();
 
	        System.out.println("Done");
 
    	}catch(IOException e){
    		e.printStackTrace();
    	}
    
		
	}
	
	public static void scaleDown(){
		//TODO Make scaleDown
	}

	

}
