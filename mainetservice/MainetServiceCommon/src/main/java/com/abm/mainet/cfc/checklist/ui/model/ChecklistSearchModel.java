package com.abm.mainet.cfc.checklist.ui.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.cfc.checklist.domain.ChecklistStatusView;
import com.abm.mainet.cfc.checklist.service.IChecklistSearchService;
import com.abm.mainet.common.ui.model.AbstractSearchFormModel;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class ChecklistSearchModel extends AbstractSearchFormModel<ChecklistStatusView> {

    private static final long serialVersionUID = 3589997812375001436L;

    @Autowired
    private IChecklistSearchService checklistSearchService;

    private Long serviceId;
    private Long applicationId;
    private String applicantName;
    private String appStatus;
    private Date fromDate;
    private Date toDate;
    private Long orgId;
    private List<ChecklistStatusView> result = new LinkedList<>();
    private boolean sucessResult;
    private boolean error;

    @Override
    protected void initializeModel() {
        orgId = UserSession.getCurrent().getOrganisation().getOrgid();
    }

    @Override
    protected List<ChecklistStatusView> querySearchResults() {
        result = checklistSearchService.serachChecklist(orgId, applicationId, serviceId, applicantName, fromDate,
                toDate, appStatus);
        if (!result.isEmpty()) {
            setSucessResult(true);
            setError(false);
        } else {
            setSucessResult(false);
            setError(true);
        }
        return result;
    }

    @Override
    protected void validateModel() {
        boolean valid = false;
        valid = !appStatus.equals(StringUtils.EMPTY) || valid;
        valid = !applicantName.equals(StringUtils.EMPTY) || valid;
        valid = (toDate != null) || valid;
        valid = (fromDate != null) || valid;
        valid = (applicationId != null) || valid;
        if (!valid) {
            addValidationError(getAppSession().getMessage("cfc.validate.service"));
        }

    }

    public IChecklistSearchService getChecklistSearchService() {
        return checklistSearchService;
    }

    public void setChecklistSearchService(final IChecklistSearchService checklistSearchService) {
        this.checklistSearchService = checklistSearchService;
    }

    @Override
    public Long getServiceId() {
        return serviceId;
    }

    @Override
    public void setServiceId(final Long serviceId) {
        this.serviceId = serviceId;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(final Long applicationId) {
        this.applicationId = applicationId;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(final String applicantName) {
        this.applicantName = applicantName;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(final Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(final Date toDate) {
        this.toDate = toDate;
    }

    public void setServiceList(final List<LookUp> serviceList) {
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    public String getAppStatus() {
        return appStatus;
    }

    public void setAppStatus(final String appStatus) {
        this.appStatus = appStatus;
    }

    public List<ChecklistStatusView> getResult() {
        return result;
    }

    public void setResult(final List<ChecklistStatusView> result) {
        this.result = result;
    }

    public boolean isSucessResult() {
        return sucessResult;
    }

    public void setSucessResult(final boolean sucessResult) {
        this.sucessResult = sucessResult;
    }

    public boolean isError() {
        return error;
    }

    public void setError(final boolean error) {
        this.error = error;
    }

}
