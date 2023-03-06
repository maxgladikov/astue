package astue.model;


import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="division")
@JsonIdentityInfo(
		generator = ObjectIdGenerators.PropertyGenerator.class,
		property = "id")
public class Division extends BaseEntity {

	public Division(String name, String description,Plant plant) {
		super();
		super.setName(name);
		this.plant=plant;
		this.description=description;
	}
	public Division(String name, String description) {
		super();
		super.setName(name);
		this.setDescription(description);
	}
	public Division(String name) {
		super();
		super.setName(name);
	}
	public String description;
	@JsonBackReference(value="division->plant")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "plant_id")
	private Plant plant;
	@JsonManagedReference(value="division->device")
	@OneToMany(mappedBy ="division",fetch = FetchType.LAZY)
	private List<Device> devices=new ArrayList<Device>();

	public void addDevice(Device device){
		devices.add(device);
	}
	@Override
	public String toString() {
		return "Division [name="+this.getName()+", plant=" + plant + ", description=" + description + "]";
	}
	
}
