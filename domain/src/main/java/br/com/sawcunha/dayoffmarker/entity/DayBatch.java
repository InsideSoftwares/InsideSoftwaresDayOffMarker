package br.com.sawcunha.dayoffmarker.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "DCY_DAY_BATCH")
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COUNTRY_ID")
    private Country country;

    @Column(name = "PROCESSED")
    private boolean isProcessed;

}
