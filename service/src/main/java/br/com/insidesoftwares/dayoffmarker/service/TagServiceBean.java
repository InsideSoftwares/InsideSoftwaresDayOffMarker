package br.com.insidesoftwares.dayoffmarker.service;

import br.com.insidesoftwares.commons.dto.request.PaginationFilter;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponse;
import br.com.insidesoftwares.commons.utils.PaginationUtils;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.tag.TagLinkRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.tag.TagRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.response.tag.TagLinkResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.response.tag.TagResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.sort.eOrderTag;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.request.ParameterNotExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.request.RequestConflictParametersException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.tag.TagCodeExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.tag.TagLinkDateInvalidException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.tag.TagLinkNotExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.tag.TagLinkOneParameterNotNullException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.tag.TagLinkParameterNotResultException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.tag.TagNotExistException;
import br.com.insidesoftwares.dayoffmarker.entity.day.Tag;
import br.com.insidesoftwares.dayoffmarker.mapper.TagMapper;
import br.com.insidesoftwares.dayoffmarker.repository.day.TagRepository;
import br.com.insidesoftwares.dayoffmarker.specification.service.RequestCreationService;
import br.com.insidesoftwares.dayoffmarker.specification.service.TagService;
import br.com.insidesoftwares.dayoffmarker.specification.validator.Validator;
import br.com.insidesoftwares.dayoffmarker.specification.validator.ValidatorLink;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
class TagServiceBean implements TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;
	private final Validator<Long, TagRequestDTO> tagValidator;
	private final ValidatorLink<TagLinkRequestDTO> tagLinkValidator;
	private final RequestCreationService requestCreationService;

    @Override
    public InsideSoftwaresResponse<List<TagResponseDTO>> findAll(final PaginationFilter<eOrderTag> paginationFilter) {

		Pageable pageable = PaginationUtils.createPageable(paginationFilter);

		Page<Tag> tagPage = tagRepository.findAll(pageable);

        return InsideSoftwaresResponse.<List<TagResponseDTO>>builder()
				.data(tagMapper.toDTOs(tagPage.getContent()))
				.paginatedDTO(
					PaginationUtils.createPaginated(
						tagPage.getTotalPages(),
						tagPage.getTotalElements(),
						tagPage.getContent().size(),
						paginationFilter.getSizePerPage()
					)
				)
				.build();
    }

    @Override
    public InsideSoftwaresResponse<TagResponseDTO> findById(final Long tagID) {
		Tag tag = findTagById(tagID);
		return InsideSoftwaresResponse.<TagResponseDTO>builder()
				.data(tagMapper.toDTO(tag))
				.build();
    }

	@Transactional(rollbackFor = {
			TagCodeExistException.class
	})
    @Override
    public void save(final TagRequestDTO tagRequestDTO) {
		tagValidator.validator(tagRequestDTO);

		Tag tag = tagMapper.toEntity(tagRequestDTO);
		tagRepository.save(tag);
    }

	@Transactional(rollbackFor = {
			TagNotExistException.class,
			TagCodeExistException.class
	})
    @Override
    public void update(
            final Long tagID,
            final TagRequestDTO tagRequestDTO
	) {
		tagValidator.validator(tagID, tagRequestDTO);

		Tag tag = findTagById(tagID);

		tag.setCode(tagRequestDTO.code());
		tag.setDescription(tagRequestDTO.description());

		tagRepository.save(tag);
    }

	@Transactional(rollbackFor = {
		TagLinkOneParameterNotNullException.class,
		TagLinkDateInvalidException.class,
		TagLinkParameterNotResultException.class,
		TagLinkNotExistException.class,
		RequestConflictParametersException.class,
		ParameterNotExistException.class
	})
	@Override
	public InsideSoftwaresResponse<TagLinkResponseDTO> linkTagByDay(final TagLinkRequestDTO tagLinkRequestDTO) {
		tagLinkValidator.validateLinkUnlink(tagLinkRequestDTO);

		String requestID = requestCreationService.createLinkTagsInDays(tagLinkRequestDTO);

		return InsideSoftwaresResponse.<TagLinkResponseDTO>builder()
			.data(TagLinkResponseDTO.builder().requestID(requestID).build())
			.build();
	}

	@Transactional(rollbackFor = {
		TagLinkOneParameterNotNullException.class,
		TagLinkDateInvalidException.class,
		TagLinkParameterNotResultException.class,
		TagLinkNotExistException.class,
		RequestConflictParametersException.class,
		ParameterNotExistException.class
	})
	@Override
	public InsideSoftwaresResponse<TagLinkResponseDTO> unlinkTagByDay(final TagLinkRequestDTO tagLinkRequestDTO) {
		tagLinkValidator.validateLinkUnlink(tagLinkRequestDTO);

		String requestID = requestCreationService.createUnlinkTagsInDays(tagLinkRequestDTO);

		return InsideSoftwaresResponse.<TagLinkResponseDTO>builder()
			.data(TagLinkResponseDTO.builder().requestID(requestID).build())
			.build();
	}

	private Tag findTagById(final Long tagID) {
		return tagRepository.findById(tagID).orElseThrow(TagNotExistException::new);
	}

}
