package br.com.sawcunha.dayoffmarker.specification.validator;

public interface Validator<I,D> {

    void validator(D dto) throws Exception;
    void validator(I id, D dto) throws Exception;

}
