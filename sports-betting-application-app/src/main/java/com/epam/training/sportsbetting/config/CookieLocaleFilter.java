package com.epam.training.sportsbetting.config;

import org.springframework.util.StringUtils;
import org.springframework.web.util.WebUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

@WebFilter(filterName = "CookieLocaleFilter", urlPatterns = {"/*"})
public class CookieLocaleFilter implements Filter {

    private static final String COOKIE_NAME = "localeInfo";
    private static final String LANG_PARAM_NAME = "lang";

    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        if (!req.getRequestURI().startsWith("/resources/")) {
            String lang = req.getParameter(LANG_PARAM_NAME);
            Locale locale = Locale.getDefault();
            Cookie cookie;
            if (lang != null) {
                locale = StringUtils.parseLocale(lang);
                cookie = new Cookie(COOKIE_NAME, lang);
                res.addCookie(cookie);
                for (int i = 0; i < req.getCookies().length; i++) {
                    if (req.getCookies()[i].getName().equals(COOKIE_NAME)) {
                        req.getCookies()[i].setValue(lang);
                    }
                }
            } else if ((cookie = WebUtils.getCookie(req, COOKIE_NAME)) != null) {
                locale = StringUtils.parseLocale(cookie.getValue());
            }
            response.setLocale(locale);
        }
        chain.doFilter(request, response);
    }

    public void destroy() {
    }

    public void init(FilterConfig arg0) throws ServletException {
    }

}