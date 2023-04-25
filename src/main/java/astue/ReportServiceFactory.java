package astue;

import java.util.EnumMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import astue.service.ReportService;
import astue.service.ReportServiceType;

@Component
public class ReportServiceFactory {
	
	private EnumMap<ReportServiceType,ReportService> serviceMap;
	
	@Autowired
	public ReportServiceFactory(List<ReportService> serviceList) {
		this.serviceMap=new EnumMap<ReportServiceType, ReportService>(ReportServiceType.class);
		for(ReportService service:serviceList) 
			this.serviceMap.put(service.getType(), service);
	}
	
	public ReportService getService(ReportServiceType type) {
		return this.serviceMap.get(type);
	}
	
}
