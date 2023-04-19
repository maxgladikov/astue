package astue.service;

import astue.exception.ResourceNotFoundException;
import astue.model.Substation;
import astue.repository.SubstationRepository;
import astue.service.interfaces.SubstationService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class SubstationServiceImpl implements SubstationService{

	private final SubstationRepository substationRepository;

	public SubstationServiceImpl(SubstationRepository substationRepository) {
		this.substationRepository = substationRepository;
	}

	@Transactional(readOnly = true)
	public List<Substation> getAll() {
		List<Substation> substations= substationRepository.findAll();
		return substations;
	}

	@Override
	public Substation getById(Long id) {
		return substationRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Entity with id="+id+"not found"));
	}

	@Override
	public Substation getByName(String name) {
		return substationRepository.findByName(name).orElseThrow(()->new ResourceNotFoundException("Entity with name="+name+"not found"));
	}


	public Substation add(Substation substation) {
		return substationRepository.save(substation);
	}
}
