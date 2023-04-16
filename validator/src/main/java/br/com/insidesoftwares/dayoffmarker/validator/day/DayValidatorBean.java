package br.com.insidesoftwares.dayoffmarker.validator.day;

import br.com.insidesoftwares.commons.utils.DateUtils;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.day.DayNotExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.tag.TagExistDayException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.tag.TagNotExistException;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.link.LinkTagRequestDTO;
import br.com.insidesoftwares.dayoffmarker.repository.DayRepository;
import br.com.insidesoftwares.dayoffmarker.repository.TagRepository;
import br.com.insidesoftwares.dayoffmarker.specification.validator.ValidatorLink;
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
	public void validateLink(Long dayID, LinkTagRequestDTO linkTagRequestDTO) {
		if(!dayRepository.existsById(dayID)) throw new DayNotExistException();
		Long sizeTags = (long) linkTagRequestDTO.tagsID().size();
		if(!tagRepository.existsByTags(sizeTags, linkTagRequestDTO.tagsID())) throw new TagNotExistException();
		LocalDate date = dayRepository.findDateByID(dayID);
		for (Long tagID : linkTagRequestDTO.tagsID()) {
			if(dayRepository.existsByDateAndTag(dayID, tagID)) throw new TagExistDayException(DateUtils.returnDate(date));
		}
	}
}
