package br.com.sawcunha.dayoffmarker.entity;

import lombok.AllArgsConstructor;
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
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Year;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "DCY_DAY")
public class Day {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "DAY_ID")
    private Long id;

    @Column(name = "DATE")
    private LocalDate date;

    @Column(name = "WEEKEND")
    private boolean isWeekend;

    @Column(name = "HOLIDAY")
    private boolean isHoliday;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COUNTRY_ID")
    private Country country;

    public DayOfWeek getDiaSemana() {
        return date.getDayOfWeek();
    }

    public boolean isLeap(){
        return Year.isLeap(this.date.getYear());
    }

    @Override
    public String toString() {
        return "Day{" +
                "id=" + id +
                ", date=" + date +
                ", isWeekend=" + isWeekend +
                ", isHoliday=" + isHoliday +
                '}';
    }
}
