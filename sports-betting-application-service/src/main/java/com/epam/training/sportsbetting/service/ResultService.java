package com.epam.training.sportsbetting.service;

import com.epam.training.sportsbetting.form.AddResultForm;

import java.util.List;
import java.util.Set;

public interface ResultService {

    Set<Integer> addResults(List<AddResultForm> addResultFormList);
}
