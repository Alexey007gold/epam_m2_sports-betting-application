package com.epam.training.sportsbetting;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@Builder
public class PageDTO<T> {

    private List<T> data;
    private int pageNumber;
    private int pageSize;
    private int numberOfElements;
    private long totalElements;

    @SuppressWarnings("unchecked")
    public static <T> PageDTO<T> of(Page<T> page) {
        return (PageDTO<T>) PageDTO.builder()
                .data((List<Object>) page.getContent())
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .numberOfElements(page.getNumberOfElements())
                .totalElements(page.getTotalElements())
                .build();
    }

    @SuppressWarnings("unchecked")
    public static <T> PageDTO<T> of(List<T> data, int page, int pageSize, int numberOfElements, long totalElements) {
        return (PageDTO<T>) PageDTO.builder()
                .data((List<Object>) data)
                .pageNumber(page)
                .pageSize(pageSize)
                .numberOfElements(numberOfElements)
                .totalElements(totalElements)
                .build();
    }
}