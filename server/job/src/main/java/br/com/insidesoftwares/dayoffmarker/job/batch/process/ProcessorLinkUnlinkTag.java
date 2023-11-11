package br.com.insidesoftwares.dayoffmarker.job.batch.process;

import br.com.insidesoftwares.dayoffmarker.commons.dto.tag.TagLinkUnlinkRequestDTO;
import br.com.insidesoftwares.dayoffmarker.domain.entity.day.Day;
import br.com.insidesoftwares.dayoffmarker.domain.entity.request.Request;
import br.com.insidesoftwares.dayoffmarker.domain.entity.request.RequestParameter;
import br.com.insidesoftwares.dayoffmarker.domain.entity.tag.DayTag;
import br.com.insidesoftwares.dayoffmarker.domain.entity.tag.DayTagPK;
import br.com.insidesoftwares.dayoffmarker.domain.entity.tag.Tag;
import br.com.insidesoftwares.dayoffmarker.domain.repository.day.DayRepository;
import br.com.insidesoftwares.dayoffmarker.domain.specification.day.DaySpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static br.com.insidesoftwares.dayoffmarker.job.utils.request.RequestParametersUtils.getDay;
import static br.com.insidesoftwares.dayoffmarker.job.utils.request.RequestParametersUtils.getDayOfWeek;
import static br.com.insidesoftwares.dayoffmarker.job.utils.request.RequestParametersUtils.getDayOfYear;
import static br.com.insidesoftwares.dayoffmarker.job.utils.request.RequestParametersUtils.getMonth;
import static br.com.insidesoftwares.dayoffmarker.job.utils.request.RequestParametersUtils.getTagsID;
import static br.com.insidesoftwares.dayoffmarker.job.utils.request.RequestParametersUtils.getYear;

@Component
@RequiredArgsConstructor
public class ProcessorLinkUnlinkTag implements ItemProcessor<Request, List<DayTag>> {

    private final DayRepository dayRepository;

    @Override
    public List<DayTag> process(final Request request) {
        List<DayTag> dayTagList = new ArrayList<>();

        TagLinkUnlinkRequestDTO tagLinkUnlinkRequestDTO = createTagLinkRequestDTO(request.getRequestParameter());
        Specification<Day> daySpecification = DaySpecification.findAllDayByTagLinkRequestDTO(tagLinkUnlinkRequestDTO);

        List<Day> days = dayRepository.findAll(daySpecification);

        tagLinkUnlinkRequestDTO.tagsID().forEach(tagID -> days.forEach(day -> {
            boolean containsTag = containsTag(day.getTags(), tagID);
            switch (request.getTypeRequest()) {
                case LINK_TAG -> {
                    if (!containsTag) {
                        dayTagList.add(createDayTag(day.getId(), tagID));
                    }
                }
                case UNLINK_TAG -> {
                    if (containsTag) {
                        dayTagList.add(createDayTag(day.getId(), tagID));
                    }
                }
            }
        }));

        return dayTagList;
    }

    private TagLinkUnlinkRequestDTO createTagLinkRequestDTO(final Set<RequestParameter> requestParameters) {
        Set<UUID> tagsID = getTagsID(requestParameters);
        Integer day = getDay(requestParameters);
        Integer month = getMonth(requestParameters);
        Integer year = getYear(requestParameters);
        Integer dayOfYear = getDayOfYear(requestParameters);
        DayOfWeek dayOfWeek = getDayOfWeek(requestParameters);

        return TagLinkUnlinkRequestDTO.builder()
                .tagsID(tagsID)
                .day(day)
                .month(month)
                .year(year)
                .dayOfYear(dayOfYear)
                .dayOfWeek(dayOfWeek)
                .build();

    }

    private boolean containsTag(Set<Tag> tags, UUID tagID) {
        return tags.stream().anyMatch(tag -> tag.getId().equals(tagID));
    }

    private DayTag createDayTag(final UUID dayID, final UUID tagID) {
        DayTagPK dayTagPK = DayTagPK.builder()
                .dayID(dayID)
                .tagID(tagID)
                .build();

        return DayTag.builder()
                .id(dayTagPK)
                .build();
    }

}
