package com.fpt.edu.herofundbackend.dto;

import lombok.Builder;
import lombok.Value;

import java.util.List;


@Value
@Builder(toBuilder = true)
public class PageDto<T> {

    long totalElements;
    int totalPages;
    int offset;
    int limit;
    List<T> items;
}
