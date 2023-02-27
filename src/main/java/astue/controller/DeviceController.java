package astue.controller;

import astue.model.Device;
import astue.model.Switchgear;
import astue.service.DeviceServiceImpl;
import astue.service.SwitchgearService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.supercsv.*;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping(path="/api/v1/devices",produces="application/json")
public class DeviceController {
	private final DeviceServiceImpl service;
	private final SwitchgearService switchgearService;

	public DeviceController(DeviceServiceImpl service, SwitchgearService switchgearService) {
		this.service = service;
		this.switchgearService = switchgearService;
	}

//	@RequestMapping(value ="/report" )
//	public void downloadCSV(HttpServletResponse response) throws IOException {
	@RequestMapping(value ="/report/{startDate}/{endDate}" )
	public void downloadCSV(
			@PathVariable("startDate") @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate startDate,
			@PathVariable("endDate") @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate endDate,
			HttpServletResponse response) throws IOException {
		System.out.println("*********** startDate= "+startDate);
		System.out.println("*********** endDate= "+endDate);
		String csvFileName = "devices.csv";
		response.setContentType("text/csv");
		// creates mock data
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"",
				csvFileName);
		response.setHeader(headerKey, headerValue);
//		List<Device> data=service.getAll();

		// uses the Super CSV API to generate CSV data from the model data
		ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(),
				CsvPreference.STANDARD_PREFERENCE);
		String[] header = { "name",  "hostAddress", "line",
				"incomer",  "consumer","ied","power","voltage" ,"description"};
		csvWriter.writeHeader(header);
		service.getAll().stream().forEach(x-> {
			try {
				csvWriter.write(x,header);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		});
		csvWriter.close();
	}
//	@GetMapping("/api/v1/reportSS/{startDate}/{endDate}")
//	public ResponseEntity<Resou>


	@GetMapping
	public Collection<Device> getAll(){
		return service.getAll();
	}

	@GetMapping("/{id}")
	public Device getById(@PathVariable Long id){
		return  service.getById(id).orElseThrow();
	}

	@PutMapping ("/{id}")
	public Device put(@RequestBody Device newDevice,@PathVariable Long id){
		Device device=service.getById(id).orElseThrow();
		device.setName(newDevice.getName());
		device.setLine(newDevice.getLine());
		device.setDrawerColumn(newDevice.getDrawerColumn());
		device.setDrawerRow(newDevice.getDrawerRow());
		device.setIed(newDevice.getIed());
		device.setPower(newDevice.getPower());
		device.setVoltage(newDevice.getVoltage());
		device.setHostAddress(newDevice.getHostAddress());
		device.setIncomer(newDevice.isIncomer());
		device.setConsumer(newDevice.isConsumer());
		device.setDescription(newDevice.getDescription());
		return  service.add(device);
	}

	@DeleteMapping ("/{id}")
	public void delete(@PathVariable Long id){
		service.delete(id);
	}

	@PostMapping("/")
	public ResponseEntity<String> add(@Valid @RequestBody Device newDevice){
		System.out.println(newDevice);
		Switchgear switchgear=switchgearService.getByName(newDevice.getSwitchgear().getName()).orElseThrow();
		newDevice.setSwitchgear(switchgear);
		System.out.println(newDevice);
		service.add(newDevice);
		return ResponseEntity.ok("Device is valid");
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(
			MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}

}
