package hello.pentaho.login;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;

public class BasicAuthenticationExample {
	public static void main(String[] args) throws Exception {
        HttpClient client = new HttpClient();

        // pass our credentials to HttpClient, they will only be used for
        // authenticating to servers with realm "realm" on the host
        // "www.verisign.com", to authenticate against
        // an arbitrary realm or host change the appropriate argument to null.
        client.getState().setCredentials(
            new AuthScope("www.verisign.com", 443, "realm"),
            new UsernamePasswordCredentials("username", "password")
        );

        // create a GET method that reads a file over HTTPS, we're assuming
        // that this file requires basic authentication using the realm above.
        GetMethod get = new GetMethod("https://www.verisign.com/products/index.html");

        // Tell the GET method to automatically handle authentication. The
        // method will use any appropriate credentials to handle basic
        // authentication requests.  Setting this value to false will cause
        // any request for authentication to return with a status of 401.
        // It will then be up to the client to handle the authentication.
        get.setDoAuthentication( true );

        try {
            // execute the GET
            int status = client.executeMethod( get );

            // print the status and response
            System.out.println(status + "\n" + get.getResponseBodyAsString());

        } finally {
            // release any connection resources used by the method
            get.releaseConnection();
        }
    }
}
