package astue.service;
import astue.model.*;
import lombok.RequiredArgsConstructor;

import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;
import java.util.Arrays;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SpringAux {
	private final PlantService plantService;
    private final  ApplicationContext ctx;
    private final  SubstationService substationService;
    private Pool pool;
    private final DeviceFactory deviceFactory;
    private final DeviceServiceImpl deviceService;

    public void getContext(){
//        *********  CTX  ********
        System.out.println("#####################");
        Arrays.asList(ctx.getBeanDefinitionNames()).stream().
                sorted(Comparator.naturalOrder()).
                forEach(System.out::println);
        System.out.println("#####################");
//		ctx.getBean;
    }
    public void populate(){
        // *** PLANTS *** \\
        Plant melamine = new Plant("L-40", "MELAMINE");
        Plant ammonia = new Plant("A-20", "AMMONIA");
        Plant urea = new Plant("U-30", "UREA");
        Plant aux = new Plant("AUX", "AUX");
        // *** DIVISIONS *** \\
        Division auxDiv=new Division("AUX","Aux",aux);
        Division a15=new Division("A-15","Tail gas",ammonia);
        Division a16=new Division("A-16","Nitrogen compressor",ammonia);
        Division a20=new Division("A-20","Ammonia",ammonia);
        Division b10=new Division("B-10","CO2 recovery",urea);
        Division o70=new Division("O-70","Boiler",ammonia);
        Division u30=new Division("U-30","Urea",urea);
        Division co2comp=new Division("CO2 comp","CO2 compressor",urea);
        ammonia.setDivisions(Arrays.asList(a15,a16,a20,o70));
        urea.setDivisions(Arrays.asList(b10,u30,co2comp));
        aux.setDivisions(Arrays.asList(auxDiv));
        // *** SUBSTATIONS *** \\
        Substation ss01 = new Substation("SS-01");
        Substation ss02 = new Substation("SS-02");
        Substation ss03 = new Substation("SS-03");
        Substation ss04 = new Substation("SS-04");
        Substation ss05 = new Substation("SS-05");
        Substation ss06 = new Substation("SS-06");
        Substation ss07 = new Substation("SS-07");
        Substation ss09 = new Substation("SS-09");
        // *** SWITCHGEARS *** \\
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
        Switchgear ss04EPMCC3001 = new Switchgear("04-EPMCC-3001", ss04);
        Switchgear ss05EPMCC3001 = new Switchgear("05-EPMCC-3001", ss05);
        Switchgear ss06PMCC3002 = new Switchgear("06-PMCC-3002", ss06);
        Switchgear ss06EPMCC3001 = new Switchgear("06-EPMCC-3001", ss06);
        Switchgear ss07ESWGR1001 = new Switchgear("07-ESWGR-1001", ss07);
        Switchgear ss07SWGR1001 = new Switchgear("07-SWGR-1001", ss07);
        Switchgear ss07EPMCC001 = new Switchgear("07-EPMCC-3001", ss07);
        Switchgear ss85SWGR = new Switchgear("85-SWGR-DG-8501-01", ss07);
        Switchgear ss09EPMCC001 = new Switchgear("09-EPMCC-3001", ss09);

        ss01.setSwitchgears(Arrays.asList(ss01SWGR1001,ss01SWGR1002));
        ss02.setSwitchgears(Arrays.asList(ss02PMCC3001,ss02EPMCC3002,ss02PMCC3003));
        ss03.setSwitchgears(Arrays.asList(ss03SWGR1001,ss03EPMCC3001,ss03PMCC3002,ss03EPMCC3003,ss03PMCC3004));
        ss04.setSwitchgears(Arrays.asList(ss04EPMCC3001));
        ss05.setSwitchgears(Arrays.asList(ss05EPMCC3001));
        ss06.setSwitchgears(Arrays.asList(ss06EPMCC3001,ss06PMCC3002));
        ss07.setSwitchgears(Arrays.asList(ss07SWGR1001,ss07ESWGR1001,ss07EPMCC001,ss85SWGR));
        ss09.setSwitchgears(Arrays.asList(ss09EPMCC001));
        System.out.println(" **************** START ****************** ");
        plantService.add(melamine);
        plantService.add(urea);
        plantService.add(ammonia);
        plantService.add(aux);

        substationService.add(ss01);
        substationService.add(ss02);
        substationService.add(ss03);
        substationService.add(ss04);
        substationService.add(ss05);
        substationService.add(ss06);
        substationService.add(ss07);
        substationService.add(ss09);

        System.out.println("******** DEVICES *********");
        Set<Device> devices=deviceFactory.createDevices("/home/max/Downloads/PlantData - ASTUE.csv");
        devices.stream().
//                peek(System.out::println).
                forEach(deviceService::add);
        System.out.println("******** COMPLETED *********");
    }
}
