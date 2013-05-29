

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * Servlet implementation class FinalProject
 */
@WebServlet("/FinalProject")
public class FinalProject extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final long MAXREQUEST = 300;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FinalProject() {
        super();
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String server = randNumService.getServer();
		long start = System.currentTimeMillis();
		out.print(redirectRequest(server));
		long end = System.currentTimeMillis();
		long newAvg = (end-start) + randNumService.getStats()/2;
		randNumService.writeStats(newAvg);
		if(newAvg>MAXREQUEST){
				randNumService.newServer();
		}
	}
	
	private String redirectRequest(String targetURL){
		URL url;
		String results = "";
		try {
			url = new URL(targetURL);
			URLConnection connection = url.openConnection();
	        BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                    connection.getInputStream()));
			String inputLine;
			
			while ((inputLine = in.readLine()) != null) 
			results = results + inputLine;
			in.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		}catch (ConnectException e){
			results = "Site Not Found";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        
		return results;
	}

}
