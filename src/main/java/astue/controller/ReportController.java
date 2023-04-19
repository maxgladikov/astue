package astue.controller;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import astue.service.report.ReportService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(path="/api/v1/report")
public class ReportController {
	
	private final  ReportService service;
	
	@RequestMapping(value ="/records/{startDate}/{endDate}" )
	public void getRecords( 	@PathVariable("startDate") @DateTimeFormat(pattern="dd.MM.yyyy_HH:mm") LocalDateTime startDate,
								@PathVariable("endDate") @DateTimeFormat(pattern="dd.MM.yyyy_HH:mm") LocalDateTime endDate,
								HttpServletResponse response) throws IOException {
		service.createRecordList(response, startDate, endDate);
	}
}
