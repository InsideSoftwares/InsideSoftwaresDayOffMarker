package br.com.insidesoftwares.dayoffmarker.job.utils.request;

import br.com.insidesoftwares.commons.specification.InsideSoftwaresUserAuthentication;
import org.springframework.stereotype.Component;

@Component
public class InsideSoftwaresUserAuthenticationBean implements InsideSoftwaresUserAuthentication {
    @Override
    public String findUserAuthentication() {
        return "DAYOFF_MARKER-JOB";
    }
}
