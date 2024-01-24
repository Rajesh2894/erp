package com.abm.mainet.rts.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cfc.objection.dto.ObjectionDetailsDto;
import com.abm.mainet.cfc.objection.service.IObjectionDetailsService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.rts.dto.FirstAppealDto;
import com.abm.mainet.rts.service.IFirstAppealService;
import com.abm.mainet.rts.ui.model.FirstAppealModel;

@Controller
@RequestMapping(MainetConstants.RightToService.FIRST_APPEAL_URL)
public class FirstAppealController extends AbstractFormController<FirstAppealModel> {

    @Autowired
    IFileUploadService fileUpload;

    @Autowired
    IFirstAppealService firstAppealService;

    @Autowired
    private ICFCApplicationMasterService cfcService;

    @Autowired
    private IObjectionDetailsService objectionDetailsService;

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(HttpServletRequest request) throws Exception {
        this.sessionCleanup(request);
        fileUpload.sessionCleanUpForFileUpload();
        return defaultResult();
    }

    /*
     * this method used in objection entry page get applicant data based on referenceNo no
     */
    // U#73424
    @ResponseBody
    @RequestMapping(params = "get-applicant-details", method = RequestMethod.POST)
    public FirstAppealDto getPropertyMasterDetails(@RequestParam("referenceNo") String referenceNo) {
        FirstAppealDto firstAppeal = null;

        // firstAppeal = firstAppealService.getApplicantData(requestDTO);

        if (StringUtils.isNumeric(referenceNo)) {
            TbCfcApplicationMstEntity cfcApplicationMstEntity = cfcService
                    .getCFCApplicationByApplicationId(Long.valueOf(referenceNo),
                            UserSession.getCurrent().getOrganisation().getOrgid());
            if (cfcApplicationMstEntity != null) {
                firstAppeal = new FirstAppealDto();
                ApplicantDetailDTO applicant = new ApplicantDetailDTO();
                applicant.setApplicantFirstName(cfcApplicationMstEntity.getApmFname());

                applicant.setApplicantMiddleName(cfcApplicationMstEntity.getApmMname());
                applicant.setApplicantLastName(cfcApplicationMstEntity.getApmLname());
                applicant.setApplicantTitle(cfcApplicationMstEntity.getApmTitle());
                applicant.setGender(cfcApplicationMstEntity.getApmSex());
                applicant.setApmUID(cfcApplicationMstEntity.getApmUID());
                CFCApplicationAddressEntity addressEntity = cfcService.getApplicantsDetails(Long.valueOf(referenceNo));
                applicant.setMobileNo(addressEntity.getApaMobilno());
                applicant.setEmailId(addressEntity.getApaEmail());
                firstAppeal.setPermanentAddress(
                        addressEntity.getApaFlatBuildingNo() != null ? addressEntity.getApaFlatBuildingNo()
                                : ""
                                        + " " + addressEntity.getApaBldgnm() != null ? addressEntity.getApaBldgnm()
                                                : "" + " "
                                                        + addressEntity.getApaAreanm() != null ? addressEntity.getApaAreanm()
                                                                : ""
                                                                        + " " + addressEntity.getApaRoadnm() != null
                                                                                ? addressEntity.getApaRoadnm()
                                                                                : "" + " "
                                                                                        + addressEntity.getApaCityName() != null
                                                                                                ? addressEntity.getApaCityName()
                                                                                                : "" + " "
                                                                                                        + addressEntity
                                                                                                                .getApaBlockName() != null
                                                                                                                        ? addressEntity
                                                                                                                                .getApaBlockName()
                                                                                                                        : "");
                if (addressEntity.getApaPincode() != null) {
                    firstAppeal.setPermanentAddress(firstAppeal.getPermanentAddress() + " " + addressEntity.getApaPincode());
                }
                if (firstAppeal.getPermanentAddress().trim().length() == 0) {
                    firstAppeal.setPermanentAddress(null);
                }
                firstAppeal.setApplicantDetailDTO(applicant);
            }

        } else {
            firstAppeal = new FirstAppealDto();
            // get data from TB_OBJECTION_MAST
            ObjectionDetailsDto objection = objectionDetailsService
                    .getObjectionDetailByObjNo(UserSession.getCurrent().getOrganisation().getOrgid(), referenceNo);
            if (objection != null) {
                ApplicantDetailDTO applicant = new ApplicantDetailDTO();
                applicant.setApplicantTitle(objection.getTitle());
                applicant.setApplicantFirstName(objection.getfName());
                applicant.setApplicantMiddleName(objection.getmName());
                applicant.setApplicantLastName(objection.getlName());
                applicant.setGender(String.valueOf(objection.getGender()));
                applicant.setApmUID(objection.getUid());
                applicant.setMobileNo(objection.getMobileNo());
                applicant.setEmailId(objection.geteMail());
                firstAppeal.setPermanentAddress(objection.getAddress());
                firstAppeal.setApplicantDetailDTO(applicant);
                // set reason for appeal and objection details
                firstAppeal.setReasonForAppeal(objection.getObjectionReason());
                firstAppeal.setGroundForAppeal(objection.getObjectionDetails());
            }
        }
        return firstAppeal != null ? firstAppeal : new FirstAppealDto();
    }

}
