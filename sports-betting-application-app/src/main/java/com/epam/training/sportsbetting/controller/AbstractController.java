package com.epam.training.sportsbetting.controller;

import com.epam.training.sportsbetting.ExtendedUserDetails;
import org.springframework.security.core.Authentication;

public class AbstractController {

    protected void checkForRole(Authentication authentication, String role) {
        ((ExtendedUserDetails) authentication.getPrincipal()).getAuthorities().stream()
                .filter(a -> a.getAuthority().equals(role))
                .findAny()
                .orElseThrow((() -> new IllegalStateException(String.format("You don't have role %s", role))));
    }
}
