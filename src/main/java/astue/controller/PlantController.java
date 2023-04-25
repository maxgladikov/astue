package astue.controller;

import java.util.Collection;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import astue.model.Plant;
import astue.service.PlantService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/plants")
public class PlantController {

	private final PlantService service;

	@GetMapping
	public ResponseEntity<Collection<Plant>> getAll(){
		return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
	}


	@PostMapping("/")
	public  ResponseEntity<?> add(@RequestBody Plant plant){
		 service.add(plant);
		 return new ResponseEntity<>( HttpStatus.OK);
	}

}
