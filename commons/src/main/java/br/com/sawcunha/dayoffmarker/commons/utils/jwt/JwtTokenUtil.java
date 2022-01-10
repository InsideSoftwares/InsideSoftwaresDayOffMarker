package br.com.sawcunha.dayoffmarker.commons.utils.jwt;

import br.com.sawcunha.dayoffmarker.commons.enums.ePermission;
import io.fusionauth.jwt.domain.JWT;
import io.fusionauth.jwt.hmac.HMACSigner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serial;
import java.io.Serializable;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@Component
public class JwtTokenUtil implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private static final String BEARER = "Bearer ";

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.valid}")
    private Integer valid;

    private HMACSigner hmacSigner;

    @PostConstruct
    private void inicializa(){
        this.hmacSigner = HMACSigner.newSHA512Signer(secret);
    }

    public String generateToken(String login,  String email, ePermission permission) {
        return doGenerateToken(login, email, permission);
    }

    private String doGenerateToken(String login,  String email, ePermission permission) {
        JWT jwt = new JWT().setIssuer("DYC")
                .setIssuedAt(ZonedDateTime.now(ZoneOffset.UTC))
                .setSubject(login)
                .setUniqueId(email)
                .addClaim("permission",permission.name())
                .setExpiration(ZonedDateTime.now(ZoneOffset.UTC).plusMinutes(valid));
        return JWT.getEncoder().encode(jwt, hmacSigner);
    }

    public Integer getValid() {
        return valid;
    }
}
