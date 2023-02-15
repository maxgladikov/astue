package astue.model;


import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="device")
//@JsonIgnoreProperties(value = { "intValue" })
public class Device extends BaseEntity {


	@NotNull
	@Column(unique=true)
	private String hostAddress;
	private String lineNumber;
	private Integer drawerNum;
	private Character drawerLetter;
	private boolean incomer;
	private boolean active;
	private Double power;
	private Character voltage;
	private String description;
	@JsonBackReference
	@ManyToOne
	private Ied ied;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	private Switchgear switchgear;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="plant_id")
	private Plant plant;

	@OneToMany(mappedBy = "device",fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Record> records=new ArrayList<Record>();



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
		public Builder setDrawer(String lineNumber,Integer drawerNum,Character letter){
			Device.this.setLineNumber(lineNumber);
			Device.this.setDrawerNum(drawerNum);
			Device.this.setDrawerLetter(letter);
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
		public Builder setActive(Boolean active){
			Device.this.setActive(active);
			return this;
		}
		public Builder setDescription(String description){
			Device.this.setDescription(description);
			return this;
		}
		public Builder setPlant(Plant plant){
			Device.this.setPlant(plant);
			return this;
		}
		public Builder setSwitchgear(Switchgear switchgear){
			Device.this.setSwitchgear(switchgear);
			return this;
		}
		public Device build(){
			return Device.this;
		}
	}

}

