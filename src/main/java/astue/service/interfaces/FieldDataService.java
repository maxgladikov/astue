package astue.service.interfaces;

import org.apache.plc4x.java.api.exceptions.PlcConnectionException;

import astue.exception.fieldbus.DeviceTypeNotSupported;
import astue.model.Device;
import astue.model.PowerRecord;

public interface FieldDataService {
	public PowerRecord proceedConsumptionReading() throws PlcConnectionException;
	public void proceedTimeSynch() throws PlcConnectionException;
}
