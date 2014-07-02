package hello.pentaho.controller;

import hello.pentaho.service.ReportGenerateService;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PentahoController {

	@Autowired
	private ReportGenerateService reportGenerateService;

	@RequestMapping(value = "/pentahoreporting/{reportName}", method = RequestMethod.GET)
	public void generateReport(@PathVariable String reportName, HttpServletRequest request, HttpServletResponse response, Map<Object, Object> paramMap) {
		
		paramMap.put("reportName", reportName);
		
		reportGenerateService.generateReport(request, response, paramMap);
	}

}
