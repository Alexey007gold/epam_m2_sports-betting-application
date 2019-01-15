package com.epam.training.sportsbetting.service.impl;

import com.epam.training.sportsbetting.ExtendedUserDetails;
import com.epam.training.sportsbetting.domain.user.User;
import com.epam.training.sportsbetting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public MyUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userService.getUserByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException(email));
        return getAccountDetails(user);
    }

    private ExtendedUserDetails getAccountDetails(User player) {
        return ExtendedUserDetails
            .builder()
            .id(player.getId())
            .username(player.getEmail())
            .password(new String(player.getPassword()))
            .authorities(Collections.emptyList())
            .isAccountNonExpired(true)
            .isAccountNonLocked(true)
            .isCredentialsNonExpired(true)
            .isEnabled(true)
            .build();
    }
}