package br.com.sawcunha.dayoffmarker.validator.tag;

import br.com.sawcunha.dayoffmarker.commons.dto.request.TagRequestDTO;
import br.com.sawcunha.dayoffmarker.commons.exception.error.DayOffMarkerGenericException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.tag.TagCodeExistException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.tag.TagNotExistException;
import br.com.sawcunha.dayoffmarker.repository.TagRepository;
import br.com.sawcunha.dayoffmarker.specification.validator.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class TagValidatorBean implements Validator<Long, TagRequestDTO> {

	private final TagRepository tagRepository;

	@Transactional(readOnly = true)
	@Override
	public void validator(TagRequestDTO tagRequestDTO) throws DayOffMarkerGenericException {
		if(tagRepository.existsByCode(tagRequestDTO.getCode())) throw new TagCodeExistException();
	}

	@Transactional(readOnly = true)
	@Override
	public void validator(Long tagID, TagRequestDTO tagRequestDTO) throws DayOffMarkerGenericException {
		if(!tagRepository.existsById(tagID)) throw new TagNotExistException();
		if(tagRepository.existsByCodeAndNotId(tagRequestDTO.getCode(),tagID)) throw new TagCodeExistException();
	}
}
