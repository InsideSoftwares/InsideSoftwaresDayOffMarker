package br.com.insidesoftwares.dayoffmarker.specification.service.request;


import br.com.insidesoftwares.dayoffmarker.commons.dto.tag.TagLinkUnlinkRequestDTO;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
public interface RequestCreationService {
    String createInitialApplication();
    String createLinkTagsInDays(final TagLinkUnlinkRequestDTO tagLinkUnlinkRequestDTO);
    String createUnlinkTagsInDays(final TagLinkUnlinkRequestDTO tagLinkUnlinkRequestDTO);
    String createHolidayRequest(final UUID fixedHolidayID);
    String createHolidayRequest();
}
