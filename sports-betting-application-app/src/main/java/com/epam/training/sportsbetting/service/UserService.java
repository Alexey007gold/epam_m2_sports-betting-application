package com.epam.training.sportsbetting.service;

import com.epam.training.sportsbetting.domain.user.User;

import java.util.Optional;

public interface UserService {

    User saveUser(User user);

    Optional<User> getUserById(Integer id);

    Optional<User> getUserByEmail(String email);

    User updateUserById(User user);
}
