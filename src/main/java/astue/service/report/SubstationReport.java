package astue.service.report;

import java.util.List;

import astue.model.Substation;
import lombok.Data;
@Data
public class SubstationReport{
	
	public  SubstationReport(Substation substation) {
		name=substation.getName();
		switchgears=substation.getSwitchgears().stream().map(swgr->new SwitchgearReport(swgr)).toList();
		active=switchgears.stream().reduce(0.0, (tmp,swgr) -> tmp+swgr.getActive(),Double::sum );
		reactive=switchgears.stream().reduce(0.0, (tmp,swgr) -> tmp+swgr.getReactive(),Double::sum );
		total=switchgears.stream().reduce(0.0, (tmp,swgr) -> tmp+swgr.getTotal(),Double::sum );

	}
	
	private final String name;
	private final List<SwitchgearReport> switchgears;
	private final double active;
	private final double reactive;
	private final double total;
	
	@Override
	public String toString() {
		StringBuilder sb=new StringBuilder();
		switchgears.stream().forEach(x->sb.append(x));
		return name + ","+active+","+reactive+","+total+sb.toString();
	}

}
