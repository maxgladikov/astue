package astue.service.implementation;

import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.function.Function;

import org.apache.plc4x.java.api.exceptions.PlcConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import astue.model.Device;
import astue.model.PowerRecord;
import astue.service.DeviceService;
import astue.service.FieldDataService;
import astue.service.RecordService;

@Service
@EnableScheduling
@RequiredArgsConstructor
public class Task {
	private final RecordService recordService;
	private final DeviceService deviceService;
	private final Function<Device,FieldDataService> fieldDataServiceFactory;
	Logger log = LoggerFactory.getLogger(this.getClass());

    @Scheduled(cron="${interval-poll}")
	public void doMeter() {
    	log.info("****************  Task read comsumption started  *******************");
    	
		List<Device> devices=deviceService.getAll().stream().filter(Device::isConsumer).sorted((a,b)->a.getName().compareTo(b.getName())).toList();
	    
		devices.stream().forEach(x -> {
				try{
			recordService.addOne(fieldDataServiceFactory.apply(x).proceedConsumptionReading());
					} catch (PlcConnectionException e) {
						log.error(e.getMessage());
					} 
		});
	    log.info("************** Task read comsumption completed **************");
	}
    
    @Scheduled(cron="${interval-sync}")
	public void doSynchronizeTesysClock() {
    	log.info("****************  Task synchronizing started  *******************");
		List<Device> devices=deviceService.getAll().stream()
															.filter(c -> c.isConsumer() == true)
															.filter(c -> c.getIed().name().equals("TESYS"))
															.toList();
		devices.stream().forEach(x -> {
			try {
				fieldDataServiceFactory.apply(x).proceedTimeSynch();
			} catch (PlcConnectionException e) {
				log.error(e.getMessage());
			}
		});
		log.info("************** Task synchronizing completed **************");
	}
}

//
//┌───────────── second (0-59)
//│ ┌───────────── minute (0 - 59)
//│ │ ┌───────────── hour (0 - 23)
//│ │ │ ┌───────────── day of the month (1 - 31)
//│ │ │ │ ┌───────────── month (1 - 12) (or JAN-DEC)
//│ │ │ │ │ ┌───────────── day of the week (0 - 7)
//│ │ │ │ │ │          (or MON-SUN -- 0 or 7 is Sunday)
//│ │ │ │ │ │
//* * * * * *
