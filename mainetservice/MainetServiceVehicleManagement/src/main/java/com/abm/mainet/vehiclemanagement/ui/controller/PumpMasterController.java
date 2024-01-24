package com.abm.mainet.vehiclemanagement.ui.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.vehiclemanagement.dto.PumpMasterDTO;
import com.abm.mainet.vehiclemanagement.service.IPumpMasterService;
import com.abm.mainet.vehiclemanagement.ui.model.PumpMasterModel;

/**
 * @author Ajay.Kumar
 *
 */
@Controller
@RequestMapping("/fuelPumpMas.html")
public class   PumpMasterController extends AbstractFormController<PumpMasterModel> {
    /**
     * The IPumpMaster Service
     */
    @Autowired
    private IPumpMasterService pumpMasterService;

    /**
     * The TbAcVendormaster Service
     */
    @Autowired
    private TbAcVendormasterService vendorService;

    private List<TbAcVendormaster> vendors;

    @InitBinder
    public void loadVendor() {
        Long sliLiveStatus = CommonMasterUtility
                .getLookUpFromPrefixLookUpValue("L", "SLI",
                        UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation())
                .getLookUpId();
        @SuppressWarnings("deprecation")
		final Long vendorStatus = CommonMasterUtility
                .getValueFromPrefixLookUp(AccountConstants.AC.getValue(), PrefixConstants.VSS)
                .getLookUpId();
        final LookUp lookUpSacStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(MainetConstants.MASTER.A,
                PrefixConstants.ACN, UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
        final Long activeStatusId = lookUpSacStatus.getLookUpId();
        if (sliLiveStatus == null || sliLiveStatus == 0) {
            setVendors(vendorService.getAllActiveVendors(UserSession.getCurrent().getOrganisation().getOrgid(), vendorStatus));
        } else {
            setVendors(vendorService.getActiveStatusVendorsAndSacAcHead(UserSession.getCurrent().getOrganisation().getOrgid(),
                    vendorStatus,
                    activeStatusId));
        }
    }

    /**
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        this.getModel().setPumpMasterList(pumpMasterService.serchPumpMaster(null, null,
                UserSession.getCurrent().getOrganisation().getOrgid()));
        loadVendor();
        setVendorDetails();
        this.getModel().setCommonHelpDocs("RefuellingPumpStationMaster.html");
        return index();
    }

    private void setVendorDetails() {
        Map<Long, String> locationMap = getVendors().stream()
                .collect(Collectors.toMap(TbAcVendormaster::getVmVendorid, TbAcVendormaster::getVmVendorname));
        this.getModel().getPumpMasterList().forEach(master -> {
            master.setVendorName(locationMap.get(master.getVendorId()));
        });
    }

    /**
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "AddRefuellingPumpMaster", method = RequestMethod.POST)
    public ModelAndView addRefuellingPumpMaster(final HttpServletRequest request) {
    	this.getModel().setMode("A");
        this.getModel().setLookUps(CommonMasterUtility.getLookUps("TYI", UserSession.getCurrent().getOrganisation()));
        this.getModel().setLookUps(CommonMasterUtility.getLookUps("PMP", UserSession.getCurrent().getOrganisation()));
        ModelAndView mv = new ModelAndView("AddPumpMaster/Form", MainetConstants.FORM_NAME, this.getModel());
        mv.addObject("vendors", vendors);
        return mv;
    }

    /**
     * @param request
     * @param pumpId
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "editRefuellingPumpMaster", method = RequestMethod.POST)
    public ModelAndView editRefuellingPumpMaster(final HttpServletRequest request, @RequestParam Long pumpId) {
        sessionCleanup(request);
        this.getModel().setMode("E");
        this.getModel().setPumpMasterDTO(pumpMasterService.getPumpByPumpId(pumpId));
        ModelAndView mv = new ModelAndView("editPumpMaster/Form", MainetConstants.FORM_NAME, this.getModel());
        mv.addObject("vendors", vendors);
        return mv;
    }

    /**
     * @param request
     * @param pumpId
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "viewRefuellingPumpMaster", method = RequestMethod.POST)
    public ModelAndView viewRefuellingPumpMaster(final HttpServletRequest request, @RequestParam Long pumpId) {
        sessionCleanup(request);
        this.getModel().setPumpMasterDTO(pumpMasterService.getPumpByPumpId(pumpId));
        ModelAndView mv = new ModelAndView("viewPumpMaster/Form", MainetConstants.FORM_NAME, this.getModel());
        mv.addObject("vendors", vendors);
        return mv;
    }

    /**
     * @param request
     * @param pumpId
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "deleteRefuellingPumpMaster", method = RequestMethod.POST)
    public ModelAndView deleteRefuellingPumpMaster(final HttpServletRequest request, @RequestParam Long pumpId) {
        Employee emp = UserSession.getCurrent().getEmployee();
        pumpMasterService.deletePumpMaster(pumpId, emp.getEmpId(), emp.getEmppiservername());
        sessionCleanup(request);
        this.getModel().setPumpMasterList(pumpMasterService.serchPumpMaster(null, null,
                UserSession.getCurrent().getOrganisation().getOrgid()));
        loadVendor();
        setVendorDetails();
        ModelAndView mv = new ModelAndView("searchPumpStationMaster", MainetConstants.FORM_NAME, this.getModel());
        mv.addObject("vendors", vendors);
        return mv;
    }

    /**
     * @param request
     * @param pumpType
     * @param puPumpname
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "searchPumpMaster", method = RequestMethod.POST)
    public ModelAndView searchPumpMaster(final HttpServletRequest request, @RequestParam(required = false) Long pumpType,
            @RequestParam(required = false) String puPumpname) {
        sessionCleanup(request);
        this.getModel().setPumpMasterList(pumpMasterService.serchPumpMaster(pumpType, puPumpname,
                UserSession.getCurrent().getOrganisation().getOrgid()));
        loadVendor();
        setVendorDetails();
        this.getModel().getPumpMasterDTO().setPuPutype(pumpType);
        this.getModel().getPumpMasterDTO().setPuPumpname(puPumpname);
        ModelAndView mv = new ModelAndView("searchPumpStationMaster", MainetConstants.FORM_NAME, this.getModel());
        mv.addObject("vendors", vendors);
        return mv;

    }
    
    @ResponseBody
    @RequestMapping(params = "statusExits", method = RequestMethod.POST)
    public Boolean checkIfexit (HttpServletRequest req, @RequestParam String pumpType,
    		@RequestParam String vendorName, @RequestParam String puAddress, @RequestParam String puPumpname) {
    	boolean stat = false;
    	Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
    	PumpMasterDTO pumpMasterDTO = new PumpMasterDTO();
    	ServletRequestDataBinder binder = new ServletRequestDataBinder(this,"command");
    	
    	binder.bind(req);
    	pumpMasterDTO.setOrgid(orgid);
    	pumpMasterDTO.setVendorId(Long.valueOf(vendorName));
    	pumpMasterDTO.setPuPumpname(puPumpname);
    	pumpMasterDTO.setPuPutype(Long.valueOf(pumpType));
    	pumpMasterDTO.setPuAddress(puAddress);
    	stat = pumpMasterService.validatePumpMaster(pumpMasterDTO);
    	
    return stat;
    }

    public List<TbAcVendormaster> getVendors() {
        return vendors;
    }

    public void setVendors(List<TbAcVendormaster> vendors) {
        this.vendors = vendors;
    }
}
