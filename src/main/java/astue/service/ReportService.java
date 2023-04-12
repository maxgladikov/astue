package astue.service;

import java.time.LocalDateTime;

public interface ReportService {
	public String generateElectric(LocalDateTime from, LocalDateTime to);
	public String generateProcess(LocalDateTime from, LocalDateTime to);
}
