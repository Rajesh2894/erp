/**
 *
 */
package com.abm.mainet.common.dao;

import java.util.List;

import com.abm.mainet.common.domain.ApplicationStatusEntity;
import com.abm.mainet.common.dto.ApplicationStatusRequestVO;

/**
 * @author vishnu.jagdale
 *
 */
public interface IApplicationStatusRepository {

    public List<ApplicationStatusEntity> getApplicationStatusList();

    public List<ApplicationStatusEntity> getApplicationStatusListOpenForUser(ApplicationStatusRequestVO requestDTO)
            throws RuntimeException;

    public List<ApplicationStatusEntity> getApplicationStatusDetail(ApplicationStatusRequestVO requestDTO)
            throws RuntimeException;

}
