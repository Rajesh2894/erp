package com.abm.mainet.swm.ui.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.master.dto.ContractMappingDTO;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.swm.dto.TripSheetDTO;
import com.abm.mainet.swm.dto.VehicleMasterDTO;
import com.abm.mainet.swm.service.ITripSheetService;
import com.abm.mainet.swm.service.IVehicleMasterService;
import com.abm.mainet.swm.service.IVendorContractMappingService;
import com.abm.mainet.swm.ui.model.TripSheetReportModel;

@Controller
@RequestMapping("/TripSheetReport.html")
public class TripSheetReportController extends AbstractFormController<TripSheetReportModel> {

	/**
	 * The IVehicleMaster Service
	 */
	@Autowired
	private IVehicleMasterService vehicleMasterService;

	/**
	 * The ITripSheet Service
	 */
	@Autowired
	private ITripSheetService tripSheetService;

	@Autowired
	private TbAcVendormasterService tbVendorMastServ;

	@Autowired
	private IVendorContractMappingService ivendorContMapServ;

	/**
	 * @param httpServletRequest
	 * @return
	 */
	@RequestMapping(method = { RequestMethod.POST })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		loadVehicle();
		setVehicleDetails();
		this.getModel().setCommonHelpDocs("TripSheetReport.html");
		return index();
	}

	private void setVehicleDetails() {
		Map<Long, String> locationMap = this.getModel().getVehicleMasterList().stream()
				.collect(Collectors.toMap(VehicleMasterDTO::getVeId, VehicleMasterDTO::getVeNo));
		this.getModel().getVehicleMasterList().forEach(master -> {
			master.setVeNo(locationMap.get(master.getVeId()));
		});
	}

	public void loadVehicle() {
		this.getModel().setVehicleMasterList(vehicleMasterService.searchVehicleByVehicleTypeAndVehicleRegNo(null, null,
				UserSession.getCurrent().getOrganisation().getOrgid()));
        //D#126877
		this.getModel().setVendorNameList(tbVendorMastServ
				.getVendorMasterData(UserSession.getCurrent().getOrganisation().getOrgid()).stream()
				.map(TbAcVendormaster::getVmVendorname).filter(vend -> vend != null).collect(Collectors.toList()));
		this.getModel().setContractNoList(ivendorContMapServ
				.getContractMappedByOrgId(UserSession.getCurrent().getOrganisation().getOrgid()).stream()
				.map(ContractMappingDTO::getContractNo).filter(cont -> cont != null).collect(Collectors.toList()));

	}

	/**
	 * @param model
	 * @param veVetype
	 * @return
	 */
	@RequestMapping(params = "getChequeNumbers")
	public @ResponseBody Map<Long, String> getChequeNumbers(final Model model,
			@RequestParam("veVetype") final Long veVetype) {
		Map<Long, String> chequeBookMap = null;
		List<VehicleMasterDTO> vehicleMasterList = vehicleMasterService.searchVehicleByVehicleTypeAndVehicleRegNo(null,
				null, UserSession.getCurrent().getOrganisation().getOrgid());
		chequeBookMap = new HashMap<>();
		if ((vehicleMasterList != null) && !vehicleMasterList.isEmpty()) {
			for (final VehicleMasterDTO en : vehicleMasterList) {
				chequeBookMap.put(en.getVeId(), en.getVeNo());
			}
		}
		return chequeBookMap;
	}

	/**
	 * Trip Sheet Summary
	 * 
	 * @param request
	 * @param veVetype
	 * @param veNo
	 * @param veRentFromdate
	 * @param veRentTodate
	 * @param reportType
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "Summary", method = RequestMethod.POST)
	public ModelAndView tripSheetSummary(final HttpServletRequest request, @RequestParam Long veVetype,
			@RequestParam Long veNo, @RequestParam("veRentFromdate") String veRentFromdate,
			@RequestParam("veRentTodate") String veRentTodate, @RequestParam("reportType") String reportType,
			@RequestParam("vendorName") String vendorName, @RequestParam("contractNo") String contractNo) {
		sessionCleanup(request);
		String redirectType = null;
		TripSheetReportModel tripSheetReportModel = this.getModel();
		VehicleMasterDTO vehicleMaster = new VehicleMasterDTO();
		vehicleMaster.setFromDate(veRentFromdate);
		vehicleMaster.setToDate(veRentTodate);
		vehicleMaster.setReporttype(reportType);
		tripSheetReportModel.setVehicleMasterDTO(vehicleMaster);
		Long OrgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Long veId = veNo;
		TripSheetDTO triplist = tripSheetService.findTripSheetReport(OrgId, veId, Utility.stringToDate(veRentFromdate),
				Utility.stringToDate(veRentTodate), veVetype,vendorName,contractNo);
		if (triplist != null) {
			tripSheetReportModel.getVehicleMasterDTO().setFlagMsg("Y");
			tripSheetReportModel.setTripSheetDTOList(triplist);
			redirectType = "TripSheetReportSummary";
		} else {
			tripSheetReportModel.getVehicleMasterDTO().setFlagMsg("N");
			tripSheetReportModel.setTripSheetDTOList(triplist);
			redirectType = "TripSheetReportList";
		}
		return new ModelAndView(redirectType, MainetConstants.FORM_NAME, tripSheetReportModel);
	}

	/**
	 * Trip Sheet Details
	 * 
	 * @param request
	 * @param veVetype
	 * @param veNo
	 * @param veRentFromdate
	 * @param veRentTodate
	 * @param reportType
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "Detail", method = RequestMethod.POST)
	public ModelAndView tripSheetDetails(final HttpServletRequest request, @RequestParam Long veVetype,
			@RequestParam Long veNo, @RequestParam("veRentFromdate") String veRentFromdate,
			@RequestParam("veRentTodate") String veRentTodate, @RequestParam("reportType") String reportType,
			@RequestParam("vendorName") String vendorName, @RequestParam("contractNo") String contractNo) {
		sessionCleanup(request);
		TripSheetReportModel tripSheetReportModel = this.getModel();
		VehicleMasterDTO vehicleMaster = new VehicleMasterDTO();
		String redirectType = null;
		vehicleMaster.setFromDate(veRentFromdate);
		vehicleMaster.setToDate(veRentTodate);
		vehicleMaster.setReporttype(reportType);
		tripSheetReportModel.setVehicleMasterDTO(vehicleMaster);
		Long OrgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Long veId = veNo;
		TripSheetDTO tripDeatilsdto = tripSheetService.findTripSheetReportDetails(OrgId, veId,
				Utility.stringToDate(veRentFromdate), Utility.stringToDate(veRentTodate), veVetype,vendorName,contractNo);
		if (tripDeatilsdto != null) {
			tripSheetReportModel.setTripSheetDTOList(tripDeatilsdto);
			tripSheetReportModel.getVehicleMasterDTO().setFlagMsg("Y");
			redirectType = "TripSheetReportDetails";
		} else {
			tripSheetReportModel.getVehicleMasterDTO().setFlagMsg("N");
			tripSheetReportModel.setTripSheetDTOList(tripDeatilsdto);
			redirectType = "TripSheetReportList";
		}
		return new ModelAndView(redirectType, MainetConstants.FORM_NAME, tripSheetReportModel);
	}

	/**
	 * serchVehicleNo
	 * 
	 * @param vehicleTypeId
	 * @param httpServletRequest
	 * @return
	 */
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
