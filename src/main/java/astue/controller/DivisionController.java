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

import astue.model.Division;
import astue.service.DivisionService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/divisions")
public class DivisionController {

	private final DivisionService service;

	@GetMapping
	public ResponseEntity<Collection<Division>> getAll(){
		return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
	}

	@GetMapping("/name/{name}")
	public ResponseEntity<Division> getByName(@PathVariable("name") String name){
		return new ResponseEntity<>(service.getByName(name), HttpStatus.OK);
	}

	@PostMapping("/")
	public  ResponseEntity<?> add(@RequestBody Division division){
		 return new ResponseEntity<>( service.add(division), HttpStatus.OK);
	}

}
