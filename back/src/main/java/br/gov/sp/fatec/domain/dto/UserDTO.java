package br.gov.sp.fatec.domain.dto;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonView;

import br.gov.sp.fatec.domain.model.UserEntity;
import br.gov.sp.fatec.domain.view.View;

public class UserDTO {

	@JsonView(value = View.User.Detail.class)
	private Long id;
    
	@Max(999999)
	@JsonView(value = View.User.Create.class)
    private Integer register;
    
	@NotBlank
	@Size(min = 3, max = 40)
	@JsonView(value = {
		View.User.Create.class, 
		View.User.Update.class
	})
    private String name;
    
	@NotBlank
	@Size(min = 3, max = 30)
	@JsonView(value = {
		View.User.Create.class, 
		View.User.Update.class
	})
    private String username;
    
	@NotBlank
	@Size(min = 3, max = 30)
	@JsonView(value = {
		View.User.Create.class, 
		View.User.Update.class
	})
    private String password;
    
	@NotBlank
	@Size(min = 3, max = 20)
	@JsonView(value = {
		View.User.Create.class, 
		View.User.Update.class
	})
    private String role;

	public UserDTO() {}
	
	public UserDTO(Long id, Integer register, String name, 
			String username, String password, String role) {
		this.id = id;
		this.register = register;
		this.name = name;
		this.username = username;
		this.password = password;
		this.role = role;
	}
    
	public UserDTO(UserEntity user) {
		this.id = user.getId();
		this.register = user.getRegister();
		this.name = user.getName();
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.role = user.getRole().toString();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getRegister() {
		return register;
	}

	public void setRegister(Integer register) {
		this.register = register;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	public static List<UserDTO> convertToList(List<UserEntity> users) {
		return users.stream().map(UserDTO::new).collect(Collectors.toList());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((register == null) ? 0 : register.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDTO other = (UserDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (register == null) {
			if (other.register != null)
				return false;
		} else if (!register.equals(other.register))
			return false;
		if (role == null) {
			if (other.role != null)
				return false;
		} else if (!role.equals(other.role))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UserDTO [id=" + id + ", register=" + register + ", name=" + name + ", username=" + username
				+ ", password=" + password + ", role=" + role + "]";
	}
}
