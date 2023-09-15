package br.com.insidesoftwares.dayoffmarker.job.batch.process;

import br.com.insidesoftwares.dayoffmarker.commons.dto.request.tag.TagLinkRequestDTO;
import br.com.insidesoftwares.dayoffmarker.domain.entity.day.Day;
import br.com.insidesoftwares.dayoffmarker.domain.entity.request.Request;
import br.com.insidesoftwares.dayoffmarker.domain.entity.request.RequestParameter;
import br.com.insidesoftwares.dayoffmarker.domain.entity.tag.DayTag;
import br.com.insidesoftwares.dayoffmarker.domain.entity.tag.DayTagPK;
import br.com.insidesoftwares.dayoffmarker.domain.entity.tag.Tag;
import br.com.insidesoftwares.dayoffmarker.domain.repository.day.DayRepository;
import br.com.insidesoftwares.dayoffmarker.domain.specification.DaySpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

		TagLinkRequestDTO tagLinkRequestDTO = createTagLinkRequestDTO(request.getRequestParameter());
		Specification<Day> daySpecification = DaySpecification.findAllDayByTagLinkRequestDTO(tagLinkRequestDTO);

		List<Day> days = dayRepository.findAll(daySpecification);

		tagLinkRequestDTO.tagsID().forEach(tagID -> days.forEach(day -> {
			boolean containsTag = containsTag(day.getTags(), tagID);
            switch (request.getTypeRequest()) {
                case LINK_TAG -> {
                    if(!containsTag) {
                        dayTagList.add(createDayTag(day.getId(), tagID));
                    }
                }
                case UNLINK_TAG -> {
                    if(containsTag) {
                        dayTagList.add(createDayTag(day.getId(), tagID));
                    }
                }
            }
		}));

        return dayTagList;
    }

	private TagLinkRequestDTO createTagLinkRequestDTO(final Set<RequestParameter> requestParameters){
		Set<Long> tagsID = getTagsID(requestParameters);
		Integer day = getDay(requestParameters);
		Integer month = getMonth(requestParameters);
		Integer year = getYear(requestParameters);
		Integer dayOfYear = getDayOfYear(requestParameters);
		DayOfWeek dayOfWeek =  getDayOfWeek(requestParameters);

		return TagLinkRequestDTO.builder()
			.tagsID(tagsID)
			.day(day)
			.month(month)
			.year(year)
			.dayOfYear(dayOfYear)
			.dayOfWeek(dayOfWeek)
			.build();

	}

	private boolean containsTag(Set<Tag> tags, Long tagID){
		return tags.stream().anyMatch(tag -> tag.getId().equals(tagID));
	}

    private DayTag createDayTag(final Long dayID, final Long tagID) {
        DayTagPK dayTagPK = DayTagPK.builder()
                .dayID(dayID)
                .tagID(tagID)
                .build();

        return DayTag.builder()
                .id(dayTagPK)
                .build();
    }

}
