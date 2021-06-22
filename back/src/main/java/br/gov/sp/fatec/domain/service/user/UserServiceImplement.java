package br.gov.sp.fatec.domain.service.user;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.gov.sp.fatec.domain.exception.UserNotFoundException;
import br.gov.sp.fatec.domain.model.UserEntity;
import br.gov.sp.fatec.domain.repository.UserRepository;

@Service
public class UserServiceImplement implements UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passEncoder;

    @Override
    @PreAuthorize("hasAnyRole('ROLE_TEACHER','ROLE_ADM')")
    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public UserEntity findById(Long id) {
        Optional<UserEntity> user = userRepository.findById(id);
        if(user.isPresent()) {        	
        	return user.get();
        }
        
        throw new UserNotFoundException(
    		MessageFormat.format("Não foi encontrado um usuário com o id {0}", id)
        );
    }

    @Override
    public UserEntity save(UserEntity user) {
    	user.setPassword(passEncoder.encode(user.getPassword()));
    	return userRepository.save(user);
    }

    @Override
    @PreAuthorize("hasAnyRole('ROLE_TEACHER','ROLE_ADM')")
    public void delete(UserEntity user) {
        userRepository.delete(user);
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public UserEntity update(UserEntity user, Long id) {
    	UserEntity foundUser = findById(id);
    	
    	UserEntity newUser = setValues(foundUser, user);
    	newUser = save(newUser);
    	return newUser;
    }
    
    @Override
    public Optional<UserEntity> findByUsername(String username) {
    	return userRepository.findByUsername(username);
    }
    
    private UserEntity setValues(UserEntity oldUser, UserEntity newUser) {
    	oldUser.setName(newUser.getName());
    	oldUser.setRole(newUser.getRole());
    	oldUser.setUsername(newUser.getUsername());
    	oldUser.setPassword(newUser.getPassword());
    	return oldUser;
    }
}
