package br.com.insidesoftwares.dayoffmarker.service;

import br.com.insidesoftwares.commons.annotation.InsideAudit;
import br.com.insidesoftwares.commons.dto.request.InsidePaginationFilterDTO;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.commons.utils.PaginationUtils;
import br.com.insidesoftwares.dayoffmarker.commons.dto.response.tag.TagResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.sort.eOrderTag;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.tag.TagNotExistException;
import br.com.insidesoftwares.dayoffmarker.domain.entity.tag.Tag;
import br.com.insidesoftwares.dayoffmarker.domain.mapper.TagMapper;
import br.com.insidesoftwares.dayoffmarker.domain.repository.day.TagRepository;
import br.com.insidesoftwares.dayoffmarker.specification.search.TagSearchService;
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
class TagSearchServiceBean implements TagSearchService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    @InsideAudit
    @Override
    public InsideSoftwaresResponseDTO<List<TagResponseDTO>> findAll(final InsidePaginationFilterDTO paginationFilter) {
        Pageable pageable = PaginationUtils.createPageable(paginationFilter, eOrderTag.ID);

        Page<Tag> tagPage = tagRepository.findAll(pageable);

        return InsideSoftwaresResponseDTO.<List<TagResponseDTO>>builder()
                .data(tagMapper.toDTOs(tagPage.getContent()))
                .insidePaginatedDTO(
                        PaginationUtils.createPaginated(
                                tagPage.getTotalPages(),
                                tagPage.getTotalElements(),
                                tagPage.getContent().size(),
                                paginationFilter.getSizePerPage()
                        )
                )
                .build();
    }

    @InsideAudit
    @Override
    public InsideSoftwaresResponseDTO<TagResponseDTO> findById(final Long tagID) {
        Tag tag = findTagById(tagID);

        return InsideSoftwaresResponseDTO.<TagResponseDTO>builder()
                .data(tagMapper.toDTO(tag))
                .build();
    }

    private Tag findTagById(final Long tagID) {
        return tagRepository.findById(tagID).orElseThrow(TagNotExistException::new);
    }

}
