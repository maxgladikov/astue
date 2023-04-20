package astue.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.core.io.InputStreamResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpHeaders;

import astue.service.report.ReportService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(path="/api/v1/report")
public class ReportController {
	
	private final  ReportService service;
	
//	@RequestMapping(value ="/records/{startDate}/{endDate}" )
//	public void getRecords( 	@PathVariable("startDate") @DateTimeFormat(pattern="dd.MM.yyyy_HH:mm") LocalDateTime startDate,
//								@PathVariable("endDate") @DateTimeFormat(pattern="dd.MM.yyyy_HH:mm") LocalDateTime endDate,
//								HttpServletResponse response) throws IOException {
//		service.createRecordList(response, startDate, endDate);
//	}
	@GetMapping(value ="/records/{startDate}/{endDate}/{reportType}",produces = MediaType.APPLICATION_PDF_VALUE )
	public ResponseEntity<InputStreamResource> getRecords(
								@PathVariable("startDate") @DateTimeFormat(pattern="dd.MM.yyyy_HH:mm") LocalDateTime startDate,
								@PathVariable("endDate") @DateTimeFormat(pattern="dd.MM.yyyy_HH:mm") LocalDateTime endDate,
								@PathVariable("reportType") String reportType) throws IOException {
		if(reportType.equals("Records.csv")) {
			ByteArrayInputStream bis = service.createRecordList(startDate, endDate);
			var headers = new HttpHeaders();
		     headers.add("Content-Disposition", "inline; filename=report.csv");
			
			return ResponseEntity
	                .ok()
	                .headers(headers)
	                .contentType(MediaType.APPLICATION_OCTET_STREAM)
	                .body(new InputStreamResource(bis));
		}else {
		
		ByteArrayInputStream bis = service.createDeviceList(startDate, endDate);
		
		 var headers = new HttpHeaders();
	     headers.add("Content-Disposition", "inline; filename=report.pdf");
	     
	     return ResponseEntity
	                .ok()
	                .headers(headers)
	                .contentType(MediaType.APPLICATION_PDF)
	                .body(new InputStreamResource(bis));
		}
		
	}
}
