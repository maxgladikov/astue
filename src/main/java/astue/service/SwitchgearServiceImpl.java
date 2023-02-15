package astue.service;

import astue.model.Switchgear;
import astue.repository.SwitchgearRepository;
import astue.util.CustomResponse;
import astue.util.CustomStatus;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
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
	public Optional<Switchgear> getById(Long id) {
		return switchgearRepository.findById(id);
	}

	@Override
	public Optional<Switchgear> getByName(String name) {
		return switchgearRepository.findByName(name);
	}

	public CustomResponse<Switchgear> findByName(String name) {
		Switchgear switchgear = switchgearRepository.findByName(name).orElseThrow();
		return new CustomResponse<>(Stream.of(switchgear).collect(Collectors.toList()),CustomStatus.SUCCESS);
	}

	public Switchgear add(Switchgear switchgear) {
		return switchgearRepository.save(switchgear);
	}
}
