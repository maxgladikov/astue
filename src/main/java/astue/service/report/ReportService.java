package astue.service.report;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import astue.service.interfaces.RecordService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportService {
	private final  RecordService service;
	
	public void createRecordList(HttpServletResponse response,LocalDateTime from,LocalDateTime to) throws IOException {
		String csvFileName = "report.csv";
		FileWriter body=new FileWriter(csvFileName);
		response.setContentType("text/csv");
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"",csvFileName);
		response.setHeader(headerKey, headerValue);
		
		
		ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(),CsvPreference.STANDARD_PREFERENCE);
		String[] header = { "name",  "ip", "active","reactive","ied","power","created"};
		csvWriter.writeHeader(header);
		service.getAllBetween(from,to).stream()
			.map(x -> new RecordDto(x)).forEach(x->{
								try {
									csvWriter.write(x,header);
								} catch (IOException e) {
									throw new RuntimeException(e);
								}
							});
		csvWriter.close();
	}

}
