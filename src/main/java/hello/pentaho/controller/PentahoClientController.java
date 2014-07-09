package hello.pentaho.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.CommonsClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class PentahoClientController {

	
	@RequestMapping("/pentaho/client")
	public String pentahoRest(HttpServletRequest req, HttpServletResponse res) {
		
		String url = "http://localhost:8080/pentaho/api/repos/:public:Reporting:01_Hello_World.prpt/report?output-target=pageable/pdf";

		RestTemplate restTemplate = createRestTemplate("admin", "password");

		HttpHeaders headers = new HttpHeaders();
		List<MediaType> mediaTypes = new ArrayList<MediaType>();
		mediaTypes.add(MediaType.valueOf("application/pdf"));
		headers.setAccept(mediaTypes);

		ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.GET, 
				                                                new HttpEntity<byte[]>(headers), 
				                                                byte[].class);
		
		res.setContentType("application/pdf");
		
		try {
			ServletOutputStream outputStream = res.getOutputStream();
			outputStream.write(response.getBody());
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	private RestTemplate createRestTemplate(String username, String password) {
        return new RestTemplate(this.createSecureTransport(username, password));
    }

    protected ClientHttpRequestFactory createSecureTransport(String username, String password){
        HttpClient client = new HttpClient();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(username,password);
        client.getState().setCredentials(AuthScope.ANY, credentials);
        CommonsClientHttpRequestFactory commons = new CommonsClientHttpRequestFactory(client);

        return commons;
    }
    
}
