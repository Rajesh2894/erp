package com.abm.mainet.cfc.challan.ui.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.cfc.challan.domain.ChallanMaster;
import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.cfc.challan.ui.validator.ChallanUpdateValidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.UserSession;

/**
 *
 * @author Rahul.Yadav
 *
 */
@Component
@Scope("session")
public class ChallanUpdateModel extends AbstractFormModel {

    private static final long serialVersionUID = 3804309243186158814L;

    @Autowired
    private IChallanService iChallanService;

    private boolean immeadiateService = false;

    private String successUrl;

    private String pageUrlFlag;

    private Long applicationNo;
    private Long challanNo;

    private ChallanMaster entity = null;

    @Override
    public boolean saveForm() {
        validateBean(getEntity(), ChallanUpdateValidator.class);
        if (!hasValidationErrors()) {
            final boolean bankTransId = iChallanService.getChallanMasterTransId(getEntity().getBankTransId());

            if (bankTransId) {
                final ChallanMaster master = iChallanService.getChallanMasterById(getEntity().getChallanId());

                if (master != null) {
                    Employee emp = UserSession.getCurrent().getEmployee();
                    getEntity().setLgIpMacUpd(emp.getEmppiservername());
                    if (!master.getChallanRcvdFlag().equalsIgnoreCase(MainetConstants.Common_Constant.YES)) {
                        iChallanService.updateChallanDetails(getEntity(), this.getTaskId(), emp.getEmplType(), emp.getEmpId(),
                                emp.getEmpname());
                        setSuccessMessage("Challan updated successfully!");
                        return true;
                    } else {
                        addValidationError(getAppSession().getMessage("challanVerification.allupdated"));
                    }
                }
            } else {
                addValidationError(getAppSession().getMessage("challanVerification.BankIdupdated"));
            }

        }

        return false;
    }

    public void editFormByApplicationId(final long applicationId) {
        setPageUrlFlag(MainetConstants.CommonConstants.C);
        final ChallanMaster challanMaster = iChallanService.getChallanMasters(null, applicationId);
        setImmeadiateService(false);
        setEntity(challanMaster);
    }

    public boolean isImmeadiateService() {
        return immeadiateService;
    }

    public void setImmeadiateService(final boolean immeadiateService) {
        this.immeadiateService = immeadiateService;
    }

    public String getSuccessUrl() {
        return successUrl;
    }

    public void setSuccessUrl(final String successUrl) {
        this.successUrl = successUrl;
    }

    public String getPageUrlFlag() {
        return pageUrlFlag;
    }

    public void setPageUrlFlag(final String pageUrlFlag) {
        this.pageUrlFlag = pageUrlFlag;
    }

    public ChallanMaster getEntity() {
        return entity;
    }

    public void setEntity(final ChallanMaster entity) {
        this.entity = entity;
    }

    public Long getApplicationNo() {
        return applicationNo;
    }

    public void setApplicationNo(final Long applicationNo) {
        this.applicationNo = applicationNo;
    }

    public Long getChallanNo() {
        return challanNo;
    }

    public void setChallanNo(final Long challanNo) {
        this.challanNo = challanNo;
    }

}
