package astue.service.report;

import java.util.Comparator;
import java.util.List;

import astue.model.Device;
import astue.model.PowerRecord;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeviceReportDto {
	private final Device device;
	private final List<PowerRecord> records;
	private StringBuilder sb=new StringBuilder();
	
	@Override
	public String toString() {
		sb.append(device.getName());
		records.stream().sorted(Comparator.comparing(PowerRecord::getCreated)).forEach(x->{sb.append(x.getActivePowerConsumption());sb.append(x.getCreated());});
		return sb.toString();
	}

}
