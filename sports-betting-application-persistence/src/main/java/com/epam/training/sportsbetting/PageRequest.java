package com.epam.training.sportsbetting;

import lombok.Data;
import org.springframework.data.domain.Pageable;

@Data
public class PageRequest {

    private int pageNumber;
    private int pageSize;

    public Pageable toPageable() {
        return org.springframework.data.domain.PageRequest.of(pageNumber, pageSize);
    }
}