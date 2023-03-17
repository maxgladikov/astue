package astue.service;

import astue.exception.ResourceNotFoundException;
import astue.model.Switchgear;
import astue.repository.SwitchgearRepository;
import astue.util.CustomResponse;
import astue.util.CustomStatus;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class SwitchgearServiceImpl implements SwitchgearService{

	private final SwitchgearRepository switchgearRepository;
	public SwitchgearServiceImpl(SwitchgearRepository switchgearRepository) {
		this.switchgearRepository = switchgearRepository;
	}

	public Collection<Switchgear> getAll() {
		return switchgearRepository.findAll();
	}

	@Override
	public Switchgear getById(Long id) {
		return switchgearRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Entity with id="+id+"not found"));
	}

	@Override
	public Switchgear getByName(String name) {
		return switchgearRepository.findByName(name).orElseThrow(()->new ResourceNotFoundException("Entity with name="+name+"not found"));
	}

	public CustomResponse<Switchgear> findByName(String name) {
		Switchgear switchgear = switchgearRepository.findByName(name).orElseThrow();
		return new CustomResponse<>(Stream.of(switchgear).collect(Collectors.toList()),CustomStatus.SUCCESS);
	}

	public Switchgear add(Switchgear switchgear) {
		return switchgearRepository.save(switchgear);
	}
}
