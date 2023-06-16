package br.com.insidesoftwares.dayoffmarker.entity;

import br.com.insidesoftwares.dayoffmarker.commons.enumeration.TypeParameter;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.TypeValue;
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

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "DOM_REQUEST_PARAMETER")
public class RequestParameter {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "REQUEST_PARAMETER_ID")
    private Long id;

    @Column(name = "TYPE_PARAMETER")
    @Enumerated(EnumType.STRING)
    private TypeParameter typeParameter;

    @Column(name = "TYPE_VALUE")
    @Enumerated(EnumType.STRING)
    private TypeValue typeValue;

    @Column(name = "VALUE_PARAMETER")
    private String value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REQUEST_ID")
    private Request request;

}
