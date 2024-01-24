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
import com.abm.mainet.swm.dto.FineChargeCollectionDTO;
import com.abm.mainet.swm.service.ILogBookReportService;
import com.abm.mainet.swm.ui.model.AllFineLogBookReportModel;

@Controller
@RequestMapping("/AllFineReport.html")
public class AllFineLogBookReportController extends AbstractFormController<AllFineLogBookReportModel> {

    @Autowired
    private ILogBookReportService iLogBookReportService;

    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        this.getModel().setCommonHelpDocs("AllFineReport.html");
        return index();
    }

    @ResponseBody
    @RequestMapping(params = "allFine", method = RequestMethod.POST)
    public ModelAndView vehiclelogbookmainpage(final HttpServletRequest request,
            @RequestParam("monthNo") Long monthNo, @RequestParam("monthName") String monthName) {
        sessionCleanup(request);
        AllFineLogBookReportModel allFine = this.getModel();
        Long LookUpId = UserSession.getCurrent().getOrganisation().getOrgCpdIdDis();
        Organisation organisation = UserSession.getCurrent().getOrganisation();
        LookUp lookUpDesc = CommonMasterUtility.getNonHierarchicalLookUpObject(LookUpId, organisation);
        String distictName = lookUpDesc.getDescLangFirst();
        FineChargeCollectionDTO FineCharges = iLogBookReportService.getFineChargeByMonthNo(
                UserSession.getCurrent().getOrganisation().getOrgid(), monthNo);
        this.getModel().setFineChargeList(FineCharges);
        allFine.getFineChargeCollectionDTO().setMonthName(monthName);
        allFine.getFineChargeCollectionDTO().setDistrictName(distictName);
        return new ModelAndView("allFineLogBookReportData", MainetConstants.FORM_NAME, allFine);
    }

}
