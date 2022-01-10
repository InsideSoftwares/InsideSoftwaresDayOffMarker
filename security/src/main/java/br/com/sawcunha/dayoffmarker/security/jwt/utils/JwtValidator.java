package br.com.sawcunha.dayoffmarker.security.jwt.utils;

import br.com.sawcunha.dayoffmarker.commons.enums.eJWTErro;
import br.com.sawcunha.dayoffmarker.security.jwt.dto.JwtDTO;
import io.fusionauth.jwt.JWTException;
import io.fusionauth.jwt.JWTExpiredException;
import io.fusionauth.jwt.JWTSigningException;
import io.fusionauth.jwt.JWTVerifierException;
import io.fusionauth.jwt.Verifier;
import io.fusionauth.jwt.domain.JWT;
import io.fusionauth.jwt.hmac.HMACVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtValidator {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.valid}")
    private Integer valid;

    public JWT validToken(String token, JwtDTO jwtValidation) {
        Verifier verifier = HMACVerifier.newVerifier(secret);
        JWT jwt = null;
        try {
            jwt = JWT.getDecoder().decode(token, verifier);
        } catch (JWTSigningException jwtSigningException){
            jwtValidation.setJwtErro(eJWTErro.SIGNING_ERRO);
        } catch (JWTVerifierException jwtVerifierException){
            jwtValidation.setJwtErro(eJWTErro.VERIFIER_ERRO);
        } catch (JWTExpiredException jwtExpiredException){
            jwtValidation.setJwtErro(eJWTErro.EXPIRED);
        } catch (JWTException e){
            jwtValidation.setJwtErro(eJWTErro.GENERIC);
        }
        return jwt;
    }

}
