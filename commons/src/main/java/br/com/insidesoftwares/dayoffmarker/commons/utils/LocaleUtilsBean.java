package br.com.insidesoftwares.dayoffmarker.commons.utils;

import br.com.insidesoftwares.commons.specification.LocaleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class LocaleUtilsBean implements LocaleUtils {

	@Autowired
	private MessageSource messageSource;

	@Override
	public Locale getLocale(){
		return LocaleContextHolder.getLocale(LocaleContextHolder.getLocaleContext());
	}

	@Override
	public String getMessage(String code, String... args){
		return messageSource.getMessage(code, args, getLocale());
	}

}
