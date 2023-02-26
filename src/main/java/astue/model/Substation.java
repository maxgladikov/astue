package astue.model;


import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name="substation")
public class Substation extends BaseEntity {

	public Substation(String name) {
		super();
		this.setName(name);
	}

	@JsonManagedReference(value="substation->switchgear")
	@OneToMany(mappedBy ="substation",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	private List<Switchgear> switchgears =new ArrayList<Switchgear>();
	
	public void addSwitchgear(Switchgear switchgear) {
		switchgears.add(switchgear);
	}

	@Override
	public String toString() {
		return "Substation [id=" + super.getId()  + ", name=" + super.getName() + ", pmcc=" + switchgears + "]";
	}
	
	
}

