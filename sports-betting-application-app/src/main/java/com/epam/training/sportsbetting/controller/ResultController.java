package com.epam.training.sportsbetting.controller;

import com.epam.training.sportsbetting.form.AddResultForm;
import com.epam.training.sportsbetting.service.ResultService;
import com.epam.training.sportsbetting.service.WagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@Transactional
public class ResultController {

    private final ResultService resultService;
    private final WagerService wagerService;

    @Autowired
    public ResultController(ResultService resultService, WagerService wagerService) {
        this.resultService = resultService;
        this.wagerService = wagerService;
    }

    @PostMapping("/api/result/add")
    public Map<Integer, Double> addResults(@RequestBody List<AddResultForm> resultForms) {
        Set<Integer> playedEvents = resultService.addResults(resultForms);
        return wagerService.processWagers(playedEvents);
    }
}
