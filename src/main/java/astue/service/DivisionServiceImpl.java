package astue.service;

import astue.model.Division;
import astue.model.Plant;
import astue.repository.DivisionRepository;
import astue.repository.PlantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DivisionServiceImpl implements DivisionService{

	private final DivisionRepository repository;
	public DivisionServiceImpl(DivisionRepository repository) {
		this.repository = repository;
	}

	public List<Division> getAll() {
		return repository.findAll();
	}

	@Override
	public Optional<Division> getById(Long id)
	{
		return repository.findById(id);
	}


	public Optional<Division> getByName(String name) {
		return repository.findByName(name);
	}

	public Division add(Division division) {
		return repository.save(division);
	}
}
