package astue.service;

import astue.model.Device;
import astue.model.Record;
import astue.model.Substation;
import astue.model.Switchgear;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class Report {
    private Switchgear switchgear;
    private List<ReportRecord> incomers;
    private List<ReportRecord> consumers;
    private List<Record> records;

    public Report(Switchgear switchgear){
        this.switchgear=switchgear;
//        incomers=(switchgear.getDevices().stream().filter(x->x.isIncomer()==true).sorted(Comparator.comparing(Device::getName)).collect(Collectors.toList())).stream().
//        peek(x->new ReportRecord(x)).collect(Collectors.toList());
//        consumers=switchgear.getDevices().stream().filter(x->x.isConsumer()==true).collect(Collectors.toList());
    }

    public List<String> doReport(){
        List<String> result=new ArrayList<>();
        result.add(switchgear.getName());
        result.add("Incomers");
        return result;
    }

}
class ReportRecord{
    private String deviceName;
    private Double activePower;
    private Double reactivePower;
    public ReportRecord(List<Record> records){
        deviceName=records.get(0).getDevice().getName();
        records=records.stream().sorted(Comparator.comparing(Record::getCreated)).collect(Collectors.toList());
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
