package astue.service;

import lombok.RequiredArgsConstructor;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import astue.model.Device;

@Service
@RequiredArgsConstructor
public class Schedule {
	private final RecordService recordService;
	private final DeviceService deviceService;
	@Autowired
	private Function<Device,FieldDataService> fieldDataServiceFactory;
	

//    @Scheduled(cron="0 0,12 * * * ?")
//    @Scheduled(cron="0,30 * * * * ?")
	public void doMeter() {
		deviceService.getAll().stream().filter(c -> c.isConsumer() == true)
													.forEach(x -> recordService.addOne(fieldDataServiceFactory.apply(x).get()));

//        parallelStream().forEach(ModbusAgent::getMetering);
	}
}
