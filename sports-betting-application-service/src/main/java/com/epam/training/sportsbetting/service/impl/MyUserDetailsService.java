package com.epam.training.sportsbetting.service.impl;

import com.epam.training.sportsbetting.ExtendedUserDetails;
import com.epam.training.sportsbetting.domain.user.Admin;
import com.epam.training.sportsbetting.domain.user.Player;
import com.epam.training.sportsbetting.domain.user.User;
import com.epam.training.sportsbetting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static com.epam.training.sportsbetting.Role.ROLE_ADMIN;
import static com.epam.training.sportsbetting.Role.ROLE_PLAYER;

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

    private ExtendedUserDetails getAccountDetails(User user) {
        return ExtendedUserDetails
            .builder()
            .id(user.getId())
            .username(user.getEmail())
            .password(new String(user.getPassword()))
            .authorities(getGrantedAuthorities(user))
            .isAccountNonExpired(true)
            .isAccountNonLocked(true)
            .isCredentialsNonExpired(true)
            .isEnabled(true)
            .build();
    }

    private List<GrantedAuthority> getGrantedAuthorities(User user) {
        List<GrantedAuthority> authorities;
        if (user instanceof Player) {
            authorities = Collections.singletonList(new SimpleGrantedAuthority(ROLE_PLAYER));
        } else if (user instanceof Admin) {
            authorities = Collections.singletonList(new SimpleGrantedAuthority(ROLE_ADMIN));
        } else {
            throw new IllegalStateException("Unknown user type");
        }
        return authorities;
    }
}