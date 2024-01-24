package com.abm.mainet.swm.ui.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.swm.dto.MRFMasterDto;
import com.abm.mainet.swm.service.IMRFMasterService;
import com.abm.mainet.swm.service.ITripSheetService;
import com.abm.mainet.swm.service.IWastageSegregationService;
import com.abm.mainet.swm.ui.model.SegregationModel;

/**
 * @author Ajay.Kumar
 *
 */
@Controller
@RequestMapping("/Segregation.html")
public class SegregationController extends AbstractFormController<SegregationModel> {

    /**
     * IMRF Master Service
     */
    @Autowired
    private IMRFMasterService mRFMasterService;

    /**
     * The IWastage Segregation Service
     */
    @Autowired
    private IWastageSegregationService wastageSegregationService;

    /**
     * The IEmployee Service
     */
    @Autowired
    private IEmployeeService employeeService;

    /**
     * The ITripSheet Service
     */
    @Autowired
    private ITripSheetService tripSheetService;

    /**
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        this.getModel().setCommonHelpDocs("Segregation.html");
        this.getModel().setMrfMasterList(mRFMasterService.serchMrfCenter(null, null,
                UserSession.getCurrent().getOrganisation().getOrgid()));
        this.getModel().setTbSwWastesegDets(wastageSegregationService.search(null, null, null,
                UserSession.getCurrent().getOrganisation().getOrgid()));
        Map<Long, String> disposalMap = this.getModel().getMrfMasterList().stream()
                .collect(Collectors.toMap(MRFMasterDto::getMrfId, MRFMasterDto::getMrfPlantName));
        this.getModel().getTbSwWastesegDets().forEach(seg -> {
            seg.setTotalVol(BigDecimal.ZERO);
            seg.getTbSwWastesegDets().forEach(segdet -> {
                seg.setTotalVol(seg.getTotalVol().add(segdet.getTripVolume()));
                seg.setdName(disposalMap.get(seg.getDeId()));
            });
        });
        return index();
    }

    /**
     * @param request
     * @return
     */
    @RequestMapping(method = { RequestMethod.POST }, params = MainetConstants.SolidWasteManagement.ADD_SEGREGATION)
    public ModelAndView addSegregation(final HttpServletRequest request) {
        this.getModel().setMrfMasterList(mRFMasterService.serchMrfCenter(null, null,
                UserSession.getCurrent().getOrganisation().getOrgid()));
        this.getModel()
                .setEmployeeList(employeeService.getAllEmployee(UserSession.getCurrent().getOrganisation().getOrgid()));
        this.getModel().setSaveMode(MainetConstants.Legal.SaveMode.CREATE);
        return new ModelAndView(MainetConstants.SolidWasteManagement.SEGREGATION_FORM, MainetConstants.FORM_NAME,
                this.getModel());
    }

    /**
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(method = { RequestMethod.POST }, params = MainetConstants.SolidWasteManagement.EDIT_SEGREGATION)
    public ModelAndView editSegregation(@RequestParam Long id, final HttpServletRequest request) {
        this.getModel().setMrfMasterList(mRFMasterService.serchMrfCenter(null, null,
                UserSession.getCurrent().getOrganisation().getOrgid()));
        this.getModel()
                .setEmployeeList(employeeService.getAllEmployee(UserSession.getCurrent().getOrganisation().getOrgid()));
        this.getModel().setSegregationDto(wastageSegregationService.getById(id));
        this.getModel().setSaveMode(MainetConstants.Legal.SaveMode.EDIT);
        return new ModelAndView(MainetConstants.SolidWasteManagement.SEGREGATION_FORM, MainetConstants.FORM_NAME,
                this.getModel());
    }

    /**
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(method = { RequestMethod.POST }, params = MainetConstants.SolidWasteManagement.VIEW_SEGREGATION)
    public ModelAndView viewTripSheetMaster(@RequestParam Long id, final HttpServletRequest request) {
        this.getModel().setMrfMasterList(mRFMasterService.serchMrfCenter(null, null,
                UserSession.getCurrent().getOrganisation().getOrgid()));
        this.getModel()
                .setEmployeeList(employeeService.getAllEmployee(UserSession.getCurrent().getOrganisation().getOrgid()));
        this.getModel().setSegregationDto(wastageSegregationService.getById(id));
        this.getModel().setSaveMode(MainetConstants.Legal.SaveMode.VIEW);
        return new ModelAndView(MainetConstants.SolidWasteManagement.SEGREGATION_FORM, MainetConstants.FORM_NAME,
                this.getModel());
    }

    /**
     * @param id
     * @param fromDate
     * @param toDate
     * @param request
     * @return
     */
    @RequestMapping(method = { RequestMethod.POST }, params = MainetConstants.SolidWasteManagement.SEARCH_SEGREGATION)
    public ModelAndView searchTripSheetMaster(@RequestParam(required = false) Long id,
            @RequestParam(required = false) Date fromDate, @RequestParam(required = false) Date toDate,
            final HttpServletRequest request) {
        this.getModel().setCommonHelpDocs("Segregation.html");
        this.getModel().setTbSwWastesegDets(wastageSegregationService.search(id, fromDate, toDate,
                UserSession.getCurrent().getOrganisation().getOrgid()));
        this.getModel().getSegregationDto().setDeId(id);
        this.getModel().getSegregationDto().setFromDate(Utility.dateToString((fromDate)));
        this.getModel().getSegregationDto().setToDate(Utility.dateToString(toDate));
        Map<Long, String> disposalMap = this.getModel().getMrfMasterList().stream()
                .collect(Collectors.toMap(MRFMasterDto::getMrfId, MRFMasterDto::getMrfPlantName));
        Map<Long, String> vendorMap = this.getModel().getVendorList().stream()
                .collect(Collectors.toMap(TbAcVendormaster::getVmVendorid, TbAcVendormaster::getVmVendorname));
        this.getModel().getTbSwWastesegDets().forEach(seg -> {
            seg.setTotalVol(BigDecimal.ZERO);
            seg.getTbSwWastesegDets().forEach(segdet -> {
                seg.setTotalVol(seg.getTotalVol().add(segdet.getTripVolume()));
                seg.setdName(disposalMap.get(seg.getDeId()));
                seg.setVendorName(vendorMap.get(seg.getVenId()));
            });
        });
        return new ModelAndView(MainetConstants.SolidWasteManagement.SEGREGATION_SEARCH, MainetConstants.FORM_NAME,
                this.getModel());
    }

    /**
     * @param date
     * @param id
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(method = { RequestMethod.POST }, params = "disposalVolume")
    public Long getGarbageVolume(@RequestParam String date, @RequestParam Long id, final HttpServletRequest request) {
        Long volume = tripSheetService.getTotalGarbageCollectInDisposalsiteByDate(id,
                UserSession.getCurrent().getOrganisation().getOrgid(), date);
        if (volume == null) {
            return 0l;
        } else {
            return volume;
        }
    }

}
