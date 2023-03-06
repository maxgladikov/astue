package astue.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="user", schema="PUBLIC")
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
	@NotBlank(message="Name is required")
	private  String name;
	@NotBlank(message="Password is required")
	private  String password;
	private  String roles;
	private  Boolean active;
	private  String activeString;

}




	
	
