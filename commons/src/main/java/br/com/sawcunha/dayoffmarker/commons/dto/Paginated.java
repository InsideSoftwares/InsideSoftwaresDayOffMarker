package br.com.sawcunha.dayoffmarker.commons.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
public class Paginated implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private int sizePerPage;
    private int totalPages;
    private long totalElements;

}
