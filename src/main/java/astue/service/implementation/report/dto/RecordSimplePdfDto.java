package astue.service.implementation.report.dto;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import astue.model.PowerRecord;
import lombok.Data;
@Data
public class RecordSimplePdfDto {
	String pattern = "dd-MM-yy HH:mm";
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern); 
	private final String active;
	private final String created;
	public RecordSimplePdfDto(PowerRecord record) {
		active=String.valueOf(record.getActivePowerConsumption());
		created=record.getCreated().format(formatter);
	}
	public List<String> getList(){
		return Arrays.asList(active,created);
		
	}
}
