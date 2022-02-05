package br.com.sawcunha.dayoffmarker.validator.tag;

import br.com.sawcunha.dayoffmarker.commons.dto.request.link.LinkDayRequestDTO;
import br.com.sawcunha.dayoffmarker.commons.dto.request.tag.TagRequestDTO;
import br.com.sawcunha.dayoffmarker.commons.exception.error.DayOffMarkerGenericException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.day.DayNotExistException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.tag.TagCodeExistException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.tag.TagExistDayException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.tag.TagNotExistException;
import br.com.sawcunha.dayoffmarker.commons.utils.DateUtils;
import br.com.sawcunha.dayoffmarker.repository.DayRepository;
import br.com.sawcunha.dayoffmarker.repository.TagRepository;
import br.com.sawcunha.dayoffmarker.specification.validator.Validator;
import br.com.sawcunha.dayoffmarker.specification.validator.ValidatorLink;
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
	public void validator(final TagRequestDTO tagRequestDTO) throws DayOffMarkerGenericException {
		if(tagRepository.existsByCode(tagRequestDTO.getCode())) throw new TagCodeExistException();
	}

	@Override
	public void validator(final Long tagID, final TagRequestDTO tagRequestDTO) throws DayOffMarkerGenericException {
		if(!tagRepository.existsById(tagID)) throw new TagNotExistException();
		if(tagRepository.existsByCodeAndNotId(tagRequestDTO.getCode(),tagID)) throw new TagCodeExistException();
	}

	@Override
	public void validator(final Long tagID) throws DayOffMarkerGenericException {
		if(!tagRepository.existsById(tagID)) throw new TagNotExistException();
	}

	@Override
	public void validateLink(
			final Long tagID,
			final LinkDayRequestDTO linkDayRequestDTO,
			final Long countryID
	) throws DayOffMarkerGenericException {
		Long sizeDays = (long) linkDayRequestDTO.getDaysID().size();
		if(!dayRepository.existsByDates(sizeDays , linkDayRequestDTO.getDaysID(), countryID)) throw new DayNotExistException();
		for (LocalDate date : linkDayRequestDTO.getDaysID()) {
			if(dayRepository.existsByDateAndTag(date, tagID, countryID)) throw new TagExistDayException(DateUtils.returnDate(date));
		}
	}
}
