package hello.pentaho.service.impl;

import hello.pentaho.service.ReportGenerateService;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pentaho.reporting.engine.classic.core.ClassicEngineBoot;
import org.pentaho.reporting.engine.classic.core.MasterReport;
import org.pentaho.reporting.engine.classic.core.modules.output.pageable.pdf.PdfReportUtil;
import org.pentaho.reporting.libraries.resourceloader.Resource;
import org.pentaho.reporting.libraries.resourceloader.ResourceException;
import org.pentaho.reporting.libraries.resourceloader.ResourceManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("reportGenerateService")
public class ReportGenerateServiceImpl implements ReportGenerateService {
	
	@Value("${templatePath}")
	private String templatePath;

	public void generateReport(HttpServletRequest request, HttpServletResponse response, Map<Object, Object> paramMap) {
		
		ClassicEngineBoot.getInstance().start();
		
		try {
			// load report definition
			ResourceManager manager = new ResourceManager();
			manager.registerDefaults();
			Resource res = null;
			
			final String reportPath = templatePath + "\\" + paramMap.get("reportName") + ".prpt";
			
			System.out.println("templatePath: " + templatePath);
			System.out.println("reportPath: " + reportPath);
			
			try {
				
				res = manager.createDirectly(new URL(reportPath), MasterReport.class);
			} 
			
			catch (MalformedURLException e1) {
				e1.printStackTrace();
			}
			
			MasterReport report = (MasterReport) res.getResource();
			
			// render the pdf
			response.setContentType("application/pdf");
			try {
				PdfReportUtil.createPDF(report, response.getOutputStream());
			} 
			
			catch (Exception e) {
				e.printStackTrace();
			}
		} 
		
		catch (ResourceException e) {
			e.printStackTrace();
		}

	}

	public String getTemplatePath() {
		return templatePath;
	}

	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}
}
