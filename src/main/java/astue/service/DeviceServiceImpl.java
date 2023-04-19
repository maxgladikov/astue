package astue.service;

import astue.exception.ResourceNotFoundException;
import astue.model.Device;
import astue.model.Division;
import astue.model.Switchgear;
import astue.repository.DeviceRepository;
import astue.service.interfaces.DeviceService;
import astue.service.interfaces.DivisionService;
import astue.service.interfaces.SwitchgearService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService{

    private final DeviceRepository repository;
    private final DivisionService divisionService;
    private final SwitchgearService switchgearService;


    @Override
    public List<Device> getAll() {
        return repository.findAll();
    }

    @Override
    public Device getById(Long id) {
        return repository.findById(id).orElseThrow(()->new ResourceNotFoundException("Entity with id="+id+"not found"));
    }

    @Override
    public Device getByName(String name) {
         return repository.findByName(name).orElseThrow(()->new ResourceNotFoundException("Entity with name="+name+"not found"));
    }

    @Override
    public Device add(Device newDevice) {
    	Switchgear switchgear=switchgearService.getByName(newDevice.getSwitchgear().getName());
		Division division=divisionService.getByName(newDevice.getDivision().getName());
		newDevice.setSwitchgear(switchgear);
		newDevice.setDivision(division);
        return repository.save(newDevice);
    }

    public void addAll(Set<Device> devices) {
        devices.stream().limit(1).peek(x-> System.out.println(x)).peek(x->repository.save(x));
    }

    public void delete(Long id){
        repository.deleteById(id);
    }

	@Override
	public void update(Device newDevice, Long id) {
		Device device=this.getById(id);
		device.setName(newDevice.getName());
		device.setLine(newDevice.getLine());
		device.setDrawerColumn(newDevice.getDrawerColumn());
		device.setDrawerRow(newDevice.getDrawerRow());
		device.setIed(newDevice.getIed());
		device.setPower(newDevice.getPower());
		device.setVoltage(newDevice.getVoltage());
		device.setHostAddress(newDevice.getHostAddress());
		device.setIncomer(newDevice.isIncomer());
		device.setConsumer(newDevice.isConsumer());
		device.setDescription(newDevice.getDescription());
		device.setSwitchgear(switchgearService.getByName(newDevice.getSwitchgear().getName()));
		device.setDivision(divisionService.getByName(newDevice.getDivision().getName()));
		this.add(device);
		
	}


}
