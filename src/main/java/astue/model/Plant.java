package astue.model;


import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="plant")
public class Plant extends BaseEntity {

	public Plant(String name, String description) {
		super.setName(name);
		this.setDescription(description);
	}
	public Plant(String name) {
		super.setName(name);
	}
	@JsonManagedReference(value="division->plant")
	@OneToMany(mappedBy ="plant",fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
	private List<Division> divisions=new ArrayList<>();

	public void addDevsions(Division division){
		divisions.add(division);
	}
}
