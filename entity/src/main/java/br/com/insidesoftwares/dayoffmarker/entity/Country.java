package br.com.insidesoftwares.dayoffmarker.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "DOM_COUNTRY")
public class Country {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "COUNTRY_ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "ACRONYM")
    private String acronym;

    @Column(name = "CODE")
    private String code;

}
