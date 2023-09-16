package br.com.insidesoftwares.dayoffmarker;

import br.com.insidesoftwares.commons.dto.request.InsidePaginationFilterDTO;
import br.com.insidesoftwares.commons.utils.PaginationUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class RepositoryTestUtils {

    public static <T> Pageable createPageable(
            final Integer page,
            final Integer sizePerpage,
            final Sort.Direction direction,
            final T order
    ) {
        InsidePaginationFilterDTO insidePaginationFilterDTO = InsidePaginationFilterDTO.builder()
                .page(page)
                .sizePerPage(sizePerpage)
                .direction(direction)
                .order(order)
                .build();

        return PaginationUtils.createPageable(insidePaginationFilterDTO);
    }
}
