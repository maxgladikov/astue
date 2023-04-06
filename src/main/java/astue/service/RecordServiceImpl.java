package astue.service;

import astue.model.Record;
import astue.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class RecordServiceImpl implements RecordService{
    
	private final RecordRepository repository;

    @Override
    public void addOne(Record record) {
        repository.save(record);
    }
}
