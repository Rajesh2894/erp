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
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.swm.dto.AreaWiseDto;
import com.abm.mainet.swm.service.IDoorToDoorGarbageCollectionService;
import com.abm.mainet.swm.ui.model.SurveyReportModel;

@Controller
@RequestMapping("/AreaWiseSurveyReport.html")
public class AreaWiseSurveyReportController extends AbstractFormController<SurveyReportModel> {

    @Autowired
    private IDoorToDoorGarbageCollectionService iDoorToDoorGarbageCollectionService;

    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        this.getModel().setCommonHelpDocs("AreaWiseSurveyReport.html");
        return index();
    }

    @ResponseBody
    @RequestMapping(params = "BWG", method = RequestMethod.POST)
    public ModelAndView bulkWasteSurveyData(final HttpServletRequest request, @RequestParam String ptype,
            @RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate) {
        sessionCleanup(request);
        String redirectType = null;
        SurveyReportModel srm = this.getModel();
        AreaWiseDto areaWiseDetails = iDoorToDoorGarbageCollectionService.getAllAreaWiseSurveyData(
                UserSession.getCurrent().getOrganisation().getOrgid(), ptype,
                Utility.stringToDate(fromDate), Utility.stringToDate(toDate));
        if (areaWiseDetails != null) {
            srm.setAreaWiseDto(areaWiseDetails);
            srm.getAreaWiseDto().setFlagMsg("Y");
            srm.getAreaWiseDto().setFromDate(fromDate);
            srm.getAreaWiseDto().setToDate(toDate);
            srm.getAreaWiseDto().setReportType("Bulk Waste Generator Report");
            redirectType = "BulkWasteGeneratorReport";
        } else {
            srm.getAreaWiseDto().setFlagMsg("N");
            redirectType = "AreaWiseSurveyList";
        }
        return new ModelAndView(redirectType, MainetConstants.FORM_NAME, srm);
    }

    @ResponseBody
    @RequestMapping(params = "PG", method = RequestMethod.POST)
    public ModelAndView parkGardenSurveyData(final HttpServletRequest request, @RequestParam String ptype,
            @RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate) {
        sessionCleanup(request);
        String redirectType = null;
        SurveyReportModel srm = this.getModel();
        AreaWiseDto areaWiseDetails = iDoorToDoorGarbageCollectionService.getAllAreaWiseSurveyData(
                UserSession.getCurrent().getOrganisation().getOrgid(), ptype,
                Utility.stringToDate(fromDate), Utility.stringToDate(toDate));
        if (areaWiseDetails != null) {
            srm.setAreaWiseDto(areaWiseDetails);
            srm.getAreaWiseDto().setFlagMsg("Y");
            srm.getAreaWiseDto().setFromDate(fromDate);
            srm.getAreaWiseDto().setToDate(toDate);
            srm.getAreaWiseDto().setReportType("Park Garden Survey");
            redirectType = "ParkGardenSurveyReport";
        } else {
            srm.getAreaWiseDto().setFlagMsg("N");
            redirectType = "AreaWiseSurveyList";
        }
        return new ModelAndView(redirectType, MainetConstants.FORM_NAME, srm);
    }

    @ResponseBody
    @RequestMapping(params = "RWA", method = RequestMethod.POST)
    public ModelAndView uesidentialSurveyData(final HttpServletRequest request, @RequestParam String ptype,
            @RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate) {
        sessionCleanup(request);
        String redirectType = null;
        SurveyReportModel srm = this.getModel();
        AreaWiseDto areaWiseDetails = iDoorToDoorGarbageCollectionService.getAllAreaWiseSurveyData(
                UserSession.getCurrent().getOrganisation().getOrgid(), ptype,
                Utility.stringToDate(fromDate), Utility.stringToDate(toDate));
        if (areaWiseDetails != null) {
            srm.setAreaWiseDto(areaWiseDetails);
            srm.getAreaWiseDto().setFlagMsg("Y");
            srm.getAreaWiseDto().setFromDate(fromDate);
            srm.getAreaWiseDto().setToDate(toDate);
            srm.getAreaWiseDto().setReportType("Park Garden Survey");
            redirectType = "ResidentialAreaReport";
        } else {
            srm.getAreaWiseDto().setFlagMsg("N");
            redirectType = "AreaWiseSurveyList";
        }
        return new ModelAndView(redirectType, MainetConstants.FORM_NAME, srm);
    }

    @ResponseBody
    @RequestMapping(params = "PT", method = RequestMethod.POST)
    public ModelAndView urinationSurveyData(final HttpServletRequest request, @RequestParam String ptype,
            @RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate) {
        sessionCleanup(request);
        String redirectType = null;
        SurveyReportModel srm = this.getModel();

        AreaWiseDto areaWiseDetails = iDoorToDoorGarbageCollectionService.getAllAreaWiseSurveyData(
                UserSession.getCurrent().getOrganisation().getOrgid(), ptype,
                Utility.stringToDate(fromDate), Utility.stringToDate(toDate));

        if (areaWiseDetails != null) {
            srm.setAreaWiseDto(areaWiseDetails);
            srm.getAreaWiseDto().setFlagMsg("Y");
            srm.getAreaWiseDto().setFromDate(fromDate);
            srm.getAreaWiseDto().setToDate(toDate);
            srm.getAreaWiseDto().setReportType("Urination Spot Survey");
            redirectType = "urinationSpotSurvey";
        } else {
            srm.getAreaWiseDto().setFlagMsg("N");
            redirectType = "AreaWiseSurveyList";
        }
        return new ModelAndView(redirectType, MainetConstants.FORM_NAME, srm);
    }

    @ResponseBody
    @RequestMapping(params = "ODS", method = RequestMethod.POST)
    public ModelAndView oPenDefecateSurveyData(final HttpServletRequest request, @RequestParam String ptype,
            @RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate) {
        sessionCleanup(request);
        String redirectType = null;
        SurveyReportModel srm = this.getModel();
        AreaWiseDto areaWiseDetails = iDoorToDoorGarbageCollectionService.getAllAreaWiseSurveyData(
                UserSession.getCurrent().getOrganisation().getOrgid(), ptype,
                Utility.stringToDate(fromDate), Utility.stringToDate(toDate));
        if (areaWiseDetails != null) {
            srm.setAreaWiseDto(areaWiseDetails);
            srm.getAreaWiseDto().setFlagMsg("Y");
            srm.getAreaWiseDto().setFromDate(fromDate);
            srm.getAreaWiseDto().setToDate(toDate);
            srm.getAreaWiseDto().setReportType("ODSpotSurvey");
            redirectType = "ODSpotSurvey";
        } else {
            srm.getAreaWiseDto().setFlagMsg("N");
            redirectType = "AreaWiseSurveyList";
        }
        return new ModelAndView(redirectType, MainetConstants.FORM_NAME, srm);
    }

    @ResponseBody
    @RequestMapping(params = "BS", method = RequestMethod.POST)
    public ModelAndView beatificationSpotSurveyData(final HttpServletRequest request, @RequestParam String ptype,
            @RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate) {
        sessionCleanup(request);
        String redirectType = null;
        SurveyReportModel srm = this.getModel();
        AreaWiseDto areaWiseDetails = iDoorToDoorGarbageCollectionService.getAllAreaWiseSurveyData(
                UserSession.getCurrent().getOrganisation().getOrgid(), ptype,
                Utility.stringToDate(fromDate), Utility.stringToDate(toDate));
        if (areaWiseDetails != null) {
            srm.setAreaWiseDto(areaWiseDetails);
            srm.getAreaWiseDto().setFlagMsg("Y");
            srm.getAreaWiseDto().setFromDate(fromDate);
            srm.getAreaWiseDto().setToDate(toDate);
            srm.getAreaWiseDto().setReportType("beatificationSpotSurvey");
            redirectType = "beatificationSpotSurvey";
        } else {
            srm.getAreaWiseDto().setFlagMsg("N");
            redirectType = "AreaWiseSurveyList";
        }
        return new ModelAndView(redirectType, MainetConstants.FORM_NAME, srm);
    }

    @ResponseBody
    @RequestMapping(params = "GVP", method = RequestMethod.POST)
    public ModelAndView goodVigilanceSurveyData(final HttpServletRequest request, @RequestParam String ptype,
            @RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate) {
        sessionCleanup(request);
        String redirectType = null;
        SurveyReportModel srm = this.getModel();
        AreaWiseDto areaWiseDetails = iDoorToDoorGarbageCollectionService.getAllAreaWiseSurveyData(
                UserSession.getCurrent().getOrganisation().getOrgid(), ptype,
                Utility.stringToDate(fromDate), Utility.stringToDate(toDate));
        if (areaWiseDetails != null) {
            srm.setAreaWiseDto(areaWiseDetails);
            srm.getAreaWiseDto().setFlagMsg("Y");
            srm.getAreaWiseDto().setFromDate(fromDate);
            srm.getAreaWiseDto().setToDate(toDate);
            srm.getAreaWiseDto().setReportType("goodVigilanceSurvey");
            redirectType = "goodVigilanceSurvey";
        } else {
            srm.getAreaWiseDto().setFlagMsg("N");
            redirectType = "AreaWiseSurveyList";
        }
        return new ModelAndView(redirectType, MainetConstants.FORM_NAME, srm);
    }

}
