package br.com.sawcunha.dayoffmarker.repository;

import br.com.sawcunha.dayoffmarker.commons.enums.eStatusRequest;
import br.com.sawcunha.dayoffmarker.commons.enums.eTypeRequest;
import br.com.sawcunha.dayoffmarker.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RequestRepository extends JpaRepository<Request, UUID> {

    @Query(" SELECT r.id FROM Request r WHERE r.jobId = :jobId AND r.typeRequest = :typeRequest")
    List<UUID> findAllByJobId(Long jobId, eTypeRequest typeRequest);

    @Query("""
			SELECT r FROM Request r
			WHERE r.jobId = :jobId AND
			 statusRequest = :statusRequest
			""")
    List<Request> findAllByJobIdAndStatusRequest(Long jobId, eStatusRequest statusRequest);

	@Query(" SELECT r FROM Request r WHERE statusRequest = :statusRequest AND r.typeRequest = :typeRequest")
	List<Request> findAllByStatusRequest(eStatusRequest statusRequest, eTypeRequest typeRequest);

	@Query(" SELECT count(r) > 0 FROM Request r WHERE statusRequest = :statusRequest AND r.typeRequest = :typeRequest")
	boolean existRequestByTypeRequestAndStatusRequest(eTypeRequest typeRequest, eStatusRequest statusRequest);

    @Query(" SELECT r FROM Request r WHERE r.typeRequest = :typeRequest AND r.statusRequest != :statusRequest")
    List<Request> findAllRequestByTypeRequestAndNotStatusRequest(eTypeRequest typeRequest, eStatusRequest statusRequest);

	@Query(" SELECT r FROM Request r WHERE r.typeRequest = :typeRequest AND r.statusRequest = :statusRequest")
	List<Request> findAllRequestByTypeRequestAndStatusRequest(eTypeRequest typeRequest, eStatusRequest statusRequest);

	@Query(" SELECT r FROM Request r WHERE r.typeRequest = :typeRequest AND r.statusRequest in :statusRequest")
	List<Request> findAllRequestByTypeRequestAndStatusRequest(eTypeRequest typeRequest, List<eStatusRequest> statusRequest);
}
