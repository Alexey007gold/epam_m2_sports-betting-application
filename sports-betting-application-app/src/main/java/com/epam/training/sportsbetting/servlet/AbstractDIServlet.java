package com.epam.training.sportsbetting.servlet;

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
}
