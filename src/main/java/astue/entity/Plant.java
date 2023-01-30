package astue.entity;


import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@Table(name="PLANTS")
public class Plant {
	
	
	
	public Plant(Integer number, String description) {
		super();
		this.number = number;
		this.description = description;
	}
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private Integer number;
	private String description;
	@OneToMany(mappedBy ="plant",fetch = FetchType.LAZY)
	private List<Node> nodes=new ArrayList<Node>();
}
