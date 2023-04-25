package astue.service.implementation.report;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.HashMap;

import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;

import astue.model.Device;
import astue.model.Plant;
import astue.model.PowerRecord;
import astue.service.DivisionService;
import astue.service.PlantService;
import astue.service.RecordService;
import astue.service.ReportService;
import astue.service.ReportServiceType;
import astue.service.implementation.report.dto.RecordSimplePdfDto;
import astue.util.PdfUtil;
import astue.model.Division;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PerformanceTestReport implements ReportService{
	
	private final PlantService plantService;
	private final DivisionService divisionService;
	private final  RecordService recordService;
	private Map<String,List<Device>> hierarchy;
	private double ammoniaConsumption=0;
	private double ureaConsumption=0;
	private double melamineConsumption=0;
	
	
	public InputStreamResource create(LocalDateTime from,LocalDateTime to) {
		var hierarcky=getHierarcky(from,to);
		String title = "Power consumption report per process unit";
		
		Document document = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
            PdfWriter writer =PdfWriter.getInstance(document, out);
            document.open();
            // TITLE
            PdfUtil.addTitle( document, title);
            // BODY
            
            // Ammonia plant
            var ammonia=hierarchy.get("ammonia");
            ammonia.parallelStream().limit(10).forEachOrdered(d -> {
        															try {
																			PdfUtil.addTable(document, "Ammonia",	Arrays.asList("tag","consumed"), 
																					  d.getName(),"hello"//calculateConsumption(d.getRecords())
																					);
																			
																		
																		} catch (DocumentException e) {
																			e.printStackTrace();
																		} catch (MalformedURLException e) {
																			e.printStackTrace();
																		} catch (IOException e) {
																			e.printStackTrace();
																		}
            });
            
//            PdfUtil.addImage(document, new Chart( d.getName(),d.getRecords() ).get());
//            document.add(Image.getInstance(writer, getChart(), 1.0f));
            document.close();

        } catch (DocumentException ex) {}

        return new InputStreamResource(new ByteArrayInputStream(out.toByteArray()));
		
	}
	
	private double calculateConsumption(List<PowerRecord> records) {
		records.stream().sorted(Comparator.comparing(PowerRecord::getCreated));
		if (records.isEmpty())
			return 0;
		return records.get(records.size()-1).getActivePowerConsumption()-records.get(0).getActivePowerConsumption();
	}
	
	private Map<String,List<Device>> getHierarcky(LocalDateTime from,LocalDateTime to) {
		List<Plant> plants=(List<Plant>) plantService.getAll();//.stream().filter(x -> x.getName().equals("Melamine")).
		
		
		var ammonia= plants.stream().filter(x -> x.getName().equalsIgnoreCase("A-20"))
				.flatMap(x -> x.getDivisions().stream())
				.map(x -> divisionService.getByName(x.getName()))
				.flatMap(x -> x.getDevices().stream())
				.filter(Device::isConsumer)
				.toList();
		var urea= plants.stream().filter(x -> x.getName().equalsIgnoreCase("U-30"))
				.flatMap(x -> x.getDivisions().stream())
				.map(x -> divisionService.getByName(x.getName()))
				.flatMap(x -> x.getDevices().stream())
				.filter(Device::isConsumer)
				.toList();
		var melamine= plants.stream().filter(x -> x.getName().equalsIgnoreCase("L-40"))
				.flatMap(x -> x.getDivisions().stream())
				.map(x -> divisionService.getByName(x.getName()))
				.flatMap(x -> x.getDevices().stream())
				.filter(Device::isConsumer)
				.toList();
		
//		List<Division> divisions=plantService.getAll().stream().flatMap(p -> p.getDivisions().stream()).toList();
//		Map<Plant,List<Division>> plants=divisions.stream().collect(Collectors.groupingBy(Division::getPlant));
		
		// Get records
				Map<Device,List<PowerRecord>> records=recordService.getAllBetween(from, to).stream().collect(Collectors.groupingBy(PowerRecord::getDevice));
//				List<Plant> map=;
//				Arrays.asList(ammonia,urea,melamine).stream().map()
				
		// Assign records to devices		
				ammonia.stream().forEach(x -> x.setRecords(records.get(x)));
				urea.stream().forEach(x -> x.setRecords(records.get(x)));
				melamine.stream().forEach(x -> x.setRecords(records.get(x)));
				Map<String,List<Device>> result=new HashMap<String, List<Device>>();
				result.put("ammonia",ammonia);
				result.put("urea",urea);
				result.put("melamine",melamine);
				return result;
	}
	
	@Override
	public ReportServiceType getType() {
		return ReportServiceType.PROCESS_PDF;
	}
}
