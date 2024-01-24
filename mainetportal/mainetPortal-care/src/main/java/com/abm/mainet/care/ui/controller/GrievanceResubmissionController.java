package com.abm.mainet.care.ui.controller;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.care.dto.ActionResponseDTO;
import com.abm.mainet.care.dto.CareRequestDTO;
import com.abm.mainet.care.dto.ComplaintRegistrationAcknowledgementDTO;
import com.abm.mainet.care.dto.GrievanceReqDTO;
import com.abm.mainet.care.dto.ResponseType;
import com.abm.mainet.care.service.ICareRequestService;
import com.abm.mainet.care.ui.model.GrievanceResubmissionModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.dto.ActionDTO;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.dms.utility.FileUploadUtility;
import com.abm.mainet.integration.ws.JersyCall;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping(MainetConstants.WINDOWS_SLASH + MainetConstants.GrievanceConstants.GRIEVANCE_RESUBMISSION_CONTROLER)
public class GrievanceResubmissionController extends AbstractFormController<GrievanceResubmissionModel> {

    @Autowired
    private ICareRequestService careRequestService;

    @RequestMapping(params = MainetConstants.GrievanceConstants.APPL_ID, method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView resubmitApplication(@ModelAttribute(MainetConstants.GrievanceConstants.APP_ID) final Long applicationId,
            final HttpServletRequest httpServletRequest) throws Exception {
        sessionCleanup(httpServletRequest);
        bindModel(httpServletRequest);

        GrievanceResubmissionModel model = getModel();
        if (applicationId != null) {
            ComplaintRegistrationAcknowledgementDTO ackm = careRequestService
                    .constructRequestStatusAcknowledgement(applicationId, UserSession.getCurrent().getLanguageId());
            if (ackm != null) {
                model.setComplaintAcknowledgementModel(ackm);
                model.getAction().setApplicationId(applicationId);
                return new ModelAndView(MainetConstants.GrievanceConstants.GRIEVANCERESUBMISSION,
                        MainetConstants.FORM_NAME,
                        model);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(params = MainetConstants.GrievanceConstants.SAVE_DETAILS, method = RequestMethod.POST)
    public ModelAndView resubmitComplaint(HttpServletRequest request, ModelMap modelMap) throws URISyntaxException, Exception {

        bindModel(request);
        GrievanceResubmissionModel model = this.getModel();
        ActionDTO resubmitAction = model.getAction();

        CareRequestDTO careRequest = careRequestService.getCareRequestBycomplaintId(resubmitAction.getApplicationId().toString());
        resubmitAction.setApplicationId(careRequest.getApplicationId());

        resubmitAction.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
        resubmitAction.setCreatedDate(new Date());
        resubmitAction.setEmpId(Long.parseLong(UserSession.getCurrent().getEmployee().getEmpmobno()));
        resubmitAction.setEmpType(model.getEmplType());
        resubmitAction.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());

        GrievanceReqDTO reqDTO = new GrievanceReqDTO();
        reqDTO.setCareRequest(careRequest);
        reqDTO.setAction(resubmitAction);
        reqDTO.setReopen(false);
        reqDTO.setAttachments(setFileUploadMethod(model.getAttachments()));

        ActionResponseDTO actResponse = new ActionResponseDTO();

        LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall
                .callRestTemplateClientForJBPM(reqDTO,
                        ServiceEndpoints.CARE_SERVICE_RESUBMIT_GRIEVANCE_URL);
        String actResponse_ = new JSONObject(responseVo).toString();

        actResponse = new ObjectMapper().readValue(actResponse_, ActionResponseDTO.class);

        if (actResponse.getResponse().toUpperCase().equals(ResponseType.Success.toString())) {
            modelMap.addAttribute(MainetConstants.GrievanceConstants.COMPLAINTACKNOWLEDGEMENTMODEL,
                    careRequestService.constructRequestStatusAcknowledgement(resubmitAction.getApplicationId(),
                            UserSession.getCurrent().getLanguageId()));
            return new ModelAndView(MainetConstants.GrievanceConstants.REGISTRATIONACKNOWLEDGEMENTRECEIPT,
                    MainetConstants.GrievanceConstants.CARE_REQUEST,
                    careRequest);
        }
        return null;
    }

    private List<DocumentDetailsVO> setFileUploadMethod(final List<DocumentDetailsVO> docs) {
        Base64 base64 = null;
        List<File> list = null;
        for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
            list = new ArrayList<>(entry.getValue());
            for (final File file : list) {
                try {
                    base64 = new Base64();
                    final String bytestring = base64.encodeToString(FileUtils.readFileToByteArray(file));
                    DocumentDetailsVO d = new DocumentDetailsVO();
                    d.setDocumentName(file.getName());
                    d.setDocumentByteCode(bytestring);
                    docs.add(d);

                } catch (final IOException e) {
                    logger.error("Exception has been occurred in file byte to string conversions", e);
                }
            }
        }
        return docs;
    }
}
