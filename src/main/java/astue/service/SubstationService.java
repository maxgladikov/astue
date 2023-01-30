package astue.service;

import astue.entity.Book;
import astue.entity.Substation;
import astue.repository.SubstationRepository;
import astue.util.CustomResponse;
import astue.util.CustomStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class SubstationService {

	@Autowired
	private final SubstationRepository substationRepository;

	public SubstationService(SubstationRepository substationRepository) {
		this.substationRepository = substationRepository;
	}

	public CustomResponse<Substation> getAll() {
		List<Substation> substations=substationRepository.findAll();
		return new CustomResponse<>(substations,CustomStatus.SUCCESS);
	}

	public CustomResponse<Substation> findByNumber(Integer number) {
		Substation substation=substationRepository.findByNumber(number).orElseThrow();
		return new CustomResponse<>(Stream.of(substation).collect(Collectors.toList()),CustomStatus.SUCCESS);
	}

	public CustomResponse<Substation> addSubstation(Substation substation) {
		Substation newSubstation=substationRepository.save(substation);
		return new CustomResponse<>(Stream.of(newSubstation).collect(Collectors.toList()),CustomStatus.SUCCESS);
	}
	
	

}
