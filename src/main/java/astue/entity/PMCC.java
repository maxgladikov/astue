package astue.entity;


import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@Table(name="PMCC")
public class PMCC {
	
	
	
	public PMCC(String tag, Substation substation) {
		this.tag = tag;
		this.substation = substation;
	}
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String tag;
	@ManyToOne(fetch = FetchType.LAZY)
	private Substation substation;
	@OneToMany(mappedBy ="pmcc",fetch = FetchType.EAGER)
	private List<Node> nodes=new ArrayList<Node>();
	@Override
	public String toString() {
		return "PMCC [tag=" + tag + "]";
	}
	
	
}

