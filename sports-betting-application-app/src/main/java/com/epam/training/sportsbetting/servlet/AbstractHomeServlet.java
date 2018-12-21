package com.epam.training.sportsbetting.servlet;

import com.epam.training.sportsbetting.domain.user.Currency;
import com.epam.training.sportsbetting.domain.user.Player;
import com.epam.training.sportsbetting.service.WagerService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class AbstractHomeServlet extends AbstractDIServlet {

    private static final String USER_KEY = "user";
    private static final String WAGERS_KEY = "wagers";
    private static final String CURRENCY_OPTIONS = "currencyOptions";

    @Autowired
    private WagerService wagerService;

    protected void passToHomePage(HttpServletRequest req, HttpServletResponse resp, Player player) throws ServletException, IOException {
        req.setAttribute(USER_KEY, player);
        req.setAttribute(WAGERS_KEY, wagerService.getWagersByPlayerId(player.getId()));
        req.setAttribute(CURRENCY_OPTIONS, Currency.values());

        req.getRequestDispatcher("/views/jsp/home.jsp").forward(req, resp);
    }
}
