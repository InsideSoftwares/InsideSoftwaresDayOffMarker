package br.com.insidesoftwares.dayoffmarker.job.batch.write;

import br.com.insidesoftwares.dayoffmarker.domain.entity.tag.DayTag;
import br.com.insidesoftwares.dayoffmarker.domain.repository.day.DayTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WriteUnlinkTag implements ItemWriter<List<DayTag>> {

	private final DayTagRepository dayTagRepository;

    @Override
    public void write(Chunk<? extends List<DayTag>> links)  {
		links.getItems().forEach(dayTags -> dayTagRepository.deleteAllInBatch(dayTags));
		dayTagRepository.flush();
    }
}
