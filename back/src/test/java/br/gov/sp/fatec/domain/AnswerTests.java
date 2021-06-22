package br.gov.sp.fatec.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import br.gov.sp.fatec.domain.dto.AnswerDTO;
import br.gov.sp.fatec.domain.dto.PostDTO;
import br.gov.sp.fatec.domain.dto.UserDTO;
import br.gov.sp.fatec.domain.model.Answer;
import br.gov.sp.fatec.domain.model.Post;
import br.gov.sp.fatec.domain.model.UserEntity;
import br.gov.sp.fatec.domain.repository.AnswerRepository;
import br.gov.sp.fatec.domain.repository.PostRepository;
import br.gov.sp.fatec.domain.repository.UserRepository;
import br.gov.sp.fatec.domain.service.answer.AnswerService;
import br.gov.sp.fatec.domain.service.post.PostService;
import br.gov.sp.fatec.domain.service.user.UserService;
import br.gov.sp.fatec.helper.TestsHelper;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
class AnswerTests {
	
	@Autowired
	private AnswerService service;
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PostRepository postRepo;
	
	@Autowired
	private AnswerRepository ansRepo;
	
	@BeforeAll
	void createAll() {
		UserDTO userDto = TestsHelper.getDTOForUserCreation();
		UserEntity user = new UserEntity(userDto);
		user = userRepo.save(user);
		
		PostDTO dto = TestsHelper.getDTOForPostCreation();
		Post post = new Post(dto);
		post.setAuthor(user);
		post = postRepo.save(post);
		
		AnswerDTO ansDto = TestsHelper.getDTOForAnswerCreation();
		Answer answer = new Answer(ansDto, user, post);
		answer = ansRepo.save(answer);
		
		post.getAnswers().add(answer);
		postRepo.save(post);
	}

	@Test
	@Transactional
	@WithMockUser(username="administrator",roles={"ADMIN"})
	void insertTest() {	
		Post post = postService.findById(1L);
		assertNotNull(post);
		
		UserEntity author = userService.findById(1L);
		assertNotNull(author);
		
		AnswerDTO dto = TestsHelper.getDTOForAnswerCreation();
		Answer answer = new Answer(dto, author, post);
		
		assertNotNull(service.save(answer));
		assertEquals(3, author.getAnswers().size());
	}

	@Test
	@WithMockUser(username="administrator",roles={"ADMIN"})
	void listTest() {
		Set<Answer> answers = service.findByAuthorIdAndPostId(2L, 2L);
		assertNotNull(answers);
		assertEquals(0, answers.size());
		
		assertNotEquals(1l, answers.size());
	}
	
	@Test
	@WithMockUser(username="administrator",roles={"ADMIN"})
	void deleteTest() {
		Post post = postService.findById(1L);
		UserEntity author = userService.findById(1L);
		AnswerDTO dto = TestsHelper.getDTOForAnswerCreation();
		Answer answer = new Answer(dto, author, post);
		answer = service.save(answer);
		
		assertNotNull(answer);
		service.delete(answer);		
	}
	
	@Test
	@WithMockUser(username="administrator",roles={"ADMIN"})
	void updateTest() {
		Post post = postService.findById(1L);
		UserEntity author = userService.findById(1L);
		
		AnswerDTO dto = TestsHelper.getDTOForAnswerCreation();
		Answer answer = new Answer(dto, author, post);
		
		assertEquals("Exemplificando resposta", answer.getContent());
		answer = service.save(answer);
		
		answer = updateAnswer(answer);
		assertNotEquals("Exemplificando resposta", answer.getContent());
		assertNotEquals(LocalDate.of(2022, 04, 10), answer.getDate());
	}
	
	private Answer updateAnswer(Answer answer) {
		answer.setContent("Conteúdo atualizado");
		answer.setDate(LocalDate.now());
		
		return answer;
	}
}
