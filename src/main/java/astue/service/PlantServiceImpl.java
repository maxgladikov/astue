package astue.service;

import astue.model.Plant;
import astue.repository.PlantRepository;
import astue.util.CustomResponse;
import astue.util.CustomStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
	public Optional<Plant> getById(Long id)
	{
		return plantRepository.findById(id);
	}


	public Optional<Plant> getByName(String name) {
		return plantRepository.findByName(name);
	}

	public Plant add(Plant plant) {
		return plantRepository.save(plant);
	}
}
