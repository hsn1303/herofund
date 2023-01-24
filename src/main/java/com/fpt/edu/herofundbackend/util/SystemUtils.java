package com.fpt.edu.herofundbackend.util;

import com.fpt.edu.herofundbackend.constant.SystemConstant;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class SystemUtils {

    public static Pageable createPageable(int offset, int limit){
        Sort sortByCreateAt = Sort.by(Sort.Direction.DESC, SystemConstant.FIELD.FIELD_CREATE_AT);
        return PageRequest.of(offset - 1, limit, sortByCreateAt);
    }

    public static int calculateTotalPage(long totalElement, int limit){
        return Math.toIntExact((totalElement + limit - 1) / limit);
    }
}
