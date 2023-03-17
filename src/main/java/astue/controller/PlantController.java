package astue.controller;

import astue.model.Plant;
import astue.service.PlantService;
import astue.service.PlantServiceImpl;
import astue.util.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/v1/plants")
public class PlantController {

	private final PlantService service;
	public PlantController(PlantService service) {
		this.service = service;
	}

	@GetMapping
	public Collection<Plant> getAll(){
		return service.getAll();
	}

//	@GetMapping("/{name}")
//	public CustomResponse<Plant> getByName(@PathVariable("name") String name){
//		return  service.getByName(name);
//	}

	@PostMapping("/")
	public  void add(@RequestBody Plant plant){
		 service.add(plant);
	}

}
