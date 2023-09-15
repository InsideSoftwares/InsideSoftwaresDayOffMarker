package br.com.insidesoftwares.dayoffmarker.validator.request;

import br.com.insidesoftwares.dayoffmarker.commons.enumeration.TypeRequest;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.request.RequestConflictParametersException;
import br.com.insidesoftwares.dayoffmarker.domain.repository.RequestRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class RequestValidatorBeanTest {

	@Mock
	private RequestRepository requestRepository;

	@InjectMocks
	private RequestValidatorBean requestValidatorBean;

	private final String HASH_REQUEST = "HASH_REQUEST";

	@Test
	void shouldntThrowExceptionByRunningMethodValidator() {
		Mockito.when(
			requestRepository.existRequestByTypeRequestAndHashRequestAndNotStatusRequest(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any())
		).thenReturn(false);

		requestValidatorBean.validateRequest(HASH_REQUEST, TypeRequest.REQUEST_CREATION_DATE);
	}

	@Test
	void shouldRequestConflictParametersExceptionThrowExceptionByRunningMethodValidator() {
		Mockito.when(
			requestRepository.existRequestByTypeRequestAndHashRequestAndNotStatusRequest(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any())
		).thenReturn(true);

		assertThrows(
			RequestConflictParametersException.class,
			() -> requestValidatorBean.validateRequest(HASH_REQUEST, TypeRequest.CREATE_DATE)
		);

	}

	@Test
	void shouldRuntimeExceptionThrowExceptionByRunningMethodValidator() {
		Mockito.when(
			requestRepository.existRequestByTypeRequestAndHashRequestAndNotStatusRequest(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any())
		).thenThrow(new RuntimeException());

		assertThrows(
			RuntimeException.class,
			() -> requestValidatorBean.validateRequest(HASH_REQUEST, TypeRequest.CREATE_HOLIDAY)
		);

	}
}
