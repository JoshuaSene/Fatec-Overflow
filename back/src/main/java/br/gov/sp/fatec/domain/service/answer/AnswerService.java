package br.gov.sp.fatec.domain.service.answer;

import java.util.Set;

import br.gov.sp.fatec.domain.model.Answer;

public interface AnswerService {
	public Set<Answer> findByAuthorId(Long authorId);
	public Answer findById(Long id);
	public Set<Answer> findByPostId(Long postId);
	public Answer save(Answer answer);
	public void delete(Answer answer);
	public Answer update(Answer answer, Long id);
	public Set<Answer> findByAuthorIdAndPostId(Long authorId, Long postId);
}
