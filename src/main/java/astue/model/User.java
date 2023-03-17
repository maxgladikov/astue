package astue.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.SimpleRouteMatcher;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="users")//, schema="PUBLIC")
public class User implements UserDetails{

	private static final long serialVersionUID = 6083592369366986703L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	@Column(unique=true)
	@NotBlank(message="Name is required")
	private  String username;
	@NotBlank(message="Password is required")
	private  String password;
	@Enumerated(EnumType.ORDINAL)
//	@NotBlank(message="Role is required")
	private  Role role;
	private  Boolean active;
	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(role.name()));
	}
	@Override
	public String getUsername() {return username;}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {return true;}
	@Override
	public boolean isEnabled() {
		return active;
	}
	@Override
	public String getPassword() {
		return password;
	}

}




	
	
