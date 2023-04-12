package astue.controller;

import java.util.Collection;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import astue.model.Substation;
import astue.service.SubstationService;

@RestController
@RequestMapping("/api/v1/substations")
public class SubstationController {
	private final SubstationService service;
	public SubstationController(SubstationService service) {
		this.service = service;
	}
	@GetMapping
	public Collection<Substation> getAll(){
		return service.getAll();
//		return  ResponseEntity.ok(service.getAll());
//		return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public Substation getById(@PathVariable Long id){
		Substation substation=service.getById(Long.valueOf(id));
		System.out.println("++++++++++++"+ substation.toString());
		return  substation;
	}
	@GetMapping("/name/{name}")
	public Substation getById(@PathVariable String name){
		Substation substation=service.getByName(name);
		return  substation;
	}


	@PostMapping("/")
	public  void add(@RequestBody Substation substation){
		service.add(substation);
	}

}
