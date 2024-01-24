package com.abm.mainet.property.service;

import java.io.Serializable;

import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;

public interface ChangeInAssessmentService extends Serializable {

    void setLastPaymentDetails(ProvisionalAssesmentMstDto assMst, Long orgId) throws Exception;

}
