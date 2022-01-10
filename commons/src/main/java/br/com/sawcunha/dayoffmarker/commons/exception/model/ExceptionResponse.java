package br.com.sawcunha.dayoffmarker.commons.exception.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ExceptionResponse {
    private final String message;
    private final String codeError;
    private final List<AttributeNotValid> validationErrors;

}