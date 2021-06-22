package br.gov.sp.fatec.domain;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import br.gov.sp.fatec.domain.model.File;
import br.gov.sp.fatec.domain.model.Post;
import br.gov.sp.fatec.domain.model.UserEntity;
import br.gov.sp.fatec.domain.repository.PostRepository;
import br.gov.sp.fatec.domain.repository.UserRepository;

@SpringBootTest
@Transactional
@Rollback
public class PostTests {

    @Autowired
    private UserRepository	userRepo;

    @Autowired
    private PostRepository	postRepo;
    
    final String	TITULO_1	=	"#TESTE_TÍT_1";
    final String	TITULO_2	=	"#TESTE_TÍT_2";

    @Test
	void testePostIncluir() {
        postRepo.save(this.testeCriaPost());
        assertTrue(postRepo.findByTitle(TITULO_1).size() > 0);
    }

	@Test
    void testeUserAlterar() {
        Post post = postRepo.save(this.testeCriaPost());
        assertFalse(postRepo.findByTitleContainsIgnoreCase(TITULO_2.toLowerCase()).size() > 0);
        post.setTitle(TITULO_2);
        postRepo.flush();
        assertTrue(postRepo.findByTitleContainsIgnoreCase(TITULO_2.toLowerCase()).size() > 0);
    }

    @Test
	void testePostDelete() {
    	Post post = postRepo.save(this.testeCriaPost());
    	assertEquals(post.getTitle() , TITULO_1);
    	Long idPost = post.getId();
        postRepo.delete(post);
        postRepo.flush();
        assertFalse(postRepo.existsById(idPost));
    }

    @Test
    void testePostPesquisas() {
    	
    	String username;
    	Post post = postRepo.save(this.testeCriaPost());
    	username = post.getAuthor().getUsername();
    	assertEquals(1, postRepo.countForUsername(username));
    	assertEquals(1, postRepo.listaPostsPorUsername(username).size());
    			
    }

    /*
     * Método padrão de criação de um post completo para testes.
     */
    public Post testeCriaPost(){
    	
    	UserTests teste = new UserTests();
    	UserEntity usu = userRepo.save(teste.criaUsuario());
    	
    	Post post = new Post();
    	
    	post.setTitle(TITULO_1);
    	post.setAuthor(usu);
    	post.setContent("Quase sem querer (Renato Russo)");
    	
    	return post;
    	
    }
    
    @Test
    void testePostGets() {
        
    	Post post = new Post();
        assertTrue(post.hashCode() > 0);
        
    	post.setId(null);
    	post.setDate(null);
    	assertTrue(post.getId()==null);
    	assertTrue(post.getDate()==null);
    	
    	post.setContent("Teste conteúdo");
    	post.setDate(LocalDate.now());
    	assertEquals("Teste conteúdo", post.getContent());
    	assertTrue(post.getFiles().isEmpty());
    	
    	Set<File> files = new HashSet<File>();
    	post.setFiles(files);
    	
        assertTrue(post.hashCode() > 0);

    }
	
}





    
