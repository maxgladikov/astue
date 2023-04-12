package astue.service;

import java.time.LocalDateTime;
import java.util.List;

import astue.model.Record;


public interface RecordService  {

        public void addOne(Record record);
        public List<Record> getAllBetween(LocalDateTime from,LocalDateTime to);
        
}
