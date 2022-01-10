package br.com.sawcunha.dayoffmarker.commons.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class LocaleUtils {

    @Autowired
    private MessageSource messageSource;

    private Locale getLocale(){
        return LocaleContextHolder.getLocale(LocaleContextHolder.getLocaleContext());
    }

    public String getMessage(String code, String... args){
        return messageSource.getMessage(code, args, getLocale());
    }

}
