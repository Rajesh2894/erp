package com.abm.mainet.vehiclemanagement.ui.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.integration.acccount.domain.AccountHeadSecondaryAccountCodeMasterEntity;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.service.IWorkflowTaskService;
import com.abm.mainet.vehiclemanagement.Constants.Constants;
import com.abm.mainet.vehiclemanagement.dto.GenVehicleMasterDTO;
import com.abm.mainet.vehiclemanagement.dto.SLRMEmployeeMasterDTO;
import com.abm.mainet.vehiclemanagement.dto.VehicleMaintenanceDTO;
import com.abm.mainet.vehiclemanagement.service.IGenVehicleMasterService;
import com.abm.mainet.vehiclemanagement.service.IOEMWarrantyService;
import com.abm.mainet.vehiclemanagement.service.ISLRMEmployeeMasterService;
import com.abm.mainet.vehiclemanagement.service.IVehicleMaintenanceService;
import com.abm.mainet.vehiclemanagement.ui.model.VehicleMaintenanceMOdel;

/**
 * @author Ajay.Kumar
 *
 */
@Controller
@RequestMapping(value = { "/vehicleMaintenanceMgmt.html", "/vehicleMaintenanceApproval.html", Constants.MAINT_INSP_APPR_URL })
public class VVehicleMaintenanceController extends AbstractFormController<VehicleMaintenanceMOdel> {

	@Autowired
	private IVehicleMaintenanceService vehicleMaintenanceService;
	@Autowired
	private IGenVehicleMasterService vehicleMasterService;
	@Autowired
	private IFileUploadService fileUpload;
	@Autowired
	private IAttachDocsService attachDocsService;
	@Resource
	private SecondaryheadMasterService secondaryheadMasterService;
	@Autowired
	private TbAcVendormasterService vendorService;
	@Autowired
	ISLRMEmployeeMasterService sLRMEmployeeMasterService;
	@Autowired
	ILocationMasService locationMasService;
	@Autowired
	private IWorkflowTaskService iWorkflowTaskService;
	@Autowired
	private IEmployeeService iEmployeeService;
	@Autowired
	private IOEMWarrantyService iOEMWarrantyService;
	
		
	private List<GenVehicleMasterDTO> getVehicleMasterList() {
		List<GenVehicleMasterDTO> vehicleMasterDTOList = vehicleMasterService.searchVehicleByVehicleTypeAndVehicleRegNo(
				null, null, null, null, UserSession.getCurrent().getOrganisation().getOrgid());
		Map<Long, String> locationMap = vehicleMasterDTOList.stream()
				.collect(Collectors.toMap(GenVehicleMasterDTO::getVeId, GenVehicleMasterDTO::getVeNo));
		if (CollectionUtils.isNotEmpty(this.getModel().getVehicleMaintenanceList())) {
			this.getModel().getVehicleMaintenanceList().forEach(master -> {
				master.setVeNo(locationMap.get(master.getVeId()));
			});
		}
		return vehicleMasterDTOList;
	}

	private void loadVendor() {
		final Long vendorStatus = CommonMasterUtility
				.getValueFromPrefixLookUp(AccountConstants.AC.getValue(), PrefixConstants.VSS).getLookUpId();
		this.getModel().setVendors(vendorService
				.getAllActiveVendors(UserSession.getCurrent().getOrganisation().getOrgid(), vendorStatus));
	}

	/**
	 * @param httpServletRequest
	 * @return
	 */
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		fileUpload.sessionCleanUpForFileUpload();
		this.getModel().setCommonHelpDocs("VehicleMaintenance.html");
		this.getModel().setVehicleMaintenanceList(vehicleMaintenanceService.searchVehicleMaintenance(null, null, null,
				null, UserSession.getCurrent().getOrganisation().getOrgid()));
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
	 * 
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
		 * CommonMasterUtility.getLevelData(MainetConstants.CommonMasterUi.TAC, 2, org);
		 * for (LookUp lookUp : taxMaslookUpList) { if
		 * (lookUp.getLookUpCode().equals(Constants.VMC)) { taxMasLookUpId =
		 * lookUp.getLookUpId(); break; } }
		 */
		/*
		 * if (taxMasLookUpId != null && org.getOrgid() != 81) { Map<Long, String>
		 * expenditureHead =
		 * secondaryheadMasterService.getTaxMasBillPaymentsAcHeadAllDetails(
		 * org.getOrgid(), taxMasLookUpId); for (Map.Entry<Long, String> entry :
		 * expenditureHead.entrySet()) {
		 * this.getModel().getVehicleMaintenanceDTO().setExpenditureId(entry.getKey());
		 * this.getModel().getVehicleMaintenanceDTO().setExpenditureHead(entry.getValue(
		 * )); } }
		 */
		// Get Deduction Account Head//
		/*
		 * Long taxMasLookUpIdded = null; List<LookUp> taxDedMaslookUpList =
		 * CommonMasterUtility.getLevelData(MainetConstants.CommonMasterUi.TAC, 1, org);
		 * for (LookUp lookUp : taxDedMaslookUpList) { if
		 * (lookUp.getLookUpCode().equals(MainetConstants.StandardAccountHeadMapping.TDS
		 * )) { taxMasLookUpIdded = lookUp.getLookUpId(); break; } }
		 */
		/*
		 * if (taxMasLookUpIdded != null && org.getOrgid() != 81) { Map<Long, String>
		 * expenditureHead1 =
		 * secondaryheadMasterService.getTaxMasBillDeductionAcHeadDescAllDetails(
		 * org.getOrgid(), taxMasLookUpIdded); VehicleMaintenanceDTO
		 * vehicleMaintenanceDTO = null; List<VehicleMaintenanceDTO>
		 * listVehicleMaintenanceDTO = new ArrayList<>(); for (Map.Entry<Long, String>
		 * entry : expenditureHead1.entrySet()) { vehicleMaintenanceDTO = new
		 * VehicleMaintenanceDTO();
		 * vehicleMaintenanceDTO.setDedAcHeadId(entry.getKey());
		 * vehicleMaintenanceDTO.setDedAcHead(entry.getValue());
		 * listVehicleMaintenanceDTO.add(vehicleMaintenanceDTO); }
		 * this.getModel().setVehicleMaintenanceList(listVehicleMaintenanceDTO); }
		 */
		this.getModel().setValueTypeList(CommonMasterUtility.getLookUps(MainetConstants.CommonMasterUi.VTY, org));
		loadVendor();
		setVendorDetails();
		this.getModel().getVendors();
		ModelAndView mv = new ModelAndView("addVehMaintenance/Form", MainetConstants.FORM_NAME, this.getModel());
		//this.getModel().setVehicles(getVehicleMasterList());
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL)) {
			List<SLRMEmployeeMasterDTO> employeeList = sLRMEmployeeMasterService.searchEmployeeList(null, null, null,
					UserSession.getCurrent().getOrganisation().getOrgid());
			this.getModel().setDrivers(employeeList);
		}
		return mv;
	}

	/**
	 * editVehicleMaintenance
	 * 
	 * @param request
	 * @param vemId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "editVehicleMaintenance", method = RequestMethod.POST)
	public ModelAndView editVehicleMaintenance(final HttpServletRequest request, @RequestParam Long vemId) {
		fileUpload.sessionCleanUpForFileUpload();
		this.getModel().setVehicleMaintenanceDTO(vehicleMaintenanceService.getVehicleMaintenance(vemId));
		ModelAndView mv = new ModelAndView("editVehMaintenance/Form", MainetConstants.FORM_NAME, this.getModel());
		mv.addObject("vehicles", getVehicleMasterList());
		final List<AttachDocs> attachDocs = attachDocsService.findByCode(
				UserSession.getCurrent().getOrganisation().getOrgid(),
				(Constants.SHORT_CODE + MainetConstants.WINDOWS_SLASH + vemId.toString()));
		this.getModel().setAttachDocsList(attachDocs);
		return mv;
	}

	@ResponseBody
	@RequestMapping(params = "deleteVehicleMaintenance", method = RequestMethod.POST)
	public ModelAndView deleteVehicleMaintenance(final HttpServletRequest request, @RequestParam Long vemId) {
		Employee emp = UserSession.getCurrent().getEmployee();
		vehicleMaintenanceService.deleteVehicleMaintenance(vemId, emp.getEmpId(), emp.getEmppiservername());
		sessionCleanup(request);
		this.getModel().setVehicleMaintenanceList(vehicleMaintenanceService.searchVehicleMaintenance(null, null, null,
				null, UserSession.getCurrent().getOrganisation().getOrgid()));
		ModelAndView mv = new ModelAndView("searchVehMaintenance", MainetConstants.FORM_NAME, this.getModel());
		mv.addObject("vehicles", getVehicleMasterList());
		return mv;
	}

	/**
	 * searchVehicleMaintenance
	 * 
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
	public ModelAndView searchVehicleMaintenance(final HttpServletRequest request,
			@RequestParam(required = false) Long vehType, @RequestParam(required = false) Long maintenanceType,
			@RequestParam(required = false) Date fromDate, @RequestParam(required = false) Date toDate, Long orgid) {
		sessionCleanup(request);
		this.getModel().getVehicleMaintenanceDTO().setVeVetype(vehType);
		this.getModel().getVehicleMaintenanceDTO().setVemMetype(maintenanceType);
		this.getModel().getVehicleMaintenanceDTO().setFromDate(fromDate);
		this.getModel().getVehicleMaintenanceDTO().setToDate(toDate);
		this.getModel().setVehicleMaintenanceList(vehicleMaintenanceService.searchVehicleMaintenance(vehType,
				maintenanceType, fromDate, toDate, UserSession.getCurrent().getOrganisation().getOrgid()));
		ModelAndView mv = new ModelAndView("searchVehMaintenance", MainetConstants.FORM_NAME, this.getModel());
		mv.addObject("vehicles", getVehicleMasterList());
		return mv;
	}

	@ResponseBody
	@RequestMapping(params = "viewVehicleMaintenance", method = RequestMethod.POST)
	public ModelAndView viewVehicleMaintenance(final HttpServletRequest request, @RequestParam Long vemId,
			Model uiModel) {
		fileUpload.sessionCleanUpForFileUpload();
		boolean isPsclEnv = Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(),
				MainetConstants.ENV_PSCL);
		this.getModel().setVehicleMaintenanceDTO(vehicleMaintenanceService.getVehicleMaintenance(vemId));
		ModelAndView mv = null;
		if (isPsclEnv) {
			this.getModel().setSaveMode("V");
			this.getModel()
					.setOemWarrantyDto(iOEMWarrantyService.findByrefNoAndOrgId(
							this.getModel().getVehicleMaintenanceDTO().getRequestNo(),
							UserSession.getCurrent().getOrganisation().getOrgid()));
			addListOnForm();
			String decision = vehicleMaintenanceService.getDecisionByAppId(this.getModel().getVehicleMaintenanceDTO().getRequestNo(), UserSession.getCurrent().getOrganisation().getOrgid());
			if (decision != null) {
				this.getModel().getVehicleMaintenanceDTO().setMaintInsAppdecision(decision);
			}			
			mv = new ModelAndView(returnViewByMaintCategory(), MainetConstants.FORM_NAME, this.getModel());
		} else {
			mv = new ModelAndView("viewVehMaintenance/Form", MainetConstants.FORM_NAME, this.getModel());
			mv.addObject("vehicles", getVehicleMasterList());
		}
		final List<AttachDocs> attachDocs = attachDocsService.findByCode(
				UserSession.getCurrent().getOrganisation().getOrgid(),
				(Constants.SHORT_CODE + MainetConstants.WINDOWS_SLASH + vemId.toString()));
		this.getModel().setAttachDocsList(attachDocs);
		return mv;
	}

	private void addListOnForm() {
		List<TbLocationMas> locations = locationMasService
				.fillAllActiveLocationByOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		this.getModel().setLocations(locations);
		List<Object[]> empList = iEmployeeService
				.findAllEmpIntialInfoByOrg(UserSession.getCurrent().getOrganisation().getOrgid());
		for (Object[] objects : empList) {
			String fullName = replaceNull(objects[0]) + MainetConstants.WHITE_SPACE + replaceNull(objects[1])
					+ MainetConstants.WHITE_SPACE + replaceNull(objects[2]);
			objects[0] = fullName;
		}
		this.getModel().setEmployees(empList);
		List<SLRMEmployeeMasterDTO> employeeList = sLRMEmployeeMasterService.searchEmployeeList(null, null, null,
				UserSession.getCurrent().getOrganisation().getOrgid());
		this.getModel().setDrivers(employeeList);
		this.getModel().setVehicles(getVehicleMasterList());
		loadVendor();
		List<AccountHeadSecondaryAccountCodeMasterEntity> budgetList  = ApplicationContextProvider.getApplicationContext().getBean(SecondaryheadMasterService.class)
		        .getSecondaryHeadcodesForWorks(UserSession.getCurrent().getOrganisation().getOrgid());
		this.getModel().setBudgetList(budgetList);
	}
	
	/**
	 * to get Form based on Maintenance Category
	 */
	private String returnViewByMaintCategory() {
		String maintCatLookupCode = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(this.getModel().getVehicleMaintenanceDTO().getMaintCategory(), UserSession.getCurrent().getOrganisation().getOrgid(), Constants.VEHICLE_MAINT_CATEGORY).getLookUpCode();
		String viewName = null;
		if(maintCatLookupCode.equalsIgnoreCase(Constants.MAINT_BY_WORKSHOP)){
			viewName = "addVehMaintenanceWorkShop/Form";
		}else if(maintCatLookupCode.equalsIgnoreCase(Constants.MAINT_BY_AMC)) {
				viewName= "addVehMaintenance/Form";
		}
		return viewName;
	}
	
	/**
	 * serchVehicleNo
	 * 
	 * @param vehicleTypeId
	 * @param httpServletRequest
	 * @return
	 */
	@RequestMapping(method = { RequestMethod.POST }, params = "getVehicleNo")
	public @ResponseBody Map<Long, String> serchVehicleNo(@RequestParam("id") Long vehicleTypeId,
			final HttpServletRequest httpServletRequest) {
		List<GenVehicleMasterDTO> result = vehicleMasterService.searchVehicleByVehicleTypeAndVehicleRegNo(vehicleTypeId,
				null, null, null, UserSession.getCurrent().getOrganisation().getOrgid());
		Map<Long, String> data = new HashMap<>();
			if (result != null && !result.isEmpty()) {
				result.forEach(vdata -> {
					data.put(vdata.getVeId(), vdata.getVeNo());
				});
			}
		return data;
	}
	
	@RequestMapping(method = { RequestMethod.POST }, params = "vehicleNo")
	public @ResponseBody Map<Long, String> serchVehicleNo(@RequestParam("id") Long vehicleTypeId,
			@RequestParam("date") Date date1, final HttpServletRequest httpServletRequest) {
		List<GenVehicleMasterDTO> result = vehicleMasterService.searchVehicleByVehicleTypeAndVehicleRegNo(vehicleTypeId,
				null, null, null, UserSession.getCurrent().getOrganisation().getOrgid());
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
	 * 
	 * @param model
	 * @param request
	 * @param vemId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "formForPrint", method = RequestMethod.POST)
	public ModelAndView formForPrint(final Model model, final HttpServletRequest request,
			@RequestParam final Long vemId) {
		getVehicleMasterList();
		this.getModel().setVehicleMaintenanceDTO(vehicleMaintenanceService.getVehicleMaintenance(vemId));
		ModelAndView mv = new ModelAndView("VehMaintenancePrint/Form", MainetConstants.FORM_NAME, this.getModel());
		VehicleMaintenanceDTO vehicleMaintenanceDTO = this.getModel().getVehicleMaintenanceDTO();
		mv.addObject("vehicleMaintenanceDTO", this.getModel().getVehicleMaintenanceDTO());
		Map<Long, String> locationMap = getVehicleMasterList().stream()
				.collect(Collectors.toMap(GenVehicleMasterDTO::getVeId, GenVehicleMasterDTO::getVeNo));
		vehicleMaintenanceDTO.setVeNo(locationMap.get(vehicleMaintenanceDTO.getVeId()));
		return mv;
	}

	/**
	 * lastMeter Reading
	 * 
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

	@RequestMapping(method = { RequestMethod.POST }, params = "saveData")
	public @ResponseBody Boolean lastMeterReading1(Long vehicleId, Date vemDate, Long vemDowntime, Long vemDowntimeunit,
			final HttpServletRequest httpServletRequest) {
		getModel().bind(httpServletRequest);
		String mode = null;
		if (getModel().getVehicleMaintenanceDTO().getVemId() != null) {
			mode = "E";
		}
		List<VehicleMaintenanceDTO> dto1 = vehicleMaintenanceService.searchVehicleMaintenanceData(vehicleId,
				UserSession.getCurrent().getOrganisation().getOrgid());
		List<VehicleMaintenanceDTO> dto = new ArrayList<VehicleMaintenanceDTO>();
		if (mode != null && mode.equals("E")) {
			dto1.forEach(entity -> {
				if (getModel().getVehicleMaintenanceDTO().getVemId() != entity.getVemId()) {
					dto.add(entity);
				}
			});
		} else {
			dto.addAll(dto1);
		}

		LookUp lookUp = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(vemDowntimeunit,
				UserSession.getCurrent().getOrganisation().getOrgid(), "UOM");
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(vemDate);
		if (lookUp.getLookUpCode().equals("DAYS")) {
			cal1.add(Calendar.DATE, vemDowntime.intValue());
		} else if (lookUp.getLookUpCode().equals("MON")) {
			cal1.add(Calendar.MONTH, vemDowntime.intValue());
		} else if (lookUp.getLookUpCode().equals("YRS")) {
			cal1.add(Calendar.YEAR, vemDowntime.intValue());
		} else if (lookUp.getLookUpCode().equals("HRS")) {
			cal1.add(Calendar.HOUR, vemDowntime.intValue());
		}
		Date date = cal1.getTime();
		for (int i = 0; i < dto.size(); i++) {
			LookUp lookUp1 = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(dto.get(i).getVemDowntimeunit(),
					UserSession.getCurrent().getOrganisation().getOrgid(), "UOM");
			Calendar cal = Calendar.getInstance();
			cal.setTime(dto.get(i).getVemDate());
			if (lookUp1.getLookUpCode().equals("DAYS")) {
				cal.add(Calendar.DATE, dto.get(i).getVemDowntime().intValue());
			} else if (lookUp1.getLookUpCode().equals("MON")) {
				cal.add(Calendar.MONTH, dto.get(i).getVemDowntime().intValue());
			} else if (lookUp1.getLookUpCode().equals("YRS")) {
				cal.add(Calendar.YEAR, dto.get(i).getVemDowntime().intValue());
			} else if (lookUp1.getLookUpCode().equals("HRS")) {
				cal.add(Calendar.HOUR, dto.get(i).getVemDowntime().intValue());
			}
			Date date1 = cal.getTime();
			String from = new SimpleDateFormat("dd/MM/yyyy").format((Date) vemDate);
			String from1 = new SimpleDateFormat("dd/MM/yyyy").format((Date) dto.get(i).getVemDate());
			String to1 = new SimpleDateFormat("dd/MM/yyyy").format((Date) date1);

			if (((vemDate.after(dto.get(i).getVemDate())) && (vemDate.before(date1)))
					|| ((date.after(dto.get(i).getVemDate())) && (date.before(date1)))
					|| ((dto.get(i).getVemDate().after(vemDate)) && (dto.get(i).getVemDate().before(date)))
					|| (((date1.after(vemDate))) && (date1.before(date)))) {
				return false;

			}
			if (from.equals(from1) || from.equals(to1)) {
				return false;
			}
		}
		return true;
	}


	private String replaceNull(Object name) {
		if (name == null) {
			name = MainetConstants.BLANK;
		}
		return name.toString();
	}

	@RequestMapping(params = "showDetails", method = RequestMethod.POST)
	public ModelAndView disposalFormApproval(@RequestParam("appNo") final String appNo,
			@RequestParam("taskId") final String taskId,
			@RequestParam(value = "actualTaskId", required = false) final Long actualTaskId,
			@RequestParam(value = "taskName", required = false) final String taskName,
			final HttpServletRequest httpServletRequest, final Model model) {
		this.sessionCleanup(httpServletRequest);
		fileUpload.sessionCleanUpForFileUpload();
		this.bindModel(httpServletRequest);
		return openTask(appNo, actualTaskId, model);
	}

	private ModelAndView openTask(final String appNo, final Long actualTaskId, final Model model) {
		VehicleMaintenanceMOdel vehicleMaintenanceMOdel = this.getModel();
		vehicleMaintenanceMOdel.setTaskId(actualTaskId);
		vehicleMaintenanceMOdel.getWorkflowActionDto().setReferenceId(appNo);
		vehicleMaintenanceMOdel.getWorkflowActionDto().setTaskId(actualTaskId);
		Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		VehicleMaintenanceDTO vehicleMaintenanceDTO = vehicleMaintenanceService.getDetailByVehNo(appNo, orgid);
		this.getModel().setVehicleMaintenanceDTO(vehicleMaintenanceDTO);
		getModel().setLevelcheck(iWorkflowTaskService.findByTaskId(actualTaskId).getCurentCheckerLevel());
		this.getModel().getVendors();
		this.getModel().setValueTypeList(CommonMasterUtility.getLookUps(MainetConstants.CommonMasterUi.VTY,
				UserSession.getCurrent().getOrganisation()));
		addListOnForm();
		final List<AttachDocs> attachDocs = attachDocsService.findByCode(
				UserSession.getCurrent().getOrganisation().getOrgid(),
				(Constants.SHORT_CODE + MainetConstants.WINDOWS_SLASH + vehicleMaintenanceDTO.getVemId()));
		this.getModel().setAttachDocsList(attachDocs);
		return new ModelAndView(returnViewByMaintCategory(), MainetConstants.FORM_NAME, this.getModel());
	}

	@ResponseBody
	@RequestMapping(params = "addSpareInfo", method = RequestMethod.POST)
	public ModelAndView addSpareInfo(final HttpServletRequest request, final Model model) {
		bindModel(request);
		this.getModel().getOemWarrantyDto().setMaintanceDate(this.getModel().getVehicleMaintenanceDTO().getVemDate());
		return new ModelAndView("OEMWarrantyMaintenanceForm", MainetConstants.FORM_NAME, this.getModel());
	}
	
	@ResponseBody
	@RequestMapping(params = "saveMaintenanceForm", method = RequestMethod.POST)
	public ModelAndView saveMaintenanceForm(final HttpServletRequest request, final Model model) throws Exception {
		bindModel(request);
		if(this.getModel().saveForm()) {
			 return jsonResult(JsonViewObject.successResult(this.getModel().getSuccessMessage()));
		}else {
			ModelAndView mv = null;		
        	this.getModel().setVehicles(getVehicleMasterListByVehicleType(this.getModel().getVehicleMaintenanceDTO().getVeVetype()));
			mv = new ModelAndView(returnViewByMaintCategory(), MainetConstants.FORM_NAME, this.getModel());
			mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
			return mv;
		}
	}
	
	/**
	 * this method gives list of selected type of vehicles 
	 */
	private List<GenVehicleMasterDTO> getVehicleMasterListByVehicleType(Long vehicleTypeId) {
		List<GenVehicleMasterDTO> result = vehicleMasterService.searchVehicleByVehicleTypeAndVehicleRegNo(vehicleTypeId,
				null, null, null, UserSession.getCurrent().getOrganisation().getOrgid());
		Map<Long, String> locationMap = result.stream()
				.collect(Collectors.toMap(GenVehicleMasterDTO::getVeId, GenVehicleMasterDTO::getVeNo));
		if (CollectionUtils.isNotEmpty(this.getModel().getVehicleMaintenanceList())) {
			this.getModel().getVehicleMaintenanceList().forEach(master -> {
				master.setVeNo(locationMap.get(master.getVeId()));
			});
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(params = "redirectToValidate", method = RequestMethod.POST)
	public ModelAndView redirectToValidate(final HttpServletRequest request, final Model model) {
		return new ModelAndView(returnViewByMaintCategory(), MainetConstants.FORM_NAME, this.getModel());
	}

	@ResponseBody
	@RequestMapping(params = "redirectToMain", method = RequestMethod.POST)
	public ModelAndView redirectToMaintenanceForm(final HttpServletRequest request, final Model model) {
		return new ModelAndView(returnViewByMaintCategory(), MainetConstants.FORM_NAME, this.getModel());
	}

	@ResponseBody
	@RequestMapping(params = "saveSpareform", method = RequestMethod.POST)
	public ModelAndView saveSpareInfo(final HttpServletRequest request, final Model mode) {
		bindModel(request);
		return new ModelAndView(returnViewByMaintCategory(), MainetConstants.FORM_NAME, this.getModel());
	}

	@ResponseBody
	@RequestMapping(method = { RequestMethod.POST }, params = "getMaintCategory")	
	public Map<String, Object> getMaintCategory(@RequestParam("vemId") Long vemId,final HttpServletRequest request){
		Map<String, Object> object = new LinkedHashMap<String, Object>();
		GenVehicleMasterDTO vehicleMasterDTO = vehicleMasterService.getVehicleByVehicleId(vemId);
	    if(vehicleMasterDTO.getVehMaintainBy() != null) {
	    	LookUp vehMaintByLookup = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(vehicleMasterDTO.getVehMaintainBy(), vehicleMasterDTO.getOrgid(), Constants.VEHICLE_MAINT_CATEGORY);
	 		Long vehMaintainByLookupID = vehMaintByLookup.getLookUpId();
	    	object.put("maintCategory", vehMaintainByLookupID);
	    	if(vehMaintByLookup.getLookUpCode().equalsIgnoreCase(Constants.MAINT_BY_AMC)) {
		    	object.put("vendorId", vehicleMasterDTO.getMaintVendorid());
		 	    this.getModel().getVehicleMaintenanceDTO().setVendorId(vehicleMasterDTO.getMaintVendorid());
		    }  
		    this.getModel().getVehicleMaintenanceDTO().setMaintCategory(vehMaintainByLookupID);
	    }
	 	return object;
	}


}
