package com.epam.training.sportsbetting.config;

import com.epam.training.sportsbetting.ExtendedUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.epam.training.sportsbetting.Role.ROLE_ADMIN;
import static com.epam.training.sportsbetting.Role.ROLE_PLAYER;

public class RedirectingAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res,
                                        Authentication authentication) throws IOException {
        List<GrantedAuthority> authorities = ((ExtendedUserDetails) authentication.getPrincipal()).getAuthorities();
        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().equals(ROLE_PLAYER))
                res.sendRedirect("home");
            else if (authority.getAuthority().equals(ROLE_ADMIN))
                res.sendRedirect("admin_home");
        }
    }
}
