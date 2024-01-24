package com.abm.mainet.swm.ui.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.swm.dto.VehicleMaintenanceDTO;
import com.abm.mainet.swm.dto.VehicleMasterDTO;
import com.abm.mainet.swm.service.IVehicleMaintenanceService;
import com.abm.mainet.swm.service.IVehicleMasterService;
import com.abm.mainet.swm.ui.model.VehicleMaintenanceModel;

/**
 * @author Ajay.Kumar
 *
 */
@Controller
@RequestMapping("/VehicleMaintenance.html")
public class VehicleMaintenanceController extends AbstractFormController<VehicleMaintenanceModel> {

    /**
     * IVehicleMaintenance Service
     */
    @Autowired
    private IVehicleMaintenanceService vehicleMaintenanceService;

    /**
     * IVehicleMaster Service
     */
    @Autowired
    private IVehicleMasterService vehicleMasterService;

    /**
     * IFileUpload Service
     */
    @Autowired
    private IFileUploadService fileUpload;

    /**
     * IAttachDocs Service
     */
    @Autowired
    private IAttachDocsService attachDocsService;

    /**
     * SecondaryheadMaster Service
     */
    @Resource
    private SecondaryheadMasterService secondaryheadMasterService;

    /**
     * TbAcVendormaster Service
     */
    @Autowired
    private TbAcVendormasterService vendorService;

    /**
     * getVehicleMasterList
     * @return
     */
    private List<VehicleMasterDTO> getVehicleMasterList() {
        List<VehicleMasterDTO> vehicleMasterDTOList = vehicleMasterService.searchVehicleByVehicleTypeAndVehicleRegNo(null, null,
                UserSession.getCurrent().getOrganisation().getOrgid());
        Map<Long, String> locationMap = vehicleMasterDTOList.stream()
                .collect(Collectors.toMap(VehicleMasterDTO::getVeId, VehicleMasterDTO::getVeNo));
        if (CollectionUtils.isNotEmpty(this.getModel().getVehicleMaintenanceList())) {
            this.getModel().getVehicleMaintenanceList().forEach(master -> {
                master.setVeNo(locationMap.get(master.getVeId()));
            });
        }
        return vehicleMasterDTOList;
    }

    @InitBinder
    public void loadVendor() {
        Long sliLiveStatus = CommonMasterUtility
                .getValueFromPrefixLookUp(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_LIVE_VALUE,
                        MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_VALUE, UserSession.getCurrent().getOrganisation())
                .getLookUpId();
        final Long vendorStatus =  CommonMasterUtility
                .getValueFromPrefixLookUp(AccountConstants.AC.getValue(), PrefixConstants.VSS)
                .getLookUpId();
        final LookUp lookUpSacStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(MainetConstants.MASTER.A,
                PrefixConstants.ACN, UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
        final Long activeStatusId = lookUpSacStatus.getLookUpId();
        if (sliLiveStatus == null || sliLiveStatus == 0) {
            this.getModel().setVendors(
                    vendorService.getAllActiveVendors(UserSession.getCurrent().getOrganisation().getOrgid(), vendorStatus));
        } else {
            this.getModel()
                    .setVendors(vendorService.getActiveStatusVendorsAndSacAcHead(
                            UserSession.getCurrent().getOrganisation().getOrgid(),
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
        fileUpload.sessionCleanUpForFileUpload();
        this.getModel().setCommonHelpDocs("VehicleMaintenance.html");
        this.getModel().setVehicleMaintenanceList(vehicleMaintenanceService.searchVehicleMaintenance(null, null, null, null,
                UserSession.getCurrent().getOrganisation().getOrgid()));
        getVehicleMasterList();
        return index();
    }

    private void setVendorDetails() {
        Map<Long, String> locationMap = this.getModel().getVendors().stream()
                .collect(Collectors.toMap(TbAcVendormaster::getVmVendorid, TbAcVendormaster::getVmVendorname));
        this.getModel().getVehicleMaintenanceList().forEach(master -> {
            master.setVendorName(locationMap.get(master.getVendorId()));
        });
    }

    /**
     * addVehicleMaintenance
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "AddVehicleMaintenance", method = RequestMethod.POST)
    public ModelAndView addVehicleMaintenance(final HttpServletRequest request) {
        final Organisation org = UserSession.getCurrent().getOrganisation();
        // Get Expenditure Account Head//
        /*
         * Long taxMasLookUpId = null; List<LookUp> taxMaslookUpList =
         * CommonMasterUtility.getLevelData(MainetConstants.CommonMasterUi.TAC, 2, org); for (LookUp lookUp : taxMaslookUpList) {
         * if (lookUp.getLookUpCode().equals(MainetConstants.SolidWasteManagement.VMC)) { taxMasLookUpId = lookUp.getLookUpId();
         * break; } }
         */
        /*
         * if (taxMasLookUpId != null && org.getOrgid() != 81) { Map<Long, String> expenditureHead =
         * secondaryheadMasterService.getTaxMasBillPaymentsAcHeadAllDetails( org.getOrgid(), taxMasLookUpId); for (Map.Entry<Long,
         * String> entry : expenditureHead.entrySet()) {
         * this.getModel().getVehicleMaintenanceDTO().setExpenditureId(entry.getKey());
         * this.getModel().getVehicleMaintenanceDTO().setExpenditureHead(entry.getValue()); } }
         */
        // Get Deduction Account Head//
        /*
         * Long taxMasLookUpIdded = null; List<LookUp> taxDedMaslookUpList =
         * CommonMasterUtility.getLevelData(MainetConstants.CommonMasterUi.TAC, 1, org); for (LookUp lookUp : taxDedMaslookUpList)
         * { if (lookUp.getLookUpCode().equals(MainetConstants.StandardAccountHeadMapping.TDS)) { taxMasLookUpIdded =
         * lookUp.getLookUpId(); break; } }
         */
        /*
         * if (taxMasLookUpIdded != null && org.getOrgid() != 81) { Map<Long, String> expenditureHead1 =
         * secondaryheadMasterService.getTaxMasBillDeductionAcHeadDescAllDetails( org.getOrgid(), taxMasLookUpIdded);
         * VehicleMaintenanceDTO vehicleMaintenanceDTO = null; List<VehicleMaintenanceDTO> listVehicleMaintenanceDTO = new
         * ArrayList<>(); for (Map.Entry<Long, String> entry : expenditureHead1.entrySet()) { vehicleMaintenanceDTO = new
         * VehicleMaintenanceDTO(); vehicleMaintenanceDTO.setDedAcHeadId(entry.getKey());
         * vehicleMaintenanceDTO.setDedAcHead(entry.getValue()); listVehicleMaintenanceDTO.add(vehicleMaintenanceDTO); }
         * this.getModel().setVehicleMaintenanceList(listVehicleMaintenanceDTO); }
         */
        this.getModel().setValueTypeList(
                CommonMasterUtility.getLookUps(MainetConstants.CommonMasterUi.VTY, org));
        loadVendor();
        setVendorDetails();
        this.getModel().getVendors();
        ModelAndView mv = new ModelAndView("addVehicleMaintenance/Form", MainetConstants.FORM_NAME, this.getModel());
        mv.addObject("vehicles", getVehicleMasterList());
        return mv;
    }

    /**
     * editVehicleMaintenance
     * @param request
     * @param vemId
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "editVehicleMaintenance", method = RequestMethod.POST)
    public ModelAndView editVehicleMaintenance(final HttpServletRequest request, @RequestParam Long vemId) {
        fileUpload.sessionCleanUpForFileUpload();
        this.getModel().setVehicleMaintenanceDTO(vehicleMaintenanceService.getVehicleMaintenance(vemId));
        ModelAndView mv = new ModelAndView("editVehicleMaintenance/Form", MainetConstants.FORM_NAME, this.getModel());
        mv.addObject("vehicles", getVehicleMasterList());
        final List<AttachDocs> attachDocs = attachDocsService.findByCode(
                UserSession.getCurrent().getOrganisation().getOrgid(),
                vemId.toString());
        this.getModel().setAttachDocsList(attachDocs);
        return mv;
    }

    @ResponseBody
    @RequestMapping(params = "deleteVehicleMaintenance", method = RequestMethod.POST)
    public ModelAndView deleteVehicleMaintenance(final HttpServletRequest request, @RequestParam Long vemId) {
        Employee emp = UserSession.getCurrent().getEmployee();
        vehicleMaintenanceService.deleteVehicleMaintenance(vemId, emp.getEmpId(), emp.getEmppiservername());
        sessionCleanup(request);
        this.getModel().setVehicleMaintenanceList(vehicleMaintenanceService.searchVehicleMaintenance(null, null, null, null,
                UserSession.getCurrent().getOrganisation().getOrgid()));
        ModelAndView mv = new ModelAndView("searchVehicleMaintenance",
                MainetConstants.FORM_NAME, this.getModel());
        mv.addObject("vehicles", getVehicleMasterList());
        return mv;
    }

    /**
     * searchVehicleMaintenance
     * @param request
     * @param vehType
     * @param maintenanceType
     * @param fromDate
     * @param toDate
     * @param orgid
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "searchVehicleMaintenance", method = RequestMethod.POST)
    public ModelAndView searchVehicleMaintenance(final HttpServletRequest request, @RequestParam(required = false) Long vehType,
            @RequestParam(required = false) Long maintenanceType, @RequestParam(required = false) Date fromDate,
            @RequestParam(required = false) Date toDate,
            Long orgid) {
        sessionCleanup(request);
        this.getModel().getVehicleMaintenanceDTO().setVeVetype(vehType);
        this.getModel().getVehicleMaintenanceDTO().setVemMetype(maintenanceType);
        this.getModel().getVehicleMaintenanceDTO().setFromDate(fromDate);
        this.getModel().getVehicleMaintenanceDTO().setToDate(toDate);
        this.getModel().setVehicleMaintenanceList(
                vehicleMaintenanceService.searchVehicleMaintenance(vehType, maintenanceType, fromDate, toDate,
                        UserSession.getCurrent().getOrganisation().getOrgid()));
        ModelAndView mv = new ModelAndView("searchVehicleMaintenance",
                MainetConstants.FORM_NAME, this.getModel());
        mv.addObject("vehicles", getVehicleMasterList());
        return mv;
    }

    @ResponseBody
    @RequestMapping(params = "viewVehicleMaintenance", method = RequestMethod.POST)
    public ModelAndView viewVehicleMaintenance(final HttpServletRequest request, @RequestParam Long vemId) {
        fileUpload.sessionCleanUpForFileUpload();
        this.getModel().setVehicleMaintenanceDTO(vehicleMaintenanceService.getVehicleMaintenance(vemId));
        ModelAndView mv = new ModelAndView("viewVehicleMaintenance/Form", MainetConstants.FORM_NAME, this.getModel());
        mv.addObject("vehicles", getVehicleMasterList());
        final List<AttachDocs> attachDocs = attachDocsService.findByCode(
                UserSession.getCurrent().getOrganisation().getOrgid(),
                vemId.toString());
        this.getModel().setAttachDocsList(attachDocs);
        return mv;
    }

    /**
     * serchVehicleNo
     * @param vehicleTypeId
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(method = { RequestMethod.POST }, params = "vehicleNo")
    public @ResponseBody Map<Long, String> serchVehicleNo(@RequestParam("id") Long vehicleTypeId,
            final HttpServletRequest httpServletRequest) {
        List<VehicleMasterDTO> result = vehicleMasterService.searchVehicleByVehicleTypeAndVehicleRegNo(vehicleTypeId, null,
                UserSession.getCurrent().getOrganisation().getOrgid());
        Map<Long, String> data = new HashMap<>();
        if (result != null && !result.isEmpty()) {
            result.forEach(vdata -> {
                data.put(vdata.getVeId(), vdata.getVeNo());
            });
        }
        return data;
    }

    /**
     * formForPrint
     * @param model
     * @param request
     * @param vemId
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "formForPrint", method = RequestMethod.POST)
    public ModelAndView formForPrint(final Model model, final HttpServletRequest request, @RequestParam final Long vemId) {
        getVehicleMasterList();
        this.getModel().setVehicleMaintenanceDTO(vehicleMaintenanceService.getVehicleMaintenance(vemId));
        ModelAndView mv = new ModelAndView("VehicleMaintenancePrint/Form", MainetConstants.FORM_NAME, this.getModel());
        VehicleMaintenanceDTO vehicleMaintenanceDTO = this.getModel().getVehicleMaintenanceDTO();
        mv.addObject("vehicleMaintenanceDTO", this.getModel().getVehicleMaintenanceDTO());
        Map<Long, String> locationMap = getVehicleMasterList().stream()
                .collect(Collectors.toMap(VehicleMasterDTO::getVeId, VehicleMasterDTO::getVeNo));
        vehicleMaintenanceDTO.setVeNo(locationMap.get(vehicleMaintenanceDTO.getVeId()));
        return mv;
    }

    /**
     * lastMeter Reading
     * @param vehicleId
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(method = { RequestMethod.POST }, params = "lastMeterReading")
    public @ResponseBody Long lastMeterReading(Long vehicleId, final HttpServletRequest httpServletRequest) {
        Long lastMeterReading = vehicleMaintenanceService.getLastMeterReading(vehicleId,
                UserSession.getCurrent().getOrganisation().getOrgid());
        return lastMeterReading != null ? lastMeterReading : 0L;
    }
}
