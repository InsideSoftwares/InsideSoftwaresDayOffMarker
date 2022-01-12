package br.com.sawcunha.dayoffmarker.commons.exception;

import br.com.sawcunha.dayoffmarker.commons.exception.enums.eExceptionCode;
import br.com.sawcunha.dayoffmarker.commons.exception.error.ApplicationAlreadyInitializedException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.ConfigurationNotExistException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.DayMonthInvalidException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.DayOffMarkerGenericException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.InvalidKeyAccessException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.TokenJWTException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.city.CityException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.country.CountryExpetion;
import br.com.sawcunha.dayoffmarker.commons.exception.error.fixedholiday.FixedHolidayException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.holiday.HolidayException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.state.StateException;
import br.com.sawcunha.dayoffmarker.commons.exception.model.AttributeNotValid;
import br.com.sawcunha.dayoffmarker.commons.exception.model.ExceptionResponse;
import br.com.sawcunha.dayoffmarker.commons.exception.utils.ExceptionUtils;
import br.com.sawcunha.dayoffmarker.commons.utils.LocaleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ControllerAdvice
public class ExceptionsHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private LocaleUtils localeUtils;

    private ExceptionResponse createResponse(String code, String... args){
        return ExceptionResponse.builder()
                .codeError(code)
                .message(localeUtils.getMessage(code, args))
                .build();
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String field = "", typesEnum = "";
        String patternField = "(\\[\\\"[\\w,\\s]+\\\"\\])";
        String patternType = "(\\[[\\w,\\s]+\\])";

        Pattern pattern = Pattern.compile(patternField);
        Matcher matcher = pattern.matcher(ex.getMessage());
        if(matcher.find()){
            field = matcher.group().replaceAll("([\\[\\\"\\]])","");
        }
        pattern = Pattern.compile(patternType);
        matcher = pattern.matcher(ex.getMessage());
        if(matcher.find()){
            typesEnum = matcher.group();
        }

        String message = localeUtils.getMessage(eExceptionCode.ATTRIBUTE_NOT_VALID.getCode(), field, typesEnum);

        return ResponseEntity.status(status).body(
                ExceptionResponse.builder()
                                    .codeError(eExceptionCode.ENUM_ERROR.getCode())
                                    .message(message)
                        .build()
        );
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<AttributeNotValid> validationErrorsDTO = new ArrayList<>();

        ex.getBindingResult().getFieldErrors()
                .forEach(
                        e -> validationErrorsDTO.add(
                                new AttributeNotValid(
                                        e.getField(),
                                        localeUtils.getMessage(e.getDefaultMessage(), e.getField())
                                )
                        )
                );

        String message = localeUtils.getMessage(eExceptionCode.ATTRIBUTE_NOT_VALID.getCode(), ((ServletWebRequest)request).getRequest().getRequestURI());

        return ResponseEntity.status(status).body(
                ExceptionResponse.builder().codeError(eExceptionCode.ATTRIBUTE_NOT_VALID.getCode())
                        .message(message)
                        .validationErrors(validationErrorsDTO).build()
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ExceptionResponse handleSecurity(ConstraintViolationException ex) {

        List<AttributeNotValid> validationErrorsDTO = new ArrayList<>();

        ex.getConstraintViolations().forEach(
                        e -> {
                            String[] path = e.getPropertyPath().toString().split("\\.");
                            List<String> attributes = new ArrayList<>();
                            attributes.add(path[path.length-1]);
                            attributes.addAll(
                                    ExceptionUtils.findValuesAnnotation(e.getConstraintDescriptor().getAnnotation())
                            );

                            validationErrorsDTO.add(
                                    new AttributeNotValid(
                                            attributes.get(0),
                                            localeUtils.getMessage(e.getMessage(), attributes.toArray(String[]::new))
                                    )
                            );
                        }
                );

        String message = localeUtils.getMessage(eExceptionCode.ATTRIBUTE_NOT_VALID.getCode());

        return ExceptionResponse.builder().codeError(eExceptionCode.ATTRIBUTE_NOT_VALID.getCode())
                        .message(message)
                        .validationErrors(validationErrorsDTO).build();
    }

    @ExceptionHandler(TokenJWTException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ExceptionResponse handleSecurity(TokenJWTException exception){
        return createResponse(exception.getCode());
    }

    @ExceptionHandler(DayOffMarkerGenericException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ExceptionResponse handleSecurity(DayOffMarkerGenericException exception){
        return createResponse(exception.getCode());
    }

    @ExceptionHandler(InvalidKeyAccessException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ExceptionResponse handleSecurity(InvalidKeyAccessException exception){
        return createResponse(exception.getCode());
    }

    @ExceptionHandler(ConfigurationNotExistException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ExceptionResponse handleSecurity(ConfigurationNotExistException exception){
        return createResponse(exception.getCode());
    }

    @ExceptionHandler(ApplicationAlreadyInitializedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ExceptionResponse handleSecurity(ApplicationAlreadyInitializedException exception){
        return createResponse(exception.getCode());
    }

    @ExceptionHandler(FixedHolidayException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ExceptionResponse handleSecurity(FixedHolidayException exception){
        return createResponse(exception.getCode());
    }

    @ExceptionHandler(StateException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ExceptionResponse handleSecurity(StateException exception){
        return createResponse(exception.getCode());
    }

    @ExceptionHandler(CityException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ExceptionResponse handleSecurity(CityException exception){
        return createResponse(exception.getCode());
    }

    @ExceptionHandler(HolidayException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ExceptionResponse handleSecurity(HolidayException exception){
        return createResponse(exception.getCode());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ExceptionResponse handleAllExceptions(Exception ex, WebRequest request) {
        ex.printStackTrace();
        return createResponse(eExceptionCode.GENERIC.getCode());
    }

    @ExceptionHandler(CountryExpetion.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ExceptionResponse handleSecurity(CountryExpetion exception){
        return createResponse(exception.getCode());
    }

    @ExceptionHandler(DayMonthInvalidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ExceptionResponse handleSecurity(DayMonthInvalidException exception){
        return createResponse(exception.getCode());
    }

}
