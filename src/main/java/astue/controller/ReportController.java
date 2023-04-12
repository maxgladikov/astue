package astue.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import astue.service.DeviceServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(path="/api/v1/report",produces="application/json")
public class ReportController {
	
	private final  DeviceServiceImpl service;
	
	
	// Electric report
//	public void downloadCSV(HttpServletResponse response) throws IOException {
	@RequestMapping(value ="/report/{startDate}/{endDate}" )
	public void downloadCSV( 	@PathVariable("startDate") @DateTimeFormat(pattern = "yyyy/MM/dd hh:mm") LocalDateTime startDate,
								@PathVariable("endDate") @DateTimeFormat(pattern = "yyyy/MM/dd hh:mm") LocalDateTime endDate,
								HttpServletResponse response) throws IOException {
		
		System.out.println("*********** startDate= "+startDate);
		System.out.println("*********** endDate= "+endDate);
		String csvFileName = "report.csv";
		response.setContentType("text/csv");
		// creates mock data
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"",
				csvFileName);
		response.setHeader(headerKey, headerValue);
//		List<Device> data=service.getAll();

		// uses the Super CSV API to generate CSV data from the model data
		
		ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(),
				CsvPreference.STANDARD_PREFERENCE);
		String[] header = { "name",  "hostAddress", "line",
				"incomer",  "consumer","ied","power","voltage" ,"description"};
		csvWriter.writeHeader(header);
		service.getAll().stream().forEach(x-> {
			try {
				csvWriter.write(x,header);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		});
		csvWriter.close();
	}
}
