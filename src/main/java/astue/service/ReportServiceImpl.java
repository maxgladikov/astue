package astue.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import astue.model.PowerRecord;
import astue.model.Switchgear;
import astue.service.interfaces.RecordService;
import astue.service.interfaces.ReportService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReportServiceImpl implements ReportService{
	
	private final RecordService service;
	
	private Switchgear switchgear;
    private List<ReportRecord> incomers;
    private List<ReportRecord> consumers;
    private List<PowerRecord> records;
/*
    public Report(Switchgear switchgear){
        this.switchgear=switchgear;
//        incomers=(switchgear.getDevices().stream().filter(x->x.isIncomer()==true).sorted(Comparator.comparing(Device::getName)).collect(Collectors.toList())).stream().
//        peek(x->new ReportRecord(x)).collect(Collectors.toList());
//        consumers=switchgear.getDevices().stream().filter(x->x.isConsumer()==true).collect(Collectors.toList());
    }
*/
    public List<String> doReport(){
        List<String> result=new ArrayList<>();
        result.add(switchgear.getName());
        result.add("Incomers");
        return result;
    }
	
	
	@Override
	public String generateElectric(LocalDateTime from, LocalDateTime to) {
		List<PowerRecord> records=service.getAllBetween(from, to);	
		
		return null;
	}

	@Override
	public String generateProcess(LocalDateTime from, LocalDateTime to) {
		// TODO Auto-generated method stub
		return null;
	}

}

class ReportRecord{
    private String deviceName;
    private Double activePower;
    private Double reactivePower;
    public ReportRecord(List<PowerRecord> records){
        deviceName=records.get(0).getDevice().getName();
        records=records.stream().sorted(Comparator.comparing(PowerRecord::getCreated)).collect(Collectors.toList());
        activePower=records.get(records.size()-1).getActivePowerConsumption()-records.get(0).getActivePowerConsumption();
        reactivePower=records.get(records.size()-1).getReactivePowerConsumption()-records.get(0).getReactivePowerConsumption();
    }

    @Override
    public String toString() {
        return  "deviceName='" + deviceName  +
                ", activePower=" + activePower +
                ", reactivePower=" + reactivePower;
    }
}
