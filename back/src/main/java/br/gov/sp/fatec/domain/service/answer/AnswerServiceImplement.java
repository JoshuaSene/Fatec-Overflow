package br.gov.sp.fatec.domain.service.answer;

import java.text.MessageFormat;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import br.gov.sp.fatec.domain.exception.AnswerNotFoundException;
import br.gov.sp.fatec.domain.model.Answer;
import br.gov.sp.fatec.domain.model.Post;
import br.gov.sp.fatec.domain.model.UserEntity;
import br.gov.sp.fatec.domain.repository.AnswerRepository;
import br.gov.sp.fatec.domain.service.post.PostService;
import br.gov.sp.fatec.domain.service.user.UserService;

@Service
public class AnswerServiceImplement implements AnswerService {

	@Autowired
	private AnswerRepository repository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PostService postService;
	
	@Override
	@PreAuthorize("isAuthenticated()")
	public Set<Answer> findByAuthorId(Long authorId) {
		UserEntity author = userService.findById(authorId);
		Set<Answer> answers = author.getAnswers();
		return answers;
	}

	@Override
	@PreAuthorize("isAuthenticated()")
	public Answer findById(Long id) {
		Optional<Answer> answers = repository.findById(id);
		
		if(answers.isPresent()) {
			return answers.get();
		}
		
		throw new AnswerNotFoundException(
			MessageFormat.format("Não foi encontrada resposta com id {0}", id)
		);
	}

	@Override
	@PreAuthorize("isAuthenticated()")
	public Set<Answer> findByPostId(Long postId) {
		Post post = postService.findById(postId);
		Post foundPost = post;
		Set<Answer> answers = foundPost.getAnswers();

		return answers;
	}

	@Override
	@PreAuthorize("isAuthenticated()")
	public Answer save(Answer answer) {
		return repository.save(answer);
	}

	@Override
	@PreAuthorize("isAuthenticated()")
	public void delete(Answer answer) {
		repository.delete(answer);
	}

	@Override
	@PreAuthorize("isAuthenticated()")
	public Answer update(Answer answer, Long id) {
		Answer foundAnswer = findById(id);
		Answer newAnswer = setValues(foundAnswer, answer);
		newAnswer = save(newAnswer);
		return newAnswer;
	}
	
	@Override
	@PreAuthorize("isAuthenticated()")
	public Set<Answer> findByAuthorIdAndPostId(Long authorId, Long postId) {
		Optional<Set<Answer>> answers = repository.findByAuthorIdAndPostId(authorId, postId);
		
		if(answers.isPresent()) {
			return answers.get();
		}
		
		throw new AnswerNotFoundException(
			MessageFormat.format("Não foram encontradas respostas com id de autor {0} e id de post {1}", 
					authorId, postId)
		);
	}

	private Answer setValues(Answer oldAnswer, Answer newAnswer) {
		oldAnswer.setContent(newAnswer.getContent());
    	return oldAnswer;
    }
}
