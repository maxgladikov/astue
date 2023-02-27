package astue.controller;

import astue.model.Division;
import astue.service.DivisionService;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;

@RestController
@RequestMapping("/api/v1/divisions")
public class DivisionController {

	private final DivisionService service;
	public DivisionController(DivisionService service) {
		this.service = service;
	}

	@GetMapping
	public Collection<Division> getAll(){
		return service.getAll();
	}

	@GetMapping("/name/{name}")
	public Division getByName(@PathVariable("name") String name){
		return  service.getByName(name).orElseThrow();
	}

	@PostMapping("/")
	public  void add(@RequestBody Division division){
		 service.add(division);
	}

}
