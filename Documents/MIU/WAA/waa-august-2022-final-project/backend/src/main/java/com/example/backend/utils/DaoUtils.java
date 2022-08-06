package com.example.backend.utils;

import com.example.backend.dto.filter.PageableSearch;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.ObjectUtils;

public class DaoUtils {

    public static <S extends PageableSearch> Pageable toPaging(S search) {
        Pageable paging = Pageable.unpaged();

        if (!(search.getPageNumber() < 0 || search.getPageSize() <= 0)) {
            if (!ObjectUtils.isEmpty(search.getSortBy())) {
                paging = PageRequest.of(search.getPageNumber(), search.getPageSize(), search.toSort());
            } else {
                paging = PageRequest.of(search.getPageNumber(), search.getPageSize());
            }
        }

        return paging;
    }

    public static String toLikeCriteria(String criteria) {
        if (ObjectUtils.isEmpty(criteria)) {
            return null;
        } else {
            return "%" + criteria + "%";
        }
    }

}
