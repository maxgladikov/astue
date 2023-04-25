package astue.controller;

import java.util.Collection;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import astue.model.Substation;
import astue.service.SubstationService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/substations")
public class SubstationController {
	
	private final SubstationService service;
	
	@GetMapping
	public ResponseEntity<Collection<Substation>> getAll(){
		return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Substation> getById(@PathVariable Long id){
		return new ResponseEntity<>(service.getById(Long.valueOf(id)), HttpStatus.OK);
	}
	@GetMapping("/name/{name}")
	public ResponseEntity<Substation> getById(@PathVariable String name){
		return new ResponseEntity<>(service.getByName(name), HttpStatus.OK);
	}


	@PostMapping("/")
	public  ResponseEntity<?> add(@RequestBody Substation substation){
		service.add(substation);
		return new ResponseEntity<>( HttpStatus.OK);
	}

}
