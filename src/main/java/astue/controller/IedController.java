package astue.controller;

import astue.model.Ied;
import astue.service.IedService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/v1/ieds")
public class IedController {

	private final IedService service;

	public IedController(IedService service) {
		this.service = service;
	}

	@GetMapping
	public Collection<Ied> getAll(){
		return service.getAll();
	}

	@GetMapping("/{id}")
	public Ied getById(@PathVariable Long id){
		return service.getById(id).orElseThrow();
	}


	@PostMapping(consumes = "application/x-www-form-urlencoded")
	public  void add(@ModelAttribute Ied newIed){
		service.add(newIed);
	}

	@PutMapping(
			path="/{id}",
			consumes = "application/x-www-form-urlencoded")
	public  Ied replace(Ied newIed, @PathVariable Long id){
		System.out.println("*******************  ID="+id);
		Ied newOne=service.getById(id).orElseThrow();
		newOne.setName(newIed.getName());
		newOne.setAddress(newIed.getAddress());
		newOne.setScale(newIed.getScale());
		newOne.setQuantity(newIed.getQuantity());
		newOne.setSlaveId(newIed.getSlaveId());
		newOne.setDataType(newIed.getDataType());
		System.out.println(newOne);
		return service.add(newOne);
	}

	@DeleteMapping("/{id}")
	public  void deleteById(@PathVariable Long id){
		service.deleteById(id);
	}

}
