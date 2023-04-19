package astue.controller;

import java.util.Collection;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import astue.model.Switchgear;
import astue.service.interfaces.SwitchgearService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/switchgears")
public class SwitchgearController {
	
	private final SwitchgearService service;

	@GetMapping
	public ResponseEntity<Collection<Switchgear>> getAll(){
		return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Switchgear> getById(@PathVariable Long id){
		return new ResponseEntity<>(service.getById(id), HttpStatus.OK);
	}
	@GetMapping("/name/{name}")
	public ResponseEntity<Switchgear> getByName(@PathVariable String name){
		return new ResponseEntity<>(service.getByName(name), HttpStatus.OK);
	}

}
