package com.epam.training.sportsbetting.controller;

import com.epam.training.sportsbetting.form.AddResultForm;
import com.epam.training.sportsbetting.service.ResultService;
import com.epam.training.sportsbetting.service.WagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.epam.training.sportsbetting.Role.ROLE_ADMIN;

@RestController
@Transactional
public class ResultController extends AbstractController {

    private final ResultService resultService;
    private final WagerService wagerService;

    @Autowired
    public ResultController(ResultService resultService, WagerService wagerService) {
        this.resultService = resultService;
        this.wagerService = wagerService;
    }

    @PostMapping("/api/result/add")
    public Map<Integer, Double> addResults(Authentication authentication, @RequestBody List<AddResultForm> resultForms) {
        checkForRole(authentication, ROLE_ADMIN);
        Set<Integer> playedEvents = resultService.addResults(resultForms);
        return wagerService.processWagers(playedEvents);
    }
}
