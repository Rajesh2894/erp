package com.abm.mainet.common.master.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.abm.mainet.common.domain.TbOrganisationAddDetEntity;
import com.abm.mainet.common.domain.TbOrganisationAddDetEntityKey;

/**
 * Repository : TbOrganisationAddDet.
 */
public interface TbOrganisationAddDetJpaRepository
        extends PagingAndSortingRepository<TbOrganisationAddDetEntity, TbOrganisationAddDetEntityKey> {

}
