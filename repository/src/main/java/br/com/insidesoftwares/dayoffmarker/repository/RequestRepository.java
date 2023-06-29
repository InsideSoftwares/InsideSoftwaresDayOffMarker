package br.com.insidesoftwares.dayoffmarker.repository;

import br.com.insidesoftwares.dayoffmarker.commons.enumeration.StatusRequest;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.TypeRequest;
import br.com.insidesoftwares.dayoffmarker.entity.request.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RequestRepository extends JpaRepository<Request, UUID> {

    @Query(" SELECT r.id FROM Request r WHERE r.jobId = :jobId AND r.typeRequest = :typeRequest")
    List<UUID> findAllByJobId(Long jobId, TypeRequest typeRequest);

    @Query("""
			SELECT r FROM Request r
			WHERE r.jobId = :jobId AND
			 statusRequest = :statusRequest
			""")
    List<Request> findAllByJobIdAndStatusRequest(Long jobId, StatusRequest statusRequest);

	@Query(" SELECT r FROM Request r WHERE statusRequest = :statusRequest AND r.typeRequest = :typeRequest")
	List<Request> findAllByStatusRequest(StatusRequest statusRequest, TypeRequest typeRequest);

	@Query(" SELECT count(r) > 0 FROM Request r WHERE statusRequest = :statusRequest AND r.typeRequest = :typeRequest")
	boolean existRequestByTypeRequestAndStatusRequest(TypeRequest typeRequest, StatusRequest statusRequest);

    @Query(" SELECT r FROM Request r WHERE r.typeRequest = :typeRequest AND r.statusRequest != :statusRequest")
    List<Request> findAllRequestByTypeRequestAndNotStatusRequest(TypeRequest typeRequest, StatusRequest statusRequest);

	@Query(" SELECT r FROM Request r WHERE r.typeRequest = :typeRequest AND r.statusRequest = :statusRequest")
	List<Request> findAllRequestByTypeRequestAndStatusRequest(TypeRequest typeRequest, StatusRequest statusRequest);

	@Query(" SELECT r FROM Request r WHERE r.typeRequest = :typeRequest AND r.statusRequest in :statusRequest")
	List<Request> findAllRequestByTypeRequestAndStatusRequest(TypeRequest typeRequest, List<StatusRequest> statusRequest);
}
