package com.abm.mainet.swm.ui.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.master.dto.TbOrganisation;
import com.abm.mainet.common.master.service.TbOrganisationService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.swm.ui.model.FineChargesCollectionReportModel;

/**
 * @author Ajay.Kumar
 *
 */
@Controller
@RequestMapping("/FineChargesCollectionReport.html")
public class FineChargesCollectionReportController extends AbstractFormController<FineChargesCollectionReportModel> {

    /**
     * The TbOrganisation Service
     */
    @Autowired
    private TbOrganisationService organisationService;

    /**
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        this.getModel().setCommonHelpDocs("FineChargesCollectionReport.html");
        loadDefaultData(httpServletRequest);
        return index();
    }

    private void loadDefaultData(HttpServletRequest httpServletRequest) {
        List<TbOrganisation> rogList = organisationService.findAll();
        this.getModel().setListOfUlb(rogList);
    }

    /**
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "Summary", method = RequestMethod.POST)
    public ModelAndView fineChargesCollectionSummary(final HttpServletRequest request) {
        sessionCleanup(request);
        return new ModelAndView("FineChargesCollectionSummaryReport", MainetConstants.FORM_NAME, this.getModel());
    }

    /**
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "Detail", method = RequestMethod.POST)
    public ModelAndView fineChargesCollectionDetails(final HttpServletRequest request) {
        sessionCleanup(request);
        return new ModelAndView("FineChargesCollectionDetailsReport", MainetConstants.FORM_NAME, this.getModel());
    }

}
