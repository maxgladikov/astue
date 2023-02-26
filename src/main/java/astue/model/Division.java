package astue.model;


import java.util.ArrayList;
import java.util.List;

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
@Table(name="plant")
public class Plant extends BaseEntity {
	
	
	
	public Plant(String tag, String description) {
		super();
		this.tag = tag;
		super.setName(description);
	}
	private String tag;
	@JsonManagedReference(value="device-plant")
	@OneToMany(mappedBy ="plant",fetch = FetchType.LAZY)
	private List<Device> devices=new ArrayList<Device>();

	public void addDevice(Device device){
		devices.add(device);
	}
}
