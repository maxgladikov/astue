package astue.service.interfaces;

import astue.model.Device;


public interface DeviceService extends BaseService<Device>{
	public void delete(Long id);
	public void update(Device device,Long id);
}
