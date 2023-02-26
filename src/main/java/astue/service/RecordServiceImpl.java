package astue.service;

import astue.exception.NoRequestedIed;
import astue.model.Device;
import astue.model.Record;
import astue.repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RecordServiceImpl implements RecordService{
    @Autowired
    private ModbusAgentFactory modbusAgentFactory;
    @Autowired
    RecordRepository repository;

    @Override
    public void makeRecord(Device device) {
        Map<String,Double> map= null;
            map = modbusAgentFactory.create(device).getMetering();
        repository.save(new Record(device,map.get("active"),map.get("reactive")));
    }
}
