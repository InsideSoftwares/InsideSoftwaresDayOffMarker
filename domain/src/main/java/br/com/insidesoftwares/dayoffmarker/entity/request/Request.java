package br.com.insidesoftwares.dayoffmarker.entity.request;

import br.com.insidesoftwares.dayoffmarker.commons.enumeration.StatusRequest;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.TypeRequest;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "DOM_REQUEST")
@NamedEntityGraph(
	name = "request-full",
	attributeNodes = {
		@NamedAttributeNode("requestParameter")
	}
)
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
    private StatusRequest statusRequest;

    @Column(name = "TYPE_REQUEST")
    @Enumerated(EnumType.STRING)
    private TypeRequest typeRequest;

	@Column(name = "JOB_ID")
	private Long jobId;

	@Column(name = "REQUEST_HASH")
	private String requestHash;

    @OneToMany(mappedBy = "request", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<RequestParameter> requestParameter;

}
