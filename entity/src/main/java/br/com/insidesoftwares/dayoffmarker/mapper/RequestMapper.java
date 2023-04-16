package br.com.insidesoftwares.dayoffmarker.mapper;

import br.com.insidesoftwares.dayoffmarker.commons.dto.batch.RequestDTO;
import br.com.insidesoftwares.dayoffmarker.entity.Request;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RequestMapper {

    Request toModel(RequestDTO resquestDTO);
    RequestDTO toDTO(Request address);
    List<RequestDTO> toDTOs(List<Request> requests);

}
