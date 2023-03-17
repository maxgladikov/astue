package astue.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import astue.model.Device;
import astue.model.Division;
import astue.model.Switchgear;
import astue.service.DeviceService;
import astue.service.DeviceServiceImpl;
import astue.service.DivisionService;
import astue.service.SwitchgearService;
import astue.util.Ied;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path="/api/v1/devices",produces="application/json")
public class DeviceController {
	@Autowired
	private  DeviceServiceImpl service;
	@Autowired
	private  SwitchgearService switchgearService;
	@Autowired
	private  DivisionService divisionService;


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
	

	@GetMapping("/ied")
	public List<Ied> getIedl(){
		Ied ied=Ied.TESYS;
		
		return Arrays.asList(ied.getDeclaringClass().getEnumConstants());
	}
	@GetMapping
	public Collection<Device> getAll(){
		return service.getAll();
	}

	@GetMapping("/{id}")
	public Device getById(@PathVariable Long id){
		return  service.getById(id);
	}

	@PutMapping ("/{id}")
	public ResponseEntity<Object> put(@RequestBody Device newDevice,@PathVariable Long id){
		Device device=service.getById(id);
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
		device.setSwitchgear(switchgearService.getByName(newDevice.getSwitchgear().getName()));
		device.setDivision(divisionService.getByName(newDevice.getDivision().getName()));
//		System.out.println("***************");
//		System.out.println("***************");
		service.add(device);
		Map<String, Object> body = new LinkedHashMap<>();
	    body.put("message", "Device has been updated");
		return new ResponseEntity<>(body, HttpStatus.OK);
	}

	@DeleteMapping ("/{id}")
	public ResponseEntity<Object> delete(@PathVariable Long id){
		service.delete(id);
		Map<String, Object> body = new LinkedHashMap<>();
	    body.put("message", "Device has been deleted");
		return new ResponseEntity<>(body, HttpStatus.OK);
	}

	@PostMapping("/")
	public ResponseEntity<Object> add(@Valid @RequestBody Device newDevice){
		System.out.println(newDevice);
		Switchgear switchgear=switchgearService.getByName(newDevice.getSwitchgear().getName());
		Division division=divisionService.getByName(newDevice.getDivision().getName());
		newDevice.setSwitchgear(switchgear);
		newDevice.setDivision(division);
		System.out.println(newDevice);
		service.add(newDevice);
		Map<String, Object> body = new LinkedHashMap<>();
	    body.put("message", "Device has been added");
		return new ResponseEntity<>(body, HttpStatus.OK);
	}

	

}
