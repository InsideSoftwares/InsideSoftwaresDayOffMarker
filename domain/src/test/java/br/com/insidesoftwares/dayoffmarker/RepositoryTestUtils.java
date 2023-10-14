package br.com.insidesoftwares.dayoffmarker;

import br.com.insidesoftwares.commons.dto.request.InsidePaginationFilterDTO;
import br.com.insidesoftwares.commons.sort.PropertiesOrder;
import br.com.insidesoftwares.commons.utils.PaginationUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class RepositoryTestUtils {

    public static Pageable createPageable(
            final Integer page,
            final Integer sizePerpage,
            final Sort.Direction direction,
            final String order
    ) {
        InsidePaginationFilterDTO insidePaginationFilterDTO = InsidePaginationFilterDTO.builder()
                .page(page)
                .sizePerPage(sizePerpage)
                .direction(direction)
                .order(order)
                .build();

        return PaginationUtils.createPageable(insidePaginationFilterDTO, eOrderCity.ID);
    }

    @AllArgsConstructor
    @Getter
    public enum eOrderCity implements PropertiesOrder {
        ID("id"),
        NAME("name"),
        ACRONYM("acronym"),
        CODE("code"),
        STATE_NAME("state.name"),
        STATE_ACRONYM("state.acronym");

        private final String properties;

        @Override
        public String properties() {
            return this.getProperties();
        }

        @Override
        public String value(String name) {
            try {
                return br.com.insidesoftwares.dayoffmarker.commons.enumeration.sort.eOrderCity.valueOf(name).getProperties();
            } catch (Exception exception) {
                return this.getProperties();
            }
        }
    }
}
