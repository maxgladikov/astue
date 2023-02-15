package astue.controller;

import astue.model.Device;
import astue.service.DeviceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/v1/devices")
public class DeviceController {
	private final DeviceService service;
	public DeviceController(DeviceService service) {
		this.service = service;
	}

	@GetMapping("/")
	public Collection<Device> getAll(){
		return service.getAll();
	}

	@GetMapping("/{id}")
	public Device getById(@PathVariable Long id){
		Device device=service.getById(Long.valueOf(id)).orElseThrow();
		System.out.println("++++++++++++"+ device.toString());
		return  device;
	}





//	@PostMapping("/")
//	public  void add(@RequestBody Device device){
//		 service.add(device);
//	}

}
