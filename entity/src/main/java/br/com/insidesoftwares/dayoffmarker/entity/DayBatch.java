package br.com.insidesoftwares.dayoffmarker.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "DOM_DAY_BATCH")
public class DayBatch {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "DAY_BATCH_ID")
    private Long id;

    @Column(name = "REQUEST_ID")
    private UUID requestID;

    @Column(name = "DATE")
    private LocalDate date;

    @Column(name = "WEEKEND")
    private boolean isWeekend;

    @Column(name = "HOLIDAY")
    private boolean isHoliday;

    @Column(name = "PROCESSED")
    private boolean isProcessed;

}
