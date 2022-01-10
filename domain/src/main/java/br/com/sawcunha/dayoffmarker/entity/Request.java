package br.com.sawcunha.dayoffmarker.entity;

import br.com.sawcunha.dayoffmarker.commons.enums.eStatusRequest;
import br.com.sawcunha.dayoffmarker.commons.enums.eTypeRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "DCY_REQUEST")
public class Request {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "REQUEST_ID")
    private UUID id;

    @Column(name = "REQUESTING")
    private String requesting;

    @Column(name = "CREATE_DATE")
    private LocalDateTime createDate;

    @Column(name = "START_DATE")
    private LocalDateTime startDate;

    @Column(name = "END_DATE")
    private LocalDateTime endDate;

    @Column(name = "STATUS_REQUEST")
    @Enumerated(EnumType.STRING)
    private eStatusRequest statusRequest;

    @Column(name = "TYPE_REQUEST")
    @Enumerated(EnumType.STRING)
    private eTypeRequest typeRequest;

    @OneToMany(mappedBy = "request", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<RequestParameter> requestParameter;

}
