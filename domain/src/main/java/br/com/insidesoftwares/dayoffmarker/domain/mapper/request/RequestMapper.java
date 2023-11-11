package br.com.insidesoftwares.dayoffmarker.domain.mapper.request;

import br.com.insidesoftwares.dayoffmarker.commons.dto.batch.RequestDTO;
import br.com.insidesoftwares.dayoffmarker.domain.entity.request.Request;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RequestMapper {

    RequestDTO toDTO(Request address);

    List<RequestDTO> toDTOs(List<Request> requests);

}
