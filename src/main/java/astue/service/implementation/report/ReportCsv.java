package astue.service.implementation.report;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.LocalDateTime;

import org.springframework.core.io.InputStreamResource;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import astue.service.RecordService;
import astue.service.ReportService;
import astue.service.ReportServiceType;
import astue.service.implementation.report.dto.RecordCsvDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ReportCsv implements ReportService{

	private final  RecordService service;
	@Override
	public InputStreamResource create(LocalDateTime from, LocalDateTime to) {
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				ICsvBeanWriter csvWriter = new CsvBeanWriter(new OutputStreamWriter(out),CsvPreference.STANDARD_PREFERENCE);
				String[] header = { "name",  "ip", "active","reactive","ied","power","created"};
				try {
					csvWriter.writeHeader(header);
				} catch (IOException e) {
					e.printStackTrace();
				}
				service.getAllBetween(from,to).stream()
					.map(RecordCsvDto::new).forEach(x->{
										try {
											csvWriter.write(x,header);
										} catch (IOException e) {
											throw new RuntimeException(e);
										}
									});
				try {
					csvWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return new InputStreamResource(new ByteArrayInputStream(out.toByteArray()));
			}
	@Override
	public ReportServiceType getType() {
		return ReportServiceType.RECORDS_CSV;
	}

}
