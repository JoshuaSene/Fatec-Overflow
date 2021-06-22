package br.gov.sp.fatec.controller;

import java.text.MessageFormat;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonView;

import br.gov.sp.fatec.domain.dto.UserDTO;
import br.gov.sp.fatec.domain.model.UserEntity;
import br.gov.sp.fatec.domain.service.user.UserService;
import br.gov.sp.fatec.domain.view.View;

@RestController
@RequestMapping(value = "user")
@CrossOrigin
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    @JsonView(value = View.User.Detail.class)
    public ResponseEntity<List<UserDTO>> getUsers(){
        List<UserEntity> users = userService.findAll();
        return ResponseEntity.ok(UserDTO.convertToList(users));
    }

    @GetMapping(value = "/{id}")
    @JsonView(value = View.User.Detail.class)
    public ResponseEntity<UserDTO> getUserDetails(@PathVariable Long id){
        UserEntity user = userService.findById(id);
        return ResponseEntity.ok(new UserDTO(user));
    }

    @PostMapping
    @JsonView(value = View.User.Detail.class)
    public ResponseEntity<UserDTO> saveUser(
    		@Valid @RequestBody @JsonView(value = View.User.Create.class) UserDTO user,
    		UriComponentsBuilder builder
    	) {
        UserEntity savedUser = userService.save(new UserEntity(user));
        
        HttpHeaders headers = new HttpHeaders();
    	headers.setLocation(
    		builder.path(MessageFormat.format("/user/{0}", savedUser.getId())).build().toUri()
    	);
    	return new ResponseEntity<UserDTO>(new UserDTO(savedUser), headers, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
    	UserEntity user = userService.findById(id);
    	userService.delete(user);
    	return ResponseEntity.status(HttpStatus.OK).body("Usuário excluído com sucesso!");
    }

    @PutMapping(value = "/{id}")
    @JsonView(value = View.User.Detail.class)
    public ResponseEntity<UserDTO> updateUser(
		@Valid @RequestBody @JsonView(value = View.User.Update.class) UserDTO user, 
		@PathVariable Long id
    ) {
    	UserEntity updatedUser = userService.update(new UserEntity(user), id);
    	
    	return ResponseEntity.ok(new UserDTO(updatedUser));
    }
}
