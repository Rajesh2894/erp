package com.abm.mainet.swm.ui.controller;

import java.util.ArrayList;
import java.util.List;
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
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.master.dto.ContractMappingDTO;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.swm.dto.VehicleMasterDetDTO;
import com.abm.mainet.swm.service.IVehicleMasterService;
import com.abm.mainet.swm.service.IVendorContractMappingService;
import com.abm.mainet.swm.ui.model.VehicleMasterModel;

/**
 * @author Ajay.Kumar
 *
 */
@Controller
@RequestMapping("/VehicleMaster.html")
public class VehicleMasterController extends AbstractFormController<VehicleMasterModel> {

    /**
     * IVehicleMaster Service
     */
    @Autowired
    private IVehicleMasterService vehicleMasterService;

    /**
     * TbDepartment Service
     */
    @Autowired
    private TbDepartmentService tbDepartmentService;

    /**
     * TbAc Vendormaster Service
     */
    @Autowired
    private TbAcVendormasterService vendorMasterService;

    /**
     * IVendorContractMapping Service
     */
    @Autowired
    private IVendorContractMappingService vendorContractMappingService;

    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        loadDefaultData(httpServletRequest);
        this.getModel().setCommonHelpDocs("VehicleMaster.html");
        return index();
    }

    /**
     * @param httpServletRequest
     */
    private void loadDefaultData(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        this.getModel().setVehicleMasterList(vehicleMasterService.searchVehicle(null, null,
                UserSession.getCurrent().getOrganisation().getOrgid()));
    }

    /**
     * Populates the list of vendors
     */
    private void populateVendorList() {
        final Organisation org = UserSession.getCurrent().getOrganisation();
        final Integer languageId = UserSession.getCurrent().getLanguageId();
        final LookUp lookUpVendorStatus = CommonMasterUtility.getValueFromPrefixLookUp(AccountConstants.AC.getValue(),
                PrefixConstants.VSS);
        final Long vendorStatus = lookUpVendorStatus.getLookUpId();
        final List<TbAcVendormaster> vendorList = vendorMasterService.getAllActiveVendors(org.getOrgid(), vendorStatus);
        this.getModel().setVendorList(vendorList);

    }

    /**
     * add Vehicle Master
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "AddVehicleMaster", method = RequestMethod.POST)
    public ModelAndView addVehicleMaster(final HttpServletRequest request) {
        populateVendorList();
        return new ModelAndView("addVehicleMaster/Form", MainetConstants.FORM_NAME, this.getModel());
    }

    /**
     * edit Vehicle Master Form
     * @param request
     * @param veId
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "editVehicleMaster", method = RequestMethod.POST)
    public ModelAndView editVehicleMasterForm(final HttpServletRequest request, @RequestParam Long veId) {
        this.getModel().setSaveMode(MainetConstants.WorksManagement.EDIT);
        sessionCleanup(request);
        populateVendorList();
        VehicleMasterDetDTO VDetDTO = null;
        List<VehicleMasterDetDTO> VDetList = new ArrayList<>();
        this.getModel().setVehicleMasterDTO(vehicleMasterService.getVehicleByVehicleId(veId));
        for (VehicleMasterDetDTO det : this.getModel().getVehicleMasterDTO().getTbSwVehicleMasterdets()) {
            VDetDTO = new VehicleMasterDetDTO();
            VDetDTO.setWasteType(det.getWasteType());
            VDetDTO.setVeCapacity(det.getVeCapacity());
            VDetList.add(VDetDTO);
        }
        this.getModel().setVehicleMasterdetList(VDetList);
        Long vendorid = this.getModel().getVehicleMasterDTO().getVmVendorid();
        String vendorName = vendorMasterService.getVendorNameById(vendorid,
                UserSession.getCurrent().getOrganisation().getOrgid());
        final TbDepartment tbDepartment = tbDepartmentService.findDeptByCode(
                UserSession.getCurrent().getOrganisation().getOrgid(),
                MainetConstants.RnLCommon.Flag_A, MainetConstants.SolidWasteManagement.SHORT_CODE);
        if (this.getModel().getVehicleMasterDTO().getVeFlag().equalsIgnoreCase("N")) {
            List<ContractMappingDTO> cntrct = vendorContractMappingService.findContractDeptWise(
                    UserSession.getCurrent().getOrganisation().getOrgid(),
                    tbDepartment, MainetConstants.RnLCommon.Flag_A).stream()
                    .filter(v -> v.getVendorName().equalsIgnoreCase(vendorid.toString()))
                    .collect(Collectors.toList());
            this.getModel().setContractlist(cntrct);
        }
        return new ModelAndView("editVehicleMaster/Form", MainetConstants.FORM_NAME, this.getModel());
    }

    /**
     * view Vehicle MasterForm
     * @param request
     * @param veId
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "viewVehicleMaster", method = RequestMethod.POST)
    public ModelAndView viewVehicleMasterForm(final HttpServletRequest request, @RequestParam Long veId) {
        this.getModel().setSaveMode(MainetConstants.WorksManagement.VIEW);
        sessionCleanup(request);
        populateVendorList();
        VehicleMasterDetDTO VDetDTO = null;
        List<VehicleMasterDetDTO> VDetList = new ArrayList<>();
        this.getModel().setVehicleMasterDTO(vehicleMasterService.getVehicleByVehicleId(veId));
        for (VehicleMasterDetDTO det : this.getModel().getVehicleMasterDTO().getTbSwVehicleMasterdets()) {
            VDetDTO = new VehicleMasterDetDTO();
            VDetDTO.setWasteType(det.getWasteType());
            VDetDTO.setVeCapacity(det.getVeCapacity());
            VDetList.add(VDetDTO);
        }
        this.getModel().setVehicleMasterdetList(VDetList);
        Long vendorid = this.getModel().getVehicleMasterDTO().getVmVendorid();
        final TbDepartment tbDepartment = tbDepartmentService.findDeptByCode(
                UserSession.getCurrent().getOrganisation().getOrgid(),
                MainetConstants.RnLCommon.Flag_A, MainetConstants.SolidWasteManagement.SHORT_CODE);
        if (this.getModel().getVehicleMasterDTO().getVeFlag().equalsIgnoreCase("N")&& vendorid!=null) {
            List<ContractMappingDTO> cntrct = vendorContractMappingService.findContractDeptWise(
                    UserSession.getCurrent().getOrganisation().getOrgid(),
                    tbDepartment, MainetConstants.RnLCommon.Flag_A).stream()
                    .filter(v -> v.getVendorName()!=null && v.getVendorName().equalsIgnoreCase(vendorid.toString()))
                    .collect(Collectors.toList());
            this.getModel().setContractlist(cntrct);
        }
        return new ModelAndView("viewVehicleMaster/Form", MainetConstants.FORM_NAME, this.getModel());
    }

    /**
     * delete Vehicle Master
     * @param request
     * @param veId
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "deleteVehiclemasterData", method = RequestMethod.POST)
    public ModelAndView deleteVehicleMaster(final HttpServletRequest request, @RequestParam Long veId) {
        Employee emp = UserSession.getCurrent().getEmployee();
        vehicleMasterService.deleteVehicle(veId, emp.getEmpId(), emp.getEmppiservername());
        loadDefaultData(request);
        return new ModelAndView("VehicleMasterForm", MainetConstants.FORM_NAME, this.getModel());
    }

    /**
     * search Vehicle Master
     * @param request
     * @param vehicleType
     * @param vehicleRegNo
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "searchVehiclemasterData", method = RequestMethod.POST)
    public ModelAndView searchVehicleMaster(final HttpServletRequest request, @RequestParam Long vehicleType,
            @RequestParam String vehicleRegNo) {
        sessionCleanup(request);
        this.getModel().getVehicleMasterDTO().setVeVetype(vehicleType);
        this.getModel().getVehicleMasterDTO().setVeNo(vehicleRegNo);
        this.getModel().setVehicleMasterList(vehicleMasterService.searchVehicle(
                vehicleType, vehicleRegNo, UserSession.getCurrent().getOrganisation().getOrgid()));
        return new ModelAndView("VehicleMasterForm", MainetConstants.FORM_NAME, this.getModel());
    }

    /**
     * getContractNo
     * @param vendorName
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(method = { RequestMethod.POST }, params = "getContractNo")
    public @ResponseBody List<ContractMappingDTO> getContractNo(@RequestParam("id") Long vendorName,
            final HttpServletRequest httpServletRequest) {
        final TbDepartment tbDepartment = tbDepartmentService.findDeptByCode(
                UserSession.getCurrent().getOrganisation().getOrgid(),
                MainetConstants.RnLCommon.Flag_A, MainetConstants.SolidWasteManagement.SHORT_CODE);
        List<ContractMappingDTO> cntrct = vendorContractMappingService
                .findContractDeptWise(UserSession.getCurrent().getOrganisation().getOrgid(),
                        tbDepartment,
                        MainetConstants.RnLCommon.Flag_A)
                .stream().filter(v -> v.getVendorName()!=null && v.getVendorName().equalsIgnoreCase(vendorName.toString()))
                .collect(Collectors.toList());
        return cntrct;
    }
}
