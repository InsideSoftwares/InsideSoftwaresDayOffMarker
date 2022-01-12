package br.com.sawcunha.dayoffmarker.security.exception;

import br.com.sawcunha.dayoffmarker.commons.exception.enums.eExceptionCode;
import br.com.sawcunha.dayoffmarker.commons.exception.model.ExceptionResponse;
import br.com.sawcunha.dayoffmarker.commons.utils.LocaleUtils;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AccessDeniedExceptionHandler implements AccessDeniedHandler {

    private final LocaleUtils localeUtils;
    private final Gson gson;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException ex) {
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .codeError(eExceptionCode.ACCESS_DENIED.getCode())
                .message(localeUtils.getMessage(eExceptionCode.ACCESS_DENIED.getCode()))
                .build();

        response.setStatus(401);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            response.getWriter().write(gson.toJson(exceptionResponse));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}