package br.gov.sp.fatec.domain.service.security;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.gov.sp.fatec.domain.model.UserEntity;
import br.gov.sp.fatec.domain.service.user.UserService;

@Service("UserSecurityService")
public class UserSecurityServiceImpl implements UserSecurityService {

	@Autowired
	private UserService userService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserEntity> user = userService.findByUsername(username);
		
		if(!user.isPresent()) {
			throw new UsernameNotFoundException(
					MessageFormat.format("Não foi encontrado um usuário com o username {0}", username)
			);
		}
		
		UserEntity foundUser = user.get(); 
		
		return User
				.builder()
					.username(username)
					.password(foundUser.getPassword())
					.authorities(
						(String[]) Arrays.asList(foundUser.getRole().toString())
										 .toArray(new String[1])
					).build();
	}

}
