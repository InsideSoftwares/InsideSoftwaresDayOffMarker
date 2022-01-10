package br.com.sawcunha.dayoffmarker.commons.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DayOffMarkerResponse<T> {

    private String duration;
    private T data;
    private Paginated paginated;

}
