package com.rupesh.userservice.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Pageable {
    private Integer page;
    private Long totalElements;
    private Integer size;
    private Integer totalPages;
    private boolean isFirst;
    private boolean isLast;
    private boolean hasNext;
    private boolean hasPrevious;
    private int totalItems;
    private boolean isSorted;

}
