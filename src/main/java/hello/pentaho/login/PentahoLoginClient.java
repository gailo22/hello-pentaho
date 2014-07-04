package hello.pentaho.login;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthPolicy;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;



public class PentahoLoginClient {
	
	public static void main(String[] args) throws Exception {
		URL url = new URL("http://localhost:8080/pentaho/j_spring_security_check");
		HttpClient httpClient = new HttpClient();
		List authPrefs = new ArrayList(1);
		authPrefs.add(AuthPolicy.DIGEST);
		httpClient.getParams().setParameter(AuthPolicy.AUTH_SCHEME_PRIORITY, authPrefs);
		final String username = "admin";
		final String password = "password";
		Credentials credentials = new UsernamePasswordCredentials(username, password);
		AuthScope authScope = new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT, AuthScope.ANY_REALM);
		httpClient.getState().setCredentials(authScope, credentials);
		
		PostMethod postMethod = new PostMethod(url.toExternalForm());
		postMethod.setDoAuthentication(true); // send creds when challenged (when we receive response code 401)
		
		try {
		  int result = httpClient.executeMethod(postMethod);
		  // do something
		  
		  System.out.println("result: " + result);
		  
		  int status = get();
		  
		  System.out.println("status: " + status);
		  
		} 
		
		catch (Exception e) {
		  e.printStackTrace();
		} 
		
		finally {
		  postMethod.releaseConnection();
		}
	}
	
	private static int get() {
		
		HttpClient httpClient = new HttpClient();
		List authPrefs = new ArrayList(2);
		authPrefs.add(AuthPolicy.DIGEST);
		authPrefs.add(AuthPolicy.BASIC);
		httpClient.getParams().setParameter(AuthPolicy.AUTH_SCHEME_PRIORITY, authPrefs);
		
		final String username = "admin";
		final String password = "password";
		Credentials credentials = new UsernamePasswordCredentials(username, password);
		AuthScope authScope = new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT, AuthScope.ANY_REALM);
		httpClient.getState().setCredentials(authScope, credentials);
		
		String url = "http://localhost:8080/pentaho/api/repos/:public:Reporting:13_Adding_%20Subreports.prpt/report?output-target=pageable%2Fpdf&accepted-page=-1&showParameters=true";
		GetMethod get = new GetMethod(url);
		
		try {
			int status = httpClient.executeMethod( get );
			
			return status;
		} 
		
		catch (Exception e) {
			e.printStackTrace();
		} 
		
		return -1;
	}
	

}
