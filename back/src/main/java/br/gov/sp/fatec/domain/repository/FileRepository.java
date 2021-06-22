package br.gov.sp.fatec.domain.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.sp.fatec.domain.model.File;

public interface FileRepository extends JpaRepository<File, Long> {
	
	public Optional<Set<File>> findByPostId(Long postId);

	public boolean existsByUrl(String url);
	
}
