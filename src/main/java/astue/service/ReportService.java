package astue.service;

import java.time.LocalDateTime;

import org.springframework.core.io.InputStreamResource;

import astue.constants.ReportServiceType;

public interface ReportService {
	public InputStreamResource create(LocalDateTime from, LocalDateTime to);
	public ReportServiceType getType();
}
