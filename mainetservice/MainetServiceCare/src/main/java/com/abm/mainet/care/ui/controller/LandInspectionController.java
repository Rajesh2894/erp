package com.abm.mainet.care.ui.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.abm.mainet.care.dto.CareRequestDTO;
import com.abm.mainet.care.dto.LandInspectionDto;
import com.abm.mainet.care.service.ICareRequestService;
import com.abm.mainet.care.service.ILandInspectionService;
import com.abm.mainet.care.ui.model.LandInspectionModel;
import com.abm.mainet.care.utility.CareUtility;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;

@Controller
@RequestMapping("/LandInspection.html")
public class LandInspectionController extends AbstractFormController<LandInspectionModel> {

    @Autowired
    IFileUploadService fileUpload;

    @Autowired
    ILandInspectionService inspectionService;

    @Autowired
    private ICareRequestService careRequestService;

    @Autowired
    private IChecklistVerificationService checklistVerificationService;

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(HttpServletRequest request) throws Exception {
        this.sessionCleanup(request);
        fileUpload.sessionCleanUpForFileUpload();
        this.getModel().setModeType(MainetConstants.FlagA);
        // code for Land Inspection start here
        List<LookUp> vlgLookUpList = CommonMasterUtility.lookUpListByPrefix("VLG",
                UserSession.getCurrent().getOrganisation().getOrgid());
        // get complaint data using application Id or complaint no
        // CareRequestDTO complaintData = CareUtility.toCareRequestDTO(careRequestService.findByComplaintId(complaintId));
        this.getModel().setVlgLookUpList(vlgLookUpList);
        return defaultResult();
    }

    @RequestMapping(params = MainetConstants.Common_Constant.FORM, method = RequestMethod.POST)
    public ModelAndView landInspectForm(
            @RequestParam(value = "complaintId", required = false) String complaintId,
            @RequestParam(value = MainetConstants.Common_Constant.TYPE, required = false) String type,
            @RequestParam(value = "taskId", required = false) Long taskId,
            @RequestParam(value = "applicationId", required = false) Long applicationId, final ModelMap model,
            HttpServletRequest request) {

        sessionCleanup(request);
        fileUpload.sessionCleanUpForFileUpload();
        LandInspectionModel inspectionModel = this.getModel();
        inspectionModel.setModeType(MainetConstants.FlagA);
        // get complaint data using complaint no
        getComplaintData(inspectionModel, complaintId);
        List<LookUp> vlgLookUpList = CommonMasterUtility.lookUpListByPrefix("VLG",
                UserSession.getCurrent().getOrganisation().getOrgid());
        inspectionModel.setVlgLookUpList(vlgLookUpList);
        inspectionModel.setComplaintId(complaintId);
        inspectionModel.setTaskId(taskId);
        inspectionModel.setApplicationId(applicationId);
        return new ModelAndView("LandInspectionApproval", MainetConstants.FORM_NAME, inspectionModel);
    }

    @RequestMapping(params = "showDetails", method = RequestMethod.POST)
    public ModelAndView showDetails(@RequestParam("appNo") final String appNo,
            @RequestParam("taskId") final String taskId,
            @RequestParam(value = "actualTaskId", required = false) final Long actualTaskId,
            @RequestParam(value = "taskName", required = false) final String taskName,
            final HttpServletRequest httpServletRequest, final Model model) {
        this.sessionCleanup(httpServletRequest);
        fileUpload.sessionCleanUpForFileUpload();
        this.bindModel(httpServletRequest);
        LandInspectionModel landInspectionModel = this.getModel();
        List<LookUp> vlgLookUpList = CommonMasterUtility.lookUpListByPrefix("VLG",
                UserSession.getCurrent().getOrganisation().getOrgid());
        landInspectionModel.setVlgLookUpList(vlgLookUpList);
        landInspectionModel.setTaskId(actualTaskId);
        landInspectionModel.getWorkflowActionDto().setReferenceId(appNo);
        landInspectionModel.getWorkflowActionDto().setTaskId(actualTaskId);
        Long lnInspetId = Long.parseLong(appNo.substring(appNo.lastIndexOf('/') + 1));

        // get data of land Inspection Entry
        LandInspectionDto inspectionDto = inspectionService.getLandInspectionData(lnInspetId);
        landInspectionModel.setInspectionDto(inspectionDto);
        // get documents related to Complaint by complaintNo
        getComplaintData(landInspectionModel, inspectionDto.getComplaintNo());

        // get documents related to Land inspection
        List<AttachDocs> attachDocs = ApplicationContextProvider.getApplicationContext()
                .getBean(IAttachDocsService.class).findByCode(UserSession.getCurrent().getOrganisation().getOrgid(),
                        "LIE" + MainetConstants.WINDOWS_SLASH + inspectionDto.getOrgId() + MainetConstants.WINDOWS_SLASH
                                + inspectionDto.getLnInspId());

        // D#126294 differentiate documents of respected section
        List<AttachDocs> photosDocs = new ArrayList<>();
        List<AttachDocs> attachDocsList = new ArrayList<>();
        for (AttachDocs docs : attachDocs) {
            String docDescType = docs.getDocDesc();
            if (StringUtils.isNotBlank(docDescType) && docDescType.equals("photo")) {
                // add in setPhotosDocs
                photosDocs.add(docs);
            } else if (StringUtils.isNotBlank(docDescType) && docDescType.equals("endDoc")) {
                // add in setAttachDocsList
                attachDocsList.add(docs);
            } else {
                photosDocs.add(docs);
            }
        }
        /*
         * if (!attachDocs.isEmpty()) { AttachDocs lastDoc = attachDocs.get(attachDocs.size() - 1); List<AttachDocs> list =
         * Arrays.asList(lastDoc); landInspectionModel.setAttachDocsList(list); } int index = attachDocs.size() - 1; // remaining
         * docs attachDocs.remove(index); landInspectionModel.setPhotosDocs(attachDocs);
         */
        landInspectionModel.setPhotosDocs(photosDocs);
        landInspectionModel.setAttachDocsList(attachDocsList);
        landInspectionModel.setModeType("V");
        landInspectionModel.getWorkflowActionDto().setComments("");
        landInspectionModel.getWorkflowActionDto().setDecision(null);
        return new ModelAndView("LandInspectionApproval", MainetConstants.FORM_NAME, landInspectionModel);

    }

    @RequestMapping(params = "saveDecision", method = RequestMethod.POST)
    public ModelAndView approvalDecision(final HttpServletRequest httpServletRequest) {
        JsonViewObject respObj = null;
        this.bindModel(httpServletRequest);
        LandInspectionModel answerModel = this.getModel();
        // server side validation
        if (StringUtils.isBlank(answerModel.getWorkflowActionDto().getDecision())) {
            getModel().addValidationError(getApplicationSession().getMessage("ln.inspec.vldnn.approval.decision"));
            return defaultMyResult();
        }
        if (StringUtils.isBlank(answerModel.getWorkflowActionDto().getComments())) {
            getModel().addValidationError(getApplicationSession().getMessage("lqp.inspec.vldnn.approval.remarks"));
            return defaultMyResult();
        }
        String decision = answerModel.getWorkflowActionDto().getDecision();
        boolean updFlag = answerModel.updateLandInspectionWrkflowApproval(UserSession.getCurrent().getOrganisation().getOrgid());
        if (updFlag) {
            if (decision.equalsIgnoreCase(MainetConstants.WorkFlow.Decision.APPROVED))
                respObj = JsonViewObject
                        .successResult(ApplicationSession.getInstance().getMessage("ln.inspec.application.approved"));
            else if (decision.equalsIgnoreCase(MainetConstants.WorkFlow.Decision.REJECTED))
                respObj = JsonViewObject
                        .successResult(ApplicationSession.getInstance().getMessage("ln.inspec.application.reject"));
            else if (decision.equalsIgnoreCase(MainetConstants.WorkFlow.Decision.FORWARD_TO_EMPLOYEE))
                respObj = JsonViewObject
                        .successResult(ApplicationSession.getInstance().getMessage("ln.inspec.application.forward"));
        } else {
            respObj = JsonViewObject
                    .successResult(ApplicationSession.getInstance().getMessage("ln.inspec.application.failure"));
        }
        return new ModelAndView(new MappingJackson2JsonView(), MainetConstants.FORM_NAME, respObj);
    }

    public void getComplaintData(LandInspectionModel inspectionModel, String complaintId) {
        CareRequestDTO complaintData = CareUtility.toCareRequestDTO(careRequestService.findByComplaintId(complaintId));
        inspectionModel.getInspectionDto().setComplaintNo(complaintId);
        if (complaintData != null) {
            inspectionModel.getInspectionDto().setComplaintDet(complaintData.getDescription());
            // get document using application id
            // care document uploaded in TB_ATTACH_CFC existing code
            List<AttachDocs> complaintDocsList = new ArrayList<>();
            List<CFCAttachment> cfcDocuments = checklistVerificationService
                    .getDocumentUploadedForAppId(complaintData.getApplicationId(), complaintData.getOrgId());
            cfcDocuments.forEach(cfcDoc -> {
                AttachDocs complaintDoc = new AttachDocs();
                BeanUtils.copyProperties(cfcDoc, complaintDoc);
                complaintDoc.setAttId(cfcDoc.getAttId());
                complaintDoc.setAttPath(cfcDoc.getAttPath());
                complaintDoc.setAttFname(cfcDoc.getAttFname());
                complaintDocsList.add(complaintDoc);
            });
            inspectionModel.setComplaintDocsList(complaintDocsList);
        }
    }

}
