package hello.pentaho.rest;

import hello.pentaho.service.ReportGenerateService;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Path("/reporting")
public class PentahoResource {
	
	@Autowired
	private ReportGenerateService reportGenerateService;
	
	private @Context HttpServletRequest request; 
	private @Context HttpServletResponse response;

	@GET
	@Path("/gen/{name}")
	@Produces("application/pdf")
	public void generatePdf(@PathParam("name") String name, @Context UriInfo info) {
		
		System.out.println("rest reporting..." + name);
		
//		String filePath = "C:\\dev\\dev_project\\git\\hello-pentaho\\src\\main\\resources\\hello.pdf";
		
//		return Response.ok(new File(filePath)).type("application/pdf").build(); 
		
		Map<Object, Object> paramMap = new HashMap<Object, Object>();
		paramMap.put("reportName", name);
		
		String from = info.getQueryParameters().getFirst("from");
		String to = info.getQueryParameters().getFirst("to");
		
		System.out.println("from: " + from);
		System.out.println("to: " + to);
		
		reportGenerateService.generateReport(request, response, paramMap);

	}
}
