package com.abm.mainet.vehiclemanagement.ui.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.vehiclemanagement.Constants.Constants;
import com.abm.mainet.vehiclemanagement.dto.GenVehicleMasterDTO;
import com.abm.mainet.vehiclemanagement.dto.PumpFuelDetailsDTO;
import com.abm.mainet.vehiclemanagement.dto.PumpMasterDTO;
import com.abm.mainet.vehiclemanagement.dto.SLRMEmployeeMasterDTO;
import com.abm.mainet.vehiclemanagement.dto.VehicleFuellingDTO;
import com.abm.mainet.vehiclemanagement.service.IGenVehicleMasterService;
import com.abm.mainet.vehiclemanagement.service.IPumpMasterService;
import com.abm.mainet.vehiclemanagement.service.ISLRMEmployeeMasterService;
import com.abm.mainet.vehiclemanagement.service.IVehicleFuellingService;
import com.abm.mainet.vehiclemanagement.ui.model.VehicleFuelModel;

/**
 * @author Ajay.Kumar
 *
 */
@Controller
@RequestMapping("/vehicleFuel.html")
public class VehicleFuelController extends AbstractFormController<VehicleFuelModel> {

    /**
     * The IVehicleFuelling Service
     */
    @Autowired
    private IVehicleFuellingService vehicleFuellingService;

    /**
     * IVehicle Master Service
     */
    @Autowired
    private IGenVehicleMasterService vehicleMasterService;

    /**
     * IPump Master Service
     */
    @Autowired
    private IPumpMasterService pumpMasterService;

    /**
     * TbAcVendormaster Service
     */
    @Autowired
    private TbAcVendormasterService vendorService;

    /**
     * IFileUpload Service
     */
    @Autowired
    private IFileUploadService fileUpload;
    
	@Autowired
	private IEmployeeService iEmployeeService;

    /**
     * IAttachDocs Service
     */
    @Autowired
    private IAttachDocsService attachDocsService;
    
    @Autowired
    private IGenVehicleMasterService iGenVehicleMasterService;
    
    @Autowired
    private ISLRMEmployeeMasterService sLRMEmployeeMasterService;
    
    private List<GenVehicleMasterDTO> getVehicleMasterList() {
        List<GenVehicleMasterDTO> vehicleMasterList = vehicleMasterService.searchVehicleByVehicleTypeAndVehicleRegNo(null, null,null,null,
                UserSession.getCurrent().getOrganisation().getOrgid());
        Map<Long, String> locationMap = vehicleMasterList.stream()
                .collect(Collectors.toMap(GenVehicleMasterDTO::getVeId, GenVehicleMasterDTO::getVeNo));
        if (CollectionUtils.isNotEmpty(this.getModel().getVehicleFuellingList())) {
            this.getModel().getVehicleFuellingList().forEach(master -> {
                master.setVeNo(locationMap.get(master.getVeId()));
            });
        }
        return vehicleMasterList;
    }

    public List<PumpMasterDTO> getPumpMasterList() {
        List<PumpMasterDTO> pumpMasterList = pumpMasterService.serchPumpMasterByPumpType(null, null,
                UserSession.getCurrent().getOrganisation().getOrgid());
        Map<Long, String> pumpMap = pumpMasterList.stream()
                .collect(Collectors.toMap(PumpMasterDTO::getPuId, PumpMasterDTO::getPuPumpname));
        if (CollectionUtils.isNotEmpty(this.getModel().getVehicleFuellingList())) {
            this.getModel().getVehicleFuellingList().forEach(master -> {
                master.setPuPumpname(pumpMap.get(master.getPuId()));
            });
        }
        return pumpMasterList;
    }

    public List<TbAcVendormaster> getVendorMasterList() {
        final Long vendorStatus = CommonMasterUtility
                .getLookUpFromPrefixLookUpValue(AccountConstants.AC.getValue(), PrefixConstants.VSS,
                        UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation())
                .getLookUpId();
        this.getModel().setVendorList(
                vendorService.getAllActiveVendors(UserSession.getCurrent().getOrganisation().getOrgid(), vendorStatus));
        return vendorService.getAllActiveVendors(UserSession.getCurrent().getOrganisation().getOrgid(), vendorStatus);
    }

    /**
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {

        sessionCleanup(httpServletRequest);
        fileUpload.sessionCleanUpForFileUpload();
        this.getModel().setVehicleFuellingList(vehicleFuellingService.searchVehicleFuelling(null, null, null, null,
                UserSession.getCurrent().getOrganisation().getOrgid()));

        this.getModel().setCommonHelpDocs("VehicleFueling.html");
        getVehicleMasterList();
        httpServletRequest.setAttribute("pumps", getPumpMasterList());
        return index();

    }

    /**
     * Add Vehicle Fueling
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "AddVehicleFueling", method = RequestMethod.POST)
    public ModelAndView addVehicleFueling(final HttpServletRequest request) {
        fileUpload.sessionCleanUpForFileUpload();
        sessionCleanup(request);
        this.getModel().setSaveMode("A");
        this.getModel().setLookUps(CommonMasterUtility.getLookUps("TYI", UserSession.getCurrent().getOrganisation()));
        ModelAndView mv = new ModelAndView("AddVehicleFuel/Form", MainetConstants.FORM_NAME, this.getModel());
        List<SLRMEmployeeMasterDTO> driverDetais = sLRMEmployeeMasterService.searchEmployeeList(null, null, null, UserSession.getCurrent().getOrganisation().getOrgid()) ;
        mv.addObject("vendors", getVendorMasterList());
        mv.addObject("vehicles", getVehicleMasterList());
        mv.addObject("pumps", getPumpMasterList());
        mv.addObject("driverDet", driverDetais);
      //  this.getModel().setEmployeList(iEmployeeService.findEmpList(UserSession.getCurrent().getOrganisation().getOrgid()));
        return mv;
    }

    /**
     * Edit Vehicle Fueling
     * @param request
     * @param vefId
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "editVehicleFuelling", method = RequestMethod.POST)
    public ModelAndView editVehicleFueling(final HttpServletRequest request, @RequestParam Long vefId) {
        sessionCleanup(request);
        request.setAttribute(getViewName(), vefId);
        this.getModel().setSaveMode("E");
        this.getModel().setVehicleFuellingDTO(vehicleFuellingService.getVehicleByVehicleId(vefId));
        Long organisationId = UserSession.getCurrent().getOrganisation().getOrgid();
        String driverId = this.getModel().getVehicleFuellingDTO().getDriverName();
        List<SLRMEmployeeMasterDTO> driverDetais = sLRMEmployeeMasterService.searchEmployeeList(null, null, null, organisationId) ;
        SLRMEmployeeMasterDTO specificDriver = sLRMEmployeeMasterService.searchEmployeeDetails(Long.valueOf(driverId), organisationId);  
        this.getModel().getVehicleFuellingDTO().setDriverName(specificDriver.getFullName());
        this.getModel().getVehicleFuellingDTO().setVeEmpId(Long.valueOf(driverId));
        
        PumpMasterDTO pumpMasterDTO = pumpMasterService.getPumpByPumpId(this.getModel().getVehicleFuellingDTO().getPuId());
        List<PumpFuelDetailsDTO> pumpFuelDetails = pumpMasterDTO.getTbSwPumpFuldets();
        
		this.getModel().getVehicleFuellingDTO().getTbSwVehiclefuelDets().forEach(vehFuelingDetailDto->{
			for (PumpFuelDetailsDTO test : pumpFuelDetails) {
		        if (vehFuelingDetailDto.getPfuId() == test.getPfuId() && (vehFuelingDetailDto.getIsDeleted() == null || vehFuelingDetailDto.getIsDeleted().equals(MainetConstants.FlagN))) {
		        	test.setPuFuName(CommonMasterUtility.getCPDDescription(test.getPuFuid(), MainetConstants.BLANK));
			        test.setPuFuUnitName(CommonMasterUtility.getCPDDescription(test.getPuFuunit(), MainetConstants.BLANK));
			        vehFuelingDetailDto.setPumpFuelName(CommonMasterUtility.getCPDDescription(test.getPuFuid(), MainetConstants.BLANK));
			        vehFuelingDetailDto.setFuelUnit(CommonMasterUtility.getCPDDescription(test.getPuFuunit(), MainetConstants.BLANK));
		        }
		    }
		});

        ModelAndView mv = new ModelAndView("editVehFuelling/Form", MainetConstants.FORM_NAME, this.getModel());
        mv.addObject("pumpFuelDetails", pumpFuelDetails);
        mv.addObject("vendors", getVendorMasterList());
        if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)) {
			this.getModel().setVehicleMasterDTOs(getVehicleMasterList());
			this.getModel().setSlrmEmployeeDTOs(driverDetais);
			this.getModel().setPumpMasterDTOList(getPumpMasterList());
		} else {
			mv.addObject("vehicles", getVehicleMasterList());
			mv.addObject("pumps", getPumpMasterList());
			mv.addObject("driverDet", driverDetais);
		}
        final List<AttachDocs> attachDocs = attachDocsService
                .findByCode(organisationId, Constants.SHORT_CODE + MainetConstants.WINDOWS_SLASH +vefId.toString()+ MainetConstants.WINDOWS_SLASH + this.getModel().getVehicleFuellingDTO().getVeId());
        this.getModel().setAttachDocsList(attachDocs);
        
        GenVehicleMasterDTO dto = iGenVehicleMasterService.getVehicleByVehicleId(this.getModel().getVehicleFuellingDTO().getVeId());
        if(dto.getVeRentFromdate()!=null) {
        	this.getModel().getVehicleFuellingDTO().setVeRentFromDt(dto.getVeRentFromdate());
        }
        if(dto.getVeRentTodate()!=null) {
        	this.getModel().getVehicleFuellingDTO().setVeRentToDt(dto.getVeRentTodate());
        }
        if(dto.getVePurDate()!=null) {
        	this.getModel().getVehicleFuellingDTO().setVePurchaseDt(dto.getVePurDate());
        }
        if(dto.getVeFlag()!="" || dto.getVeFlag()!=null) {
        	this.getModel().getVehicleFuellingDTO().setVeDeptFlag(dto.getVeFlag());
        }
        if(dto.getFuelType() !=null) {  
        	this.getModel().getVehicleFuellingDTO().setvName(CommonMasterUtility.getCPDDescription(dto.getFuelType(), MainetConstants.BLANK));
        }
        return mv;
    }

    /**
     * view Vehicle Fueling
     * @param request
     * @param vefId
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "viewVehicleFueling", method = RequestMethod.POST)
    public ModelAndView viewVehicleFueling(final HttpServletRequest request, @RequestParam Long vefId) {
        sessionCleanup(request);
        this.getModel().setVehicleFuellingDTO(vehicleFuellingService.getVehicleByVehicleId(vefId));
        Long organisationId = UserSession.getCurrent().getOrganisation().getOrgid();
        String driverId = this.getModel().getVehicleFuellingDTO().getDriverName();
        SLRMEmployeeMasterDTO specificDriver = sLRMEmployeeMasterService.searchEmployeeDetails(Long.valueOf(driverId), organisationId);  
        this.getModel().getVehicleFuellingDTO().setDriverName(specificDriver.getFullName());
        PumpMasterDTO pumpMasterDTO = pumpMasterService.getPumpByPumpId(this.getModel().getVehicleFuellingDTO().getPuId());
        List<PumpFuelDetailsDTO> pumpFuelDetails = pumpMasterDTO.getTbSwPumpFuldets();
        
        this.getModel().getVehicleFuellingDTO().getTbSwVehiclefuelDets().forEach(vehFuelingDetailDto->{
        	for (PumpFuelDetailsDTO test : pumpFuelDetails) {
		        if (vehFuelingDetailDto.getPfuId() == test.getPfuId() && (vehFuelingDetailDto.getIsDeleted() == null || vehFuelingDetailDto.getIsDeleted().equals(MainetConstants.FlagN))) {
                    test.setPuFuName(CommonMasterUtility.getCPDDescription(test.getPuFuid(), MainetConstants.BLANK));
                    test.setPuFuUnitName(CommonMasterUtility.getCPDDescription(test.getPuFuunit(), MainetConstants.BLANK));
                    vehFuelingDetailDto.setPumpFuelName(CommonMasterUtility.getCPDDescription(test.getPuFuid(), MainetConstants.BLANK));
                    vehFuelingDetailDto.setFuelUnit(CommonMasterUtility.getCPDDescription(test.getPuFuunit(), MainetConstants.BLANK));
                }
            }
        });     
        
        ModelAndView mv = new ModelAndView("viewVehFuelling/Form", MainetConstants.FORM_NAME, this.getModel());
        mv.addObject("pumpFuelDetails", pumpFuelDetails);
        mv.addObject("vendors", getVendorMasterList());
        mv.addObject("vehicles", getVehicleMasterList());
        mv.addObject("pumps", getPumpMasterList());
        final List<AttachDocs> attachDocs = attachDocsService
                .findByCode(organisationId, Constants.SHORT_CODE + MainetConstants.WINDOWS_SLASH +vefId.toString()+ MainetConstants.WINDOWS_SLASH + this.getModel().getVehicleFuellingDTO().getVeId());
        this.getModel().setAttachDocsList(attachDocs);
        return mv;
    }

    /**
     * delete Vehicle Fueling
     * @param request
     * @param vefId
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "deleteVehicleFueling", method = RequestMethod.POST)
    public ModelAndView deleteVehicleFueling(final HttpServletRequest request, @RequestParam Long vefId) {
        Employee emp = UserSession.getCurrent().getEmployee();
        vehicleFuellingService.deleteVehicle(vefId, emp.getEmpId(), emp.getEmppiservername());
        sessionCleanup(request);
        this.getModel().setVehicleFuellingList(vehicleFuellingService.searchVehicleFuelling(null, null, null, null,
                UserSession.getCurrent().getOrganisation().getOrgid()));
        ModelAndView mv = new ModelAndView("searchVehicleFuel", MainetConstants.FORM_NAME, this.getModel());
        mv.addObject("vendors", getVendorMasterList());
        mv.addObject("vehicles", getVehicleMasterList());
        mv.addObject("pumps", getPumpMasterList());
        return mv;
    }
    
    /**
     * delete Vehicle Fueling
     * @param request
     * @param vefId
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "deleteVehicleFuelingDet", method = RequestMethod.POST)
    public boolean deleteVehicleFuelingDet(final HttpServletRequest request, @RequestParam Long vefId,@RequestParam Long vefdId) {
    	 Employee emp = UserSession.getCurrent().getEmployee();
        vehicleFuellingService.deleteVehicleDet(vefId, vefdId,  emp.getEmpId(),emp.getEmppiservername());
        sessionCleanup(request);
        return true;
    }

    /**
     * fetch Vehicle Fueling Details
     * @param request
     * @param pumpId
     * @param orgId
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "fetchVehicleFuelingDetails", method = RequestMethod.POST)
    public ModelAndView fetchVehicleFuelingDetails(final HttpServletRequest request,
            @RequestParam(required = false) Long pumpId, Long orgId) {
        PumpMasterDTO pumpMasterDTO = pumpMasterService.getPumpByPumpId(pumpId);
        List<PumpFuelDetailsDTO> pumpFuelDetails = new ArrayList<>();
        pumpMasterDTO.getTbSwPumpFuldets().forEach(det -> {
        	if(det.getPuActive().equalsIgnoreCase(MainetConstants.Y_FLAG)) {
        		det.setPuFuName(CommonMasterUtility.getCPDDescription(det.getPuFuid(), MainetConstants.BLANK));
                det.setPuFuUnitName(CommonMasterUtility.getCPDDescription(det.getPuFuunit(), MainetConstants.BLANK));
                pumpFuelDetails.add(det);
        	}
        });
        ModelAndView mv = new ModelAndView("AddPumpFuelDet/Form", MainetConstants.FORM_NAME, this.getModel());
        mv.addObject("pumpFuelDetails", pumpFuelDetails);
        return mv;
    }

    /**
     * search Vehicle Fueling
     * @param request
     * @param vehicleType
     * @param pumpId
     * @param fromDate
     * @param todDate
     * @param orgId
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "searchVehicleFueling", method = RequestMethod.POST)
    public ModelAndView searchVehicleFueling(final HttpServletRequest request,
            @RequestParam(required = false) Long vehicleType, @RequestParam(required = false) Long pumpId,
            @RequestParam(required = false) Date fromDate, @RequestParam(required = false) Date todDate, Long orgId) {
        sessionCleanup(request);
        this.getModel().getVehicleFuellingDTO().setVeVetype(vehicleType);
        this.getModel().getVehicleFuellingDTO().setPuId(pumpId);
        this.getModel().getVehicleFuellingDTO().setFromDate(fromDate);
        this.getModel().getVehicleFuellingDTO().setToDate(todDate);
        this.getModel().setVehicleFuellingList(vehicleFuellingService.searchVehicleFuelling(vehicleType, pumpId,
                fromDate, todDate, UserSession.getCurrent().getOrganisation().getOrgid()));
        ModelAndView mv = new ModelAndView("searchVehicleFuel", MainetConstants.FORM_NAME, this.getModel());
        mv.addObject("vendors", getVendorMasterList());
        mv.addObject("vehicles", getVehicleMasterList());
        mv.addObject("pumps", getPumpMasterList());
        return mv;
    }

    /**
     * serchVehicleNo
     * @param vehicleTypeId
     * @param httpServletRequest
     * @return
     */

    @RequestMapping(method = { RequestMethod.POST }, params = "vehicleNo")
    public @ResponseBody Map<Long, String> serchVehicleNo(@RequestParam("id") Long vehicleTypeId,@RequestParam("date") Date date1,
            final HttpServletRequest httpServletRequest) {
    	
    	Long deptId=UserSession.getCurrent().getEmployee().getTbDepartment().getDpDeptid();
        List<GenVehicleMasterDTO> result=new ArrayList<>();
        Long fireDeptId = ApplicationContextProvider.getApplicationContext().getBean(DepartmentService.class)
                .getDepartmentIdByDeptCode("FM");
		if (deptId == fireDeptId) {
			result = vehicleMasterService.searchVehicleByVehicleTypeAndVehicleRegNo(vehicleTypeId, null, fireDeptId,
					null, UserSession.getCurrent().getOrganisation().getOrgid());
		} else {
			result = vehicleMasterService.searchVehicleByVehicleTypeAndVehicleRegNo(vehicleTypeId, null, null, null,
					UserSession.getCurrent().getOrganisation().getOrgid());
		}

        Map<Long, String> data = new HashMap<>();
     
		if (result != null && !result.isEmpty()) {
			result.forEach(vdata -> {
				Calendar cal1 = Calendar.getInstance();
		    	cal1.setTime(date1);
		    	Date date = cal1.getTime();
				if (vdata.getVeFlag().equals(MainetConstants.FlagN)
						&& ((date.after(vdata.getVeRentFromdate()) && date.before(vdata.getVeRentTodate()))
								|| (date.equals(vdata.getVeRentFromdate()))
								|| (date.equals(vdata.getVeRentFromdate())))) {
					data.put(vdata.getVeId(), vdata.getVeNo());
				} else if (vdata.getVeFlag().equals(MainetConstants.FlagY)) {
					data.put(vdata.getVeId(), vdata.getVeNo());
				}
			});
			

		}
        return data;
    }

    /**
     * formForPrint
     * @param model
     * @param request
     * @param vefId
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "formForPrint", method = RequestMethod.POST)
    public ModelAndView formForPrint(final Model model, final HttpServletRequest request,
            @RequestParam final Long vefId) {
        VehicleFuellingDTO vehicleFuellingDTO = vehicleFuellingService.getVehicleByVehicleId(vefId);
        PumpMasterDTO pumpMasterDTO = pumpMasterService.getPumpByPumpId(vehicleFuellingDTO.getPuId());
        vehicleFuellingDTO.setPuPumpname(pumpMasterDTO.getPuPumpname());
        Map<Long, Long> itemMap = pumpMasterDTO.getTbSwPumpFuldets().stream()
                .collect(Collectors.toMap(PumpFuelDetailsDTO::getPfuId, PumpFuelDetailsDTO::getPuFuid));
        getVendorMasterList();
        Map<Long, String> vendorMap = this.getModel().getVendorList()
                .stream().collect(Collectors.toMap(TbAcVendormaster::getVmVendorid,
                        TbAcVendormaster::getVmVendorname));
        vehicleFuellingDTO.setvName(vendorMap.get(pumpMasterDTO.getVendorId()));
        Map<Long, String> locationMap = getVehicleMasterList().stream()
                .collect(Collectors.toMap(GenVehicleMasterDTO::getVeId, GenVehicleMasterDTO::getVeNo));
        vehicleFuellingDTO.getTbSwVehiclefuelDets().forEach(det -> {
            if(det.getVefdUnit()!=null && det.getPfuId()!=null) {
            det.setVefdUnitDesc(CommonMasterUtility.getCPDDescription(det.getVefdUnit(), MainetConstants.BLANK));
            det.setItemDesc(CommonMasterUtility.getCPDDescription(itemMap.get(det.getPfuId()), MainetConstants.BLANK));
            det.setVefdCost(det.getVefdCost());
            }
        });
        String driverId = vehicleFuellingDTO.getDriverName();
        SLRMEmployeeMasterDTO specificDriver = sLRMEmployeeMasterService.searchEmployeeDetails(Long.valueOf(driverId), UserSession.getCurrent().getOrganisation().getOrgid());  
        vehicleFuellingDTO.setDriverName(specificDriver.getFullName());
        vehicleFuellingDTO.setVeNo(locationMap.get(vehicleFuellingDTO.getVeId()));
        ModelAndView mv = new ModelAndView("VehicleFuelPrint/Form", MainetConstants.FORM_NAME, this.getModel());
        mv.addObject("vehicleFuellingDTO", vehicleFuellingDTO);
        mv.addObject("vehicles", getVehicleMasterList());
        return mv;
    }

    /**
     * last Meter Reading
     * @param vehicleId
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(method = { RequestMethod.POST }, params = "lastMeterReading")
    public @ResponseBody Long lastMeterReading(Long vehicleId, final HttpServletRequest httpServletRequest) {
        Long lastMeterReading = vehicleFuellingService.getLastMeterReading(vehicleId,
                UserSession.getCurrent().getOrganisation().getOrgid());
        return lastMeterReading != null ? lastMeterReading : 0L;
    }
    
      
    @RequestMapping(params = "searchVeNo", method = RequestMethod.POST)
	public @ResponseBody VehicleFuellingDTO searchVeNo(@RequestParam("vemId") Long vemId, HttpServletRequest request) {
		getModel().bind(request);
		Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		List<Object[]> list = vehicleMasterService.getVehicleByNumber(vemId, orgid);
		VehicleFuellingDTO dto = null;
		if(list != null) {
			for(Object[] obj : list) {
				dto = new VehicleFuellingDTO();
				dto.setVeId(Long.valueOf((Long)obj[0]));
				dto.setVeNo(obj[1].toString());
				//setting the fuelTypeName like Petrol or Desel or Gas 
				dto.setvName(CommonMasterUtility.getCPDDescription(Long.valueOf((Long)obj[2]), MainetConstants.BLANK));
			}
		}
		return dto;
	}

    @RequestMapping(params = "searchVeNoData", method = RequestMethod.POST)
	public @ResponseBody VehicleFuellingDTO searchVeNoData(@RequestParam("veNo") Long veNo, HttpServletRequest request) {
		getModel().bind(request);
		 
		GenVehicleMasterDTO vehicledto = iGenVehicleMasterService.getVehicleByVehicleId(veNo);
		VehicleFuellingDTO dto = new VehicleFuellingDTO();
		
	        if(vehicledto.getVePurDate()!=null) {
	        	dto.setVehiclePurchaseDate(new SimpleDateFormat("dd/MM/yyyy").format((Date) vehicledto.getVePurDate()));
	        }
	        if(vehicledto.getVeRentFromdate()!=null) {
	        	dto.setVehicleFromDate(new SimpleDateFormat("dd/MM/yyyy").format((Date) vehicledto.getVeRentFromdate()));
	        }
	        if(vehicledto.getVeRentTodate()!=null) {
	        	dto.setVehicleToeDate(new SimpleDateFormat("dd/MM/yyyy").format((Date) vehicledto.getVeRentTodate()));
	        }
		
		return dto;
	}


}
