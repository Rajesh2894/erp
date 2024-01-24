package com.abm.mainet.vehiclemanagement.ui.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

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
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.master.dto.ContractMappingDTO;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.vehiclemanagement.Constants.Constants;
import com.abm.mainet.vehiclemanagement.dto.GenVehicleMasterDTO;
import com.abm.mainet.vehiclemanagement.dto.GenVehicleMasterDetDTO;
import com.abm.mainet.vehiclemanagement.dto.SLRMEmployeeMasterDTO;
import com.abm.mainet.vehiclemanagement.service.IGenVehicleMasterService;
import com.abm.mainet.vehiclemanagement.service.ISLRMEmployeeMasterService;
import com.abm.mainet.vehiclemanagement.service.IVendorContractMappingService;
import com.abm.mainet.vehiclemanagement.ui.model.GeVehicleMasterModel;

@Controller
@RequestMapping(value = "/veMasterCon.html")
public class VmVehicleMasterController extends AbstractFormController<GeVehicleMasterModel> {

	/**
	 * IVehicleMaster Service
	 */
	@Autowired
	private IGenVehicleMasterService vehicleMasterService;

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

	@Autowired
	 private  ISLRMEmployeeMasterService sLRMEmployeeMasterService;
	
	@Autowired
	private IAttachDocsService attachDocsService;
	
	@Autowired
	private IFileUploadService fileUpload;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		loadDefaultData(httpServletRequest);
		this.getModel().setCommonHelpDocs("veMasterCon.html");
		return index();
	}

	/**
	 * @param httpServletRequest
	 */
	private void loadDefaultData(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		httpServletRequest.setAttribute("departments", loadDepartmentList());
		httpServletRequest.setAttribute("locations", loadLocation());
		httpServletRequest.setAttribute("vehicle",(vehicleMasterService.searchVehicle(null, null, null, null,null,
				UserSession.getCurrent().getOrganisation().getOrgid())));
		this.getModel().setVehicleMasterList(vehicleMasterService.searchVehicle(null, null, null, null,null,
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
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "AddVehicleMaster", method = RequestMethod.POST)
	public ModelAndView addVehicleMast(Model model, final HttpServletRequest request) {
		sessionCleanup(request);
		fileUpload.sessionCleanUpForFileUpload();
		populateVendorList();
		request.setAttribute("departments", loadDepartmentList());
		request.setAttribute("locations", loadLocation());
		List<SLRMEmployeeMasterDTO> employeeList = sLRMEmployeeMasterService.searchEmployeeList(null, null, null, UserSession.getCurrent().getOrganisation().getOrgid());
		model.addAttribute("employees", employeeList);
		return new ModelAndView("addVmVehicleMaster/Form", MainetConstants.FORM_NAME, this.getModel());
	}

	/**
	 * edit Vehicle Master Form
	 * 
	 * @param request
	 * @param veId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "editVehicleMaster", method = RequestMethod.POST)
	public ModelAndView editVehicleMasterFor(Model model, final HttpServletRequest request, @RequestParam Long veId) {
		this.getModel().setSaveMode(MainetConstants.WorksManagement.EDIT);
		sessionCleanup(request);
		fileUpload.sessionCleanUpForFileUpload();
		populateVendorList();
		request.setAttribute("departments", loadDepartmentList());
		request.setAttribute("locations", loadLocation());
		List<SLRMEmployeeMasterDTO> employeeList = sLRMEmployeeMasterService.searchEmployeeList(null, null, null, UserSession.getCurrent().getOrganisation().getOrgid());
		request.setAttribute("employees", employeeList);
		GenVehicleMasterDetDTO VDetDTO = null;
		List<GenVehicleMasterDetDTO> VDetList = new ArrayList<>();
		this.getModel().setVehicleMasterDTO(vehicleMasterService.getVehicleByVehicleId(veId));
		for (GenVehicleMasterDetDTO det : this.getModel().getVehicleMasterDTO().getTbSwVehicleMasterdets()) {
			VDetDTO = new GenVehicleMasterDetDTO();
			VDetDTO.setWasteType(det.getWasteType());
			VDetDTO.setVeCapacity(det.getVeCapacity());
			VDetList.add(VDetDTO);
		}
		this.getModel().setVehicleMasterdetList(VDetList);
		Long vendorid = this.getModel().getVehicleMasterDTO().getVmVendorid();
		String vendorName = vendorMasterService.getVendorNameById(vendorid,
				UserSession.getCurrent().getOrganisation().getOrgid());
		final TbDepartment tbDepartment = tbDepartmentService.findDeptByCode(
				UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.RnLCommon.Flag_A,
				Constants.SHORT_CODE);
		if (this.getModel().getVehicleMasterDTO().getVeFlag().equalsIgnoreCase("N")) {
			List<ContractMappingDTO> cntrct = vendorContractMappingService
					.findContractDeptWise(UserSession.getCurrent().getOrganisation().getOrgid(), tbDepartment,
							MainetConstants.RnLCommon.Flag_A)
					.stream().filter(v -> v.getVendorName().equalsIgnoreCase(vendorid.toString()))
					.collect(Collectors.toList());
			this.getModel().setContractlist(cntrct);
		}
		final List<AttachDocs> attachDocs = attachDocsService.findByCode(
                UserSession.getCurrent().getOrganisation().getOrgid(),
                (Constants.SHORT_CODE + MainetConstants.WINDOWS_SLASH +Constants.VECH_MASTER+ this.getModel().getVehicleMasterDTO().getVeId()+MainetConstants.WINDOWS_SLASH + UserSession.getCurrent().getOrganisation().getOrgid()));
        this.getModel().setAttachDocsList(attachDocs);
		return new ModelAndView("editVmVehicleMaster/Form", MainetConstants.FORM_NAME, this.getModel());
	}

	/**
	 * view Vehicle MasterForm
	 * 
	 * @param request
	 * @param veId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "viewVehicleMaster", method = RequestMethod.POST)
	public ModelAndView viewVehicleMasterFor(Model model, final HttpServletRequest request, @RequestParam Long veId) {
		this.getModel().setSaveMode(MainetConstants.WorksManagement.VIEW);
		sessionCleanup(request);
		populateVendorList();
		request.setAttribute("departments", loadDepartmentList());
		request.setAttribute("locations", loadLocation());
		List<SLRMEmployeeMasterDTO> employeeList = sLRMEmployeeMasterService.searchEmployeeList(null, null, null, UserSession.getCurrent().getOrganisation().getOrgid());
		request.setAttribute("employees", employeeList);
		GenVehicleMasterDetDTO VDetDTO = null;
		List<GenVehicleMasterDetDTO> VDetList = new ArrayList<>();
		this.getModel().setVehicleMasterDTO(vehicleMasterService.getVehicleByVehicleId(veId));
		for (GenVehicleMasterDetDTO det : this.getModel().getVehicleMasterDTO().getTbSwVehicleMasterdets()) {
			VDetDTO = new GenVehicleMasterDetDTO();
			VDetDTO.setWasteType(det.getWasteType());
			VDetDTO.setVeCapacity(det.getVeCapacity());
			VDetList.add(VDetDTO);
		}
		this.getModel().setVehicleMasterdetList(VDetList);
		Long vendorid = this.getModel().getVehicleMasterDTO().getVmVendorid();
		final TbDepartment tbDepartment = tbDepartmentService.findDeptByCode(
				UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.RnLCommon.Flag_A,
				Constants.SHORT_CODE);
		if (this.getModel().getVehicleMasterDTO().getVeFlag().equalsIgnoreCase("N")) {
			List<ContractMappingDTO> cntrct = vendorContractMappingService
					.findContractDeptWise(UserSession.getCurrent().getOrganisation().getOrgid(), tbDepartment,
							MainetConstants.RnLCommon.Flag_A)
					.stream().filter(v -> v.getVendorName().equalsIgnoreCase(vendorid.toString()))
					.collect(Collectors.toList());
			this.getModel().setContractlist(cntrct);
		}
		final List<AttachDocs> attachDocs = attachDocsService.findByCode(
                UserSession.getCurrent().getOrganisation().getOrgid(),
                (Constants.SHORT_CODE + MainetConstants.WINDOWS_SLASH +Constants.VECH_MASTER+this.getModel().getVehicleMasterDTO().getVeId() +MainetConstants.WINDOWS_SLASH + UserSession.getCurrent().getOrganisation().getOrgid()));
        this.getModel().setAttachDocsList(attachDocs);
		return new ModelAndView("viewVmVehicleMaster/Form", MainetConstants.FORM_NAME, this.getModel());
	}

	/**
	 * delete Vehicle Master
	 * 
	 * @param request
	 * @param veId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "deleteVehiclemasterData", method = RequestMethod.POST)
	public ModelAndView deleteVehicleMast(final HttpServletRequest request, @RequestParam Long veId) {
		Employee emp = UserSession.getCurrent().getEmployee();
		vehicleMasterService.deleteVehicle(veId, emp.getEmpId(), emp.getEmppiservername());
		loadDefaultData(request);
		return new ModelAndView("VmVehicleMasterForm", MainetConstants.FORM_NAME, this.getModel());
	}

	/**
	 * search Vehicle Master
	 * 
	 * @param request
	 * @param vehicleType
	 * @param vehicleRegNo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "searchVehiclemasterData", method = RequestMethod.POST)
	public ModelAndView searchVehicleMast(final HttpServletRequest request, @RequestParam Long vehicleType,
			@RequestParam String vehicleRegNo, @RequestParam(value ="veChasisSrno",required = false ) String veChasisSrno,
			@RequestParam Long deptId, @RequestParam Long location) {
		sessionCleanup(request);
		this.getModel().getVehicleMasterDTO().setVeVetype(vehicleType);
		this.getModel().getVehicleMasterDTO().setVeNo(vehicleRegNo);
		this.getModel().getVehicleMasterDTO().setVeChasisSrno(veChasisSrno);
		this.getModel().getVehicleMasterDTO().setDeptId(deptId);
		this.getModel().getVehicleMasterDTO().setLocId(location);
		request.setAttribute("departments", loadDepartmentList());
		request.setAttribute("locations", loadLocation());
		this.getModel().setVehicleMasterList(vehicleMasterService.searchVehicle(vehicleType, vehicleRegNo,veChasisSrno, deptId,
				location, UserSession.getCurrent().getOrganisation().getOrgid()));
		request.setAttribute("vehicle",(vehicleMasterService.searchVehicle(null, null, null, null,null,
				UserSession.getCurrent().getOrganisation().getOrgid())));
		return new ModelAndView("VmVehicleMasterForm", MainetConstants.FORM_NAME, this.getModel());
	}

	/**
	 * getContractNo
	 * 
	 * @param vendorName
	 * @param httpServletRequest
	 * @return
	 */

	@RequestMapping(method = { RequestMethod.POST }, params = "getContractNo")
	public @ResponseBody List<ContractMappingDTO> getContractNo(@RequestParam("id") Long vendorName,
			final HttpServletRequest httpServletRequest) {
		final TbDepartment tbDepartment = tbDepartmentService.findDeptByCode(
				UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.RnLCommon.Flag_A,
				Constants.SHORT_CODE);
		List<ContractMappingDTO> cntrct = vendorContractMappingService
				.findContractDeptWise(UserSession.getCurrent().getOrganisation().getOrgid(), tbDepartment,
						MainetConstants.RnLCommon.Flag_A)
				.stream().filter(v -> v.getVendorName().equalsIgnoreCase(vendorName.toString()))
				.collect(Collectors.toList());
		return cntrct;
	}

	/**
	 * @return Department List
	 */
	private List<Department> loadDepartmentList() {
		DepartmentService departmentService = ApplicationContextProvider.getApplicationContext()
				.getBean(DepartmentService.class);
		List<Department> departments = departmentService
				.getDepartments(UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.FlagA);
		return departments;
	}

	private List<TbLocationMas> loadLocation() {
		ILocationMasService locationMasService = ApplicationContextProvider.getApplicationContext()
				.getBean(ILocationMasService.class);
		List<TbLocationMas> locations = locationMasService
				.fillAllActiveLocationByOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		return locations;
	}
	
    @ResponseBody
    @RequestMapping(params = "recordExists", method = RequestMethod.POST)
    public Boolean checkIfexit (@RequestParam String EngineNo, @RequestParam String chasisno, @RequestParam String veReg, @RequestParam Long veId) {
    	boolean stat = false;
    	Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
    	GenVehicleMasterDTO genVehicleMasterDTO = new GenVehicleMasterDTO();
    	
    	genVehicleMasterDTO.setOrgid(orgid);
    	//genVehicleMasterDTO.setVeEngSrno(EngineNo);
    	genVehicleMasterDTO.setVeChasisSrno(chasisno);
    	genVehicleMasterDTO.setVeRegNo(veReg);
    	genVehicleMasterDTO.setVeId(veId);
    	stat = vehicleMasterService.validateVehicle(genVehicleMasterDTO);
    	
    return stat;
    }
    
    

}
