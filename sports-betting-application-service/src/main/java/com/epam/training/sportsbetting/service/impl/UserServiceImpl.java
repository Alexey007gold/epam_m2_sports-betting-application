package com.epam.training.sportsbetting.service.impl;

import com.epam.training.sportsbetting.domain.user.Admin;
import com.epam.training.sportsbetting.domain.user.Player;
import com.epam.training.sportsbetting.domain.user.User;
import com.epam.training.sportsbetting.entity.AdminEntity;
import com.epam.training.sportsbetting.entity.PlayerEntity;
import com.epam.training.sportsbetting.entity.UserEntity;
import com.epam.training.sportsbetting.repository.UserRepository;
import com.epam.training.sportsbetting.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private List<UserRepository<? extends UserEntity>> userRepositories;

    private ModelMapper mapper;

    @Autowired
    public UserServiceImpl(List<UserRepository<? extends UserEntity>> userRepositories, ModelMapper mapper) {
        this.userRepositories = userRepositories;
        this.mapper = mapper;
    }

    @Override
    public Optional<User> getUserById(Integer id) {
        UserEntity userEntity = userRepositories.parallelStream().map(r -> r.findById(id)).findFirst().orElseThrow().orElseThrow();
        return Optional.of(mapper.map(userEntity, User.class));
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        UserEntity userEntity = userRepositories.parallelStream()
                .map(r -> r.findByEmail(email))
                .flatMap(Optional::stream)
                .filter(Objects::nonNull)
                .findAny().orElseThrow();
        return mapToUser(userEntity);
    }

    private Optional<User> mapToUser(UserEntity userEntity) {
        if (userEntity instanceof PlayerEntity) {
            return Optional.of(mapper.map(userEntity, Player.class));
        } else if (userEntity instanceof AdminEntity) {
            return Optional.of(mapper.map(userEntity, Admin.class));
        }
        throw new IllegalStateException("Unknown user type");
    }
}
