package br.com.sawcunha.dayoffmarker.commons.utils;

import br.com.sawcunha.dayoffmarker.commons.dto.Paginated;
import br.com.sawcunha.dayoffmarker.commons.enums.sort.PropertiesOrder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
public class PaginationUtils {

    public static Paginated createPaginated(final int totalPages,final long totalElements, final int sizePerPage){
        return Paginated.builder()
                .totalPages(totalPages)
                .totalElements(totalElements)
                .sizePerPage(sizePerPage)
                .build();
    }

    private static int calculatePage(final int page){
        return page - 1;
    }

    public static Pageable createPageable(final int page, final int sizePerPage, final Sort sort) {
        return PageRequest.of(
                calculatePage(page),
                sizePerPage,
                sort
        );
    }

    public static Pageable createPageable(
            final int page,
            final int sizePerPage,
            final Sort.Direction direction,
            final PropertiesOrder order
    ) {
        return PageRequest.of(
                calculatePage(page),
                sizePerPage,
                createSort(direction,order)
        );
    }

    public static Pageable createPageable(
            final int page,
            final int sizePerPage,
            final Sort.Direction direction,
            final PropertiesOrder... order
    ) {
        return PageRequest.of(
                calculatePage(page),
                sizePerPage,
                createSort(direction, order)
        );
    }

    public static Sort createSort(
            final Sort.Direction direction,
            final PropertiesOrder order
    ){
        return Sort.by(direction, order.properties());
    }

    public static Sort createSort(
            final Sort.Direction direction,
            final PropertiesOrder... order
    ){
        return Sort.by(
                direction,
                Arrays.stream(order)
                        .map(PropertiesOrder::properties)
                        .toArray(String[]::new)
        );
    }

}
