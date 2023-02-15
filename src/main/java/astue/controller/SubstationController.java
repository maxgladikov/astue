package astue.controller;

import astue.model.Device;
import astue.model.Substation;
import astue.service.SubstationService;
import astue.service.SubstationServiceImpl;
import astue.util.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/v1/substations")
public class SubstationController {
	private final SubstationService service;
	public SubstationController(SubstationService service) {
		this.service = service;
	}

	@GetMapping("/")
	public Collection<Substation> getAll(){
		return  service.getAll();
	}

	@GetMapping("/{id}")
	public Substation getById(@PathVariable Long id){
		Substation substation=service.getById(Long.valueOf(id)).orElseThrow();
		System.out.println("++++++++++++"+ substation.toString());
		return  substation;
	}
	@GetMapping("/name/{name}")
	public Substation getById(@PathVariable String name){
		Substation substation=service.getByName(name).orElseThrow();
		return  substation;
	}


	@PostMapping("/")
	public  void add(@RequestBody Substation substation){
		service.add(substation);
	}

}
