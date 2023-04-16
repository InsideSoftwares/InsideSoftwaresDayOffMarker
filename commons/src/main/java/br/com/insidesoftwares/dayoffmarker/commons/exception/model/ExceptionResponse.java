package br.com.insidesoftwares.dayoffmarker.commons.exception.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Builder
@Getter
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ExceptionResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String message;
    private final String codeError;
    private final List<AttributeNotValid> validationErrors;

}
