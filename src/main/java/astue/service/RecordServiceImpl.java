package astue.service;

import astue.model.PowerRecord;
import astue.repository.RecordRepository;
import astue.service.interfaces.RecordService;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class RecordServiceImpl implements RecordService{
    
	private final RecordRepository repository;

    @Override
    public void addOne(PowerRecord record) {
        repository.save(record);
    }

	@Override
	public List<PowerRecord> getAllBetween(LocalDateTime from, LocalDateTime to) {
		return repository.findAllByCreatedBetween(from, to).orElseThrow();
	}
}
