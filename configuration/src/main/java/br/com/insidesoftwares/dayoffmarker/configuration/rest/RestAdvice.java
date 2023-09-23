package br.com.insidesoftwares.dayoffmarker.configuration.rest;

import br.com.insidesoftwares.commons.configuration.rest.InsideSoftwaresRestAdvice;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestAdvice extends InsideSoftwaresRestAdvice {
    private static final String PACKAGE_CONTROLLER = "br.com.insidesoftwares.dayoffmarker.controller";

    public RestAdvice() {
        super(PACKAGE_CONTROLLER);
    }
}
