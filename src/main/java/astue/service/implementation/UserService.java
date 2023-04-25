package astue.service.implementation;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import astue.model.User;
import astue.repository.UserRepository;
import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsManager{

private final	UserRepository repository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return repository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Not found: "+username));
	}
	@Override
	public void createUser(UserDetails user) {
		repository.save((User) user);
	}
	@Override
	public void updateUser(UserDetails user) {
		User newUser=repository.findById(((User) user).getId())
				.orElseThrow(() -> new UsernameNotFoundException("Not found: "+user.getUsername()));
		newUser.setUsername(user.getUsername());
		newUser.setPassword(user.getPassword());
		newUser.setActive(user.isEnabled());
		repository.save(newUser);
	}
	@Override
	public void deleteUser(String username) {
		User user=repository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Not found: "+username));
		repository.delete(user);
	}
	@Override
	public void changePassword(String oldPassword, String newPassword) {
		User newUser=repository.findByPassword(oldPassword).orElseThrow(() -> new UsernameNotFoundException("user with requested password wasn't found"));
		newUser.setPassword(newPassword);
		repository.save(newUser);
	}
	@Override
	public boolean userExists(String username) {
		return repository.findByUsername(username).isPresent();
	}
	
	public List<User> getAll() {
		return repository.findAll();
	}

}