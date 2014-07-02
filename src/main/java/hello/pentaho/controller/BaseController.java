package hello.pentaho.controller;

import hello.pentaho.service.ReportGenerateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class BaseController {
	
	@Autowired
	private ReportGenerateService reportGenerateService;

	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public String welcome(ModelMap model) {

		model.addAttribute("message", "Maven Web Project + Spring 3 MVC - welcome()");

		//Spring uses InternalResourceViewResolver and return back index.jsp
		return "index";

	}

	@RequestMapping(value = "/welcome/{name}", method = RequestMethod.GET)
	public String welcomeName(@PathVariable String name, ModelMap model) {

		model.addAttribute("message", "Maven Web Project + Spring 3 MVC - " + name);
		return "index";

	}
	
	@RequestMapping(value = "/reporting/pdf", method = RequestMethod.GET)
	public String pdf(ModelMap model) {
		
		model.addAttribute("a", 1);
		
		return "pdf";
		
	}
	
	@RequestMapping(value = "/reporting/generatePdf", method = RequestMethod.GET)
	public String generatePdf(ModelMap model) {
		
		model.addAttribute("a", 1);
		
		//reportGenerateService.generateReport(request, response, paramMap);
		
		
		return "pdf";
		
	}

}
