package astue.model;


import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
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
	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	private Substation substation;
	@JsonManagedReference
	@OneToMany(mappedBy ="switchgear",cascade = CascadeType.ALL)
	private List<Device> devices =new ArrayList<Device>();

	@Override
	public String toString() {
		return "PMCC [tag=" + super.getName() + "]";
	}
	

	public void addDevice(Device device){
		devices.add(device);
	}

}

