package br.com.insidesoftwares.dayoffmarker.validator.request;

import br.com.insidesoftwares.dayoffmarker.commons.enumeration.StatusRequest;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.TypeRequest;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.request.RequestConflictParametersException;
import br.com.insidesoftwares.dayoffmarker.domain.repository.request.RequestRepository;
import br.com.insidesoftwares.dayoffmarker.specification.validator.RequestValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
class RequestValidatorBean implements RequestValidator {

    private final RequestRepository requestRepository;

    @Override
    public void validateRequest(final String requestHash, final TypeRequest typeRequest) {
        try {
            boolean existRequest =
                    requestRepository.existRequestByTypeRequestAndHashRequestAndNotStatusRequest(
                            typeRequest,
                            requestHash,
                            List.of(
                                    StatusRequest.ERROR,
                                    StatusRequest.FINALIZED,
                                    StatusRequest.SUCCESS
                            )
                    );

            if (existRequest) throw new RequestConflictParametersException();

        } catch (RequestConflictParametersException requestConflictParametersException) {
            log.error("Error: RequestConflictParametersException", requestConflictParametersException);
            throw requestConflictParametersException;
        } catch (Exception exception) {
            log.error("Error: #validRequestInitial", exception);
            throw exception;
        }
    }
}
