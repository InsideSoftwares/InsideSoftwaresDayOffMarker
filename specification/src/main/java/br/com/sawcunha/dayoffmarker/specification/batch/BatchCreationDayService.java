package br.com.sawcunha.dayoffmarker.specification.batch;

import br.com.sawcunha.dayoffmarker.commons.dto.batch.RequestDTO;
import br.com.sawcunha.dayoffmarker.commons.enums.eStatusRequest;
import br.com.sawcunha.dayoffmarker.entity.Country;
import br.com.sawcunha.dayoffmarker.entity.Day;
import br.com.sawcunha.dayoffmarker.entity.DayBatch;
import br.com.sawcunha.dayoffmarker.entity.Request;

import java.util.List;
import java.util.UUID;

public interface BatchCreationDayService {

    List<RequestDTO> findAllRequestDTOForBatch(final List<UUID> requests, final eStatusRequest statusRequest);
    List<Request> findAllRequestForBatch(final List<UUID> requests, final eStatusRequest statusRequest);

    List<DayBatch> findAllDayBatchForBatch(final List<UUID> requests);

    Country findCountry(final Long id);

    void createDayBatch(final DayBatch dayBatch);

    void createDay(final Day day);

    void saveRequest(final Request request);

}
