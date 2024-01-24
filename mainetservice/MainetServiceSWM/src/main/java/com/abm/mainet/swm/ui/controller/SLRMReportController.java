package com.abm.mainet.swm.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.swm.dto.WastageSegregationDTO;
import com.abm.mainet.swm.service.IMRFMasterService;
import com.abm.mainet.swm.service.IWastageSegregationService;
import com.abm.mainet.swm.ui.model.SLRMReportModel;

@Controller
@RequestMapping("/SLRMReport.html")
public class SLRMReportController extends AbstractFormController<SLRMReportModel> {

    @Autowired
    private IMRFMasterService mRFMasterService;
    @Autowired
    private IWastageSegregationService wastageSegregationService;

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        this.getModel().setCommonHelpDocs("SLRMReport.html");
        loadMrfCenterName(httpServletRequest);
        return defaultResult();
    }

    private void loadMrfCenterName(final HttpServletRequest httpServletRequest) {
        // sessionCleanup(httpServletRequest);
        this.getModel().setMrfMasterList(
                mRFMasterService.serchMrfCenter(null, null, UserSession.getCurrent().getOrganisation().getOrgid()));
    }

    @ResponseBody
    @RequestMapping(params = "slrmWasteWiseSegregation", method = RequestMethod.POST)
    public ModelAndView vehiclelogbookmainpage(final HttpServletRequest request, @RequestParam("mrfId") Long mrfId,
            @RequestParam("monthNo") Long monthNo, @RequestParam("monthName") String monthName,
            @RequestParam("centerName") String centerName) {
        sessionCleanup(request);
        SLRMReportModel srm = this.getModel();
        Long LookUpId = UserSession.getCurrent().getOrganisation().getOrgCpdIdDis();
        Organisation organisation = UserSession.getCurrent().getOrganisation();
        LookUp lookUpDesc = CommonMasterUtility.getNonHierarchicalLookUpObject(LookUpId, organisation);
        String distictName = lookUpDesc.getDescLangFirst();
        WastageSegregationDTO slrmSegregationDTO = wastageSegregationService
                .findSlrmWiseSegregation(UserSession.getCurrent().getOrganisation().getOrgid(), mrfId, monthNo);

        if (slrmSegregationDTO != null) {
            this.getModel().setWastageSegregationDTO(slrmSegregationDTO);
        }
        srm.getWastageSegregationDTO().setMrfCenterName(centerName);
        srm.getWastageSegregationDTO().setDsName(distictName);
        srm.getWastageSegregationDTO().setMonthName(monthName);
        return new ModelAndView("slrmWasteWiseSegregationReport", MainetConstants.FORM_NAME, srm);
    }
}
