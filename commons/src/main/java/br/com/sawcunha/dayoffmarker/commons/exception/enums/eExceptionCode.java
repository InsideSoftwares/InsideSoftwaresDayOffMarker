package br.com.sawcunha.dayoffmarker.commons.exception.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum eExceptionCode {

    INVALID_KEY_ACCESS("DOMA-001"),

    ATTRIBUTE_NOT_VALID("DOM-001"),
    CONFIGURATION_NOT_EXIST("DOM-002"),
    APPLICATION_ALREADY_INITIALIZED("DOM-003"),
    COUNTRY_NAME_INVALID("DOM-004"),
    FIXED_HOLIDAY_NOT_EXIST("DOM-005"),
    COUNTRY_NOT_EXIST("DOM-006"),
    STATE_NOT_EXIST("DOM-007"),
    CITY_NOT_EXIST("DOM-008"),
    HOLIDAY_NOT_EXIST("DOM-009"),
    COUNTRY_NAME_EXIST("DOM-010"),
    COUNTRY_CODE_EXIST("DOM-011"),
    COUNTRY_ACRONYM_EXIST("DOM-012"),
    STATE_NAME_COUNTRY_ACRONYM_EXIST("DOM-013"),
    STATE_COUNTRY_ACRONYM_EXIST("DOM-014"),
    CITY_CODE_ACRONYM_STATE_EXIST("DOM-015"),
    CITY_NAME_STATE_EXIST("DOM-016"),
    CITY_CODE_STATE_EXIST("DOM-017"),
    FIXED_HOLIDAY_DAY_MONTH_COUNTRY_EXIST("DOM-018"),
    DAY_MONTH_INVALID("DOM-019"),


    ENUM_ERROR("DOM-998"),
    GENERIC("DOM-999");


    private final String code;
}
