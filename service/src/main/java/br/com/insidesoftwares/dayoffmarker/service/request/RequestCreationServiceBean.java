package br.com.insidesoftwares.dayoffmarker.service.request;

import br.com.insidesoftwares.commons.utils.DateUtils;
import br.com.insidesoftwares.commons.utils.HashUtils;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.tag.TagLinkRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.Configurationkey;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.StatusRequest;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.TypeParameter;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.TypeRequest;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.TypeValue;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.request.ParameterNotExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.request.RequestConflictParametersException;
import br.com.insidesoftwares.dayoffmarker.domain.entity.request.Request;
import br.com.insidesoftwares.dayoffmarker.domain.entity.request.RequestParameter;
import br.com.insidesoftwares.dayoffmarker.specification.service.ConfigurationService;
import br.com.insidesoftwares.dayoffmarker.specification.service.request.RequestCreationService;
import br.com.insidesoftwares.dayoffmarker.specification.service.request.RequestService;
import br.com.insidesoftwares.dayoffmarker.specification.validator.RequestValidator;
import br.com.insidesoftwares.utils.AuthenticationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class RequestCreationServiceBean implements RequestCreationService {

    private final ConfigurationService configurationService;
    private final RequestService requestService;
    private final RequestValidator requestValidator;

	private final static String PIPE = "|";
	private final static String REQUESTING = "System";

    @Transactional(rollbackFor = {
		RequestConflictParametersException.class,
		ParameterNotExistException.class
    })
    @Override
    public String createInitialApplication() {
        UUID keyRequest = UUID.randomUUID();

        Request request = Request.builder()
                .id(keyRequest)
                .createDate(LocalDateTime.now())
                .typeRequest(TypeRequest.REQUEST_CREATION_DATE)
                .statusRequest(StatusRequest.CREATED)
                .requesting(REQUESTING)
                .build();

        request.setRequestParameter(
                createRequestParameterInitial(request)
        );

		requestService.saveRequest(request);
        return keyRequest.toString();
    }

	@Transactional(rollbackFor = {
		RequestConflictParametersException.class,
		ParameterNotExistException.class
	})
	@Override
	public String createLinkTagsInDays(final TagLinkRequestDTO tagLinkRequestDTO) {
		UUID keyRequest = UUID.randomUUID();

		Request request = Request.builder()
			.id(keyRequest)
			.createDate(LocalDateTime.now())
			.typeRequest(TypeRequest.LINK_TAG)
			.statusRequest(StatusRequest.CREATED)
			.requesting(getUser())
			.build();

		request.setRequestParameter(
			createRequestParameterLinkUnlinkTag(request, tagLinkRequestDTO)
		);

		requestService.saveRequest(request);
		return keyRequest.toString();
	}

	@Transactional(rollbackFor = {
		RequestConflictParametersException.class,
		ParameterNotExistException.class
	})
	@Override
	public String createUnlinkTagsInDays(final TagLinkRequestDTO tagLinkRequestDTO) {
		UUID keyRequest = UUID.randomUUID();

		Request request = Request.builder()
			.id(keyRequest)
			.createDate(LocalDateTime.now())
			.typeRequest(TypeRequest.UNLINK_TAG)
			.statusRequest(StatusRequest.CREATED)
			.requesting(getUser())
			.build();

		request.setRequestParameter(
			createRequestParameterLinkUnlinkTag(request, tagLinkRequestDTO)
		);

		requestService.saveRequest(request);
		return keyRequest.toString();
	}

	@Transactional(rollbackFor = {
		RequestConflictParametersException.class,
		ParameterNotExistException.class
	})
	@Override
	public String createDateRequest(
		final Request request,
		final Integer month,
		final Integer year
	)  {
		UUID keyRequest = UUID.randomUUID();

		Request newRequest =  Request.builder()
			.id(keyRequest)
			.statusRequest(StatusRequest.CREATED)
			.typeRequest(TypeRequest.CREATE_DATE)
			.createDate(LocalDateTime.now())
			.requesting(request.getRequesting())
			.build();

		Set<RequestParameter> requestParameters =
			createRequestParameter(
				newRequest,
				month.toString(),
				year.toString(),
				request.getId().toString()
			);

		newRequest.setRequestParameter(requestParameters);

		requestService.saveRequest(newRequest);
		return keyRequest.toString();
	}

	@Transactional(rollbackFor = {
		RequestConflictParametersException.class,
		ParameterNotExistException.class
	})
	@Override
	public String createHolidayRequest(final Long fixedHolidayID) {
		UUID keyRequest = UUID.randomUUID();

		Request request =  Request.builder()
			.id(keyRequest)
			.statusRequest(StatusRequest.CREATED)
			.typeRequest(TypeRequest.CREATE_HOLIDAY)
			.createDate(LocalDateTime.now())
			.requesting(REQUESTING)
			.build();

		Set<RequestParameter> requestParameters =
			createRequestParameter(
				request,
				fixedHolidayID.toString()
			);

		request.setRequestParameter(requestParameters);

		requestService.saveRequest(request);
		return keyRequest.toString();
	}

	private Set<RequestParameter> createRequestParameter(
		final Request request,
		final String fixedHolidayID
	) {

		Set<RequestParameter> requestParameters = new HashSet<>();

		requestParameters.add(
			RequestParameter.builder()
				.typeParameter(TypeParameter.FIXED_HOLIDAY_ID)
				.typeValue(TypeValue.LONG)
				.value(fixedHolidayID)
				.request(request)
				.build()
		);

		String requestParametersHash = getHashValues(requestParameters, request.getTypeRequest().name());
		request.setRequestHash(requestParametersHash);

		requestValidator.validateRequest(requestParametersHash, request.getTypeRequest());

		return requestParameters;
	}

	private Set<RequestParameter> createRequestParameter(
		final Request request,
		final String month,
		final String year,
		final String requestID
	)  {

		Set<RequestParameter> requestParameters = new HashSet<>();

		requestParameters.add(
			createRequestParameter(request, TypeParameter.MONTH, TypeValue.INT, month)
		);
		requestParameters.add(
			createRequestParameter(request, TypeParameter.YEAR, TypeValue.INT, year)
		);
		requestParameters.add(
			createRequestParameter(request, TypeParameter.REQUEST_ORIGINAL, TypeValue.STRING, requestID)
		);

		String requestParametersHash = getHashValues(requestParameters, request.getTypeRequest().name());
		request.setRequestHash(requestParametersHash);

		requestValidator.validateRequest(requestParametersHash, request.getTypeRequest());

		return requestParameters;
	}

	private Set<RequestParameter> createRequestParameterInitial(final Request request) {
        String limitBackYears = configurationService.findValueConfigurationByKey(Configurationkey.LIMIT_BACK_DAYS_YEARS);
        String limitForwardYears = configurationService.findValueConfigurationByKey(Configurationkey.LIMIT_FORWARD_DAYS_YEARS);
        LocalDate baseDate = LocalDate.now();

        String startYear = DateUtils.returnYearMinus(baseDate,limitBackYears);
        String endYear = DateUtils.returnYearPlus(baseDate,limitForwardYears);

        Set<RequestParameter> requestParameters = new HashSet<>();

        requestParameters.add(
                createRequestParameter(request, TypeParameter.START_YEAR, TypeValue.INT, startYear)
        );
        requestParameters.add(
                createRequestParameter(request, TypeParameter.END_YEAR, TypeValue.INT, endYear)
        );

		String requestParametersHash = getHashValues(requestParameters, request.getTypeRequest().name());
		request.setRequestHash(requestParametersHash);

		requestValidator.validateRequest(requestParametersHash, request.getTypeRequest());

        return requestParameters;
    }

	private Set<RequestParameter> createRequestParameterLinkUnlinkTag(
		final Request request,
		final TagLinkRequestDTO tagLinkRequestDTO
	){
		Set<RequestParameter> requestParameters = new HashSet<>();

		if(Objects.nonNull(tagLinkRequestDTO.day())){
			requestParameters.add(
				createRequestParameter(request, TypeParameter.DAY, TypeValue.INT, tagLinkRequestDTO.day().toString())
			);
		}
		if(Objects.nonNull(tagLinkRequestDTO.month())){
			requestParameters.add(
				createRequestParameter(request, TypeParameter.MONTH, TypeValue.INT, tagLinkRequestDTO.month().toString())
			);
		}
		if(Objects.nonNull(tagLinkRequestDTO.year())){
			requestParameters.add(
				createRequestParameter(request, TypeParameter.YEAR, TypeValue.INT, tagLinkRequestDTO.year().toString())
			);
		}
		if(Objects.nonNull(tagLinkRequestDTO.dayOfYear())){
			requestParameters.add(
				createRequestParameter(request, TypeParameter.DAY_OF_YEAR, TypeValue.INT, tagLinkRequestDTO.dayOfYear().toString())
			);
		}
		if(Objects.nonNull(tagLinkRequestDTO.dayOfWeek())){
			requestParameters.add(
				createRequestParameter(request, TypeParameter.DAY_OF_WEEK, TypeValue.STRING, tagLinkRequestDTO.dayOfWeek().name())
			);
		}
		if(Objects.nonNull(tagLinkRequestDTO.tagsID())){
			String tagsID = tagLinkRequestDTO.tagsID().stream()
				.map(Object::toString)
				.collect(Collectors.joining(PIPE));
			requestParameters.add(
				createRequestParameter(request, TypeParameter.TAG_ID, TypeValue.ARRAY, tagsID)
			);
		}

		String requestParametersHash = getHashValues(requestParameters, request.getTypeRequest().name());
		request.setRequestHash(requestParametersHash);

		requestValidator.validateRequest(requestParametersHash, request.getTypeRequest());

		return requestParameters;
	}

    private RequestParameter createRequestParameter(
            final Request request,
            final TypeParameter typeParameter,
            final TypeValue typeValue,
            final String value
    ){
        return RequestParameter.builder()
                .typeParameter(typeParameter)
                .typeValue(typeValue)
                .value(value)
                .request(request)
                .build();
    }

	private String getUser(){
		return AuthenticationUtils.getUserNameAuthenticated();
	}

	private String getHashValues(final Set<RequestParameter> requestParameters, final String typeRequest) {
		String values = typeRequest + requestParameters.stream().map(RequestParameter::getValue).collect(Collectors.joining(PIPE));

		return HashUtils.createHash(values);
	}
}
