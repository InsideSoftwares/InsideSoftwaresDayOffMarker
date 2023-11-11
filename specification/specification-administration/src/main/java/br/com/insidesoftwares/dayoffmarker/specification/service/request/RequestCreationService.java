package br.com.insidesoftwares.dayoffmarker.specification.service.request;


import br.com.insidesoftwares.dayoffmarker.commons.dto.tag.TagLinkUnlinkRequestDTO;
import br.com.insidesoftwares.dayoffmarker.domain.entity.request.Request;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
public interface RequestCreationService {
    String createInitialApplication();

    String createLinkTagsInDays(final TagLinkUnlinkRequestDTO tagLinkUnlinkRequestDTO);

    String createUnlinkTagsInDays(final TagLinkUnlinkRequestDTO tagLinkUnlinkRequestDTO);

    String createDateRequest(final Request request, final Integer month, final Integer year);

    String createHolidayRequest(final UUID fixedHolidayID);
}
