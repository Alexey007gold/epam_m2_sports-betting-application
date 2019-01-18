package com.epam.training.sportsbetting.servlet;

import com.epam.training.sportsbetting.ExtendedUserDetails;
import com.epam.training.sportsbetting.domain.user.Player;
import com.epam.training.sportsbetting.form.UpdatePlayerForm;
import com.epam.training.sportsbetting.service.PlayerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static com.epam.training.sportsbetting.Role.ROLE_PLAYER;
import static java.util.stream.Collectors.toMap;

@Component
@WebServlet(urlPatterns = "/updateAccount")
public class UpdateAccountServlet extends AbstractHomeServlet {

    @Autowired
    private PlayerService playerService;
    @Autowired
    private ModelMapper mapper;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        checkForRole(ROLE_PLAYER);
        Integer userId = ((ExtendedUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        UpdatePlayerForm form = getUpdatePlayerForm(req);

        Player result = playerService.updatePlayerById(userId, form);

        passToHomePage(req, resp, result);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        checkForRole(ROLE_PLAYER);
        Integer userId = ((ExtendedUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        Player player = playerService.getPlayerById(userId)
                .orElseThrow(IllegalStateException::new);

        passToHomePage(req, resp, player);
    }

    private UpdatePlayerForm getUpdatePlayerForm(HttpServletRequest req) {
        Map<String, String> parameterMap = req.getParameterMap().entrySet().stream()
            .collect(toMap(Map.Entry::getKey, e -> toUTF8(e.getValue()[0])));

        return mapper.map(parameterMap, UpdatePlayerForm.class);
    }

    private String toUTF8(String val) {
        byte[] bytes = val.getBytes(StandardCharsets.ISO_8859_1);
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
