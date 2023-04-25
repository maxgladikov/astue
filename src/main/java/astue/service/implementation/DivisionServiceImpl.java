package astue.service.implementation;

import astue.exception.ResourceNotFoundException;
import astue.model.Division;
import astue.model.Plant;
import astue.repository.DivisionRepository;
import astue.repository.PlantRepository;
import astue.service.DivisionService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DivisionServiceImpl implements DivisionService{

	private final DivisionRepository repository;

	
	public List<Division> getAll() {
		return repository.findAll();
	}

	@Override
	public Division getById(Long id)
	{
		return repository.findById(id).orElseThrow(()->new ResourceNotFoundException("Entity with id="+id+"not found"));
	}


	public Division getByName(String name) {
		return repository.findByName(name).orElseThrow(()->new ResourceNotFoundException("Entity with name="+name+"not found"));
	}

	public Division add(Division division) {
		return repository.save(division);
	}
}
