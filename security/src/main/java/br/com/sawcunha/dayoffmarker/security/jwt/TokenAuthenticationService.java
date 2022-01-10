package br.com.sawcunha.dayoffmarker.security.jwt;

import br.com.sawcunha.dayoffmarker.commons.enums.eJWTErro;
import br.com.sawcunha.dayoffmarker.commons.logger.LogService;
import br.com.sawcunha.dayoffmarker.security.jwt.dto.JwtDTO;
import br.com.sawcunha.dayoffmarker.security.jwt.utils.JwtValidator;
import io.fusionauth.jwt.domain.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Objects;

@Component
public class TokenAuthenticationService {

    @Autowired
    private LogService<TokenAuthenticationService> logService;

    @PostConstruct
    public void init(){
        logService.init(TokenAuthenticationService.class);
        logService.logInfor("Init TokenAuthenticationService");
    }

    @Autowired
    private JwtValidator jwtValidator;

    static final String HEADER_STRING = "Authorization";

    public JwtDTO getAuthentication(HttpServletRequest request) {
        JwtDTO jwtValidation = new JwtDTO();
        try{
            String token = request.getHeader(HEADER_STRING);
            if (Objects.nonNull(token)) {
                if (token.matches("(Bearer\\s)(.+)")) {
                    JWT jwt = jwtValidator.validToken(token.replace("Bearer ", ""), jwtValidation);
                    if (Objects.nonNull(jwt)) {
                        jwtValidation.setValid(true);
                        String permission = (String) jwt.getObject("permission");
                        jwtValidation.setIdentifier(jwt.uniqueId);
                        jwtValidation.setAuthenticationOptional(
                                new UsernamePasswordAuthenticationToken(
                                        jwt.subject, null,
                                        Collections.singleton(new SimpleGrantedAuthority(permission))
                                )
                        );
                    }
                }
            } else {
                jwtValidation.setJwtErro(eJWTErro.NOT_HAS_TOKEN);
            }
        } catch (Exception e){
            jwtValidation.setJwtErro(eJWTErro.GENERIC);
            jwtValidation.setValid(false);
            logService.logError(eJWTErro.GENERIC.getCode(), e.getMessage(), e.getCause());
        }
        return jwtValidation;
    }

}
