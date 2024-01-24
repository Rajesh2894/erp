package com.abm.mainet.swm.ui.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.swm.dto.SanitationMasterDTO;
import com.abm.mainet.swm.service.IPublicToiletMasterService;
import com.abm.mainet.swm.ui.model.PublicToiletMasterModel;

/**
 * @author Ajay.Kumar
 *
 */
@Controller
@RequestMapping("/PublicToiletMaster.html")
public class PublicToiletMasterController extends AbstractFormController<PublicToiletMasterModel> {

    /**
     * The IPublicToiletMaster Service
     */
    @Autowired
    private IPublicToiletMasterService publicToiletMasterService;

    /**
     * The ILocationMas Service
     */
    @Autowired
    private ILocationMasService iLocationMasService;

    /**
     * The add Public Toilet Master
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "addPublicToiletMaster", method = RequestMethod.POST)
    public ModelAndView addPublicToiletMaster(final HttpServletRequest request) {
        this.getModel().setLocList(iLocationMasService.findlAllLocationByLocationCategoryAndOrgId(
                this.getModel().getLocationCat(), UserSession.getCurrent().getOrganisation().getOrgid()));
        return new ModelAndView("addPublicToiletMaster/Form", MainetConstants.FORM_NAME, this.getModel());
    }

    /**
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        this.getModel().setSanitationMasterList(publicToiletMasterService.searchToilet(null, null, null, null, null,
                null, null, null, UserSession.getCurrent().getOrganisation().getOrgid()));
        this.getModel().setCommonHelpDocs("PublicToiletMaster.html");
        return defaultResult();
    }

    /**
     * search Public Toilet Master
     * @param request
     * @param type
     * @param siteName
     * @param ward
     * @param zone
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "searchPublicToiletMaster", method = RequestMethod.POST)
    public ModelAndView searchPublicToiletMaster(final HttpServletRequest request,
            @RequestParam(required = false) Long type, @RequestParam(required = false) String siteName,
            @RequestParam(required = false) Long ward, @RequestParam(required = false) Long zone) {
        // sessionCleanup(request);
        this.getModel().getSanitationMasterDTO().setSanType(type);
        this.getModel().getSanitationMasterDTO().setSanName(siteName);
        this.getModel().getSanitationMasterDTO().setCodWard1(ward);
        this.getModel().getSanitationMasterDTO().setCodWard2(zone);
        this.getModel().setSanitationMasterList(publicToiletMasterService.searchToilet(null, type, siteName, ward, zone,
                null, null, null, UserSession.getCurrent().getOrganisation().getOrgid()));
        ModelAndView mv = new ModelAndView("searchPublicToiletMaster", MainetConstants.FORM_NAME, this.getModel());
        return mv;
    }

    /**
     * edit Public Toilet Master
     * @param request
     * @param sanId
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "editPublicToiletMaster", method = RequestMethod.POST)
    public ModelAndView editPublicToiletMaster(final HttpServletRequest request, @RequestParam Long sanId) {
        sessionCleanup(request);
        this.getModel().setSanitationMasterDTO(publicToiletMasterService.getPublicToiletByPublicToiletId(sanId));
        this.getModel().setLocList(iLocationMasService.findlAllLocationByLocationCategoryAndOrgId(
                this.getModel().getLocationCat(), UserSession.getCurrent().getOrganisation().getOrgid()));
        ModelAndView mv = new ModelAndView("editPublicToiletMaster/Form", MainetConstants.FORM_NAME, this.getModel());
        return mv;
    }

    /**
     * view Public Toilet Master
     * @param request
     * @param sanId
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "viewPublicToiletMaster", method = RequestMethod.POST)
    public ModelAndView viewPublicToiletMaster(final HttpServletRequest request, @RequestParam Long sanId) {
        sessionCleanup(request);
        this.getModel().setSanitationMasterDTO(publicToiletMasterService.getPublicToiletByPublicToiletId(sanId));
        this.getModel().setLocList(iLocationMasService.findlAllLocationByLocationCategoryAndOrgId(
                this.getModel().getLocationCat(), UserSession.getCurrent().getOrganisation().getOrgid()));
        ModelAndView mv = new ModelAndView("viewPublicToiletMaster/Form", MainetConstants.FORM_NAME, this.getModel());
        return mv;
    }

    /**
     * delete Public Toilet Master
     * @param request
     * @param sanId
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "deletePublicToiletMaster", method = RequestMethod.POST)
    public ModelAndView deletePublicToiletMaster(final HttpServletRequest request, @RequestParam Long sanId) {
        Employee emp = UserSession.getCurrent().getEmployee();
        publicToiletMasterService.deletePublicToilet(sanId, emp.getEmpId(), emp.getEmppiservername());
        sessionCleanup(request);
        this.getModel().setSanitationMasterList(publicToiletMasterService.searchToilet(null, null, null, null, null,
                null, null, null, UserSession.getCurrent().getOrganisation().getOrgid()));
        return new ModelAndView("searchPublicToiletMaster", MainetConstants.FORM_NAME, this.getModel());
    }

    @RequestMapping(params = "getMapData", method = { RequestMethod.POST }, produces = "application/json")
    @ResponseBody
    public Object[] getMapData() {
        final Object data[] = new Object[] { null, null, null, null };
        List<String[]> position = null;
        TbLocationMas locList;
        List<SanitationMasterDTO> pubToiletmstr = publicToiletMasterService.searchToilet(null, null, null, null, null,
                null, null, null, UserSession.getCurrent().getOrganisation().getOrgid());
        if (pubToiletmstr != null && !pubToiletmstr.isEmpty()) {
            position = new ArrayList<>();
            for (SanitationMasterDTO mstr : pubToiletmstr) {
                try {
                    if (mstr.getSanLocId() != null) {
                        locList = iLocationMasService.findById(mstr.getSanLocId());
                        if (locList.getLatitude() != null && locList.getLongitude() != null) {
                            final String[] mapData = new String[] { mstr.getSanName(), locList.getLatitude(),
                                    locList.getLongitude() };
                            position.add(mapData);
                        }
                    }
                } catch (Exception e) {
                    logger.error("error while encoding", e);
                }
            }
        }
        data[0] = position;
        return data;
    }
}
