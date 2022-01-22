package br.com.sawcunha.dayoffmarker.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Year;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "DOM_DAY")
@ToString
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

	@OneToOne(mappedBy = "day")
	private Holiday holiday;

	@Column(name = "DAY_TAG")
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="DOM_DAY_TAG", joinColumns=
			{@JoinColumn(name="DAY_ID")}, inverseJoinColumns=
			{@JoinColumn(name="TAG_ID")})
	private Set<Tag> tags;

    public DayOfWeek getDiaSemana() {
        return date.getDayOfWeek();
    }

    public boolean isLeap(){
        return Year.isLeap(this.date.getYear());
    }

	public void addTag(Tag tag){
		if(Objects.isNull(tags)){
			tags = new HashSet<>();
		}
		tags.add(tag);
	}
}
