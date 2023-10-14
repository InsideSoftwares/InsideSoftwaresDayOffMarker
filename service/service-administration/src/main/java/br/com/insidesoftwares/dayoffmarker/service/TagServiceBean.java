package br.com.insidesoftwares.dayoffmarker.service;

import br.com.insidesoftwares.commons.annotation.InsideAudit;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.tag.TagLinkUnlinkRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.tag.TagRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.response.tag.TagLinkResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.request.ParameterNotExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.request.RequestConflictParametersException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.tag.TagCodeExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.tag.TagLinkDateInvalidException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.tag.TagLinkNotExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.tag.TagLinkOneParameterNotNullException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.tag.TagLinkParameterNotResultException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.tag.TagNotExistException;
import br.com.insidesoftwares.dayoffmarker.domain.entity.tag.Tag;
import br.com.insidesoftwares.dayoffmarker.domain.mapper.TagMapper;
import br.com.insidesoftwares.dayoffmarker.domain.repository.day.TagRepository;
import br.com.insidesoftwares.dayoffmarker.specification.service.TagService;
import br.com.insidesoftwares.dayoffmarker.specification.service.request.RequestCreationService;
import br.com.insidesoftwares.dayoffmarker.specification.validator.Validator;
import br.com.insidesoftwares.dayoffmarker.specification.validator.ValidatorLink;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
class TagServiceBean implements TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;
    private final Validator<Long, TagRequestDTO> tagValidator;
    private final ValidatorLink<TagLinkUnlinkRequestDTO> tagLinkValidator;
    private final RequestCreationService requestCreationService;

    @InsideAudit
    @Transactional(rollbackFor = {
            TagCodeExistException.class
    })
    @Override
    public InsideSoftwaresResponseDTO<Long> save(final TagRequestDTO tagRequestDTO) {
        tagValidator.validator(tagRequestDTO);

        Tag tag = tagMapper.toEntity(tagRequestDTO);
        tag = tagRepository.save(tag);

        return InsideSoftwaresResponseDTO.<Long>builder().data(tag.getId()).build();
    }

    @InsideAudit
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

    @InsideAudit
    @Transactional(rollbackFor = {
            TagLinkOneParameterNotNullException.class,
            TagLinkDateInvalidException.class,
            TagLinkParameterNotResultException.class,
            TagLinkNotExistException.class,
            RequestConflictParametersException.class,
            ParameterNotExistException.class
    })
    @Override
    public InsideSoftwaresResponseDTO<TagLinkResponseDTO> linkTagByDay(final TagLinkUnlinkRequestDTO tagLinkUnlinkRequestDTO) {
        tagLinkValidator.validateLinkUnlink(tagLinkUnlinkRequestDTO);

        String requestID = requestCreationService.createLinkTagsInDays(tagLinkUnlinkRequestDTO);

        return InsideSoftwaresResponseDTO.<TagLinkResponseDTO>builder()
                .data(TagLinkResponseDTO.builder().requestID(requestID).build())
                .build();
    }

    @InsideAudit
    @Transactional(rollbackFor = {
            TagLinkOneParameterNotNullException.class,
            TagLinkDateInvalidException.class,
            TagLinkParameterNotResultException.class,
            TagLinkNotExistException.class,
            RequestConflictParametersException.class,
            ParameterNotExistException.class
    })
    @Override
    public InsideSoftwaresResponseDTO<TagLinkResponseDTO> unlinkTagByDay(final TagLinkUnlinkRequestDTO tagLinkUnlinkRequestDTO) {
        tagLinkValidator.validateLinkUnlink(tagLinkUnlinkRequestDTO);

        String requestID = requestCreationService.createUnlinkTagsInDays(tagLinkUnlinkRequestDTO);

        return InsideSoftwaresResponseDTO.<TagLinkResponseDTO>builder()
                .data(TagLinkResponseDTO.builder().requestID(requestID).build())
                .build();
    }

    private Tag findTagById(final Long tagID) {
        return tagRepository.findById(tagID).orElseThrow(TagNotExistException::new);
    }

}
