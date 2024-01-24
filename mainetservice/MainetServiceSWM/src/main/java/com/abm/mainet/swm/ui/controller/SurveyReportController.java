package com.abm.mainet.swm.ui.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.WardZoneDescDTO;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.swm.dto.SurveyReportDTO;
import com.abm.mainet.swm.service.ISurveyFormService;
import com.abm.mainet.swm.ui.model.SurveyReportModel;

@Controller
@RequestMapping("/SurveyReportMaster.html")
public class SurveyReportController extends AbstractFormController<SurveyReportModel> {

    @Autowired
    private ILocationMasService iLocationMasService;

    @Autowired
    private ISurveyFormService surveyFormService;

    @Autowired
    private DepartmentService departmentService;

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);         
        return defaultResult();
    }

    @RequestMapping(method = { RequestMethod.POST }, params = "searchSurveyReport")
    public ModelAndView searchSurveyReport(@RequestParam(required = false) Long id, final HttpServletRequest request) {
        sessionCleanup(request);  
        
        List<TbLocationMas> locList = iLocationMasService.findlAllLocationByLocationCategoryAndOrgId(id,
                UserSession.getCurrent().getOrganisation().getOrgid());

        locList.forEach(tr->{          
            this.getModel().getSurveyReportDTOList().add(surveyFormService.searchSurveyDetails(tr.getLocId(), UserSession.getCurrent().getOrganisation().getOrgid()));
        });
        this.getModel().setLocationList(locList);
        return new ModelAndView("SurveyReportSearch", MainetConstants.FORM_NAME, this.getModel());
    };

    @RequestMapping(method = { RequestMethod.POST }, params = "addSurveyReport")
    public ModelAndView addSurveyReport(@RequestParam(required = false) Long locId, final HttpServletRequest request) {
        SurveyReportDTO surveydto = new SurveyReportDTO();

        surveydto = surveyFormService.searchSurveyDetails(locId, UserSession.getCurrent().getOrganisation().getOrgid());
        TbLocationMas location = iLocationMasService.findById(locId);
        surveydto.setLocId(locId);

        surveydto.setLocAddress(location.getLocAddress());
        surveydto.setLocName(location.getLocNameEng());

        /*if (surveydto.getSuId() == null) {
            Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.SolidWasteManagement.SHORT_CODE);
            List<WardZoneDescDTO> wardzonedesc = iLocationMasService
                    .fetchOperationalWard(UserSession.getCurrent().getOrganisation().getOrgid(), deptId, locId);
            if (!wardzonedesc.isEmpty()) {
                String wardName = wardzonedesc.get(1).getWardZoneDesc();
                
                 * surveydto.setCodWard1(wardzonedesc.get(0).getWardZoneLookupId());
                 * surveydto.setCodWard2(wardzonedesc.get(1).getWardZoneLookupId());
                 
                surveydto.setCodWard2Str(wardName);
            }
        }*/

        this.getModel().setSurveyReportDTO(surveydto);
        this.getModel().setSaveMode(MainetConstants.SolidWasteManagement.SaveMode.EDIT);

        return new ModelAndView("SurveyReportForm", MainetConstants.FORM_NAME, this.getModel());
    }

    @RequestMapping(method = { RequestMethod.POST }, params = "viewSurveyReport")
    public ModelAndView viewSurveyReport(@RequestParam(required = false) Long locId, final HttpServletRequest request) {
        SurveyReportDTO surveydto = new SurveyReportDTO();

        surveydto = surveyFormService.searchSurveyDetails(locId, UserSession.getCurrent().getOrganisation().getOrgid());
        TbLocationMas location = iLocationMasService.findById(locId);
        surveydto.setLocId(locId);

        surveydto.setLocAddress(location.getLocAddress());
        surveydto.setLocName(location.getLocNameEng());

        /*if (surveydto.getSuId() == null) {
            Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.SolidWasteManagement.SHORT_CODE);
            List<WardZoneDescDTO> wardzonedesc = iLocationMasService
                    .fetchOperationalWard(UserSession.getCurrent().getOrganisation().getOrgid(), deptId, locId);
            if (!wardzonedesc.isEmpty()) {
                String wardName = wardzonedesc.get(1).getWardZoneDesc();
                
                 * surveydto.setCodWard1(wardzonedesc.get(0).getWardZoneLookupId());
                 * surveydto.setCodWard2(wardzonedesc.get(1).getWardZoneLookupId());
                 
                surveydto.setCodWard2Str(wardName);
            }
        }*/

        this.getModel().setSurveyReportDTO(surveydto);
        this.getModel().setSaveMode(MainetConstants.SolidWasteManagement.SaveMode.VIEW);
        return new ModelAndView("SurveyReportForm", MainetConstants.FORM_NAME, this.getModel());
    }


}
