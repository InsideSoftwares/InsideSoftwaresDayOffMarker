package br.com.insidesoftwares.dayoffmarker.domain.repository;

import br.com.insidesoftwares.dayoffmarker.commons.enumeration.StatusRequest;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.TypeRequest;
import br.com.insidesoftwares.dayoffmarker.domain.entity.request.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RequestRepository extends JpaRepository<Request, UUID> {

    @Query(" SELECT r FROM Request r WHERE statusRequest = :statusRequest AND r.typeRequest = :typeRequest")
    List<Request> findAllByStatusRequest(final StatusRequest statusRequest, final TypeRequest typeRequest);

    @Query("""
            SELECT count(r.id) > 0 FROM Request r
            WHERE statusRequest = :statusRequest
            AND r.typeRequest = :typeRequest
            """)
    boolean existRequestByTypeRequestAndStatusRequest(final TypeRequest typeRequest, final StatusRequest statusRequest);

    @Query("""
            SELECT COUNT(r.id) > 0 FROM Request r
            WHERE r.typeRequest = :typeRequest
            AND r.requestHash = :requestHash
            AND r.statusRequest NOT IN :statusRequest
            """)
    boolean existRequestByTypeRequestAndHashRequestAndNotStatusRequest(
            final TypeRequest typeRequest,
            final String requestHash,
            final List<StatusRequest> statusRequest
    );
}
