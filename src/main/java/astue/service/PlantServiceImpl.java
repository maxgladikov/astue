package astue.service;

import astue.exception.ResourceNotFoundException;
import astue.model.Plant;
import astue.repository.PlantRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PlantServiceImpl implements PlantService{

	private final PlantRepository plantRepository;
	public PlantServiceImpl(PlantRepository plantRepository) {
		this.plantRepository = plantRepository;
	}

	public List<Plant> getAll() {
		return plantRepository.findAll();
	}

	@Override
	public Plant getById(Long id)
	{
		return plantRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Entity with id="+id+"not found"));
	}


	public Plant getByName(String name) {
		return plantRepository.findByName(name).orElseThrow(()->new ResourceNotFoundException("Entity with name="+name+"not found"));
	}

	public Plant add(Plant plant) {
		return plantRepository.save(plant);
	}
}
