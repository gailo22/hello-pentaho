package hello.pentaho.login;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class RestLoginClient {
	
	String baseUrl = "http://localhost:8080/pentaho";

	public String authenticateGetCookie(String user, String password) {
		
		HttpMessageConverter<MultiValueMap<String, ?>> formHttpMessageConverter = new FormHttpMessageConverter();

		HttpMessageConverter<String> stringHttpMessageConverternew = new StringHttpMessageConverter();

		List<HttpMessageConverter<?>> messageConverters = new LinkedList<HttpMessageConverter<?>>();

		messageConverters.add(formHttpMessageConverter);
		messageConverters.add(stringHttpMessageConverternew);
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("j_username", user);
		map.add("j_password", password);

		String authURL = baseUrl + "/j_spring_security_check";
		RestTemplate restTemplate = new RestTemplate();

		restTemplate.setMessageConverters(messageConverters);

		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(map, requestHeaders);

		ResponseEntity<String> result = restTemplate.exchange(authURL, org.springframework.http.HttpMethod.POST, entity, String.class);
		
		System.out.println("result: " + result);
		
		HttpHeaders respHeaders = result.getHeaders();
		System.out.println(respHeaders.toString());

		System.out.println(result.getStatusCode());

		String cookies = respHeaders.getFirst("Set-Cookie");
		
		return cookies;
	}
	
	public void getPdf(String cookie) {
		
		String pdfUrl = baseUrl + "/api/repos/:public:Reporting:13_Adding_%20Subreports.prpt/report?ts=1404360738225&SelectCountry=20&output-target=pageable%2Fpdf&accepted-page=-1&showParameters=true&renderMode=REPORT&htmlProportionalWidth=false";
		
		HttpClient client = new HttpClient();
		
		HttpMethod get = new GetMethod(pdfUrl);
        get.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
        get.setRequestHeader("Cookie", cookie);
        
        try {
			int i = client.executeMethod(get);
			System.out.println("i: " + i);
			
		} 
        
        catch (HttpException e) {
			e.printStackTrace();
		} 
        
        catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}
	
	
	public static void main(String[] args) {
		
		RestLoginClient loginClient = new RestLoginClient();
		
		String cookie = loginClient.authenticateGetCookie("admin", "password");
		
		System.out.println("cookie: " + cookie);
		
		loginClient.getPdf(cookie);
		
	}
}
