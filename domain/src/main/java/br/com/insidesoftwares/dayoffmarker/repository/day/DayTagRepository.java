package br.com.insidesoftwares.dayoffmarker.repository.day;

import br.com.insidesoftwares.dayoffmarker.entity.day.DayTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DayTagRepository extends JpaRepository<DayTag, Long> {}
