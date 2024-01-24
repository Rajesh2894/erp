package com.abm.mainet.property.ui.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.service.IWorkFlowTypeService;
import com.abm.mainet.property.dto.PropertyTransferMasterDto;
import com.abm.mainet.property.dto.PropertyTransferOwnerDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentOwnerDtlDto;
import com.abm.mainet.property.service.AssesmentMastService;
import com.abm.mainet.property.service.MutationService;
import com.abm.mainet.property.service.PropertyTransferService;
import com.abm.mainet.property.ui.model.MutationModel;

@Controller
@RequestMapping("/MutationApproval.html")
public class MutationApprovalController extends AbstractFormController<MutationModel> {

    @Autowired
    private PropertyTransferService propertyTransferService;

    @Autowired
    private ServiceMasterService serviceMaster;

    @Autowired
    private AssesmentMastService assesmentMastService;

    @Autowired
    private IFileUploadService fileUpload;

    @Autowired
    private IChecklistVerificationService iChecklistVerificationService;

    @Autowired
    private ILocationMasService iLocationMasService;

    @Autowired
    private MutationService mutationService;

    @RequestMapping(params = "showDetails", method = RequestMethod.POST)
    public ModelAndView defaultLoad(@RequestParam("appNo") final long applicationId, @RequestParam("taskId") final long serviceId,
            @RequestParam("actualTaskId") final long taskId, final HttpServletRequest httpServletRequest) throws Exception {
        this.sessionCleanup(httpServletRequest);
        fileUpload.sessionCleanUpForFileUpload();
        final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        MutationModel model = this.getModel();
        model.setAssType(MainetConstants.Property.MUT);
        model.setOrgId(orgId);
        ServiceMaster service = serviceMaster.getServiceMaster(serviceId, orgId);
        model.setServiceId(service.getSmServiceId());
        model.setDeptId(service.getTbDepartment().getDpDeptid());
        PropertyTransferMasterDto dto = propertyTransferService.getPropTransferMstByAppId(orgId, applicationId);
        dto.setApmApplicationId(applicationId);
        dto.setSmServiceId(serviceId);
        dto.setDeptId(service.getTbDepartment().getDpDeptid());
        ProvisionalAssesmentMstDto assMst = assesmentMastService.fetchAssessmentMasterByPropNo(orgId, dto.getProAssNo());
        setDescriptionOfOwners(model, dto, assMst);
        model.setPropTransferDto(dto);
        model.setMutationLevelFlag("A");
        model.setApprovalFlag(MainetConstants.FlagY);
        assMst.setLocationName(iLocationMasService.getLocationNameById(assMst.getLocId(), orgId));
        model.setProvisionalAssesmentMstDto(assMst);
        getModel().setDocumentList(iChecklistVerificationService.getDocumentUploaded(applicationId, orgId));
        model.setLastAuthorizer(true);
        boolean lastApproval = ApplicationContextProvider.getApplicationContext().getBean(IWorkFlowTypeService.class)
                .isLastTaskInCheckerTaskList(model.getWorkflowActionDto().getTaskId());
        if (lastApproval && StringUtils.equals(service.getSmScrutinyChargeFlag(), MainetConstants.FlagY)) {
            getModel().setLoiChargeApplFlag(MainetConstants.FlagY);
        } else {
            getModel().setLoiChargeApplFlag(MainetConstants.FlagN);
        }
        getModel().getWorkflowActionDto().setApplicationId(applicationId);

        if (assMst.getAssLandType() != null && assMst.getAssLandType() > 0) {
            assMst.setAssLandTypeDesc(CommonMasterUtility
                    .getNonHierarchicalLookUpObject(assMst.getAssLandType(),
                            UserSession.getCurrent().getOrganisation())
                    .getDescLangFirst());
            LookUp landTypePrefix = CommonMasterUtility.getNonHierarchicalLookUpObject(
                    assMst.getAssLandType(), UserSession.getCurrent().getOrganisation());
            getModel().setLandTypePrefix(landTypePrefix.getLookUpCode());
        }
        model.setProvisionalAssesmentMstDto(assMst);
        if (getModel().getLandTypePrefix() != null) {
            getModel().setlandTypeDetails();
        }
        model.setDropDownValues(assMst, UserSession.getCurrent().getOrganisation());
        getModel().getWorkflowActionDto().setTaskId(taskId);
        if (service.getSmScrutinyChargeFlag() != null) {
            Map<Long, Double> loiMap = mutationService.getLoiChargesAtApproval(dto, service,
                    UserSession.getCurrent().getOrganisation(), assMst);
            if (loiMap != null) {
                model.setLoiCharges(loiMap);
            }
        }

        return new ModelAndView("MutationAuthView", MainetConstants.FORM_NAME, getModel());
    }

    private void setDescriptionOfOwners(MutationModel model, PropertyTransferMasterDto dto, ProvisionalAssesmentMstDto assMst) {
        LookUp ownerType = CommonMasterUtility.getNonHierarchicalLookUpObject(
                assMst.getAssOwnerType(), UserSession.getCurrent().getOrganisation());
        model.setOwnershipPrefix(ownerType.getLookUpCode());
        LookUp lookup = CommonMasterUtility.getValueFromPrefixLookUp(ownerType.getLookUpCode(),
                MainetConstants.Property.propPref.OWT,
                UserSession.getCurrent().getOrganisation());
        model.setOwnershipTypeValue(lookup.getDescLangFirst());
        assMst.setProAssOwnerTypeName(ownerType.getDescLangFirst());
        LookUp ownerTypeNew = CommonMasterUtility.getNonHierarchicalLookUpObject(
                dto.getOwnerType(), UserSession.getCurrent().getOrganisation());

        if (MainetConstants.Property.SO.equals(ownerType.getLookUpCode())
                || MainetConstants.Property.JO.equals(ownerType.getLookUpCode())) {
            for (ProvisionalAssesmentOwnerDtlDto owner : assMst.getProvisionalAssesmentOwnerDtlDtoList()) {
                owner.setProAssGenderId(
                        CommonMasterUtility
                                .getNonHierarchicalLookUpObject(owner.getGenderId(), UserSession.getCurrent().getOrganisation())
                                .getDescLangFirst());
                owner.setProAssRelationId(
                        CommonMasterUtility.getNonHierarchicalLookUpObject(owner.getRelationId(),
                                UserSession.getCurrent().getOrganisation()).getDescLangFirst());
            }
        }
        if (MainetConstants.Property.SO.equals(ownerTypeNew.getLookUpCode())
                || MainetConstants.Property.JO.equals(ownerTypeNew.getLookUpCode())) {
            for (PropertyTransferOwnerDto ownerNew : dto.getPropTransferOwnerList()) {
                ownerNew.setGenderIdDesc(
                        CommonMasterUtility
                                .getNonHierarchicalLookUpObject(ownerNew.getGenderId(),
                                        UserSession.getCurrent().getOrganisation())
                                .getDescLangFirst());
                ownerNew.setRelationIdDesc(
                        CommonMasterUtility.getNonHierarchicalLookUpObject(ownerNew.getRelationId(),
                                UserSession.getCurrent().getOrganisation()).getDescLangFirst());
            }
        }

        model.setOwnershipPrefixNew(ownerTypeNew.getLookUpCode());
        dto.setProAssOwnerTypeName(ownerTypeNew.getDescLangFirst());
    }
}
