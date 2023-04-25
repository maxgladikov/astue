package astue.service.implementation;

import org.springframework.stereotype.Service;

import astue.model.Device;
import astue.model.PowerRecord;
import astue.service.DeviceService;
import astue.service.RecordService;
import astue.service.SubstationService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TestReport {
	private final DeviceService deviceService;
	private final RecordService recordService;
	private final SubstationService substationService;
	
	/*
	private final SpringAux aux;
	@Value("#{new Boolean('${custom.populate}')}")
	private boolean populate;
	@Value("${custom.populate}")
	@Value("${server.port}")
	*/
	
	public void doSomeThing(){
		Device device=deviceService.getByName("10-MP-1006B");
		PowerRecord record=new PowerRecord(device,100.0,1000.0);
		System.out.println(record+"******************");
		System.out.println(record.getDevice().getName()+"********************");
		recordService.addOne(record);

		
		///*
		// Create a hierarchy
		
		
		/*
		 
		// Get records
		LocalDateTime from=LocalDateTime.now().minusDays(2);
		LocalDateTime to=LocalDateTime.now();
		List<Record> records=recordService.getAllBetween(from, to);
		Map<Device,List<Record>> recordsTmp=records.stream().collect(Collectors.groupingBy(Record::getDevice));
		Map<String,List<Record>> recordsMap=recordsTmp.keySet().stream().collect(Collectors.toMap(x->x.getName(), x->recordsTmp.get(x)));
		
		*/
		
		
		/*
		// Electric report
		List<Substation> substations=(List<Substation>) substationService.getAll();
		Map<Substation, List<Switchgear>> switchgearsTmp=switchgearService.getAll().stream().collect(Collectors.groupingBy(Switchgear::getSubstation));
		Map<String, List<Switchgear>> switchgearsMap=switchgearsTmp.keySet().stream().collect(Collectors.toMap(x->x.getName(), x->switchgearsTmp.get(x)));
		// Add switchgears to substations
		substations.stream().forEach(ss -> ss.setSwitchgears(switchgearsMap.get(ss.getName())));
		// Add records to device
		substations.stream().forEach(
								ss->ss.getSwitchgears().stream().forEach(
													swgr -> swgr.getDevices().stream().forEach(
																				dev -> dev.setRecords( recordsMap.get(dev.getName())
//																						.stream().sorted(Comparator.comparing(Record::getCreated)).toList() 
																						))));
		ElectricReport report=new ElectricReport(substations);
//		DeviceReport deviceReport=new DeviceReport(substations.get(2).getSwitchgears().get(3).getDevices().get(17));
//		*/
		
		
		/*
		// Process report
		
		System.out.println();
		List<Plant> plants=(List<Plant>) plantService.getAll();
		*/
		
		
		
//		recordsMap.get("10-MP-1008B").stream().sorted(Comparator.comparing(Record::getCreated)).forEach(x->System.out.println(x.getCreated())); 
//		System.out.println("************");
//		recordsMap.get("10-MP-1008B").stream().sorted(Comparator.comparing(Record::getCreated).reversed()).forEach(x->System.out.println(x.getCreated())); 
		//*/

		
	}
	
}
