package br.com.insidesoftwares.dayoffmarker.domain.entity.country;

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

import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "DOM_COUNTRY")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "COUNTRY_ID")
    private UUID id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "ACRONYM")
    private String acronym;
    @Column(name = "CODE")
    private String code;
}
