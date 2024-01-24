/**
 * 
 */
package com.abm.mainet.asset.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.asset.service.IRevaluationService;
import com.abm.mainet.asset.ui.dto.RevaluationDTO;
import com.abm.mainet.common.constant.MainetConstants;
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
public class AssetRevaluationModel extends AbstractFormModel {

    /**
     * 
     */
    private static final long serialVersionUID = -2633660636835343470L;

    @Autowired
    private IRevaluationService service;

    private RevaluationDTO revaluationDTO = new RevaluationDTO();
    private AuditDetailsDTO audit = new AuditDetailsDTO();
    private List<LookUp> accountHead = new ArrayList<>();
    private Long taskId;
    private String accountLive = "N";

    public RevaluationDTO getRevaluationDTO() {
        return revaluationDTO;
    }

    public void setRevaluationDTO(RevaluationDTO revaluationDTO) {
        this.revaluationDTO = revaluationDTO;
    }

    public AuditDetailsDTO getAudit() {
        return audit;
    }

    public void setAudit(AuditDetailsDTO audit) {
        this.audit = audit;
    }

    public List<LookUp> getAccountHead() {
        return accountHead;
    }

    public void setAccountHead(List<LookUp> accountHead) {
        this.accountHead = accountHead;
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
        final RevaluationDTO dto = getRevaluationDTO();
        validateConstraints(dto, RevaluationDTO.class, getBindingResult());
        String referenceId = StringUtils.EMPTY;// used for display reference no on POPUP
        if (hasValidationErrors()) {
            return false;
        }

        try {
            referenceId = service.revaluate(dto, audit, "");
            status = true;
            // D72548
            setSuccessMessage(ApplicationSession.getInstance().getMessage("asset.revaluation.request.success",
                    new Object[] { referenceId }));
        } catch (FrameworkException exp) {
            status = false;
            setSuccessMessage(ApplicationSession.getInstance().getMessage("asset.revaluation.request.failure"));
        }
        return status;
    }

    public void loadTransferDetails() {
        WorkflowTaskAction wfTask = this.getWorkflowActionDto();
        String uid = wfTask.getReferenceId();
        String revalId = uid.substring(uid.lastIndexOf(MainetConstants.AssetManagement.WF_TXN_IDEN_REVAL) + 4);
        this.setRevaluationDTO(service.getDetails(Long.valueOf(revalId)));
    }

    public boolean apprRevaluationAction() {
        boolean retFlag = false;
        WorkflowTaskAction wfAction = this.getWorkflowActionDto();
        String decFlag = wfAction.getDecision();
        AuditDetailsDTO auditDto = new AuditDetailsDTO();
        UserSession userSession = UserSession.getCurrent();
        auditDto.setEmpId(userSession.getEmployee().getEmpId());
        auditDto.setEmpIpMac(userSession.getEmployee().getEmppiservername());
        // Defect #5437 for this i need to do this
        service.docUpload(getWorkflowActionDto().getReferenceId(), auditDto, wfAction);
        service.executeWfAction(getWorkflowActionDto().getReferenceId(), auditDto, wfAction);
        if (decFlag != null && decFlag.equals(MainetConstants.WorkFlow.Decision.APPROVED)) {
            retFlag = true;
        } else if (decFlag != null && decFlag.equals(MainetConstants.WorkFlow.Decision.REJECTED)) {
            retFlag = true;
        }
        return retFlag;
    }
}
