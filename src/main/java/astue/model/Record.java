package astue.model;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;


@Entity
@Data
@NoArgsConstructor
@Table(name="record")
public class Record {
	public Record(Device device, Double activePowerConsumption, Double reactivePowerConsumption) {
		this.device = device;
		this.activePowerConsumption = activePowerConsumption;
		this.reactivePowerConsumption = reactivePowerConsumption;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="device_id")
	private Device device;

	@Column(name="active")
	private Double activePowerConsumption;
	@Column(name="reactive")
	private Double reactivePowerConsumption;

	@CreationTimestamp
	@Column(updatable=false,nullable = false)
	private LocalDateTime created;
//	@UpdateTimestamp
//	@Column(nullable = false)
//	private LocalDateTime updated;

}
