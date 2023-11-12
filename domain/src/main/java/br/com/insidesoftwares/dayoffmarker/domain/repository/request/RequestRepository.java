package br.com.insidesoftwares.dayoffmarker.domain.repository.request;

import br.com.insidesoftwares.dayoffmarker.commons.enumeration.StatusRequest;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.TypeRequest;
import br.com.insidesoftwares.dayoffmarker.domain.entity.request.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RequestRepository extends JpaRepository<Request, UUID> {

    List<Request> findAllByStatusRequestAndTypeRequest(final StatusRequest statusRequest, final TypeRequest typeRequest);
}
