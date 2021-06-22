package br.gov.sp.fatec.domain.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.gov.sp.fatec.domain.dto.UserDTO;
import br.gov.sp.fatec.domain.enums.Role;
 
@Entity
@Table(name = "User")
public class UserEntity {
	
    @Id
    @Column(name = "id_user")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true)
    private Integer register;
    
    @Column(length = 40)
    private String name;
    
    @Column(length = 30)
    private String username;
    
    @Column(length = 100)
    private String password;
    
    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private Role role;
    
    @OneToMany(
    	mappedBy = "author",
		orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    private Set<Post> posts = new HashSet<Post>();;
    
    @OneToMany(
    	mappedBy = "author", 
		orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    private Set<Answer> answers = new HashSet<Answer>();;
    
    public UserEntity() {}
    
    public UserEntity(UserDTO dto) {
    	this.register = dto.getRegister();
    	this.name = dto.getName();
    	this.username = dto.getUsername();
    	this.password = dto.getPassword();
    	this.role = Role.valueOf(dto.getRole().toUpperCase());
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

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	public Set<Post> getPosts() {
		return posts;
	}
	
	public void setPosts(Set<Post> posts) {
		this.posts = posts;
	}

	public Set<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(Set<Answer> answers) {
		this.answers = answers;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", register=" + register + ", name=" + name + ", username=" + username + ", password="
				+ password + ", role=" + role + "]";
	}
	
}
