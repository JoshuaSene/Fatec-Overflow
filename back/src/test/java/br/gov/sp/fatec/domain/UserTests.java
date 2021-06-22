package br.gov.sp.fatec.domain;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import br.gov.sp.fatec.domain.enums.Role;
import br.gov.sp.fatec.domain.model.Post;
import br.gov.sp.fatec.domain.model.UserEntity;
import br.gov.sp.fatec.domain.repository.UserRepository;

@SpringBootTest
@Transactional
@Rollback
public class UserTests {

    @Autowired
    private UserRepository userRepo;

    final String    USERNAME_1  = "#TESTE_USU_1";
    final String    EMAIL_1     = "@teste_usuario_1@saloon.br";
    final String    NOME_1      = "#NOME_1_USUARIO";
    final String    NOME_2      = "#NOME_2_USUARIO";
    final String    SENHA_1     = "$enhaF0rt3";
    final Role      REGRA_1     = Role.ROLE_STUDENT; 

    @Test
	void testeUserIncluir() {
        userRepo.save(this.criaUsuario());
        assertTrue(userRepo.existsByUsername(USERNAME_1));
    }
    
	@Test
    void testeUserAlterar() {
        UserEntity usu = userRepo.save(this.criaUsuario());
        assertFalse(userRepo.findByNameContainsIgnoreCase(NOME_2).size() > 0);
        usu.setName(NOME_2);
        userRepo.save(usu);
        userRepo.flush();
        assertTrue(userRepo.findByNameContainsIgnoreCase(NOME_2).size() > 0);
    }

    @Test
	void testeUserDelete() {
    	UserEntity usu = userRepo.save(this.criaUsuario());
    	assertTrue(userRepo.existsByUsername(USERNAME_1));
        userRepo.delete(usu);
        assertFalse(userRepo.existsByUsername(USERNAME_1));
    }
    
    @Test
    void testeUserPesquisas() {
    	Integer qtd = userRepo.findAll().size();
    	assertTrue( userRepo.findByNameContainsIgnoreCase("_1_").size() == 0);
    	userRepo.save(this.criaUsuario());
    	assertTrue(userRepo.findByNameContainsIgnoreCase("_1_").size() > 0);
    	assertTrue(userRepo.findAll().size() == (qtd+1));
    			
    }
    
    @Test
	void testeUserGets() {
        UserEntity usu = new UserEntity();
        assertTrue(usu.hashCode() > 0);
        
        usu = userRepo.save(this.criaUsuario());
        assertTrue(userRepo.existsByUsername(USERNAME_1));
        assertEquals(1, usu.getRegister());
        assertEquals(NOME_1, usu.getName());
        assertEquals(SENHA_1, usu.getPassword());
        assertNotNull(usu.toString());
        assertEquals(USERNAME_1, usu.getUsername());
        assertEquals(REGRA_1, usu.getRole());
        
        HashSet<Post> posts = new HashSet<Post>();
        usu.setPosts(posts);
        assertNotNull(usu.hashCode());
        
        assertTrue(usu.equals(usu));
        assertFalse(usu.equals(null));
        assertFalse(usu.equals(posts));
        
        UserEntity usu2 = new UserEntity();
        assertFalse(usu.equals(usu2));
        assertFalse(usu2.equals(usu));

        usu2.setId(9999999L);
        assertFalse(usu.equals(usu2));
        assertFalse(usu2.equals(usu));
        
        usu2.setId(usu.getId());
        assertFalse(usu.equals(usu2));
        assertFalse(usu2.equals(usu));
        
        usu = this.criaUsuario();
        usu.setPassword(null);
        usu2 = new UserEntity();
        usu2.setPassword("123");
        assertFalse(usu.equals(usu2));
        assertFalse(usu2.equals(usu));
        
        posts = new HashSet<Post>();
        usu.setPosts(posts);
        assertEquals(0,usu.getPosts().size());
        
        
    }

    /*
     * Método padrão de criação de uma entidade completa para testes.
     */
    public UserEntity criaUsuario(){
    	
    	UserEntity usu = new UserEntity();
    	
    	usu.setRegister(1);
    	usu.setName(NOME_1);
    	usu.setUsername(USERNAME_1);
    	usu.setPassword("$enhaF0rt3");
    	usu.setRole(REGRA_1);

    	return usu;
    	
    }

}