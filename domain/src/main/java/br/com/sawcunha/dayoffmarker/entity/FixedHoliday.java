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
import java.time.LocalTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "DCY_FIXED_HOLIDAY")
public class FixedHoliday {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "FIXED_HOLIDAY_ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "DAY_HOLIDAY")
    private Integer day;

    @Column(name = "MONTH_HOLIDAY")
    private Integer month;

    @Column(name = "OPTIONAL")
    private boolean isOptional;

    @Column(name = "FROM_TIME")
    private LocalTime fromTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COUNTRY_ID")
    private Country country;

}
