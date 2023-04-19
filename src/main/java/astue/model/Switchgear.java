package astue.model;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="switchgear")
public class Switchgear extends BaseEntity {
	public Switchgear(String name, Substation substation) {
		super();
		this.substation = substation;
		super.setName(name);
	}
	public Switchgear(String name){
		this.setName(name);
	}
	@JsonBackReference(value="substation->switchgear")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="substation_id")
	private Substation substation;
	@JsonManagedReference(value="switchgear->device")
	@OneToMany(mappedBy ="switchgear",cascade = CascadeType.PERSIST)
	private List<Device> devices =new ArrayList<Device>();

	@Override
	public String toString() {
		return "Switchgear [substation=" + substation.getName() + ", devices=" + devices + "]";
	}
	

	public void addDevice(Device device){
		devices.add(device);
	}

}

