package astue.service.implementation.report.dto;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import astue.model.Device;
import astue.model.PowerRecord;
import lombok.Getter;

@Getter
public class DeviceConsumptionDto{
	
	private final String name;
	private  Double active;
	private  Double reactive;
	private final Double total;
	
	public DeviceConsumptionDto(Device device) {
		name=device.getName();
		Optional.ofNullable(device.getRecords()).ifPresentOrElse(
				calc
				, 
				()-> {active=0.0;reactive=0.0;}
				);
			total=Math.sqrt(Math.pow(active, 2)+Math.pow(reactive, 2));
	}
	
	Consumer<List<PowerRecord>> calc= records -> {
		active=	records.stream().max( Comparator.comparing(PowerRecord::getActivePowerConsumption) ).orElse(new PowerRecord(null,0.1,0.1)).getActivePowerConsumption()-
				records.stream().min( Comparator.comparing(PowerRecord::getActivePowerConsumption) ).orElse(new PowerRecord(null,0.1,0.1)).getActivePowerConsumption();
		reactive=	records.stream().max( Comparator.comparing(PowerRecord::getReactivePowerConsumption) ).orElse(new PowerRecord(null,0.1,0.1)).getReactivePowerConsumption()-
				records.stream().min( Comparator.comparing(PowerRecord::getReactivePowerConsumption) ).orElse(new PowerRecord(null,0.1,0.1)).getReactivePowerConsumption();
	};
	
}
