package astue.service.implementation.report.dto;

public record DeviceReportDto(String name, Double active,Double reactive,Double total) {
	
//	public DeviceReport(Device device) {
//		name=device.getName();
//		this.incomer=device.isIncomer();
//		recordPresentAndMoreThenOne=Optional.ofNullable(device.getRecords()).isPresent();
//		if (recordPresentAndMoreThenOne)
//								recordPresentAndMoreThenOne=device.getRecords().size()>1;
//		if (recordPresentAndMoreThenOne) {
//			active=	device.getRecords().stream().max( Comparator.comparing(PowerRecord::getActivePowerConsumption) ).orElse(new PowerRecord(null,0.1,0.1)).getActivePowerConsumption()-
//				  	device.getRecords().stream().min( Comparator.comparing(PowerRecord::getActivePowerConsumption) ).orElse(new PowerRecord(null,0.1,0.1)).getActivePowerConsumption();
//			reactive=	device.getRecords().stream().max( Comparator.comparing(PowerRecord::getReactivePowerConsumption) ).orElse(new PowerRecord(null,0.1,0.1)).getReactivePowerConsumption()-
//			  		device.getRecords().stream().min( Comparator.comparing(PowerRecord::getReactivePowerConsumption) ).orElse(new PowerRecord(null,0.1,0.1)).getReactivePowerConsumption();
//			
//		}else {
//			active=0;
//			reactive=0;
//		}
//		total=Math.sqrt(Math.pow(active, 2)+Math.pow(reactive, 2));
//		
//	}
	
	
	
	
}
