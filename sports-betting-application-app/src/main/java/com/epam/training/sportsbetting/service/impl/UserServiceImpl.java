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
        this.userMap = new HashMap<>();
    }

    public void loadUsers() throws URISyntaxException, IOException {
        List<String> lines = Files.readAllLines(Paths.get(this.getClass().getClassLoader().getResource("users.tsv").toURI()));

        for (String line : lines) {
            String[] values = line.split("\t");
            userMap.put(userId.incrementAndGet(), new Player.Builder()
                .withId(userId.get())
                .withEmail(values[0])
                .withPassword(passwordEncoder.encode(values[1]).toCharArray())
                .withAccountNumber(values[2])
                .withBalance(Double.parseDouble(values[3]))
                .withCurrency(Currency.valueOf(values[4]))
                .withName(values[5])
                .withBirthDate(LocalDate.parse(values[6]))
                .build());
        }
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
        loadUsers();
    }
}
