package br.com.insidesoftwares.dayoffmarker.validator.request;

import br.com.insidesoftwares.dayoffmarker.commons.enumeration.StatusRequest;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.TypeRequest;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.request.ParameterNotExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.request.RequestConflictParametersException;
import br.com.insidesoftwares.dayoffmarker.repository.RequestRepository;
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

			if(existRequest) throw new RequestConflictParametersException();

        } catch (ParameterNotExistException | RequestConflictParametersException exeption){
            log.error("Error: ParameterNotExistException or RequestConflictParametersException", exeption);
            throw exeption;
        } catch (Exception e){
			log.error("Error: #validRequestInitial", e);
            throw e;
        }
    }
}
