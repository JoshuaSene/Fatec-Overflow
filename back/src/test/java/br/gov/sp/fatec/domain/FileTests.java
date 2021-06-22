package br.gov.sp.fatec.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import br.gov.sp.fatec.domain.dto.FileDTO;
import br.gov.sp.fatec.domain.enums.Role;
import br.gov.sp.fatec.domain.model.File;
import br.gov.sp.fatec.domain.model.Post;
import br.gov.sp.fatec.domain.model.UserEntity;
import br.gov.sp.fatec.domain.repository.FileRepository;
import br.gov.sp.fatec.domain.repository.PostRepository;
import br.gov.sp.fatec.domain.repository.UserRepository;

@SpringBootTest
@Transactional
@Rollback
public class FileTests {

    final String    URL_1   = "www.fatecoverflow.com.br/files/arqteste001.txt";
    final String    URL_2   = "www.fatecoverflow.com.br/files/arqteste002.txt";
    final String    NAME_1  = "#Arquivo de teste do FatecOverFlow";

    @Autowired
    private UserRepository	userRepo;

    @Autowired
    private FileRepository fileRepo;
    
    @Autowired
    private PostRepository postRepo;
    
    @Test
	void testeFileIncluir() {
        fileRepo.save(this.criaFile());
        assertTrue(fileRepo.existsByUrl(URL_1));
    }

	@Test
    void testeFileAlterar() {

		File file = fileRepo.save(this.criaFile());
        assertFalse(fileRepo.existsByUrl(URL_2));
        file.setUrl(URL_2);
        fileRepo.save(file);
        fileRepo.flush();
        assertTrue(fileRepo.existsByUrl(URL_2));
        
    }

    @Test
	void testeFileDelete() {
    	
		File file = fileRepo.save(this.criaFile());
        assertTrue(fileRepo.existsByUrl(URL_1));
        fileRepo.delete(file);
        assertFalse(fileRepo.existsByUrl(URL_1));
    }

    @Test
    void testeFilePesquisas() {

		File file = fileRepo.save(this.criaFile());
    	Optional<Set<File>> files = fileRepo.findByPostId(file.getId());
    	assertNotNull(files.get());
    	assertEquals(files.get().size(), 0);


    }

    @Test
	void testeFileGets() {
    	
    	Post post = this.criaPost();
    	
    	File file = new File(URL_1, NAME_1, post);
    	
    	file = fileRepo.save(file);
    	
        assertNotNull(file.getId());
        assertEquals(NAME_1, file.getName());
        assertEquals(post, file.getPost());
        assertEquals(URL_1, file.getUrl());
        
    }

    /*
     * Método padrão de criação de uma entidade completa para testes.
     */
    private Post criaPost() {
    	
    	UserEntity usu = new UserEntity();
    	usu.setRegister(1);
    	usu.setName("#USU_TEMP_FILE");
    	usu.setUsername("#USU_FILE");
    	usu.setPassword("#pwUsuFile");
    	usu.setRole(Role.ROLE_STUDENT);
    	userRepo.save(usu);

    	Post post = new Post();
    	post.setTitle("#POST_FILE");
    	post.setAuthor(usu);
    	post.setContent("#Novo arquivo texto.");
    	return postRepo.save(post);

    }
    
    public File criaFile(){
    	
    	Post post = this.criaPost();
    	
    	FileDTO dto = new FileDTO();
    	dto.setUrl(URL_1);
    	dto.setName(NAME_1);
    	dto.setPost(post.getId());
    	
    	File file = new File( dto , post );
    	
    	file.setId(null);
    	
    	return file;
    	
    }

}

