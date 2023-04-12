package astue.service;

import org.apache.plc4x.java.api.exceptions.PlcConnectionException;

import astue.exception.fieldbus.DeviceTypeNotSupported;
import astue.model.Device;
import astue.model.Record;

public interface FieldDataService {
	public Record get() throws PlcConnectionException;
	public void execute() throws PlcConnectionException;
}
