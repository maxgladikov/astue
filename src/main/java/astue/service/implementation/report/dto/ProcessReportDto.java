package astue.service.implementation.report.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import astue.model.Division;

public class ProcessReportDto {
	private Map<ConsumptionType,Double> ammonia;
	private Map<ConsumptionType,Double> urea;
	private Map<ConsumptionType,Double> melamine;

	private Map<Division,Map<LocalDateTime,List<Double>>> ammoniaDivisions;
}

