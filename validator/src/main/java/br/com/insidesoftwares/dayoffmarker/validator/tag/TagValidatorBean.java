package br.com.insidesoftwares.dayoffmarker.validator.tag;

import br.com.insidesoftwares.commons.utils.DateUtils;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.tag.TagCodeExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.tag.TagExistDayException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.tag.TagNotExistException;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.link.LinkDayRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.tag.TagRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.day.DayNotExistException;
import br.com.insidesoftwares.dayoffmarker.repository.DayRepository;
import br.com.insidesoftwares.dayoffmarker.repository.TagRepository;
import br.com.insidesoftwares.dayoffmarker.specification.validator.Validator;
import br.com.insidesoftwares.dayoffmarker.specification.validator.ValidatorLink;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
class TagValidatorBean implements Validator<Long, TagRequestDTO>, ValidatorLink<Long, LinkDayRequestDTO> {

	private final TagRepository tagRepository;
	private final DayRepository dayRepository;

	@Override
	public void validator(final TagRequestDTO tagRequestDTO) {
		if(tagRepository.existsByCode(tagRequestDTO.getCode())) throw new TagCodeExistException();
	}

	@Override
	public void validator(final Long tagID, final TagRequestDTO tagRequestDTO) {
		if(!tagRepository.existsById(tagID)) throw new TagNotExistException();
		if(tagRepository.existsByCodeAndNotId(tagRequestDTO.getCode(),tagID)) throw new TagCodeExistException();
	}

	@Override
	public void validator(final Long tagID) {
		if(!tagRepository.existsById(tagID)) throw new TagNotExistException();
	}

	@Override
	public void validateLink(
			final Long tagID,
			final LinkDayRequestDTO linkDayRequestDTO
	) {
		Long sizeDays = (long) linkDayRequestDTO.getDaysID().size();
		if(!dayRepository.existsByDates(sizeDays , linkDayRequestDTO.getDaysID())) throw new DayNotExistException();
		for (LocalDate date : linkDayRequestDTO.getDaysID()) {
			if(dayRepository.existsByDateAndTag(date, tagID)) throw new TagExistDayException(DateUtils.returnDate(date));
		}
	}
}
