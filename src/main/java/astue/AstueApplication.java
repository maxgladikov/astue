package astue;

import astue.model.*;
import astue.service.IedService;
import astue.service.PlantService;
import astue.service.SubstationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;
import java.util.Comparator;


@SpringBootApplication
public class AstueApplication implements CommandLineRunner{
	@Autowired
	private ApplicationContext ctx;
	@Autowired
	private PlantService plantService;
	@Autowired
	private SubstationService substationService;
	@Autowired
	private IedService iedService;


	//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
	public static void main(String[] args) {
		SpringApplication.run(AstueApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		*********  CTX  ********
//		System.out.println("#####################");
//		Arrays.asList(ctx.getBeanDefinitionNames()).stream().
//				sorted(Comparator.naturalOrder()).
//				forEach(System.out::println);
//		System.out.println("#####################");
//		ctx.getBean;

		// fill DB
		Ied tesys=new Ied("tesys",1,143,4,1,"UInt");
//        IED tesys=new IED();
        Ied f650=new Ied("f650",254,0xF44,8,1000,"Int");
        iedService.add(tesys);
        iedService.add(f650);
		Plant melamine = new Plant("L-40", "Melamine");
		Plant ammonia = new Plant("A-20", "Ammonia");
		Plant uria = new Plant("U-30", "Urea");
		Substation ss01 = new Substation("SS-01");
		Substation ss02 = new Substation("SS-02");
		Substation ss03 = new Substation("SS-03");
		Substation ss04 = new Substation("SS-04");
		Substation ss05 = new Substation("SS-05");
		Substation ss06 = new Substation("SS-06");
		Substation ss07 = new Substation("SS-07");
		Switchgear ss01SWGR1001 = new Switchgear("01-SWGR-1001", ss01);
		Switchgear ss01SWGR1002 = new Switchgear("01-SWGR-1002", ss01);
		Switchgear ss02PMCC3001 = new Switchgear("02-PMCC-3001", ss02);
		Switchgear ss02EPMCC3002 = new Switchgear("02-EPMCC-3002", ss02);
		Switchgear ss02PMCC3003 = new Switchgear("02-PMCC-3003", ss02);
		Switchgear ss03SWGR1001 = new Switchgear("03-SWGR-1001", ss03);
		Switchgear ss03EPMCC3001 = new Switchgear("03-EPMCC-3001", ss03);
		Switchgear ss03PMCC3002 = new Switchgear("03-PMCC-3002", ss03);
		Switchgear ss03EPMCC3003 = new Switchgear("03-EPMCC-3003", ss03);
		Switchgear ss03PMCC3004 = new Switchgear("03-PMCC-3004", ss03);
		Switchgear ss04EPMCC3001 = new Switchgear("04-PMCC-3001", ss04);
		Switchgear ss05EPMCC3001 = new Switchgear("05-PMCC-3001", ss05);
		Switchgear ss06PMCC3002 = new Switchgear("06-PMCC-3002", ss06);
		Switchgear ss06EPMCC3001 = new Switchgear("06-EPMCC-3001", ss06);
		Switchgear ss07ESWGR1001 = new Switchgear("07-ESWGR-1001", ss07);
		Switchgear ss07SWGR1001 = new Switchgear("07-SWGR-1001", ss07);
		Switchgear ss07EPMCC001 = new Switchgear("07-EPMCC-3001", ss07);
		Switchgear ss85SWGR = new Switchgear("85-SWGR-DG-8501-01", ss07);


//        Device d04UPV01=new Device("04-UP-V01","172.16.120.201",ss04EPMCC3001, IED.UPS_V,ammonia,"none");
//        ss04EPMCC3001.addDevice(d04UPV01);
//        ammonia.addDevice(d04UPV01);
		ss01.setSwitchgears(Arrays.asList(ss01SWGR1001,ss01SWGR1002));
		ss02.setSwitchgears(Arrays.asList(ss02PMCC3001,ss02EPMCC3002,ss02PMCC3003));
		ss03.setSwitchgears(Arrays.asList(ss03SWGR1001,ss03EPMCC3001,ss03PMCC3002,ss03EPMCC3003,ss03PMCC3004));
		ss04.setSwitchgears(Arrays.asList(ss04EPMCC3001));
		ss05.setSwitchgears(Arrays.asList(ss05EPMCC3001));
		ss06.setSwitchgears(Arrays.asList(ss06EPMCC3001,ss06PMCC3002));
		ss07.setSwitchgears(Arrays.asList(ss07SWGR1001,ss07ESWGR1001,ss07EPMCC001,ss85SWGR));

		plantService.add(melamine);
		plantService.add(uria);
		plantService.add(ammonia);

		substationService.add(ss01);
		substationService.add(ss02);
		substationService.add(ss03);
		substationService.add(ss04);
		substationService.add(ss05);
		substationService.add(ss06);
		substationService.add(ss07);

		System.out.println(" **************** START ****************** ");
//		ProcessorCSV.read("/home/max/Downloads/astue.csv").remove(1).stream().
//				map(u->(return new Device();))
	}

}
