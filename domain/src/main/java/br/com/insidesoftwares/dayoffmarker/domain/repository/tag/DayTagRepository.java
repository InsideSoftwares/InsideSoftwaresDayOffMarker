package br.com.insidesoftwares.dayoffmarker.domain.repository.tag;

import br.com.insidesoftwares.dayoffmarker.domain.entity.tag.DayTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DayTagRepository extends JpaRepository<DayTag, UUID> {
}
