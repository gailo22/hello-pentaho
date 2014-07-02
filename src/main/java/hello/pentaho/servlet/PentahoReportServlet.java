package hello.pentaho.servlet;

import java.io.IOException;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pentaho.reporting.engine.classic.core.ClassicEngineBoot;
import org.pentaho.reporting.engine.classic.core.MasterReport;
import org.pentaho.reporting.engine.classic.core.modules.output.pageable.pdf.PdfReportUtil;
import org.pentaho.reporting.libraries.resourceloader.Resource;
import org.pentaho.reporting.libraries.resourceloader.ResourceManager;


public class PentahoReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public PentahoReportServlet() {
        super();
        
        ClassicEngineBoot.getInstance().start();
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	ResourceManager manager = new ResourceManager();
        manager.registerDefaults();
        String reportPath = "file:" + this.getServletContext().getRealPath("/WEB-INF/reporting/templates/01_Hello_World.prpt");
        
        System.out.println("reportPath: " + reportPath);
        
        response.setContentType("application/pdf");
        
        try {
            Resource res = manager.createDirectly(new URL(reportPath), MasterReport.class);
            MasterReport report = (MasterReport) res.getResource();
            //HtmlReportUtil.createStreamHTML(report, response.getOutputStream());
            PdfReportUtil.createPDF(report, response.getOutputStream());
        } 
        
        catch (Exception e) {
             e.printStackTrace();
        }
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

}
