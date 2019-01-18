package com.epam.training.sportsbetting.service.impl;

import com.epam.training.sportsbetting.entity.AdminEntity;
import com.epam.training.sportsbetting.repository.AdminRepository;
import com.epam.training.sportsbetting.service.AdminService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService, InitializingBean {

    private final AdminRepository adminRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public AdminServiceImpl(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        loadAdmins();
    }

    private void loadAdmins() throws URISyntaxException, IOException {
        List<String> lines = Files.readAllLines(Paths.get(this.getClass().getClassLoader().getResource("admins.tsv").toURI()));

        for (String line : lines) {
            try {
                String[] values = line.split("\t");

                AdminEntity admin = AdminEntity.builder()
                        .enabled(true)
                        .email(values[0])
                        .password(passwordEncoder.encode(values[1]).toCharArray())
                        .name(values[2])
                        .build();
                adminRepository.save(admin);
            } catch (DataIntegrityViolationException e) {
                //already present
            }
        }
    }
}
