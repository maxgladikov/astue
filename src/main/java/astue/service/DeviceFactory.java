package astue.service;

import astue.model.Device;
import astue.util.ProcessorCSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DeviceFactory {
    @Autowired
    private Pool pool;

    public Set<Device> createDevices(String path){
        pool.refresh();
        List<List<String>> raw= ProcessorCSV.readUTF(path);
        Set<Device> result= raw.stream()
                        .skip(1)
                        .map(x->Device.newBuilder()
                        .setName(x.get(2))
                        .setDivision(pool.getDivisions().get(Optional.of(x.get(4)).orElse("AUX")))
                        .setSwitchgear(pool.getSwitchgears().get(x.get(6)))
                        .setLine(x.get(7))
                        .setDrawerColumn(Integer.parseInt(x.get(8)))
                        .setDrawerRow(x.get(9).charAt(0))
                        .setIed(x.get(10))
                        .setVoltage(x.get(11).charAt(0))
                        .setPower(Double.parseDouble(Optional.of(x.get(12)).orElse("0")))
                        .setIp(Optional.of(x.get(13)).orElse("192.168.0.1"))
                        .setIncomer(x.get(14))
                        .setConsumer(x.get(15))
                        .setDescription(Optional.of(x.get(16)).orElse("-"))
                        .build())
                .collect(Collectors.toSet());
//        result.stream().forEach(x->pool.getDivisions().get(x.getDivision()).addDevice(x));
        result.stream().forEach(System.out::println);
        return result;
    }
}
