package br.com.sawcunha.dayoffmarker.commons.exception.utils;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public class ExceptionUtils {

    public static List<String> findValuesAnnotation(Annotation annotation){
        List<String> values = new ArrayList<>();
        if(annotation instanceof Max max){
            values.add(String.valueOf(max.value()));
        }
        if(annotation instanceof Min min){
            values.add(String.valueOf(min.value()));
        }
        if(annotation instanceof Size size){
            values.add(String.valueOf(size.min()));
            values.add(String.valueOf(size.max()));
        }
        return values;
    }



}
