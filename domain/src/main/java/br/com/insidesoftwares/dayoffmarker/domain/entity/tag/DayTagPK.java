package br.com.insidesoftwares.dayoffmarker.domain.entity.tag;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class DayTagPK {

    @Column(name = "DAY_ID")
    private UUID dayID;
    @Column(name = "TAG_ID")
    private UUID tagID;

}
