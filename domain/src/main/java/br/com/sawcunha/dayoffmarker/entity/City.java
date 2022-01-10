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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Set;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "DCY_CITY")
public class City {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "CITY_ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "ACRONYM")
    private String acronym;

    @Column(name = "CODE")
    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STATE_ID")
    private State state;

    @Column(name = "CITY_HOLIDAY")
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="DCY_CITY_HOLIDAY", joinColumns=
            {@JoinColumn(name="CITY_ID")}, inverseJoinColumns=
            {@JoinColumn(name="HOLIDAY_ID")})
    private Set<Holiday> holidays;

    @Column(name = "CITY_NOT_HOLIDAY")
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="DCY_CITY_NOT_HOLIDAY", joinColumns=
            {@JoinColumn(name="CITY_ID")}, inverseJoinColumns=
            {@JoinColumn(name="HOLIDAY_ID")})
    private Set<Holiday> notHolidays;

}
