package astue.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@MappedSuperclass
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1075550895985504661L;
	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Name is mandatory")
    @Column(unique=true)
    private String name;
    private String description;
	
    
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		// If the object is compared with itself then return true 
		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		
		if (getClass() != obj.getClass())
			return false;
		
		BaseEntity other = (BaseEntity) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
	
	
//	@Override
//	public boolean equals(Object obj) {
//		if ( obj.getClass().equals(this.getClass()) )
//			this.name.equals(obj.)
//		boolean same= ) 
//		if (this.name.equals(obj.))
//	}
    
    
    
}
