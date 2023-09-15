package br.com.insidesoftwares.dayoffmarker.specification.service;


import br.com.insidesoftwares.dayoffmarker.commons.dto.request.tag.TagLinkRequestDTO;
import br.com.insidesoftwares.dayoffmarker.domain.entity.request.Request;
import org.springframework.validation.annotation.Validated;

@Validated
public interface RequestCreationService {

    String createInitialApplication();
	String createLinkTagsInDays(final TagLinkRequestDTO tagLinkRequestDTO);
	String createUnlinkTagsInDays(final TagLinkRequestDTO tagLinkRequestDTO);
	String createDateRequest(final Request request, final Integer month, final Integer year);
	String createHolidayRequest(final Long fixedHolidayID);
}
