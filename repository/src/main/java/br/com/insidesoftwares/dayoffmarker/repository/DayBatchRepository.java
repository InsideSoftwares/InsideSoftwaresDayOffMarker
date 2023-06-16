package br.com.insidesoftwares.dayoffmarker.repository;

import br.com.insidesoftwares.dayoffmarker.entity.DayBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface DayBatchRepository extends JpaRepository<DayBatch, Long> {

    @Query("SELECT d FROM DayBatch d WHERE d.requestID in :requestsID AND d.isProcessed = :processed")
    List<DayBatch> findAllByRequestIDAndProcess(List<UUID>requestsID, boolean processed);

	boolean existsByDate(final LocalDate date);
}
