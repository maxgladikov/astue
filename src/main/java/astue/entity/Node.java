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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@Table(name="NODES")
public class Node {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String tag;
	private String hostAddress;
	@ManyToOne(fetch = FetchType.LAZY)
	private PMCC pmcc;
	private String drawer;
	private String ied;
	@ManyToOne(fetch = FetchType.LAZY)
	private Plant plant;
	private Integer power;
	@OneToMany(mappedBy = "node",fetch = FetchType.LAZY)
	private List<Record> records=new ArrayList<Record>();
	private String description;
}

