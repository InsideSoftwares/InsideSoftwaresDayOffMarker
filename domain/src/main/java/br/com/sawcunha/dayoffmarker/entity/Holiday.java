package br.com.sawcunha.dayoffmarker.entity;

import br.com.sawcunha.dayoffmarker.commons.enums.eTypeHoliday;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "DOM_HOLIDAY")
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
    private eTypeHoliday holidayType;

    @Column(name = "FROM_TIME")
    private LocalTime fromTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DAY_ID")
    private Day day;

	@Column(name = "OPTIONAL")
	private boolean optional;

	@Column(name = "AUTOMATIC_UPDATE")
	private boolean automaticUpdate;

}
