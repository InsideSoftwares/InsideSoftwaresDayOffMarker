package br.com.sawcunha.dayoffmarker.validator.day;

import br.com.sawcunha.dayoffmarker.commons.dto.request.link.LinkTagRequestDTO;
import br.com.sawcunha.dayoffmarker.commons.exception.error.DayOffMarkerGenericException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.day.DayNotExistException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.tag.TagExistDayException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.tag.TagNotExistException;
import br.com.sawcunha.dayoffmarker.commons.utils.DateUtils;
import br.com.sawcunha.dayoffmarker.repository.DayRepository;
import br.com.sawcunha.dayoffmarker.repository.TagRepository;
import br.com.sawcunha.dayoffmarker.specification.validator.ValidatorLink;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
class DayValidatorBean implements ValidatorLink<Long, LinkTagRequestDTO> {

	private final DayRepository dayRepository;
	private final TagRepository tagRepository;

	@Override
	public void validateLink(Long dayID, LinkTagRequestDTO linkTagRequestDTO, Long countryID) throws DayOffMarkerGenericException {
		if(!dayRepository.existsById(dayID)) throw new DayNotExistException();
		Long sizeTags = (long) linkTagRequestDTO.getTagsID().size();
		if(!tagRepository.existsByTags(sizeTags, linkTagRequestDTO.getTagsID())) throw new TagNotExistException();
		LocalDate date = dayRepository.findDateByID(dayID);
		for (Long tagID : linkTagRequestDTO.getTagsID()) {
			if(dayRepository.existsByDateAndTag(dayID, tagID, countryID)) throw new TagExistDayException(DateUtils.returnDate(date));
		}
	}
}
