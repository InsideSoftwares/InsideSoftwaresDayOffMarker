package br.com.sawcunha.dayoffmarker.specification.service;


import br.com.sawcunha.dayoffmarker.commons.exception.error.DayOffMarkerGenericException;
import org.springframework.validation.annotation.Validated;

@Validated
public interface RequestCreationService {

    String createInitialApplication(final String countryName) throws DayOffMarkerGenericException;

}
