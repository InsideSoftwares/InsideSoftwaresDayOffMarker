package br.com.insidesoftwares.dayoffmarker.specification.service;


import org.springframework.validation.annotation.Validated;

@Validated
public interface RequestCreationService {

    String createInitialApplication(final String countryName);

}
