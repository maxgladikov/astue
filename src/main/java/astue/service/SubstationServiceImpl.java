package astue.service;

import astue.model.Substation;
import astue.repository.SubstationRepository;
import astue.util.CustomResponse;
import astue.util.CustomStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class SubstationServiceImpl implements SubstationService{

	private final SubstationRepository substationRepository;

	public SubstationServiceImpl(SubstationRepository substationRepository) {
		this.substationRepository = substationRepository;
	}


	public List<Substation> getAll() {
		List<Substation> substations= substationRepository.findAll();
		return substations;
	}

	@Override
	public Optional<Substation> getById(Long id) {
		return substationRepository.findById(id);
	}

	@Override
	public Optional<Substation> getByName(String name) {
		return substationRepository.findByName(name);
	}


	public Substation add(Substation substation) {
		return substationRepository.save(substation);
	}
}
