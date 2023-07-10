package br.com.insidesoftwares.dayoffmarker.validator.tag;

import br.com.insidesoftwares.commons.utils.DateUtils;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.tag.TagLinkRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.tag.TagRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.tag.TagCodeExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.tag.TagLinkDateInvalidException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.tag.TagLinkNotExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.tag.TagLinkOneParameterNotNullException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.tag.TagLinkParameterNotResultException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.tag.TagNotExistException;
import br.com.insidesoftwares.dayoffmarker.entity.Day;
import br.com.insidesoftwares.dayoffmarker.repository.DayRepository;
import br.com.insidesoftwares.dayoffmarker.repository.TagRepository;
import br.com.insidesoftwares.dayoffmarker.specification.DaySpecification;
import br.com.insidesoftwares.dayoffmarker.specification.validator.Validator;
import br.com.insidesoftwares.dayoffmarker.specification.validator.ValidatorLink;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
class TagValidatorBean implements Validator<Long, TagRequestDTO>, ValidatorLink<TagLinkRequestDTO> {

	private final TagRepository tagRepository;
	private final DayRepository dayRepository;

	@Override
	public void validator(final TagRequestDTO tagRequestDTO) {
		if(tagRepository.existsByCode(tagRequestDTO.code())) throw new TagCodeExistException();
	}

	@Override
	public void validator(final Long tagID, final TagRequestDTO tagRequestDTO) {
		if(!tagRepository.existsById(tagID)) throw new TagNotExistException();
		if(tagRepository.existsByCodeAndNotId(tagRequestDTO.code(),tagID)) throw new TagCodeExistException();
	}

	@Override
	public void validator(final Long tagID) {
		if(!tagRepository.existsById(tagID)) throw new TagNotExistException();
	}

	@Override
	public void validateLinkUnlink(final TagLinkRequestDTO tagLinkRequestDTO) {

		if(
			Objects.isNull(tagLinkRequestDTO.dayOfWeek()) &&
			Objects.isNull(tagLinkRequestDTO.dayOfYear()) &&
			Objects.isNull(tagLinkRequestDTO.day()) &&
			Objects.isNull(tagLinkRequestDTO.month()) &&
			Objects.isNull(tagLinkRequestDTO.year())
		) throw new TagLinkOneParameterNotNullException();

		if(
				Objects.nonNull(tagLinkRequestDTO.day()) &&
				Objects.nonNull(tagLinkRequestDTO.month()) &&
				Objects.nonNull(tagLinkRequestDTO.year()) &&
					!DateUtils.isDateValid(tagLinkRequestDTO.day(), tagLinkRequestDTO.month(), tagLinkRequestDTO.year())
		) {
			throw new TagLinkDateInvalidException();
		}

		if(
			Objects.nonNull(tagLinkRequestDTO.day()) &&
				Objects.nonNull(tagLinkRequestDTO.month()) &&
				!DateUtils.isDateValid(tagLinkRequestDTO.day(), tagLinkRequestDTO.month())
		) {
			throw new TagLinkDateInvalidException();
		}

		Specification<Day> daySpecification = DaySpecification.exitsDayByTagLinkRequestDTO(tagLinkRequestDTO);
		boolean exitsDayByTagLinkRequestDTO = dayRepository.exists(daySpecification);

		if(!exitsDayByTagLinkRequestDTO){
			throw new TagLinkParameterNotResultException();
		}

		tagLinkRequestDTO.tagsID().forEach(tagID -> {
			if(!tagRepository.existsById(tagID)) throw new TagLinkNotExistException(tagID);
		});

	}
}
