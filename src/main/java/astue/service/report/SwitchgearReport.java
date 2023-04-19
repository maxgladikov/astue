package astue.service.report;

import java.util.List;
import astue.model.Switchgear;
import lombok.Data;
@Data
public class SwitchgearReport {
	
	public  SwitchgearReport(Switchgear swgr) {
		name=swgr.getName();
		devices=swgr.getDevices().stream().map(x->new DeviceReport(x)).toList();
		active=devices.stream().filter(d-> d.isIncomer()==true ).reduce(0.0, (tmp,d) -> tmp+d.getActive(),Double::sum );
		reactive=devices.stream().filter(d-> d.isIncomer()==true ).reduce(0.0, (tmp,d) -> tmp+d.getReactive(),Double::sum );
		total=devices.stream().filter(d-> d.isIncomer()==true ).reduce(0.0, (tmp,d) -> tmp+d.getTotal(),Double::sum );
	}
	
	private final String name;
	private final List<DeviceReport> devices;
	private final double active;
	private final double reactive;
	private final double total;
	
	
	@Override
	public String toString() {
		StringBuilder sb=new StringBuilder();
		devices.stream().forEach(x->sb.append(x));
		return name + ","+active+","+reactive+","+total+sb.toString();
	}
	
}
