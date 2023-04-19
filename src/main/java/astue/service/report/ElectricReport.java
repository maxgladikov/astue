package astue.service.report;

import java.util.List;

import astue.model.Substation;

public class ElectricReport {
	public ElectricReport(List<Substation> substationList) {
		substations=substationList.stream().map(ss->new SubstationReport(ss)).toList();
		active=substations.stream().reduce(0.0, (tmp,ss) -> tmp+ss.getActive(),Double::sum );
		reactive=substations.stream().reduce(0.0, (tmp,swgr) -> tmp+swgr.getReactive(),Double::sum );
		total=substations.stream().reduce(0.0, (tmp,swgr) -> tmp+swgr.getTotal(),Double::sum );
	}
	
	private final List<SubstationReport> substations;
	private final double active;
	private final double reactive;
	private final double total;
	
	@Override
	public String toString() {
		StringBuilder sb=new StringBuilder();
		substations.stream().forEach(x->sb.append(x));
		return "Plant" + ","+active+","+reactive+","+total+sb.toString();
	}
}
