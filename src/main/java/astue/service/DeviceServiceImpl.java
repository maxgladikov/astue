package astue.service;

import astue.model.Device;
import astue.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class DeviceServiceImpl implements DeviceService{

    private final DeviceRepository repository;
    public DeviceServiceImpl(DeviceRepository repository) {
        this.repository = repository;
    }


    @Override
    public List<Device> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Device> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Device> getByName(String name) {
         return repository.findByName(name);
    }

    @Override
    public Device add(Device device) {
        return repository.save(device);
    }
}
