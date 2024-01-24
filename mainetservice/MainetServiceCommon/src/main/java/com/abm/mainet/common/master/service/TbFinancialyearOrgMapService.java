/**
 *
 */
package com.abm.mainet.common.master.service;

import com.abm.mainet.common.domain.TbFincialyearorgMapEntity;
import com.abm.mainet.common.master.dto.TbFincialyearorgMap;

/**
 * @author Harsha.Ramachandran
 *
 */

public interface TbFinancialyearOrgMapService {

    TbFincialyearorgMap create(TbFincialyearorgMap bean);

    TbFincialyearorgMapEntity findByOrgId(Long faYearId, Long orgId);

    TbFincialyearorgMap update(TbFincialyearorgMap bean);

    TbFincialyearorgMapEntity findById(Long mapId);
}
