package br.com.sawcunha.dayoffmarker.commons.enums;

public enum eJWTErro {
    GENERIC("AUTH-001"),
    EXPIRED("AUTH-002"),
    SIGNING_ERRO("AUTH-003"),
    VERIFIER_ERRO("AUTH-004"),
    NOT_HAS_TOKEN("AUTH-005"),
    USER_TOKEN_INVALID("AUTH-006");

    private final String code;

    eJWTErro(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
