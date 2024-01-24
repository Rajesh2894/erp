package com.abm.mainet.rts.ui.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.model.AbstractFormModel;

@Component
@Scope("session")
public class FirstAppealModel extends AbstractFormModel {

    private static final long serialVersionUID = 989703012613053846L;
    private String hasError = MainetConstants.BLANK;
    private Long serviceId;
    private Long orgId;
    private Long userId;
    private String saveMode;

    public String getHasError() {
        return hasError;
    }

    public void setHasError(String hasError) {
        this.hasError = hasError;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSaveMode() {
        return saveMode;
    }

    public void setSaveMode(String saveMode) {
        this.saveMode = saveMode;
    }

}
