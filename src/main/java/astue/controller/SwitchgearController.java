package astue.controller;

import astue.model.Substation;
import astue.model.Switchgear;
import astue.service.SubstationServiceImpl;
import astue.service.SwitchgearService;
import astue.service.SwitchgearServiceImpl;
import astue.util.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/switchgears")
public class SwitchgearController {
	private final SwitchgearService service;
	public SwitchgearController(SwitchgearService service) {
		this.service = service;
	}

	@GetMapping
	public Collection<Switchgear> getAll(){
		return service.getAll();
	}

	@GetMapping("/{id}")
	public Switchgear getById(@PathVariable Long id){
		return service.getById(id);
	}
	@GetMapping("/name/{name}")
	public Switchgear getByName(@PathVariable String name){
		return service.getByName(name);
	}

}
