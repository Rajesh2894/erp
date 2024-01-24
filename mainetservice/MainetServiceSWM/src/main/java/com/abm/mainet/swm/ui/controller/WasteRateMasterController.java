package com.abm.mainet.swm.ui.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.swm.dto.WasteRateMasterDTO;
import com.abm.mainet.swm.service.IWasteRateMasterService;
import com.abm.mainet.swm.ui.model.WasteRateMasterModel;

/**
 * @author Ajay.Kumar
 *
 */
@Controller
@RequestMapping("/WasteRateChart.html")
public class WasteRateMasterController extends AbstractFormController<WasteRateMasterModel> {
    private static final Logger logger = Logger.getLogger(WasteRateMasterController.class);

    /**
     * IWasteRateMaster Service
     */
    @Autowired
    IWasteRateMasterService wasteRateMasterService;

    @Autowired
    IOrganisationService organisationService;

    private static Long prefixLevel;
    private static int orgId;

    /**
     * index
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        this.getModel().setCommonHelpDocs("WasteRateChart.html");
        this.getModel()
                .setWasteRateList(wasteRateMasterService.getAllRate(UserSession.getCurrent().getOrganisation().getOrgid()));

        prefixLevel = wasteRateMasterService.getPrefixLevel("WTY", UserSession.getCurrent().getOrganisation().getOrgid());
        if (prefixLevel != null) {
            orgId = (int) UserSession.getCurrent().getOrganisation().getOrgid();
        } else {
            prefixLevel = wasteRateMasterService.getPrefixLevel("WTY", organisationService.getSuperUserOrganisation().getOrgid());
            orgId = (int) organisationService.getSuperUserOrganisation().getOrgid();
        }
        return index();
    }

    /**
     * getWasteList
     * @param wasteType
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(method = { RequestMethod.POST }, params = "selectWasteType")
    public @ResponseBody List<LookUp> getWasteList(@RequestParam("wasteType") long wasteType,
            final HttpServletRequest httpServletRequest) {
        List<LookUp> subType = null;
        try {
            subType = ApplicationSession.getInstance().getChildsByOrgPrefixTopParentLevel(orgId, "WTY", wasteType, prefixLevel);
        } catch (Exception e) {
            logger.error("Prefix Not Defined child ULB level:" + e);
        }
        return subType;
    }

    /**
     * add Rate Master
     * @param request
     * @return
     */
    @RequestMapping(method = { RequestMethod.POST }, params = "AddRateMaster")
    public ModelAndView addRateMaster(final HttpServletRequest request) {
        this.getModel().setPrefixLevel(prefixLevel);
        List<WasteRateMasterDTO> wList = wasteRateMasterService.getAllRate(UserSession.getCurrent().getOrganisation().getOrgid());
        this.getModel().getWasteRateMasterDto().setWasteRateList(wList);
        this.getModel().setSaveMode(MainetConstants.Legal.SaveMode.CREATE);
        return new ModelAndView("RateMaster", MainetConstants.FORM_NAME, this.getModel());
    }

    /**
     * edit Rate Master
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(method = { RequestMethod.POST }, params = "EditWasteRateList")
    public ModelAndView editRateMaster(@RequestParam("id") long id, final HttpServletRequest request) {
        this.getModel().setPrefixLevel(prefixLevel);
        this.getModel().setWasteRateMasterDto(wasteRateMasterService.getWasteRateById(id));
        this.getModel().setSaveMode(MainetConstants.Legal.SaveMode.EDIT);
        return new ModelAndView("RateMasterEdit", MainetConstants.FORM_NAME, this.getModel());
    }

    /**
     * view Rate Master
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(method = { RequestMethod.POST }, params = "ViewWasteRateList")
    public ModelAndView viewRateMaster(@RequestParam("id") long id, final HttpServletRequest request) {
        this.getModel().setPrefixLevel(prefixLevel);
        this.getModel().setWasteRateMasterDto(wasteRateMasterService.getWasteRateById(id));
        this.getModel().setSaveMode(MainetConstants.Legal.SaveMode.VIEW);
        return new ModelAndView("RateMasterEdit", MainetConstants.FORM_NAME, this.getModel());
    }

}
