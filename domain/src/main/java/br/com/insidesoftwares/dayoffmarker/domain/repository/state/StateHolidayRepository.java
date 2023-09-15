package br.com.insidesoftwares.dayoffmarker.domain.repository.state;

import br.com.insidesoftwares.dayoffmarker.domain.entity.state.StateHoliday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateHolidayRepository extends JpaRepository<StateHoliday, Long> {

}
