package astue.service.report;

import java.time.LocalDateTime;

import astue.model.PowerRecord;
import astue.util.Ied;
import lombok.Data;
@Data
public class RecordDto {
	private final String name;
	private final double active;
	private final double reactive;
	private final Ied ied;
	private final double power;
	private final String ip;
	private final LocalDateTime created;
	public RecordDto(PowerRecord record) {
		name=record.getDevice().getName();
		active=record.getActivePowerConsumption();
		reactive=record.getReactivePowerConsumption();
		ied=record.getDevice().getIed();
		power=record.getDevice().getPower();
		ip=record.getDevice().getHostAddress();
		created=record.getCreated();
	}

}
