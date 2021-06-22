package br.gov.sp.fatec.domain.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.sp.fatec.domain.model.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
	public Optional<Set<Answer>> findByAuthorIdAndPostId(Long authorId, Long postId);
}
