/**
 *
 */
package com.abm.mainet.common.integration.dms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.integration.dms.domain.CFCAttachment;

/**
 * @author vishnu.jagdale
 *
 */
@Repository
public interface IFileUploadCrudRepository extends CrudRepository<CFCAttachment, Long> {

    @Query("FROM CFCAttachment sf WHERE sf.applicationId=:applicationId AND sf.serviceId=:serviceId AND sf.orgid=:orgId")
    public List<CFCAttachment> fetchAllUploadedDocument(@Param("applicationId") Long applicationId,
            @Param("serviceId") Long serviceId, @Param("orgId") Long orgId);
}
