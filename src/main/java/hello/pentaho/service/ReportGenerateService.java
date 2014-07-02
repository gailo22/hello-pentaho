package hello.pentaho.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ReportGenerateService {

	public void generateReport(HttpServletRequest request, HttpServletResponse response, Map<Object, Object> paramMap);
}
