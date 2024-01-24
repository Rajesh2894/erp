package com.abm.mainet.property.dao;

import java.util.List;

import com.abm.mainet.common.dto.GridSearchDTO;
import com.abm.mainet.common.dto.PagingDTO;
import com.abm.mainet.property.domain.MutationRegistrationEntity;
import com.abm.mainet.property.dto.MutationIntimationDto;

public interface IMutationIntimationDao {

    List<MutationRegistrationEntity> searchMutationIntimation(MutationIntimationDto mutationIntimationDto, PagingDTO pagingDTO,
            GridSearchDTO gridSearchDTO);

    int getcountOfSearchMutationIntimation(MutationIntimationDto mutationIntimationDto, PagingDTO pagingDTO,
            GridSearchDTO gridSearchDTO);

}
