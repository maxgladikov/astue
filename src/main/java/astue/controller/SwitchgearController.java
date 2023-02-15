package astue.controller;

import astue.model.Substation;
import astue.model.Switchgear;
import astue.service.SubstationServiceImpl;
import astue.service.SwitchgearService;
import astue.service.SwitchgearServiceImpl;
import astue.util.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

	@RequestMapping(method = RequestMethod.GET)
	public Collection<Switchgear> getAll(){
		return service.getAll();
	}

	@RequestMapping(path = "/{id}",method = RequestMethod.GET)
	public Switchgear getById(@PathVariable Long id){
		return service.getById(id).orElseThrow();
	}
	@RequestMapping(path = "/name/{name}",method = RequestMethod.GET)
	public Switchgear getByName(@PathVariable String name){
		return service.getByName(name).orElseThrow();
	}

}
