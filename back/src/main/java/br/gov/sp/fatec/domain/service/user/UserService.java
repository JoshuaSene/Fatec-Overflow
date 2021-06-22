package br.gov.sp.fatec.domain.service.user;

import java.util.List;
import java.util.Optional;

import br.gov.sp.fatec.domain.model.UserEntity;

public interface UserService {
    public List<UserEntity> findAll();
    public UserEntity findById(Long id);
    public Optional<UserEntity> findByUsername(String username);
    public UserEntity save(UserEntity user);
    public void delete(UserEntity user);
    public UserEntity update(UserEntity user, Long id);
}