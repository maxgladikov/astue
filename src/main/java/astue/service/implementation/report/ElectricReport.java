package astue.service.implementation.report;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import astue.model.Device;
import astue.model.Division;
import astue.model.Plant;
import astue.model.PowerRecord;
import astue.service.DivisionService;
import astue.service.PlantService;
import astue.service.RecordService;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class ElectricReport {

	private ElectricReport parent;
	private List<ElectricReport> children = new LinkedList<>();
	private String name;
	private Double active;
	private Double reactive;
	private Double total;
	private boolean valid = true;
	private Map<LocalDateTime, Double> seriesActive = new HashMap<>();
	private Map<LocalDateTime, Double> seriesReactive = new HashMap<>();
	private Map<LocalDateTime, Double> seriesTotal = new HashMap<>();

	public ElectricReport() {
	}

	public ElectricReport(String name) {
		this.name = name;
	}

	public static ElectricReport createFromDevice(Device device) {
		Map<LocalDateTime, Double> localSeriesActive = new HashMap<>();
		Map<LocalDateTime, Double> localSeriesReactive = new HashMap<>();
		Map<LocalDateTime, Double> localSeriesTotal = new HashMap<>();
		boolean localValid;
		ElectricReport report = new ElectricReport();
		report.setName(device.getName());
		List<PowerRecord> records = device.getRecords();
		if (Optional.ofNullable(records).isEmpty()) {
			report.setValid(false);
			return report;
		}
		if (records.size() <= 1) {
			report.setValid(false);
			return report;
		}

		records.stream().forEach(x -> {
			localSeriesActive.put(x.getCreated().truncatedTo(ChronoUnit.HOURS), x.getActivePowerConsumption());
			localSeriesReactive.put(x.getCreated().truncatedTo(ChronoUnit.HOURS), x.getReactivePowerConsumption());
		});
		Double minActive = localSeriesActive
				.get(localSeriesActive.keySet().stream().min(LocalDateTime::compareTo).orElseThrow());
		Double minReactive = localSeriesReactive
				.get(localSeriesReactive.keySet().stream().min(LocalDateTime::compareTo).orElseThrow());
		localSeriesActive.replaceAll((k, v) -> v - minActive);
		localValid = localSeriesActive.keySet().stream().allMatch(x -> localSeriesActive.get(x) >= 0);
		localSeriesReactive.replaceAll((k, v) -> v - minReactive);
		localValid = localValid && localSeriesReactive.keySet().stream().allMatch(x -> localSeriesReactive.get(x) >= 0);
		report.setValid(localValid);
		if (localValid) {
			localSeriesActive.keySet().stream().forEach(x -> localSeriesTotal.put(x,
					Math.sqrt(Math.pow(localSeriesActive.get(x), 2) + Math.pow(localSeriesReactive.get(x), 2))));
			report.setValid(localValid);
			report.setSeriesActive(localSeriesActive);
			report.setSeriesReactive(localSeriesReactive);
			report.setSeriesTotal(localSeriesTotal);
			report.calculateConsumption();
		}
		return report;
	}

	public static ElectricReport createFromPlants(List<Plant> plants) {
		ElectricReport report = new ElectricReport("AUM");
		plants.forEach(x -> report.addChild(new ElectricReport(x.getName())));
		plants.stream().flatMap(x -> x.getDivisions().stream())
				.forEach(div -> report.getChildren().stream()
						.filter(rep -> rep.getName().equals(div.getPlant().getName())).findFirst().orElseThrow()
						.addChild(new ElectricReport(div.getName())));

		plants.stream().flatMap(plant -> plant.getDivisions().stream()).flatMap(div -> div.getDevices().stream())
				.forEach(dev -> report.getChildren().stream().flatMap(plantRep -> plantRep.getChildren().stream())
				.filter(divReport -> divReport.getName().equals(dev.getDivision().getName())).findFirst().orElseThrow()
				.addChild(ElectricReport.createFromDevice(dev)));
		
		report.getChildren().stream().flatMap(plant -> plant.getChildren().stream()).forEach(ElectricReport::update);
		report.getChildren().forEach(ElectricReport::update); 
		report.update();
		return report;
	}


	public void addChild(ElectricReport child) {
		children.add(child);
		child.setParent(this);
		addSeries(child);
	}

	public void update() {
		if(!children.isEmpty()) {
			if(children.size()>1) {
				children.stream().filter(child -> child.isValid()).forEach(child -> {
									mergeSeries(seriesActive, child.seriesActive);
									mergeSeries(seriesReactive, child.seriesReactive);
									mergeSeries(seriesTotal, child.seriesTotal);
					});
				if(children.stream().anyMatch(child -> child.isValid()))
					calculateConsumption();
			}
		}else {
			valid=false;
		}
		
	}
	
	private void calculateConsumption() {
		if (valid) {
			active = seriesActive.get(seriesActive.keySet().stream().max(LocalDateTime::compareTo).orElse(seriesActive.keySet().iterator().next()))
				- 	 seriesActive.get(seriesActive.keySet().stream().min(LocalDateTime::compareTo).orElse(seriesActive.keySet().iterator().next()));

			reactive = seriesReactive.get(seriesReactive.keySet().stream().max(LocalDateTime::compareTo).orElseThrow())
					- seriesReactive.get(seriesReactive.keySet().stream().min(LocalDateTime::compareTo).orElseThrow());

			total = seriesTotal.get(seriesTotal.keySet().stream().min(LocalDateTime::compareTo).orElseThrow())
					- seriesTotal.get(seriesTotal.keySet().stream().min(LocalDateTime::compareTo).orElseThrow());
		}
	}

	private void addSeries(ElectricReport child) {
		if (child.isValid()) {
			var childSeriesActive = child.getSeriesActive();
			var childSeriesReactive = child.getSeriesReactive();
			var childSeriesTotal = child.getSeriesTotal();

			if (children.size() == 1) {
				seriesActive = childSeriesActive;
				seriesReactive = childSeriesReactive;
				seriesTotal = childSeriesTotal;
				return;
			}

			seriesActive = mergeSeries(seriesActive, childSeriesActive);
			seriesReactive = mergeSeries(seriesReactive, childSeriesReactive);
			seriesTotal = mergeSeries(seriesTotal, childSeriesTotal);
		}
	}

	private Map<LocalDateTime, Double> mergeSeries(Map<LocalDateTime, Double> parent, Map<LocalDateTime, Double> child) {
		List<LocalDateTime> mergedSet = Stream.concat(parent.keySet().stream(), child.keySet().stream())
				.collect(Collectors.toSet()).stream().sorted().toList();
		Map<LocalDateTime, Double> merged = new HashMap<>();
		double value = 0.0;
		for (LocalDateTime t : mergedSet) {
			if (parent.containsKey(t) && child.containsKey(t)) {
				value = child.get(t) + parent.get(t);
				merged.put(t, value);
			} else if (parent.containsKey(t)) {
				value = parent.get(t);
				merged.put(t, value);
			} else if (child.containsKey(t)) {
				value = child.get(t) + value;
				merged.put(t, value);
			}
		}
		return merged;
	}

}
