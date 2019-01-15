package com.epam.training.sportsbetting.repository;

import com.epam.training.sportsbetting.entity.AbstractEntity;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
public interface AbstractRepository<E extends AbstractEntity> extends PagingAndSortingRepository<E, Integer> {
}