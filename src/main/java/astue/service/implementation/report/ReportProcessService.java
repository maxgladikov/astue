package astue.service.implementation.report;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import astue.constants.ReportServiceType;
import astue.model.Device;
import astue.model.Division;
import astue.model.Plant;
import astue.model.PowerRecord;
import astue.service.DivisionService;
import astue.service.PlantService;
import astue.service.RecordService;
import astue.service.ReportService;
import astue.util.ChartUtil;
import astue.util.PdfUtil;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportProcessService implements ReportService {

	private final PlantService plantService;
	private final DivisionService divisionService;
	private final RecordService recordService;
	private Document document = new Document();

	public InputStreamResource create(LocalDateTime from,LocalDateTime to) {
		ElectricReport plantReport=ElectricReport.createFromPlants(getPlants(from,to));
		String title = "Power consumption report per process unit";
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		try {
            PdfWriter writer =PdfWriter.getInstance(document, out);
            document.open();
            // TITLE
            PdfUtil.addTitle( document, title);
            // BODY
            getTable(plantReport);
            PdfUtil.addImage(document, ChartUtil.getChartImage(plantReport));
            
            document.close();

        } catch (DocumentException | IOException ex) {}

        return new InputStreamResource(new ByteArrayInputStream(out.toByteArray()));
	}

	public List<Plant> getPlants(LocalDateTime from,LocalDateTime to) {
		List<Plant> plants = (List<Plant>) plantService.getAll();
		Map<Plant, List<Division>> divisions = divisionService.getAll().stream().collect(Collectors.groupingBy(Division::getPlant));
		plants.forEach(plant -> plant.setDivisions(divisions.get(plant)));
		Map<Device, List<PowerRecord>> records = recordService.getAllBetween(from, to).stream().collect(Collectors.groupingBy(PowerRecord::getDevice));
		plants.forEach(plant -> plant.getDivisions().forEach(division -> division.getDevices().forEach(device -> device.setRecords(records.get(device)))));
		return plants;
	}

	private void getTable(ElectricReport report) {
		try {
			PdfUtil.addTable(document, report.getName() , Arrays.asList("name","consumed")
												,Arrays.asList( Arrays.asList("Active",report.getActive().toString())) );
		} catch (DocumentException e) {
			e.printStackTrace();
		} 
		try {
			PdfUtil.addImage(document, ChartUtil.getChartImage(report));
		} catch (IOException | DocumentException e) {
			e.printStackTrace();
		}
	}

	@Override
	public ReportServiceType getType() {
		return ReportServiceType.PROCESS_PDF;
	}
}
