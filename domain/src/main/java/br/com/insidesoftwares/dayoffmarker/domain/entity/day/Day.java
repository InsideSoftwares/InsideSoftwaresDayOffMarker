package br.com.insidesoftwares.dayoffmarker.domain.entity.day;

import br.com.insidesoftwares.dayoffmarker.domain.entity.holiday.Holiday;
import br.com.insidesoftwares.dayoffmarker.domain.entity.tag.Tag;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedEntityGraphs;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Year;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "DOM_DAY")
@NamedEntityGraphs({
        @NamedEntityGraph(
                name = "day-full",
                attributeNodes = {
                        @NamedAttributeNode("holidays"),
                        @NamedAttributeNode("tags")
                }
        ),
        @NamedEntityGraph(
                name = "day-tags",
                attributeNodes = {
                        @NamedAttributeNode("tags")
                }
        )
})
public class Day {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "DAY_ID")
    private UUID id;
    @Column(name = "DATE")
    private LocalDate date;
    @Column(name = "WEEKEND")
    private boolean isWeekend;
    @Column(name = "HOLIDAY")
    private boolean isHoliday;
    @Column(name = "DAY_OF_WEEK")
    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;
    @Column(name = "DAY_OF_YEAR")
    private int dayOfYear;
    @OneToMany(mappedBy = "day", fetch = FetchType.LAZY)
    private Set<Holiday> holidays;
    @Column(name = "DAY_TAG")
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "DOM_DAY_TAG", joinColumns =
            {@JoinColumn(name = "DAY_ID")}, inverseJoinColumns =
            {@JoinColumn(name = "TAG_ID")})
    private Set<Tag> tags;

    public boolean isLeap() {
        return Year.isLeap(this.date.getYear());
    }

    public void addTag(Tag tag) {
        if (Objects.isNull(tags)) {
            tags = new HashSet<>();
        }
        tags.add(tag);
    }
}
