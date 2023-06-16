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

import java.time.LocalTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "DOM_FIXED_HOLIDAY")
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

}
