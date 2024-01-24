package com.abm.mainet.swm.ui.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.swm.dto.MRFMasterDto;
import com.abm.mainet.swm.dto.TripSheetDTO;
import com.abm.mainet.swm.dto.VehicleMasterDTO;
import com.abm.mainet.swm.service.IBeatMasterService;
import com.abm.mainet.swm.service.IMRFMasterService;
import com.abm.mainet.swm.service.ITripSheetService;
import com.abm.mainet.swm.service.IVehicleMasterService;
import com.abm.mainet.swm.ui.model.TripSheetMasterModel;

@Controller
@RequestMapping("/TripSheetMaster.html")
public class TripSheetMasterController extends AbstractFormController<TripSheetMasterModel> {

	@Autowired
	private ITripSheetService tripSheetService;

	@Autowired
	private IVehicleMasterService vehicleMasterService;

	@Autowired
	private IMRFMasterService mRFMasterService;

	@Autowired
	private IBeatMasterService routeMasterService;

	@Autowired
	IFileUploadService fileUpload;

	@Autowired
	private IEmployeeService employeeService;

	@Autowired
	private IAttachDocsService attachDocsService;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		fileUpload.sessionCleanUpForFileUpload();
		this.bindModel(httpServletRequest);
		this.getModel().setCommonHelpDocs("TripSheetMaster.html");
		this.getModel().setVehicleMasterList(
				vehicleMasterService.searchVehicle(null, null, UserSession.getCurrent().getOrganisation().getOrgid()));
		this.getModel().setMrfMasterList(
				mRFMasterService.serchMrfCenter(null, null, UserSession.getCurrent().getOrganisation().getOrgid()));
		this.getModel().setTripSheetDtos(
				tripSheetService.search(null, null, null, UserSession.getCurrent().getOrganisation().getOrgid()));
		Map<Long, String> disposalMap = this.getModel().getMrfMasterList().stream()
				.collect(Collectors.toMap(MRFMasterDto::getMrfId, MRFMasterDto::getMrfPlantName));

		Map<Long, String> veheicleMap = this.getModel().getVehicleMasterList().stream()
				.collect(Collectors.toMap(VehicleMasterDTO::getVeId, VehicleMasterDTO::getVeNo));

		Map<Long, Long> veheicleMap1 = this.getModel().getVehicleMasterList().stream()
				.collect(Collectors.toMap(VehicleMasterDTO::getVeId, VehicleMasterDTO::getVeVetype));

		this.getModel().getTripSheetDtos().forEach(trip -> {
			trip.setDeName(disposalMap.get(trip.getDeId()));
			trip.setVeNo(veheicleMap.get(trip.getVeId()));
			if (veheicleMap1.get(trip.getVeId()) != null) {
				trip.setVeType(
						CommonMasterUtility.getCPDDescription(veheicleMap1.get(trip.getVeId()), MainetConstants.BLANK));
			}

		});

		return defaultResult();
	}

	@RequestMapping(method = { RequestMethod.POST }, params = MainetConstants.SolidWasteManagement.ADD_TRIP_SHEET)
	public ModelAndView addTripSheetMaster(final HttpServletRequest request) {

		this.getModel().setVehicleMasterList(vehicleMasterService.searchVehicleByVehicleTypeAndVehicleRegNo(null, null,
				UserSession.getCurrent().getOrganisation().getOrgid()));
		this.getModel().setMrfMasterList(
				mRFMasterService.serchMrfCenter(null, null, UserSession.getCurrent().getOrganisation().getOrgid()));
		this.getModel().setRoutelist(routeMasterService.serchRouteByRouteTypeAndRouteNo(null, null,
				UserSession.getCurrent().getOrganisation().getOrgid()));
		this.getModel()
				.setEmployeeList(employeeService.getAllEmployee(UserSession.getCurrent().getOrganisation().getOrgid()));
		this.getModel().setSaveMode(MainetConstants.Legal.SaveMode.CREATE);
		return new ModelAndView(MainetConstants.SolidWasteManagement.TRIP_SHEET_FORM, MainetConstants.FORM_NAME,
				this.getModel());
	}

	@RequestMapping(method = { RequestMethod.POST }, params = MainetConstants.SolidWasteManagement.EDIT_TRIP_SHEET)
	public ModelAndView editTripSheetMaster(@RequestParam Long id, final HttpServletRequest request) {

		setupTripSheet(id);
		this.getModel().setSaveMode(MainetConstants.Legal.SaveMode.EDIT);
		this.getModel().setRoutelist(routeMasterService.serchRouteByRouteTypeAndRouteNo(null, null,
				UserSession.getCurrent().getOrganisation().getOrgid()));
		this.getModel()
				.setEmployeeList(employeeService.getAllEmployee(UserSession.getCurrent().getOrganisation().getOrgid()));

		final List<AttachDocs> attachDocs = attachDocsService
				.findByCode(UserSession.getCurrent().getOrganisation().getOrgid(), "SWM_Tripsheet" + id.toString());
		this.getModel().setAttachDocsList(attachDocs);

		return new ModelAndView(MainetConstants.SolidWasteManagement.TRIP_SHEET_FORM, MainetConstants.FORM_NAME,
				this.getModel());
	}

	@RequestMapping(method = { RequestMethod.POST }, params = MainetConstants.SolidWasteManagement.VIEW_TRIP_SHEET)
	public ModelAndView viewTripSheetMaster(@RequestParam Long id, final HttpServletRequest request) {
		setupTripSheet(id);
		this.getModel().setSaveMode(MainetConstants.Legal.SaveMode.VIEW);
		this.getModel().setRoutelist(routeMasterService.serchRouteByRouteTypeAndRouteNo(null, null,
				UserSession.getCurrent().getOrganisation().getOrgid()));
		this.getModel()
				.setEmployeeList(employeeService.getAllEmployee(UserSession.getCurrent().getOrganisation().getOrgid()));
		final List<AttachDocs> attachDocs = attachDocsService
				.findByCode(UserSession.getCurrent().getOrganisation().getOrgid(), "SWM_Tripsheet" + id.toString());
		this.getModel().setAttachDocsList(attachDocs);
		return new ModelAndView(MainetConstants.SolidWasteManagement.TRIP_SHEET_FORM, MainetConstants.FORM_NAME,
				this.getModel());
	}

	@RequestMapping(method = { RequestMethod.POST }, params = MainetConstants.SolidWasteManagement.SEARCH_TRIP_SHEET)
	public ModelAndView searchTripSheetMaster(@RequestParam(required = false) Long id,
			@RequestParam(required = false) Long veType, @RequestParam(required = false) String fromDate,
			@RequestParam(required = false) String toDate, final HttpServletRequest request) {

		/*
		 * this.getModel().setVehicleMasterList(vehicleMasterService.
		 * searchVehicleByVehicleTypeAndVehicleRegNo(type, null,
		 * UserSession.getCurrent().getOrganisation().getOrgid()));
		 * this.getModel().setDisposalMasterList(disposalMasterService.
		 * serchDisposalSiteBySiteNumberAndSiteName(null, null,
		 * UserSession.getCurrent().getOrganisation().getOrgid()));
		 */

		// this.getModel().getTripSheetDto().setVeType(CommonMasterUtility.getCPDDescription(type,
		// "E"));
		this.getModel().getTripSheetDto().setFromDate(Utility.stringToDate(fromDate));
		this.getModel().getTripSheetDto().setToDate(Utility.stringToDate(toDate));
		this.getModel().getTripSheetDto().setNo(id);

		this.getModel().setTripSheetDtos(tripSheetService.search(id, Utility.stringToDate(fromDate),
				Utility.stringToDate(toDate), UserSession.getCurrent().getOrganisation().getOrgid()));

		Map<Long, String> disposalMap = this.getModel().getMrfMasterList().stream()
				.collect(Collectors.toMap(MRFMasterDto::getMrfId, MRFMasterDto::getMrfPlantName));
		Map<Long, String> veheicleMap = this.getModel().getVehicleMasterList().stream()
				.collect(Collectors.toMap(VehicleMasterDTO::getVeId, VehicleMasterDTO::getVeNo));
		Map<Long, Long> veheicleMap1 = this.getModel().getVehicleMasterList().stream()
				.collect(Collectors.toMap(VehicleMasterDTO::getVeId, VehicleMasterDTO::getVeVetype));
		List<TripSheetDTO> tripSheets = new ArrayList<>();
		this.getModel().getTripSheetDtos().forEach(trip -> {
			trip.setDeName(disposalMap.get(trip.getDeId()));
			trip.setVeNo(veheicleMap.get(trip.getVeId()));
			trip.setVeType(CommonMasterUtility.getCPDDescription(veheicleMap1.get(trip.getVeId()), "E"));
			if (veheicleMap1.get(trip.getVeId()).equals(veType)) {
				tripSheets.add(trip);
			}
		});
		if (id == null && fromDate.isEmpty() && toDate.isEmpty() && veType != null) {
			this.getModel().setTripSheetDtos(tripSheets);
		}

		return new ModelAndView(MainetConstants.SolidWasteManagement.TRIP_SHEET_SEARCH, MainetConstants.FORM_NAME,
				this.getModel());

	}

	/*
	 * @RequestMapping(params = MainetConstants.SolidWasteManagement.SWM_APPROVAL,
	 * method = RequestMethod.POST) public @ResponseBody String
	 * sendForApproval(final HttpServletRequest request,
	 * 
	 * @RequestParam(name = MainetConstants.WorkDefination.WORK_ID) final Long
	 * workId,
	 * 
	 * @RequestParam(name = MainetConstants.WorksManagement.MODE) final String mode)
	 * { bindModel(request); String flag = null; //
	 * this.getModel().setSaveMode(MainetConstants.SolidWasteManagement.SaveMode
	 * .SEARCH); this.getModel().setNewWorkCode(workDefinitionService.
	 * findAllWorkDefinitionById(workId).getWorkcode()); Long orgId =
	 * UserSession.getCurrent().getOrganisation().getOrgid();
	 * List<WorkDefinitionSancDetDto>
	 * detDtos=workDefinitionService.getAllSanctionDetailsByWorkId(workId);
	 * ServiceMaster sm = iServiceMasterService.getServiceMasterByShortCode("WOA",
	 * orgId); WorkflowMas workFlowMas =
	 * iWorkflowTyepResolverService.resolveServiceWorkflowType(orgId,
	 * detDtos.get(0).getServiceId(), sm.getSmServiceId(), null, null, null, null,
	 * null); if (workFlowMas != null) { flag =
	 * WorksWorkFlowService.initiateWorkFlowWorksService(this.getModel().
	 * prepareWorkFlowTaskAction(), workFlowMas.getWfId(),
	 * "WorkEstimateApproval.html", MainetConstants.FlagA);
	 * workDefinitionService.updateWorkDefinationMode(workId,
	 * MainetConstants.FlagP); } else { flag = MainetConstants.FlagN; } return flag;
	 * }
	 */

	private void setupTripSheet(Long id) {
		this.getModel().setVehicleMasterList(vehicleMasterService.searchVehicleByVehicleTypeAndVehicleRegNo(null, null,
				UserSession.getCurrent().getOrganisation().getOrgid()));
		this.getModel().setMrfMasterList(
				mRFMasterService.serchMrfCenter(null, null, UserSession.getCurrent().getOrganisation().getOrgid()));
		this.getModel().setTripSheetDto(tripSheetService.getById(id));
		TripSheetDTO tripDTO = tripSheetService.getById(id);
		tripDTO.setTripIntimeDesc(timeToStringConvert(tripDTO.getTripIntime()));
		if (tripDTO.getTripOuttime() != null) {
			tripDTO.setTripOuttimeDesc(timeToStringConvert(tripDTO.getTripOuttime()));
		}
		this.getModel().setTripSheetDto(tripDTO);
	}

	private String timeToStringConvert(Date date) {
		String dateString = null;
		DateFormat sdf = new SimpleDateFormat("HH:mm");
		dateString = sdf.format(date);
		return dateString;
	}

	@RequestMapping(method = { RequestMethod.POST }, params = "vehicleNo")
	public @ResponseBody Map<Long, String> serchVehicleNo(@RequestParam("id") Long vehicleTypeId,
			final HttpServletRequest httpServletRequest) {
		List<VehicleMasterDTO> result = vehicleMasterService.searchVehicleByVehicleTypeAndVehicleRegNo(vehicleTypeId,
				null, UserSession.getCurrent().getOrganisation().getOrgid());
		Map<Long, String> data = new HashMap<>();
		if (result != null && !result.isEmpty()) {
			result.forEach(vdata -> {
				data.put(vdata.getVeId(), vdata.getVeNo());
			});
		}
		return data;
	}

}
