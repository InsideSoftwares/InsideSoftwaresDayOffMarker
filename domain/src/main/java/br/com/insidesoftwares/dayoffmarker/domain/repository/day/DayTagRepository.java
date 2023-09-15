package br.com.insidesoftwares.dayoffmarker.domain.repository.day;

import br.com.insidesoftwares.dayoffmarker.domain.entity.tag.DayTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DayTagRepository extends JpaRepository<DayTag, Long> {}
