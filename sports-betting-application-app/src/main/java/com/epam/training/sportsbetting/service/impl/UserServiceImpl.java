package com.epam.training.sportsbetting.service.impl;

import com.epam.training.sportsbetting.domain.user.Currency;
import com.epam.training.sportsbetting.domain.user.Player;
import com.epam.training.sportsbetting.domain.user.User;
import com.epam.training.sportsbetting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private List<User> userList;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        userList = new ArrayList<>();
        userList.add(new Player.Builder()
            .withId(0)
            .withEmail("arnold@gmail.com")
            .withPassword(passwordEncoder.encode("1234").toCharArray())
            .withAccountNumber("1234")
            .withBalance(1000)
            .withCurrency(Currency.EUR)
            .withName("Arnold Schwarzenegger")
            .withBirthDate(LocalDate.of(1941, 1, 2))
            .build());
        userList.add(new Player.Builder()
            .withId(1)
            .withEmail("john@gmail.com")
            .withPassword(passwordEncoder.encode("1234").toCharArray())
            .withAccountNumber("12345")
            .withBalance(10500)
            .withCurrency(Currency.EUR)
            .withName("John Doe")
            .withBirthDate(LocalDate.of(1945, 10, 21))
            .build());
    }

    @Override
    public User saveUser(User user) {
        if (user.getId() == null) {
            user.setId(userList.size());
        }
        userList.add(user.getId(), user);
        return user;
    }

    @Override
    public Optional<User> getUserById(Integer id) {
        if (userList.size() >= id) {
            return Optional.ofNullable(userList.get(id));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        for (User user : userList) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    @Override
    public User updateUserById(User user) {
        if (userList.size() >= user.getId()) {
            userList.set(user.getId(), user);
            return user;
        } else {
            throw new IllegalStateException(String.format("User with id %d not found", user.getId()));
        }
    }
}
