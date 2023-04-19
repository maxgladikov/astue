package astue.model;


import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name="record")
public class PowerRecord {
	
	public PowerRecord(Device device, Double activePowerConsumption, Double reactivePowerConsumption) {
		this.device = device;
		this.activePowerConsumption = activePowerConsumption;
		this.reactivePowerConsumption = reactivePowerConsumption;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@NotNull
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
	
	@Override
	public String toString() {
		return "Record [id=" + id + ", device=" + device.getName() + ", activePowerConsumption=" + activePowerConsumption
				+ ", reactivePowerConsumption=" + reactivePowerConsumption + ", created=" + created + "]";
	}

	

}
