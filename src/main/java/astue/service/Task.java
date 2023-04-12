package astue.service;

import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.plc4x.java.api.exceptions.PlcConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import astue.model.Device;

@Service
@RequiredArgsConstructor
public class Task {
	private final RecordService recordService;
	private final DeviceService deviceService;
	private final Function<Device,FieldDataService> fieldDataServiceFactory;
	Logger log = LoggerFactory.getLogger(this.getClass());

    @Scheduled(cron="0 0,12 * * * ?")
//    @Scheduled(cron="0,30 * * * * ?")
//    @Scheduled(cron="30,0 * * * * ?")
//    @Scheduled(cron="0,5 * * * * ?")
	public void doMeter() {
    	System.out.println("****************  Task read comsumtion started  *******************");
		List<Device> devices=deviceService.getAll().stream().filter(c -> c.isConsumer() == true)
//				.peek(System.out::println)
				.collect(Collectors.toList());
	    devices.stream().forEach(x -> {
				try {
					recordService.addOne(fieldDataServiceFactory.apply(x).get());
				} catch (PlcConnectionException e) {
					log.error(e.getMessage());
				}
		});
		System.out.println("************** Task read comsumtion **************");
	}
    @Scheduled(cron="0 0,24 * * * ?")
//	@Scheduled(cron="30,0 * * * * ?")
//	@Scheduled(cron="0,10 * * * * ?")
	public void synchronizeTesysClock() {
		System.out.println("****************  Task synchronizing started  *******************");
		List<Device> devices=deviceService.getAll().stream()
															.filter(c -> c.isConsumer() == true)
															.filter(c -> c.getIed().name().equals("TESYS"))
//				.peek(System.out::println)
				.collect(Collectors.toList());
		devices.stream().forEach(x -> {
			try {
				fieldDataServiceFactory.apply(x).execute();
			} catch (PlcConnectionException e) {
				log.error(e.getMessage());
			}
		});
		System.out.println("************** Task synchronizing **************");
	}
}
