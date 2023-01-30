package astue.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
@Table(name="USERS", schema="PUBLIC")
public class User {
	
	public User() {}
	public User(String name, String password, String roles) {
		super();
		this.name = name;
		this.password = password;
		this.roles = roles;
		active = true;
	}
	
	
	
	public User(String name, String password, String roles, Boolean active) {
		super();
		this.name = name;
		this.password = password;
		this.roles = roles;
		this.active = active;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	@Column(unique=true)
	@NotBlank(message="Street is required")
	private  String name;
	@NotBlank(message="Street is required")
	private  String password;
	private  String roles;
	private  Boolean active;
	private  String activeString;

}




	
	
