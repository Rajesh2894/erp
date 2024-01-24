package com.abm.mainet.common.workflow.ui.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.workflow.dto.BpmBrmDeploymentMasterDTO;
import com.abm.mainet.common.workflow.service.BpmBrmDeploymentService;

@Component
public class BpmBrmDeploymentValidator extends BaseEntityValidator<BpmBrmDeploymentMasterDTO> {

    @Autowired
    private BpmBrmDeploymentService bpmBrmDeploymentService;

    @Override
    protected void performValidations(BpmBrmDeploymentMasterDTO entity,
            EntityValidationContext<BpmBrmDeploymentMasterDTO> entityValidationContext) {

        if (entity.getGroupId() != null)
            entityValidationContext.rejectIfEmpty(entity.getGroupId(), MainetConstants.WorkFlow.BpmBrmDeployment.GROUPID);
        if (entity.getArtifactId() != null)
            entityValidationContext.rejectIfEmpty(entity.getArtifactId(), MainetConstants.WorkFlow.BpmBrmDeployment.ARTIFACTID);
        if (entity.getProcessId() != null)
            entityValidationContext.rejectIfEmpty(entity.getProcessId(), MainetConstants.WorkFlow.BpmBrmDeployment.PROCESSID);
        if (entity.getVersion() != null)
            entityValidationContext.rejectIfEmpty(entity.getVersion(), MainetConstants.WorkFlow.BpmBrmDeployment.VERSION);
        if (entity.getStatus() != null)
            entityValidationContext.rejectIfEmpty(entity.getStatus(), MainetConstants.WorkFlow.BpmBrmDeployment.STATUS);
        if (entity.getBpmRuntime() != null)
            entityValidationContext.rejectIfNotSelected(entity.getArtifactId(),
                    MainetConstants.WorkFlow.BpmBrmDeployment.BPMRUNTIME);
        if (bpmBrmDeploymentService.isDeploymentExist(entity))
            entityValidationContext.addOptionConstraint(
                    ApplicationSession.getInstance().getMessage(
                            MainetConstants.WorkFlow.BpmBrmDeployment.MessagesKey.VALIDATION_DEPLOYMENT_ALREADY_EXIST));
    }

}
