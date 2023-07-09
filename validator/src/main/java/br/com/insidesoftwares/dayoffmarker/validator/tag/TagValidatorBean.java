package br.com.insidesoftwares.dayoffmarker.validator.tag;

import br.com.insidesoftwares.dayoffmarker.commons.dto.request.tag.TagLinkRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.tag.TagRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.tag.TagCodeExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.tag.TagLinkOneParameterNotNullException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.tag.TagNotExistException;
import br.com.insidesoftwares.dayoffmarker.repository.TagRepository;
import br.com.insidesoftwares.dayoffmarker.specification.validator.Validator;
import br.com.insidesoftwares.dayoffmarker.specification.validator.ValidatorLink;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
class TagValidatorBean implements Validator<Long, TagRequestDTO>, ValidatorLink<TagLinkRequestDTO> {

	private final TagRepository tagRepository;

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
	public void validateLink(final TagLinkRequestDTO linkDayRequestDTO) {

		if(
			Objects.isNull(linkDayRequestDTO.dayOfWeek()) &&
			Objects.isNull(linkDayRequestDTO.dayOfYear()) &&
			Objects.isNull(linkDayRequestDTO.day()) &&
			Objects.isNull(linkDayRequestDTO.month()) &&
			Objects.isNull(linkDayRequestDTO.year())
		) throw new TagLinkOneParameterNotNullException();

		if(
				Objects.nonNull(linkDayRequestDTO.day()) &&
				Objects.nonNull(linkDayRequestDTO.month()) &&
				Objects.nonNull(linkDayRequestDTO.year())
		) {

		}


	}
}
