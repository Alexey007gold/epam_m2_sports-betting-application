package com.epam.training.sportsbetting.service.impl;

import com.epam.training.sportsbetting.ExtendedUserDetails;
import com.epam.training.sportsbetting.domain.user.Player;
import com.epam.training.sportsbetting.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final PlayerService playerService;

    @Autowired
    public MyUserDetailsService(PlayerService playerService) {
        this.playerService = playerService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        Player player = playerService.getPlayerByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException(email));
        return getAccountDetails(player, Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
    }

    private ExtendedUserDetails getAccountDetails(Player player, List<GrantedAuthority> authorities) {
        return ExtendedUserDetails
            .builder()
            .id(player.getId())
            .username(player.getEmail())
            .password(new String(player.getPassword()))
            .authorities(authorities)
            .isAccountNonExpired(true)
            .isAccountNonLocked(true)
            .isCredentialsNonExpired(true)
            .isEnabled(true)
            .build();
    }
}