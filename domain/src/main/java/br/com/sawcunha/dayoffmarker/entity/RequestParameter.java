package br.com.sawcunha.dayoffmarker.entity;

import br.com.sawcunha.dayoffmarker.commons.enums.eTypeParameter;
import br.com.sawcunha.dayoffmarker.commons.enums.eTypeValue;
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
    private eTypeParameter typeParameter;

    @Column(name = "TYPE_VALUE")
    @Enumerated(EnumType.STRING)
    private eTypeValue typeValue;

    @Column(name = "VALUE_PARAMETER")
    private String value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REQUEST_ID")
    private Request request;

}
