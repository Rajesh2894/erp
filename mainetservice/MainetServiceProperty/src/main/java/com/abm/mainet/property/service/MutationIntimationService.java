package com.abm.mainet.property.service;

import java.util.List;

import javax.jws.WebService;

import org.springframework.http.ResponseEntity;

import com.abm.mainet.common.dto.GridSearchDTO;
import com.abm.mainet.common.dto.PagingDTO;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.property.domain.MutationRegistrationEntity;
import com.abm.mainet.property.dto.MutationIntimationDto;
import com.abm.mainet.property.dto.MutationRegistrationDto;
import com.abm.mainet.property.dto.PropertyTransferMasterDto;

@WebService
public interface MutationIntimationService {

    ResponseEntity<?> saveMutationIntimation(MutationRegistrationDto mutationRegistration);

    List<MutationIntimationDto> getMutationIntimationGridData(MutationIntimationDto mutationIntimationDto, PagingDTO pagingDTO,
            GridSearchDTO gridSearchDTO);

    String downloadMutationDocument(MutationIntimationDto dto, String docString);

    MutationRegistrationEntity fetchMutationRegistrationById(long mutId, Long orgId);

    MutationIntimationDto getMutationIntimationView(long mutId, Long orgId);

    PropertyTransferMasterDto getTansferDtoByMutIntimation(MutationIntimationDto dto);

    int getcountOfSearchMutationIntimation(MutationIntimationDto mutationIntimationDto,
            PagingDTO pagingDTO, GridSearchDTO gridSearchDTO);

    void updateMutationRegistrationByMutId(Long applicationNo, long mutId, Long orgId);
  
    List<TbBillMas> getPropertyDuesByPropNo(String propertyNo, Long orgId);

}
