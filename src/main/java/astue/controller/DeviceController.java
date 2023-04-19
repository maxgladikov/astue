package astue.controller;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import astue.model.Device;
import astue.service.interfaces.DeviceService;
import astue.util.Ied;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/devices", produces = "application/json")
public class DeviceController {

	private final DeviceService service;

	@GetMapping("/ied")
	public ResponseEntity<List<Ied>> getIed() {
		Ied ied = Ied.TESYS;
		return new ResponseEntity<>(Arrays.asList(ied.getDeclaringClass().getEnumConstants()), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<Collection<Device>> getAll() {
		return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Device> getById(@PathVariable Long id) {
		return new ResponseEntity<>(service.getById(id), HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Object> put(@RequestBody Device newDevice, @PathVariable Long id) {
		service.update(newDevice,id);
		Map<String,String> body=new HashMap();
		body.put("message","success");
		return new ResponseEntity<>(body,HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		service.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/")
	public ResponseEntity<?> add(@Valid @RequestBody Device newDevice) {
		service.add(newDevice);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

}
