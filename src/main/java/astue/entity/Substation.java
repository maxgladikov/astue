package astue.entity;


import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@Table(name="SUBSTATIONS")
public class Substation {
	
	
	
	public Substation(Integer number, String description) {
		this.number = number;
		this.description = description;
	}
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private Integer number;
	private String description;
	@OneToMany(mappedBy ="substation",fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
	private List<PMCC> pmcc=new ArrayList<PMCC>();
	
	public void addPMCC(PMCC mcc) {
		pmcc.add(mcc);
	}
	@Override
	public String toString() {
		return "Substation [id=" + id + ", number=" + number + ", description=" + description + ", pmcc=" + pmcc + "]";
	}
	
	
}

