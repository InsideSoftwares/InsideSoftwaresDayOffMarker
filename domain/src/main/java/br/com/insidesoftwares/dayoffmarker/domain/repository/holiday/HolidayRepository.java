package br.com.insidesoftwares.dayoffmarker.domain.repository.holiday;

import br.com.insidesoftwares.dayoffmarker.domain.entity.holiday.Holiday;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface HolidayRepository extends JpaRepository<Holiday, UUID>, JpaSpecificationExecutor<Holiday>, QuerydslPredicateExecutor<Holiday> {

    @EntityGraph(value = "holiday-full")
    @Override
    Page<Holiday> findAll(Specification<Holiday> holidaySpecification, Pageable pageable);
}
