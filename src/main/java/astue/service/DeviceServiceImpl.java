package astue.service;

import astue.exception.ResourceNotFoundException;
import astue.model.Device;
import astue.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class DeviceServiceImpl implements DeviceService{

    private final DeviceRepository repository;
    public DeviceServiceImpl(DeviceRepository repository) {
        this.repository = repository;
    }


    @Override
//    @Transactional(readOnly = true)
//    @Retryable(maxAttempts = 2)
    public List<Device> getAll() {
        return repository.findAll();
    }

    @Override
    public Device getById(Long id) {
        return repository.findById(id).orElseThrow(()->new ResourceNotFoundException("Entity with id="+id+"not found"));
    }

    @Override
//    @Transactional
//    @Retryable
    public Device getByName(String name) {
         return repository.findByName(name).orElseThrow(()->new ResourceNotFoundException("Entity with name="+name+"not found"));
    }

    @Override
    public Device add(Device device) {
        return repository.save(device);
    }

    public void addAll(Set<Device> devices) {
        devices.stream().limit(1).peek(x-> System.out.println(x)).peek(x->repository.save(x));
    }

    public void delete(Long id){
        repository.deleteById(id);
    }
}
