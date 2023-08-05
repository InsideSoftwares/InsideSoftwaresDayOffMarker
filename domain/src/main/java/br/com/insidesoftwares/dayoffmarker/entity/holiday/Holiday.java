package br.com.insidesoftwares.dayoffmarker.entity.holiday;

import br.com.insidesoftwares.dayoffmarker.commons.enumeration.TypeHoliday;
import br.com.insidesoftwares.dayoffmarker.entity.day.Day;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "DOM_HOLIDAY")
@NamedEntityGraph(
	name = "holiday-full",
	attributeNodes = {
		@NamedAttributeNode("day")
	}
)
public class Holiday {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "HOLIDAY_ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE")
    private TypeHoliday holidayType;

    @Column(name = "FROM_TIME")
    private LocalTime fromTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DAY_ID")
    private Day day;

	@Column(name = "OPTIONAL")
	private boolean optional;

	@Column(name = "AUTOMATIC_UPDATE")
	private boolean automaticUpdate;

	@Column(name = "FIXED_HOLIDAY_ID")
	private Long fixedHolidayID;
}
