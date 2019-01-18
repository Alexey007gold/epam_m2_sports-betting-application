package com.epam.training.sportsbetting.servlet;

import com.epam.training.sportsbetting.ExtendedUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import static org.springframework.web.context.support.SpringBeanAutowiringSupport.processInjectionBasedOnServletContext;

/**
 * Abstract Servlet class that handles Dependency Injection
 */
public abstract class AbstractDIServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        processInjectionBasedOnServletContext(this, getServletContext());
    }

    protected void checkForRole(String role) {
        ((ExtendedUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .getAuthorities().stream()
                .filter(a -> a.getAuthority().equals(role))
                .findAny()
                .orElseThrow((() -> new IllegalStateException(String.format("You don't have role %s", role))));
    }
}
