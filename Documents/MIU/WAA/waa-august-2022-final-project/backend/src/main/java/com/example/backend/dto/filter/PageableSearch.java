package com.example.backend.dto.filter;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Sort;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@ApiModel("Pageable Search Dto")
public class PageableSearch implements Serializable {

    protected int pageNumber = 0;

    protected int pageSize = 10;

    protected String sortBy;

    protected String sortDirection;

    public String getSortDirection() {
        return sortDirection == null || sortDirection.equals("") ? "asc" : sortDirection;
    }

    public Sort.Direction getDirection() {
        return getSortDirection().toLowerCase().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
    }

    public Sort toSort() {
        if (sortBy == null) {
            return Sort.unsorted();
        }

        if (sortDirection == null) {
            return Sort.by(Sort.Order.desc(sortBy));
        }
        return Sort.by(
                "DESC".equalsIgnoreCase(sortDirection)
                        ? Sort.Order.desc(sortBy)
                        : Sort.Order.asc(sortBy)
        );
    }

}
