package br.com.insidesoftwares.dayoffmarker.domain.entity.state;

import br.com.insidesoftwares.dayoffmarker.domain.entity.country.Country;
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
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "DOM_STATE")
@NamedEntityGraphs({
        @NamedEntityGraph(
                name = "state-full",
                attributeNodes = {
                        @NamedAttributeNode("country")
                }
        ),
        @NamedEntityGraph(
                name = "state-holiday",
                attributeNodes = {
                        @NamedAttributeNode("country"),
                        @NamedAttributeNode(value = "stateHolidays", subgraph = "holiday")
                },
                subgraphs = {
                        @NamedSubgraph(
                                name = "holiday",
                                attributeNodes = {
                                        @NamedAttributeNode(value = "holiday")
                                }
                        )
                }
        )
})
public class State {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "STATE_ID")
    private UUID id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "ACRONYM")
    private String acronym;
    @Column(name = "CODE")
    private String code;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COUNTRY_ID")
    private Country country;
    @OneToMany(mappedBy = "state", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<StateHoliday> stateHolidays;

    public void addHoliday(final StateHoliday stateHoliday) {
        if (Objects.isNull(stateHolidays)) {
            stateHolidays = new HashSet<>();
        }
        if (!containsHoliday(stateHoliday)) {
            stateHolidays.add(stateHoliday);
        }
    }

    public void deleteHoliday(final UUID holidayId) {
        if (Objects.nonNull(stateHolidays)) {
            stateHolidays.removeIf(holiday -> holiday.getId().getHolidayId().equals(holidayId));
        }
    }

    public void updateHoliday(final StateHoliday stateHoliday, boolean isHoliday) {
        if (Objects.nonNull(stateHolidays)) {
            Optional<StateHoliday> StateHolidayOptional = stateHolidays.stream()
                    .filter(holiday -> holiday.getId().getHolidayId().equals(stateHoliday.getId().getHolidayId())).findFirst();
            StateHolidayOptional.ifPresent(holiday -> {
                holiday.setStateHoliday(isHoliday);
            });
        }
    }

    public Optional<StateHoliday> containsHoliday(final UUID holidayId) {
        return this.stateHolidays.stream()
                .filter(holiday -> holiday.getId().getHolidayId().equals(holidayId))
                .findFirst();
    }

    private boolean containsHoliday(final StateHoliday stateHoliday) {
        return this.stateHolidays.stream().anyMatch(holiday -> holiday.getId().getHolidayId().equals(stateHoliday.getId().getHolidayId()));
    }
}
