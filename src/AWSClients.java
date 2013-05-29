

import java.io.IOException;

import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.simpledb.*;

public class AWSClients {
	private static PropertiesCredentials getProperties() throws IOException{
		return new PropertiesCredentials(
	             AWSClients.class.getResourceAsStream("AwsCredentials.properties"));
	}
	public static AmazonS3  getS3Client() throws IOException {
        return new AmazonS3Client(getProperties());
	}   
	
	public static AmazonSimpleDBClient getSimpleDBClient() throws IOException{
		return new AmazonSimpleDBClient(getProperties());
	}
	
	public static AmazonEC2Client getEC2Client() throws IOException{
		return new AmazonEC2Client(getProperties());
	}
}
