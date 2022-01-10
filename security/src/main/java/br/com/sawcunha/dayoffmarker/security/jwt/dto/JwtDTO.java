package br.com.sawcunha.dayoffmarker.security.jwt.dto;

import br.com.sawcunha.dayoffmarker.commons.enums.eJWTErro;
import org.springframework.security.core.Authentication;

import java.util.Optional;

public class JwtDTO {

    private Optional<Authentication> authenticationOptional;
    private boolean valid;
    private eJWTErro jwtErro;
    private String identifier;

    public JwtDTO() {
        this.authenticationOptional = Optional.empty();
    }

    public Optional<Authentication> getAuthenticationOptional() {
        return authenticationOptional;
    }

    public void setAuthenticationOptional(Authentication authenticationOptional) {
        this.authenticationOptional = Optional.of(authenticationOptional);
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public eJWTErro getJwtErro() {
        return jwtErro;
    }

    public void setAuthenticationOptional(Optional<Authentication> authenticationOptional) {
        this.authenticationOptional = authenticationOptional;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public void setJwtErro(eJWTErro jwtErro) {
        this.jwtErro = jwtErro;
        this.setValid(false);
    }

}
