package br.com.sawcunha.dayoffmarker.commons.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DayOffMarkerResponse<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String duration;
    private T data;
    private Paginated paginated;

}
