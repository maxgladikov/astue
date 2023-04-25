package astue.service.implementation.report.dto;

import java.time.format.DateTimeFormatter;

import astue.model.PowerRecord;
import astue.util.Ied;
import lombok.Getter;
@Getter
public class RecordCsvDto {
	String pattern = "dd-MM-yy HH:mm";
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern); 
	private final String name;
	private final String ip;
	private final double active;
	private final double reactive;
	private final Ied ied;
	private final double power;
	private final String created;
	public RecordCsvDto(PowerRecord record) {
		name=record.getDevice().getName();
		ip=record.getDevice().getHostAddress();
		active=record.getActivePowerConsumption();
		reactive=record.getReactivePowerConsumption();
		ied=record.getDevice().getIed();
		power=record.getDevice().getPower();
		created=record.getCreated().format(formatter);
	}
}
