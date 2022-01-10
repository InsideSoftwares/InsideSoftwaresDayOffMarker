package br.com.sawcunha.dayoffmarker.repository;

import br.com.sawcunha.dayoffmarker.commons.enums.eStatusRequest;
import br.com.sawcunha.dayoffmarker.commons.enums.eTypeRequest;
import br.com.sawcunha.dayoffmarker.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RequestRepository extends JpaRepository<Request, UUID> {

    @Query(" SELECT r FROM Request r WHERE r.id in :ids ")
    List<Request> findAllById(List<UUID> ids);

    @Query(" SELECT r FROM Request r WHERE r.id in :ids AND statusRequest = :statusRequest")
    List<Request> findAllByIdAndStatusRequest(List<UUID> ids, eStatusRequest statusRequest);

    Optional<Request> findRequestByTypeRequestAndStatusRequest(eTypeRequest typeRequest, eStatusRequest statusRequest);

}
