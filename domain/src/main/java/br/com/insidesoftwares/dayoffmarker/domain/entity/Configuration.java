package br.com.insidesoftwares.dayoffmarker.domain.entity;

import br.com.insidesoftwares.dayoffmarker.commons.enumeration.Configurationkey;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "DOM_CONFIGURATION")
public class Configuration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CONFIGURATION_ID")
    private Long id;

    @Column(name = "CONFIGURATION_KEY")
    @Enumerated(EnumType.STRING)
    private Configurationkey key;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "CONFIGURATION_VALUE")
    private String value;

    @Column(name = "DEFAULT_VALUE")
    private String defaultValue;

    public String getValueOrDefaulValue() {
        return Objects.nonNull(value) ? value : defaultValue;
    }
}
