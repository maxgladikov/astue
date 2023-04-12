package astue.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import astue.model.Record;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReportServiceImpl implements ReportService{
	
	private final RecordService service;
	
	@Override
	public String generateElectric(LocalDateTime from, LocalDateTime to) {
		List<Record> records=service.getAllBetween(from, to);	
		
		return null;
	}

	@Override
	public String generateProcess(LocalDateTime from, LocalDateTime to) {
		// TODO Auto-generated method stub
		return null;
	}

}
