package br.com.sawcunha.dayoffmarker.service;

import br.com.sawcunha.dayoffmarker.commons.dto.DayOffMarkerResponse;
import br.com.sawcunha.dayoffmarker.commons.dto.request.link.LinkDayRequestDTO;
import br.com.sawcunha.dayoffmarker.commons.dto.request.tag.TagRequestDTO;
import br.com.sawcunha.dayoffmarker.commons.dto.response.tag.TagResponseDTO;
import br.com.sawcunha.dayoffmarker.commons.enums.sort.eOrderTag;
import br.com.sawcunha.dayoffmarker.commons.exception.error.DayOffMarkerGenericException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.DayOffMarkerGenericRuntimeException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.country.CountryNameInvalidException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.day.DayNotExistException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.tag.TagCodeExistException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.tag.TagExistDayException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.tag.TagNotExistException;
import br.com.sawcunha.dayoffmarker.commons.utils.PaginationUtils;
import br.com.sawcunha.dayoffmarker.entity.Country;
import br.com.sawcunha.dayoffmarker.entity.Tag;
import br.com.sawcunha.dayoffmarker.mapper.TagMapper;
import br.com.sawcunha.dayoffmarker.repository.TagRepository;
import br.com.sawcunha.dayoffmarker.specification.service.CountryService;
import br.com.sawcunha.dayoffmarker.specification.service.DayService;
import br.com.sawcunha.dayoffmarker.specification.service.TagService;
import br.com.sawcunha.dayoffmarker.specification.validator.Validator;
import br.com.sawcunha.dayoffmarker.specification.validator.ValidatorLink;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TagServiceBean implements TagService {

    private final TagRepository tagRepository;
	private final DayService dayService;
	private final CountryService countryService;
    private final TagMapper tagMapper;
	private final Validator<Long, TagRequestDTO> tagValidator;
	private final ValidatorLink<Long, LinkDayRequestDTO> linkValidator;

    @Override
    public DayOffMarkerResponse<List<TagResponseDTO>> findAll(
			final int page,
            final int sizePerPage,
            final Sort.Direction direction,
            final eOrderTag orderTag
    ) throws DayOffMarkerGenericException {

		Pageable pageable = PaginationUtils.createPageable(page, sizePerPage, direction, orderTag);

		Page<Tag> tagPage = tagRepository.findAll(pageable);

        return DayOffMarkerResponse.<List<TagResponseDTO>>builder()
				.data(tagMapper.toDTOs(tagPage.getContent()))
				.paginated(
					PaginationUtils.createPaginated(
						tagPage.getTotalPages(),
						tagPage.getTotalElements(),
						sizePerPage
					)
				)
				.build();
    }

    @Override
    public DayOffMarkerResponse<TagResponseDTO> findById(final Long tagID) throws DayOffMarkerGenericException {
		Tag tag = tagRepository.findById(tagID).orElseThrow(TagNotExistException::new);
		return DayOffMarkerResponse.<TagResponseDTO>builder()
				.data(tagMapper.toDTO(tag))
				.build();
    }

	@Transactional(rollbackFor = {
			TagCodeExistException.class
	})
    @Override
    public DayOffMarkerResponse<TagResponseDTO> save(final TagRequestDTO tagRequestDTO) throws DayOffMarkerGenericException {
		tagValidator.validator(tagRequestDTO);

		Tag tag = tagMapper.toEntity(tagRequestDTO);
		tag = tagRepository.save(tag);

		return DayOffMarkerResponse.<TagResponseDTO>builder()
				.data(tagMapper.toDTO(tag))
				.build();
    }

	@Transactional(rollbackFor = {
			TagNotExistException.class,
			TagCodeExistException.class
	})
    @Override
    public DayOffMarkerResponse<TagResponseDTO> update(
            final Long tagID,
            final TagRequestDTO tagRequestDTO
    ) throws DayOffMarkerGenericException {
		tagValidator.validator(tagID, tagRequestDTO);

		Tag tag = tagRepository.getById(tagID);

		tag.setCode(tagRequestDTO.getCode());
		tag.setDescription(tagRequestDTO.getDescription());

		tag = tagRepository.save(tag);

		return DayOffMarkerResponse.<TagResponseDTO>builder()
				.data(tagMapper.toDTO(tag))
				.build();
    }

	@Transactional(rollbackFor = {
			TagNotExistException.class,
			TagExistDayException.class,
			DayNotExistException.class,
			DayOffMarkerGenericRuntimeException.class,
			CountryNameInvalidException.class
	})
	@Override
	public void linkDay(final Long tagID, final LinkDayRequestDTO linkDayRequestDTO) throws DayOffMarkerGenericException {
		tagValidator.validator(tagID);
		linkValidator.validateLink(tagID, linkDayRequestDTO);
		Tag tag = tagRepository.getById(tagID);
		Country country = countryService.findCountryByNameOrDefault("Brazil");
		linkDayRequestDTO.getDaysID().forEach(date -> {
			try {
				dayService.linkTagDay(date, tag, country);
			} catch (DayOffMarkerGenericException e) {
				e.printStackTrace();
				throw new DayOffMarkerGenericRuntimeException(e.getCode());
			}
		});
	}
}
