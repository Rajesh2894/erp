/**
 * 
 */
package com.abm.mainet.asset.ui.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.asset.service.IRetireService;
import com.abm.mainet.asset.ui.dto.RetirementDTO;
import com.abm.mainet.asset.ui.validator.AssetDetailsValidator;
import com.abm.mainet.asset.ui.validator.AssetRetirementValidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.BankMasterEntity;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.asset.dto.AuditDetailsDTO;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;

/**
 * @author sarojkumar.yadav
 *
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class AssetRetirementModel extends AbstractFormModel {

    /**
     * 
     */
    private static final long serialVersionUID = -2633660636835343470L;

    @Autowired
    private IRetireService service;

    private RetirementDTO retireDTO = new RetirementDTO();
    private AuditDetailsDTO audit = new AuditDetailsDTO();
    private List<LookUp> taxMasAccHead = new ArrayList<>();
    // this is for pay mode whose other value is Y
    private List<LookUp> paymode = new ArrayList<>(50);
    private List<BankMasterEntity> bankList = new ArrayList<>(50);
    private String taxMasterError;
    private Long taskId;
    private String accountLive = "N";

    public String getTaxMasterError() {
        return taxMasterError;
    }

    public void setTaxMasterError(String taxMasterError) {
        this.taxMasterError = taxMasterError;
    }

    public List<BankMasterEntity> getBankList() {
        return bankList;
    }

    public void setBankList(List<BankMasterEntity> bankList) {
        this.bankList = bankList;
    }

    public List<LookUp> getPaymode() {
        return paymode;
    }

    public void setPaymode(List<LookUp> paymode) {
        this.paymode = paymode;
    }

    public RetirementDTO getRetireDTO() {
        return retireDTO;
    }

    public void setRetireDTO(RetirementDTO retireDTO) {
        this.retireDTO = retireDTO;
    }

    public AuditDetailsDTO getAudit() {
        return audit;
    }

    public void setAudit(AuditDetailsDTO audit) {
        this.audit = audit;
    }

    public List<LookUp> getTaxMasAccHead() {
        return taxMasAccHead;
    }

    public void setTaxMasAccHead(List<LookUp> taxMasAccHead) {
        this.taxMasAccHead = taxMasAccHead;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getAccountLive() {
        return accountLive;
    }

    public void setAccountLive(String accountLive) {
        this.accountLive = accountLive;
    }

    @Override
    public boolean saveForm() {
        boolean status = false;
        final RetirementDTO dto = getRetireDTO();

        validateConstraints(dto, RetirementDTO.class, getBindingResult());
        validateBean(dto, AssetRetirementValidator.class);
        if (hasValidationErrors()) {
            return false;
        }
        // T#92467
        Map<String, String> data = AssetDetailsValidator.getModuleDeptAndServiceCode(UserSession.getCurrent().getModuleDeptCode(),
                MainetConstants.AssetManagement.TRF_RETIRE_SERV_SHORTCODE,
                MainetConstants.ITAssetManagement.IT_RETIRE_APPR_SERVICE_CODE);
        dto.setDeptCode(UserSession.getCurrent().getModuleDeptCode());
        try {
            // D#85182
            String referenceId = service.retire(dto, audit, "", data.get(MainetConstants.ITAssetManagement.MODULE_DEPT_KEY),
                    data.get(MainetConstants.ITAssetManagement.SERVICE_CODE_KEY));
            status = true;
            setSuccessMessage(ApplicationSession.getInstance().getMessage("asset.retire.request.success",
                    new Object[] { referenceId }));
        } catch (FrameworkException exp) {
            status = false;
            setSuccessMessage(ApplicationSession.getInstance().getMessage("asset.retire.request.failure"));
        }
        return status;
    }

    public void loadRetirementDetails() {
        WorkflowTaskAction wfTask = this.getWorkflowActionDto();
        String uid = wfTask.getReferenceId();
        String retireId = uid.substring(uid.lastIndexOf(MainetConstants.AssetManagement.WF_TXN_IDEN_RETIRE) + 4);
        this.setRetireDTO(service.getDetails(Long.valueOf(retireId)));
    }

    public boolean apprRetirementAction(String moduleDeptCode) {
        boolean retFlag = false;
        WorkflowTaskAction wfAction = this.getWorkflowActionDto();
        String decFlag = wfAction.getDecision();
        AuditDetailsDTO auditDto = new AuditDetailsDTO();
        UserSession userSession = UserSession.getCurrent();
        auditDto.setEmpId(userSession.getEmployee().getEmpId());
        auditDto.setEmpIpMac(userSession.getEmployee().getEmppiservername());
        // T#92467
        Map<String, String> data = AssetDetailsValidator.getModuleDeptAndServiceCode(moduleDeptCode,
                MainetConstants.AssetManagement.TRF_RETIRE_SERV_SHORTCODE,
                MainetConstants.ITAssetManagement.IT_RETIRE_APPR_SERVICE_CODE);
        service.executeWfAction(getWorkflowActionDto().getReferenceId(), auditDto, wfAction,
                data.get(MainetConstants.ITAssetManagement.MODULE_DEPT_KEY),
                data.get(MainetConstants.ITAssetManagement.SERVICE_CODE_KEY));
        if (decFlag != null && decFlag.equals(MainetConstants.WorkFlow.Decision.APPROVED)) {
            retFlag = true;
        } else if (decFlag != null && decFlag.equals(MainetConstants.WorkFlow.Decision.REJECTED)) {
            retFlag = true;
        }
        return retFlag;
    }
}
