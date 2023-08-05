package br.com.insidesoftwares.dayoffmarker.job.batch.write;

import br.com.insidesoftwares.dayoffmarker.entity.day.DayTag;
import br.com.insidesoftwares.dayoffmarker.repository.day.DayTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WriteLinkTag implements ItemWriter<List<DayTag>> {

	private final DayTagRepository dayTagRepository;

    @Override
    public void write(Chunk<? extends List<DayTag>> links)  {
		links.getItems().forEach(dayTags -> dayTagRepository.saveAllAndFlush(dayTags));
    }
}
