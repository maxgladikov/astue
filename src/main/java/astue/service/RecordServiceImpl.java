package astue.service;

import astue.model.Record;
import astue.repository.RecordRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class RecordServiceImpl implements RecordService{
    
	private final RecordRepository repository;

    @Override
    public void addOne(Record record) {
        repository.save(record);
    }

	@Override
	public List<Record> getAllBetween(LocalDateTime from, LocalDateTime to) {
		return repository.findAllByCreatedBetween(from, to).orElseThrow();
	}
}
