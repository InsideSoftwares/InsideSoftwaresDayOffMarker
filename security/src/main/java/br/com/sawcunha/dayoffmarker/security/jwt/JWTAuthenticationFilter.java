package br.com.sawcunha.dayoffmarker.security.jwt;

import br.com.sawcunha.dayoffmarker.commons.enums.eJWTErro;
import br.com.sawcunha.dayoffmarker.commons.exception.model.ExceptionResponse;
import br.com.sawcunha.dayoffmarker.commons.logger.LogService;
import br.com.sawcunha.dayoffmarker.commons.utils.LocaleUtils;
import br.com.sawcunha.dayoffmarker.security.jwt.dto.JwtDTO;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.annotation.PostConstruct;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends GenericFilterBean {

    private final TokenAuthenticationService authenticationService;
    private final LocaleUtils localeUtils;
    private final LogService<JWTAuthenticationFilter> logService;
    private final Gson gson;

    private static final Pattern pattern = Pattern.compile("(api\\/v([0-9])+)");

    @PostConstruct
    public void init(){
        logService.init(JWTAuthenticationFilter.class);
        logService.logInfor("Init JWTAuthenticationFilter");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        setLocale(request);
        enableCors(response);

        AtomicReference<Authentication> authentication = new AtomicReference<>(null);
        if(isValidationJWT(request.getServletPath())) {
            JwtDTO jwtValidation = authenticationService
                    .getAuthentication(request);

            if (jwtValidation.isValid()) {
                jwtValidation.getAuthenticationOptional().ifPresent(authentication::set);
            } else {
                setUnauthorizedResponse(response,jwtValidation.getJwtErro());
                return;
            }
        }

        SecurityContextHolder.getContext().setAuthentication(authentication.get());
        filterChain.doFilter(request, response);
    }

    private boolean isValidationJWT(String path){
        Matcher matcher = pattern.matcher(path);
        return matcher.find();
    }

    private void setUnauthorizedResponse(HttpServletResponse response, eJWTErro error) {

        logService.logInfor(error.getCode(),"Error when trying to validate login.");
        try {
            ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                    .codeError(error.getCode())
                    .message(localeUtils.getMessage(error.getCode()))
                    .build();
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            response.getWriter().write(gson.toJson(exceptionResponse));
        } catch (Exception e) {
            logService.logError(error.getCode(),"Error when trying to validate login.", e.getCause());
        }
    }

    private void setLocale(HttpServletRequest request){
        String language = request.getHeader("Accept-Language");
        Locale locale = Objects.nonNull(language) ? Locale.forLanguageTag(language) : Locale.forLanguageTag("pt-BR");
        LocaleContextHolder.setDefaultLocale(locale);
    }

    private void enableCors(HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET,POST,DELETE,PUT,OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Max-Age", "180");
    }

}