package astue.service;

import astue.model.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class Schedule {
    @Autowired
    private RecordService recordService;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private ModbusAgentFactory modbusAgentFactory;

//    @Scheduled(cron="0 0,12 * * * ?")
//    @Scheduled(cron="0,30 * * * * ?")
    public void doMeter(){
//        Set<Device> devices=deviceService.getAll().stream().filter(c->c.isActive()==true)
//                .collect(Collectors.toSet());
//        modbusAgent.call(Device.newBuilder().setIp("192.168.56.109").setIed("Tesys")
//                .build()).stream().forEach(System.out::println);
//        Map<String,Double> map=modbusAgent.getMetering(Device.newBuilder().setIp("192.168.56.109").setIed("TESYS").build());
            Arrays.asList(modbusAgentFactory.create(Device.newBuilder().setName("Test1").setIp("192.168.56.109").setIed("F650").build()),
                    modbusAgentFactory.create(Device.newBuilder().setName("Test2").setIp("192.168.56.110").setIed("TESYS").build())).
            parallelStream().forEach(ModbusAgent::getMetering);
//            modbusAgentFactory.create(Device.newBuilder().setName("Test1").setIp("192.168.56.109").setIed("F650").build()).getMetering();
//            modbusAgentFactory.create(Device.newBuilder().setName("Test2").setIp("192.168.56.110").setIed("TESYS").build()).getMetering();
//        System.out.println(map);
//        deviceService.getAll().stream().forEach(x-> recordService.makeRecord(x));
    }
}
