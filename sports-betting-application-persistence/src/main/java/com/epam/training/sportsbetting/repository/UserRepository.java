package com.epam.training.sportsbetting.repository;

import com.epam.training.sportsbetting.entity.UserEntity;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface UserRepository<T extends UserEntity> extends AbstractRepository<T> {

    Optional<T> findByEmail(String email);

}
