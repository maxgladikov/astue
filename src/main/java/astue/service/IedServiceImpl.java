package astue.service;

import astue.model.Ied;
import astue.repository.IedRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IedServiceImpl implements IedService{

    private final IedRepository repository;
    public IedServiceImpl(IedRepository repository) {
        this.repository = repository;
    }


    @Override
    public List<Ied> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Ied> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Ied> getByName(String name) {
         return repository.findByName(name);
    }

    @Override
    public Ied add(Ied ied) {
      return  repository.save(ied);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
