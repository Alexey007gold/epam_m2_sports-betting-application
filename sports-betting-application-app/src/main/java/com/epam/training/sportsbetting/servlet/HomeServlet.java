package com.epam.training.sportsbetting.servlet;

import com.epam.training.sportsbetting.ExtendedUserDetails;
import com.epam.training.sportsbetting.domain.user.Player;
import com.epam.training.sportsbetting.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@WebServlet(urlPatterns = {"", "/home"})
public class HomeServlet extends AbstractHomeServlet {

    @Autowired
    private PlayerService playerService;

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer userId = ((ExtendedUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        Player player = playerService.getPlayerById(userId)
            .orElseThrow(() -> new IllegalStateException("Player was not found"));

        passToHomePage(req, resp, player);
    }
}
