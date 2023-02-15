package astue.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
//import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


@Data
@NoArgsConstructor
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Ied extends BaseEntity implements Serializable {
    private Integer slaveId;
    private Integer address;
    private Integer quantity;
    private Integer scale;
    private String dataType;
    @OneToMany(mappedBy = "ied",fetch = FetchType.LAZY)
    @JsonManagedReference
    @JsonIgnore
    private List<Device> devices;

    @JsonCreator
    public Ied(String name,Integer slaveId, Integer address, Integer quantity, Integer scale, String dataType) {
        this.setName(name);
        this.slaveId = slaveId;
        this.address = address;
        this.quantity = quantity;
        this.scale = scale;
        this.dataType = dataType;
    }

    //Tesys
    //143-144 UDint Active, kWh
    //145-146 UDint Reactive, kVARh

    //F650 Scale 1000
    //0xF44 Positive active
    //0xF46 Negative active
    //0xF48 Positive reactive
    //0xF4A Negative reactive
}
