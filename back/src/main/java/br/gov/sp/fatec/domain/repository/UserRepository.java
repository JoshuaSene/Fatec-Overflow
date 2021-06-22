package br.gov.sp.fatec.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.gov.sp.fatec.domain.model.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

	public Optional<UserEntity> findById(Long id);

	public Optional<UserEntity> findByUsername(String username);

	public List<UserEntity> findByNameContainsIgnoreCase(String name);
	
	public boolean existsByUsername(String username);

	@Query("select u from UserEntity u where u.id=?1")
	public UserEntity buscarPorId(Long id);

    public List<UserEntity> findAll();

}
