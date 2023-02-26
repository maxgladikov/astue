package astue.service;

import astue.model.Division;
import astue.model.Plant;
import astue.model.Substation;
import astue.model.Switchgear;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.function.Function;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class Pool {
    @Autowired
    private SubstationService substationService;
    @Autowired
    private SwitchgearService switchgearService;
    @Autowired
    private PlantService plantService;
    @Autowired
    private DivisionService divisionService;
    @Getter
    private Map<String, Plant> plants=new HashMap<>();
    @Getter
    private Map<String, Switchgear> switchgears=new HashMap<>();
    @Getter
    private Map<String, Substation> substations=new HashMap<>();
    @Getter
    private Map<String, Division> divisions=new HashMap<>();

    public void refresh(){
        Set<Plant> plantSet=new LinkedHashSet<>(plantService.getAll());
        Set<Switchgear> switchgearSet=new LinkedHashSet<>(switchgearService.getAll());
        Set<Substation> substationSet=new LinkedHashSet<>(substationService.getAll());
        Set<Division> divisionSet=new LinkedHashSet<>(divisionService.getAll());
        plants.clear();
        switchgears.clear();
        substations.clear();
        divisions.clear();
        plants=plantSet.stream().collect(Collectors.toMap(x->x.getName(),x->x));
        switchgears=switchgearSet.stream().collect(Collectors.toMap(x->x.getName(),x->x));
        substations=substationSet.stream().collect(Collectors.toMap(x->x.getName(),x->x));
        divisions=divisionSet.stream().collect(Collectors.toMap(x->x.getName(),x->x));
    }
}