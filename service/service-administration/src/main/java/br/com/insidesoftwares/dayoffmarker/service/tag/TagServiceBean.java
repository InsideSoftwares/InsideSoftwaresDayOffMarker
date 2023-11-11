package br.com.insidesoftwares.dayoffmarker.service.tag;

import br.com.insidesoftwares.commons.annotation.InsideAudit;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.tag.TagLinkResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.tag.TagLinkUnlinkRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.tag.TagRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.request.ParameterNotExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.request.RequestConflictParametersException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.tag.TagCodeExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.tag.TagLinkDateInvalidException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.tag.TagLinkNotExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.tag.TagLinkOneParameterNotNullException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.tag.TagLinkParameterNotResultException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.tag.TagNotExistException;
import br.com.insidesoftwares.dayoffmarker.domain.entity.tag.Tag;
import br.com.insidesoftwares.dayoffmarker.domain.mapper.tag.TagMapper;
import br.com.insidesoftwares.dayoffmarker.domain.repository.tag.TagRepository;
import br.com.insidesoftwares.dayoffmarker.specification.service.request.RequestCreationService;
import br.com.insidesoftwares.dayoffmarker.specification.service.tag.TagService;
import br.com.insidesoftwares.dayoffmarker.specification.validator.Validator;
import br.com.insidesoftwares.dayoffmarker.specification.validator.ValidatorLink;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
class TagServiceBean implements TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;
    private final Validator<UUID, TagRequestDTO> tagValidator;
    private final ValidatorLink<TagLinkUnlinkRequestDTO> tagLinkValidator;
    private final RequestCreationService requestCreationService;

    @InsideAudit
    @Transactional(rollbackFor = {
            TagCodeExistException.class
    })
    @Override
    public InsideSoftwaresResponseDTO<UUID> save(final TagRequestDTO tagRequestDTO) {
        tagValidator.validator(tagRequestDTO);

        Tag tag = tagMapper.toEntity(tagRequestDTO);
        tag = tagRepository.save(tag);

        return InsideSoftwaresResponseDTO.<UUID>builder().data(tag.getId()).build();
    }

    @InsideAudit
    @Transactional(rollbackFor = {
            TagNotExistException.class,
            TagCodeExistException.class
    })
    @Override
    public void update(
            final UUID tagID,
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

    private Tag findTagById(final UUID tagID) {
        return tagRepository.findById(tagID).orElseThrow(TagNotExistException::new);
    }

}
