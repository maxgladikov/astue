package astue.model;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import astue.util.DeviceDeserializer;
import astue.util.DeviceSerializer;
import astue.util.Ied;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name="device")
@JsonSerialize(using = DeviceSerializer.class)
@JsonDeserialize(using = DeviceDeserializer.class)
public class Device extends BaseEntity {
	private static final long serialVersionUID = -1405799293989434211L;
	@NotNull
	@Column(unique=true)
	@Pattern(regexp = "^[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}$",message="ip isn't correct")
	private String hostAddress;
	private String line;
	private Integer drawerColumn;
	private Character drawerRow;
	private boolean incomer;
	private boolean consumer;
	@Min(0)
	private Double power;
	private Character voltage;
	@Enumerated(EnumType.ORDINAL)
	private Ied ied;
	@CreationTimestamp
	@Column(updatable=false,nullable = false)
	private LocalDateTime created;
//	@JsonBackReference(value="switchgear->device")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "switchgear_id")
	private Switchgear switchgear;

//	@JsonBackReference(value="division->device")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "division_id")
	private Division division;

	@OneToMany(mappedBy = "device",fetch = FetchType.LAZY)
//	@JsonIgnore
	private List<Record> records=new ArrayList<>();

	@Override
	public String toString() {
		return "Device{" +
				"name="+this.getName()+'\''+
				", hostAddress='" + hostAddress + '\'' +
				", line='" + line + '\'' +
				", drawerNum=" + drawerColumn +
				", drawerLetter=" + drawerRow +
				", incomer=" + incomer +
				", consumer=" + consumer +
				", power=" + power +
				", voltage=" + voltage +
				", description='" + super.getDescription() + '\'' +
				", ied='" + ied + '\'' +
				", created=" + created +
				", switchgear=" + switchgear +
				", division=" + division +
				'}';
	}

	public static Builder newBuilder(){
		return new Device().new Builder();
	}
	public  class Builder{
		private Builder(){}
		public Builder setName(String name){
			Device.this.setName(name);
			return this;
		}
		public Builder setIp(String ip){
			Device.this.setHostAddress(ip);
			return this;
		}
		public Builder setLine(String line){
			Device.this.setLine(line);
			return this;
		}
		public Builder setDrawerColumn(Integer drawerColumn){
			Device.this.setDrawerColumn(drawerColumn);
			return this;
		}
		public Builder setDrawerRow(Character drawerRow){
			Device.this.setDrawerRow(drawerRow);
			return this;
		}
		public Builder setVoltage(Character voltage){
			Device.this.setVoltage(voltage);
			return this;
		}
		public Builder setPower(Double power){
			Device.this.setPower(power);
			return this;
		}
		public Builder setIncomer(boolean incomer){
			Device.this.setIncomer(incomer);
			return this;
		}
		public Builder setIncomer(String str){
			Device.this.setIncomer(str.equals("TRUE")?true:false);
			return this;
		}
		public Builder setConsumer(boolean consumer){
			Device.this.setConsumer(consumer);
			return this;
		}
		public Builder setConsumer(String str){
			Device.this.setConsumer(str.equals("TRUE")?true:false);
			return this;
		}
		public Builder setDescription(String description){
			Device.this.setDescription(description);
			return this;
		}
		public Builder setDivision(Division division){
			Device.this.setDivision(division);
			return this;
		}
		public Builder setIed(String ied){
			Device.this.setIed(Ied.valueOf(ied));
			return this;
		}
		public Builder setSwitchgear(Switchgear switchgear){
			Device.this.setSwitchgear(switchgear);
			return this;
		}
		public Device build(){
			created=LocalDateTime.now();
			return Device.this;
		}
	}

}

