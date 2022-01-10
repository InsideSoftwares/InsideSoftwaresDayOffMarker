package br.com.sawcunha.dayoffmarker.commons.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Paginated {

    private int sizePerPage;
    private int totalPages;
    private long totalElements;

}
