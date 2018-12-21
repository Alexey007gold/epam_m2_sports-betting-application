package com.epam.training.sportsbetting.service.impl;

import com.epam.training.sportsbetting.domain.user.Currency;
import com.epam.training.sportsbetting.domain.user.Player;
import com.epam.training.sportsbetting.domain.user.User;
import com.epam.training.sportsbetting.form.UpdateUserForm;
import com.epam.training.sportsbetting.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class UserServiceImpl implements UserService, InitializingBean {

    private Map<Integer, User> userMap;

    private PasswordEncoder passwordEncoder;

    private ModelMapper mapper;

    private AtomicInteger userId = new AtomicInteger();

    @Autowired
    public UserServiceImpl(PasswordEncoder passwordEncoder, ModelMapper mapper) {
        this.passwordEncoder = passwordEncoder;
        this.mapper = mapper;
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
            user.setId(userId.incrementAndGet());
        }
        userMap.put(user.getId(), user);
        return user;
    }

    @Override
    public Optional<User> getUserById(Integer id) {
        return Optional.ofNullable(userMap.get(id));
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userMap.values().stream().filter(u -> u.getEmail().equalsIgnoreCase(email)).findFirst();
    }

    @Override
    public User updateUserById(Integer id, UpdateUserForm form) {
        User user = userMap.get(id);
        if (user == null) {
            throw new IllegalStateException(String.format("User with id %d not found", id));
        }
        mapper.map(form, user);
        return user;
    }

    @Override
    public void afterPropertiesSet() throws IOException, URISyntaxException {
        //PostConstruct alternative for java 9+
        init();
    }
}
