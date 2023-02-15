package astue.model;


import jakarta.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Entity
@Data
@NoArgsConstructor
@Table(name="record")
public class Record {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@ManyToOne(fetch = FetchType.LAZY)
	private Device device;
	private Date date;
	@Column(name="active")
	private Double activePowerConsumption;
	@Column(name="reactive")
	private Double reactivePowerConsumption;
}
