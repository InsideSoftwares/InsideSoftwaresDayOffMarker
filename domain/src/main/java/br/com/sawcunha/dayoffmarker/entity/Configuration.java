package br.com.sawcunha.dayoffmarker.entity;

import br.com.sawcunha.dayoffmarker.commons.enums.eConfigurationkey;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "DCY_CONFIGURATION")
public class Configuration {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "CONFIGURATION_ID")
    private Long id;

    @Column(name = "CONFIGURATION_KEY")
    @Enumerated(EnumType.STRING)
    private eConfigurationkey key;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "CONFIGURATION_VALUE")
    private String value;

    @Column(name = "DEFAULT_VALUE")
    private String defaultValue;

    public String getValueOrDefaulValue(){
        return Objects.nonNull(value) ? value : defaultValue;
    }
}
