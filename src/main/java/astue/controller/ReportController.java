package astue.controller;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.core.io.InputStreamResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import astue.constants.ReportServiceType;
import astue.service.implementation.report.ReportServiceFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(path="/api/v1/report")
public class ReportController {
	
	private final ReportServiceFactory serviceFactory;
	
	@GetMapping(value ="/records/{startDate}/{endDate}/{reportType}",produces = MediaType.APPLICATION_PDF_VALUE )
	public ResponseEntity<InputStreamResource> getReport(
								@PathVariable("startDate") @DateTimeFormat(pattern="dd.MM.yyyy_HH:mm") LocalDateTime startDate,
								@PathVariable("endDate") @DateTimeFormat(pattern="dd.MM.yyyy_HH:mm") LocalDateTime endDate,
								@PathVariable("reportType") String reportType) throws IOException {
		
		if(reportType.equals("Records.csv")) {
			var headers = new HttpHeaders();
		     headers.add("Content-Disposition", "inline; filename=report.csv");
			
			return ResponseEntity
	                .ok()
	                .headers(headers)
	                .contentType(MediaType.APPLICATION_OCTET_STREAM)
	                .body(serviceFactory.getService(ReportServiceType.RECORDS_CSV).create(startDate, endDate));
		}else {
		 var headers = new HttpHeaders();
	     headers.add("Content-Disposition", "inline; filename=report.pdf");
	     
	     return ResponseEntity
	                .ok()
	                .headers(headers)
	                .contentType(MediaType.APPLICATION_PDF)
	                .body(serviceFactory.getService(ReportServiceType.PROCESS_PDF).create(startDate, endDate));
		}
		
	}
}
