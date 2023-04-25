package astue.service;

import java.time.LocalDateTime;
import java.util.List;

import astue.model.PowerRecord;


public interface RecordService  {

        public void addOne(PowerRecord record);
        public List<PowerRecord> getAllBetween(LocalDateTime from,LocalDateTime to);
        
}
