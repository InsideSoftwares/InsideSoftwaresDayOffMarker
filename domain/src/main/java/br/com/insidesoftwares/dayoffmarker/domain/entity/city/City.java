package br.com.insidesoftwares.dayoffmarker.domain.entity.city;

import br.com.insidesoftwares.dayoffmarker.domain.entity.state.State;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedEntityGraphs;
import jakarta.persistence.NamedSubgraph;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "DOM_CITY")
@NamedEntityGraphs({
	@NamedEntityGraph(
		name = "city-full",
		attributeNodes = {
			@NamedAttributeNode("state")
		}
	),
	@NamedEntityGraph(
		name = "city-holiday",
		attributeNodes = {
			@NamedAttributeNode("state"),
			@NamedAttributeNode(value = "cityHolidays", subgraph = "holiday")
		},
		subgraphs = {
			@NamedSubgraph(
				name = "holiday",
				attributeNodes = {
					@NamedAttributeNode("holiday")
				}
			)
		}
	),
	@NamedEntityGraph(
		name = "city-full-holiday",
		attributeNodes = {
			@NamedAttributeNode(value = "state", subgraph = "state-holidays"),
			@NamedAttributeNode(value = "cityHolidays", subgraph = "holiday")
		},
		subgraphs = {
			@NamedSubgraph(
				name = "holiday",
				attributeNodes = {
					@NamedAttributeNode("holiday")
				}
			),
			@NamedSubgraph(
				name = "state-holidays",
				attributeNodes = {
					@NamedAttributeNode(value = "stateHolidays", subgraph = "state-holidays-holiday")
				}
			),
			@NamedSubgraph(
				name = "state-holidays-holiday",
				attributeNodes = {
					@NamedAttributeNode(value = "holiday", subgraph = "state-holidays-holiday-day")
				}
			),
			@NamedSubgraph(
				name = "state-holidays-holiday-day",
				attributeNodes = {
					@NamedAttributeNode("day")
				}
			)
		}
	)
})
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

	@OneToMany(mappedBy = "city", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	Set<CityHoliday> cityHolidays;

	public void addHoliday(final CityHoliday cityHoliday){
		if(Objects.isNull(cityHolidays)){
			cityHolidays = new HashSet<>();
		}
		if(!containsHoliday(cityHoliday)) {
			cityHolidays.add(cityHoliday);
		}
	}

	public void deleteHoliday(final Long holidayId){
		if(Objects.nonNull(cityHolidays)){
			cityHolidays.removeIf(holiday -> holiday.getId().getHolidayId().equals(holidayId));
		}
	}

	public void updateHoliday(final CityHoliday cityHoliday, boolean isHoliday){
		if(Objects.nonNull(cityHolidays)){
			Optional<CityHoliday> cityHolidayOptional = cityHolidays.stream()
				.filter(holiday -> holiday.getId().getHolidayId().equals(cityHoliday.getId().getHolidayId())).findFirst();
			cityHolidayOptional.ifPresent(holiday -> {
				holiday.setCityHoliday(isHoliday);
			});
		}
	}

	public Optional<CityHoliday> containsHoliday(final Long holidayId) {
		return this.cityHolidays.stream()
			.filter(holiday -> holiday.getId().getHolidayId().equals(holidayId))
			.findFirst();
	}

	private boolean containsHoliday(final CityHoliday cityHoliday) {
		return this.cityHolidays.stream().anyMatch(holiday -> holiday.getId().getHolidayId().equals(cityHoliday.getId().getHolidayId()));
	}

}
