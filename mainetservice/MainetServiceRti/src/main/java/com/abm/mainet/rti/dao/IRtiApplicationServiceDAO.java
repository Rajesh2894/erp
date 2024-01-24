/**
 * @author  : Harshit kumar
 * @since   : 20 Feb 2018
 * @comment : Method for Data transaction for RTI application like save/update/fetch
 */
package com.abm.mainet.rti.dao;

import java.util.List;

import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.rti.domain.TbRtiApplicationDetails;
import com.abm.mainet.rti.domain.TbRtiMediaDetails;

public interface IRtiApplicationServiceDAO {

    void saveRtiApplicationForm(TbRtiApplicationDetails tbRtiApplicationDetails);

    void saveRtiMediaDetails(TbRtiMediaDetails tbRtiMediaDetails);

    TbRtiApplicationDetails getRtiApplicationDetails(Long appId, Long orgId);

    List<TbRtiMediaDetails> getMediaList(Long rtiId, Long orgID);

    String getdepartmentNameById(Long deptId);

    Boolean getRtiNumber(String rtiNumber, Long orgId);

    Long getApplicationNumberByRefNo(String rtiNumber, Long serviceId, Long orgId, Long empId);

    String getRtiApplicationNumberForObj(Long rtiNumber, Long orgId);

    TbServiceReceiptMasEntity getReceiptDetails(Long appId, Long orgId);

    TbRtiApplicationDetails getRTIApplicationDetail(long apmApplicationId, long orgId);

    String getBplType(String rtiNo, Long orgId);

    List<TbRtiApplicationDetails> getDetails(Long apmApplicationId, Long orgID);

    List<TbRtiApplicationDetails> getRtiAction(Long forwardDeptId, Long orgId);

	TbRtiApplicationDetails getRtiApplicationDetailsForDSCL(Long appId, Long orgId);

}
