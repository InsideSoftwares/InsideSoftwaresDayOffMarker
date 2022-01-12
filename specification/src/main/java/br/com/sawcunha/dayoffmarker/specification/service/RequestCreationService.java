package br.com.sawcunha.dayoffmarker.specification.service;


import org.springframework.validation.annotation.Validated;

@Validated
public interface RequestCreationService {

    String createInitialApplication(final String countryName) throws Exception;

}
