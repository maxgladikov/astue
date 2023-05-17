package astue.service.implementation.report;

import java.util.EnumMap;
import java.util.List;

import org.springframework.stereotype.Component;

import astue.constants.ReportServiceType;
import astue.service.ReportService;

@Component
public class ReportServiceFactory {
	
	private EnumMap<ReportServiceType,ReportService> serviceMap;
	
	public ReportServiceFactory(List<ReportService> serviceList) {
		
		this.serviceMap=new EnumMap<>(ReportServiceType.class);
		for(ReportService service:serviceList) 
			this.serviceMap.put(service.getType(), service);
	}
	
	public ReportService getService(ReportServiceType type) {
		return this.serviceMap.get(type);
	}
	
}
